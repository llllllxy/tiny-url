package org.tinycloud.tinyurl.common.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

/**
 * <p>
 * 生成租户AK和SK的工具类
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-11-30 15:36
 */
public class KeyGenUtils {


    /**
     * 生成字符串 access_key
     *
     * @return 生成的32位长度的16进制字符串
     */
    public static String generateAk() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 生成字符串 access_key_secret
     *
     * @return 生成随机字符串
     */
    public static String generateSk() {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "")
                + UUID.randomUUID().toString().replaceAll("-", "");
        byte[] by = uuid.getBytes(StandardCharsets.UTF_8);
        String safeBase64Str = Base64.getEncoder().encodeToString(by);
        safeBase64Str = safeBase64Str.replace('+', '_');
        safeBase64Str = safeBase64Str.replace('/', '_');
        safeBase64Str = safeBase64Str.replace("=", "");
        return safeBase64Str;
    }

    /**
     * 测试一下
     *
     * @param args 参数
     */
    public static void main(String[] args) {
        System.out.println("ak = " + generateAk());
        System.out.println("sk = " + generateSk());
    }
}
