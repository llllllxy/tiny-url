package org.tinycloud.tinyurl.common.utils.cipher;

import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import java.nio.charset.StandardCharsets;
import java.security.Security;
import java.util.Arrays;

/**
 * 国密3加密算法工具类
 * 消息摘要算法，同类型的有MD5和SHA256等，该算法已公开。校验结果为256位。
 * 经过在线网站验证 https://lzltool.cn/SM3，算法正确
 *
 * @author liuxingyu01
 * @since 2022-12-04 17:47
 **/
public class SM3Utils {
    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * 计算SM3摘要值
     *
     * @param srcData 原文byte字节数组
     * @return 摘要值，对于SM3算法来说是32字节
     */
    public static byte[] hash(byte[] srcData) {
        SM3Digest digest = new SM3Digest();
        digest.update(srcData, 0, srcData.length);
        byte[] hash = new byte[digest.getDigestSize()];
        digest.doFinal(hash, 0);
        return hash;
    }

    /**
     * 计算SM3摘要值
     *
     * @param content 原文字符串
     * @return 摘要值，对于SM3算法来说是32字节，转换成了16进制字符串
     */
    public static String hash(String content) {
        byte[] srcData = content.getBytes(StandardCharsets.UTF_8);
        return Hex.toHexString(hash(srcData));
    }

    /**
     * 计算SM3摘要值（+盐）
     *
     * @param srcData 原文
     * @param rand    盐值
     * @return 摘要值，对于SM3算法来说是32字节
     */
    public static byte[] hashWithRand(byte[] srcData, byte[] rand) {
        SM3Digest digest = new SM3Digest();
        digest.update(srcData, 0, srcData.length);
        digest.update(rand, 0, rand.length);
        byte[] hash = new byte[digest.getDigestSize()];
        digest.doFinal(hash, 0);
        return hash;
    }

    /**
     * 计算SM3摘要值（+盐）
     *
     * @param content 原文字符串
     * @param salt    盐值字符串
     * @return 摘要值，对于SM3算法来说是32字节，转换成了16进制字符串
     */
    public static String hashWithRand(String content, String salt) {
        byte[] srcData = content.getBytes(StandardCharsets.UTF_8);
        byte[] rand = salt.getBytes(StandardCharsets.UTF_8);
        return Hex.toHexString(hashWithRand(srcData, rand));
    }

    /**
     * 验证摘要
     *
     * @param srcData 原文
     * @param sm3Hash 摘要值
     * @return 返回true标识验证成功，false标识验证失败
     */
    public static boolean verify(byte[] srcData, byte[] sm3Hash) {
        byte[] newHash = hash(srcData);
        if (Arrays.equals(newHash, sm3Hash)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 验证摘要
     *
     * @param srcStr     原文
     * @param sm3HashStr 摘要值
     * @return 返回true标识验证成功，false标识验证失败
     */
    public static boolean verify(String srcStr, String sm3HashStr) {
        byte[] srcData = srcStr.getBytes(StandardCharsets.UTF_8);
        byte[] sm3Hash = Hex.decode(sm3HashStr);
        byte[] newHash = hash(srcData);
        if (Arrays.equals(newHash, sm3Hash)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 计算SM3 Mac值
     *
     * @param key     key值，可以是任意长度的字节数组
     * @param srcData 原文
     * @return Mac值，对于HMac-SM3来说是32字节
     */
    public static byte[] hmac(byte[] key, byte[] srcData) {
        KeyParameter keyParameter = new KeyParameter(key);
        SM3Digest digest = new SM3Digest();
        HMac mac = new HMac(digest);
        mac.init(keyParameter);
        mac.update(srcData, 0, srcData.length);
        byte[] result = new byte[mac.getMacSize()];
        mac.doFinal(result, 0);
        return result;
    }

    /**
     * 计算SM3 Mac值
     *
     * @param key     key值
     * @param content 原文
     * @return Mac值，对于HMac-SM3来说是32字节
     */
    public static String hmac(String key, String content) {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        byte[] srcData = content.getBytes(StandardCharsets.UTF_8);
        return Hex.toHexString(hmac(keyBytes, srcData));
    }

    /**
     * 测试
     *
     * @param args
     */
    public static void main(String[] args) {
        String str = "abcdefg";
        String result = hash(str);
        System.out.println(result);

        String key = "123456";
        String result2 = hmac(key, str);
        System.out.println(result2);
    }
}
