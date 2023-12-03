package org.tinycloud.tinyurl.function.tenant.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.tinycloud.tinyurl.common.constant.GlobalConstant;
import org.tinycloud.tinyurl.common.model.ApiResult;
import org.tinycloud.tinyurl.common.utils.CaptchaCodeGen;
import org.tinycloud.tinyurl.common.utils.cipher.SM2Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 租户会话管理-控制器
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-12-2023/12/3 21:03
 */
@Slf4j
@RestController
@RequestMapping("/tenant/auth")
public class TenantAuthController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @GetMapping("/getCode")
    @ResponseBody
    public ApiResult<Object> getCode() {
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

        // 存入redis 60秒
        stringRedisTemplate.opsForValue().set(codeRedisKey, String.join("&", code, privateKey), 60, TimeUnit.SECONDS);

        // 验证码图片的bae64和rsa公钥返回给前端
        Map<String, String> result = new HashMap<>();
        result.put("uuid", uuid);
        result.put("img", base64);
        result.put("publicKey", publicKey);
        return ApiResult.success(result, "获取验证码成功");
    }

}
