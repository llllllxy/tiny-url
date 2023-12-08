package org.tinycloud.tinyurl.function.tenant.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.tinycloud.tinyurl.common.config.ApplicationConfig;
import org.tinycloud.tinyurl.common.config.interceptor.TenantAuthUtil;
import org.tinycloud.tinyurl.common.constant.GlobalConstant;
import org.tinycloud.tinyurl.common.enums.TenantErrorCode;
import org.tinycloud.tinyurl.common.exception.TenantException;
import org.tinycloud.tinyurl.common.utils.CaptchaCodeGen;
import org.tinycloud.tinyurl.common.utils.JsonUtils;
import org.tinycloud.tinyurl.common.utils.StrUtils;
import org.tinycloud.tinyurl.common.utils.cipher.SM2Utils;
import org.tinycloud.tinyurl.common.utils.cipher.SM3Utils;
import org.tinycloud.tinyurl.function.tenant.bean.dto.TenantLoginDto;
import org.tinycloud.tinyurl.function.tenant.bean.entity.TTenant;
import org.tinycloud.tinyurl.function.tenant.bean.vo.TenantCaptchaCodeVo;
import org.tinycloud.tinyurl.function.tenant.mapper.TenantMapper;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-12-05 15:01
 */
@Slf4j
@Service
public class TenantAuthService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ApplicationConfig applicationConfig;

    @Autowired
    private TenantMapper tenantMapper;

    public TenantCaptchaCodeVo getCode() {
        // 保存验证码信息，生成验证key
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");

        String codeRedisKey = GlobalConstant.TENANT_CAPTCHA_CODE_REDIS_KEY + uuid;

        // 生成验证码图片，并返回base64编码
        CaptchaCodeGen captchaCode = new CaptchaCodeGen(120, 38, 4, 10);
        String base64 = captchaCode.getBase64();
        // 生成4位随机数，作为验证码图片里的数
        String code = captchaCode.getCode();

        Map<String, String> rsaKey = SM2Utils.genKeyPair();
        String publicKey = rsaKey.get("pubKey");
        String privateKey = rsaKey.get("priKey");

        // 将验证码和私钥，存入redis 60秒
        this.stringRedisTemplate.opsForValue().set(codeRedisKey, String.join("&", code, privateKey), 60, TimeUnit.SECONDS);

        // 验证码图片的bae64和rsa公钥返回给前端
        TenantCaptchaCodeVo vo = new TenantCaptchaCodeVo();
        vo.setImg(base64);
        vo.setUuid(uuid);
        vo.setPublicKey(publicKey);
        return vo;
    }


    public String login(TenantLoginDto dto) {
        String username = dto.getUsername();
        String password = dto.getPassword();
        String captcha = dto.getCaptcha();
        String uuid = dto.getUuid();

        String codeRedisKey = GlobalConstant.TENANT_CAPTCHA_CODE_REDIS_KEY + uuid;
        String redisCache = this.stringRedisTemplate.opsForValue().get(codeRedisKey);
        if (StrUtils.isEmpty(redisCache)) {
            throw new TenantException(TenantErrorCode.CAPTCHA_IS_MISMATCH);
        }
        String code = redisCache.split("&")[0];
        String privateKey = redisCache.split("&")[1];
        if (!captcha.equalsIgnoreCase(code)) {
            throw new TenantException(TenantErrorCode.CAPTCHA_IS_MISMATCH);
        }
        TTenant entity = this.tenantMapper.selectOne(
                Wrappers.<TTenant>lambdaQuery().eq(TTenant::getTenantAccount, username)
                        .eq(TTenant::getDelFlag, GlobalConstant.NOT_DELETED));
        if (Objects.isNull(entity)) {
            throw new TenantException(TenantErrorCode.TENANT_USERNAME_OR_PASSWORD_MISMATCH);
        }
        if (GlobalConstant.DISABLED.equals(entity.getStatus())) {
            throw new TenantException(TenantErrorCode.TENANT_IS_DISABLE);
        }
        String passwordDecrypt = SM2Utils.decrypt(privateKey, password);
        if (StrUtils.isEmpty(passwordDecrypt)) {
            throw new TenantException(TenantErrorCode.CAPTCHA_IS_MISMATCH);
        }
        String passwordDecryptHash = SM3Utils.hash(passwordDecrypt);
        if (!entity.getTenantPassword().equals(passwordDecryptHash)) {
            throw new TenantException(TenantErrorCode.TENANT_USERNAME_OR_PASSWORD_MISMATCH);
        }

        String token = "tinyurl_" + UUID.randomUUID().toString().trim().replaceAll("-", "");
        // 将验证码和私钥，存入redis 60秒
        entity.setTenantPassword(null);
        this.stringRedisTemplate.opsForValue().set(GlobalConstant.TENANT_TOKEN_REDIS_KEY + token, JsonUtils.toJsonString(entity), applicationConfig.getTenantAuthTimeout(), TimeUnit.SECONDS);

        return token;
    }


    public Boolean logout(HttpServletRequest request) {
        String token = TenantAuthUtil.getToken(request);
        this.stringRedisTemplate.delete(GlobalConstant.TENANT_TOKEN_REDIS_KEY + token);
        return true;
    }

}
