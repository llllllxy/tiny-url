package org.tinycloud.tinyurl.function.restful.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.tinycloud.tinyurl.common.config.ApplicationConfig;
import org.tinycloud.tinyurl.common.constant.GlobalConstant;
import org.tinycloud.tinyurl.common.enums.RestfulErrorCode;
import org.tinycloud.tinyurl.common.exception.RestfulException;
import org.tinycloud.tinyurl.common.utils.IpGetUtils;
import org.tinycloud.tinyurl.common.utils.JsonUtils;
import org.tinycloud.tinyurl.common.utils.StrUtils;
import org.tinycloud.tinyurl.common.utils.cipher.SM3Utils;
import org.tinycloud.tinyurl.function.restful.bean.dto.AuthenticationDto;
import org.tinycloud.tinyurl.function.restful.bean.dto.SignatureDto;
import org.tinycloud.tinyurl.function.tenant.bean.entity.TTenant;
import org.tinycloud.tinyurl.function.tenant.mapper.TenantMapper;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * </p>
 *
 * @author liuxingyu01
 * @since 2024-02-27 14:14
 */
@Service
public class ApiAuthService {

    @Autowired
    private TenantMapper tenantMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ApplicationConfig applicationConfig;

    public String authCode() {
        String authCode = UUID.randomUUID().toString().replace("-", "");
        // 缓存redis里60秒，表示有效期
        this.stringRedisTemplate.opsForValue().set(GlobalConstant.TENANT_RESTFUL_AUTHCODE_REDIS_KEY + authCode,
                authCode, 60, TimeUnit.SECONDS);
        return authCode;
    }

    public String signature(SignatureDto dto) {
        return SM3Utils.hmac(dto.getAuthCode(), dto.getAccessKeySecret());
    }

    public String authentication(AuthenticationDto dto, HttpServletRequest request) {
        String accessKey = dto.getAccessKey();
        String authCode = dto.getAuthCode();
        String signature = dto.getSignature();

        // 第一步、校验authCode是否存在
        boolean hasKey = this.stringRedisTemplate.hasKey(GlobalConstant.TENANT_RESTFUL_AUTHCODE_REDIS_KEY + authCode);
        if (!hasKey) {
            throw new RestfulException(RestfulErrorCode.AUTHCODE_NOT_EXIST_OR_EXPIRED);
        }

        // 第二步、校验accessKey是否存在
        TTenant entity = this.tenantMapper.selectOne(
                Wrappers.<TTenant>lambdaQuery().eq(TTenant::getAccessKey, accessKey)
                        .eq(TTenant::getDelFlag, GlobalConstant.NOT_DELETED));
        if (Objects.isNull(entity)) {
            throw new RestfulException(RestfulErrorCode.AK_NOT_EXIST);
        }

        // 第三步、判断ak是否到期，租户是否启用
        Date now = new Date();
        if (entity.getExpireTime().before(now)) {
            throw new RestfulException(RestfulErrorCode.AK_IS_EXPIRED);
        }
        if (entity.getStatus().equals(GlobalConstant.DISABLED)) {
            throw new RestfulException(RestfulErrorCode.TENANT_IS_DISABLED);
        }

        // 第四步、签名校验
        String accessKeySecret = entity.getAccessKeySecret();
        String nowSignature = SM3Utils.hmac(authCode, accessKeySecret);
        if (!signature.equals(nowSignature)) {
            throw new RestfulException(RestfulErrorCode.SIGNATURE_CHECK_FAILED);
        }

        // 第五步，白名单校验
        Integer checkIpFlag = entity.getCheckIpFlag();
        if (checkIpFlag == 1) {
            String ipWhitelist = entity.getIpWhitelist();
            if (StrUtils.isNotBlank(ipWhitelist)) {
                if (!ipWhitelist.contains(IpGetUtils.getIpAddr(request))) {
                    throw new RestfulException(RestfulErrorCode.IP_IS_NOT_IN_WHITELIST);
                }
            }
        }

        String token = "tinyurl_" + UUID.randomUUID().toString().replace("-", "");
        // 缓存redis里60秒，表示有效期
        entity.setTenantPassword(null);
        this.stringRedisTemplate.opsForValue().set(GlobalConstant.TENANT_RESTFUL_TOKEN_REDIS_KEY + token,
                JsonUtils.toJsonString(entity), applicationConfig.getApiAuthTimeout(), TimeUnit.SECONDS);
        return token;
    }
}
