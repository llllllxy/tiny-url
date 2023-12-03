package org.tinycloud.tinyurl.common.utils.cipher;

import org.bouncycastle.asn1.gm.GMNamedCurves;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.bouncycastle.crypto.params.*;
import org.bouncycastle.crypto.signers.SM2Signer;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;


/**
 * SM2加解密工具类（密文数据顺序使用的新标准(C1 C3 C2)）
 * 为非对称加密，基于ECC。该算法已公开。由于该算法基于ECC，故其签名速度与秘钥生成速度都快于RSA
 * ECC 256位（SM2采用的就是ECC 256位的一种）安全强度比RSA 2048位高，但运算速度快于RSA
 * 经过在线网站验证 https://lzltool.cn/SM2 算法正确
 *
 * @author liuxingyu01
 * @since 2022-12-03-16:47
 **/
public class SM2Utils {
    private static final Logger log = LoggerFactory.getLogger(SM2Utils.class);

    /**
     * 生成SM2公私钥对
     *
     * @return AsymmetricCipherKeyPair
     */
    private static AsymmetricCipherKeyPair genKeyPair0() {
        // 1.获取一条SM2曲线参数
        X9ECParameters sm2ECParameters = GMNamedCurves.getByName("sm2p256v1");
        // 2.构造domain参数
        ECDomainParameters domainParameters = new ECDomainParameters(sm2ECParameters.getCurve(),
                sm2ECParameters.getG(), sm2ECParameters.getN());
        // 3.创建密钥生成器
        ECKeyPairGenerator keyPairGenerator = new ECKeyPairGenerator();
        // 4.初始化生成器,带上随机数
        try {
            keyPairGenerator.init(new ECKeyGenerationParameters(domainParameters, SecureRandom.getInstance("SHA1PRNG")));
        } catch (NoSuchAlgorithmException e) {
            log.error("生成公私钥对时出现异常:", e);
        }
        // 3.生成密钥对
        AsymmetricCipherKeyPair asymmetricCipherKeyPair = keyPairGenerator.generateKeyPair();
        return asymmetricCipherKeyPair;
    }

    /**
     * 生成公私钥对(默认不压缩公钥，加密解密都使用BC库才能使用压缩)
     *
     * @return 公钥私钥键值对Map，格式是16进制hex格式的
     */
    public static Map<String, String> genKeyPair() {
        return genKeyPair(false);
    }

    /**
     * 生成公私钥对
     *
     * @param compressedPubKey 是否压缩公钥，加密解密都使用BC库才能使用压缩
     * @return 公钥私钥键值对Map，格式是16进制hex格式的
     */
    public static Map<String, String> genKeyPair(boolean compressedPubKey) {
        AsymmetricCipherKeyPair asymmetricCipherKeyPair = genKeyPair0();
        // 提取公钥点
        ECPoint ecPoint = ((ECPublicKeyParameters) asymmetricCipherKeyPair.getPublic()).getQ();
        // 公钥前面的02或者03表示是压缩公钥,04表示未压缩公钥,04的时候,可以去掉前面的04
        String pubKey = Hex.toHexString(ecPoint.getEncoded(compressedPubKey));
        BigInteger privateKey = ((ECPrivateKeyParameters) asymmetricCipherKeyPair.getPrivate()).getD();
        String priKey = privateKey.toString(16);

        return new HashMap<String, String>() {{
            put("priKey", priKey);
            put("pubKey", pubKey);
        }};
    }

    /**
     * 私钥签名
     *
     * @param privateKey 私钥
     * @param content    待签名内容
     * @return 签名
     */
    public static String sign(String privateKey, String content) throws CryptoException {
        // 待签名内容转为字节数组
        byte[] message = Hex.decode(content);

        // 获取一条SM2曲线参数
        X9ECParameters sm2ECParameters = GMNamedCurves.getByName("sm2p256v1");
        // 构造domain参数
        ECDomainParameters domainParameters = new ECDomainParameters(sm2ECParameters.getCurve(),
                sm2ECParameters.getG(), sm2ECParameters.getN());

        BigInteger privateKeyD = new BigInteger(privateKey, 16);
        ECPrivateKeyParameters privateKeyParameters = new ECPrivateKeyParameters(privateKeyD, domainParameters);

        // 创建签名实例
        SM2Signer sm2Signer = new SM2Signer();

        // 初始化签名实例,带上ID,国密的要求,ID默认值:1234567812345678
        try {
            sm2Signer.init(true, new ParametersWithID(new ParametersWithRandom(privateKeyParameters, SecureRandom.getInstance("SHA1PRNG")), Strings.toByteArray("1234567812345678")));
        } catch (NoSuchAlgorithmException e) {
            log.error("签名时出现异常:", e);
        }
        sm2Signer.update(message, 0, message.length);
        // 生成签名,签名分为两部分r和s,分别对应索引0和1的数组
        byte[] signBytes = sm2Signer.generateSignature();

        String sign = Hex.toHexString(signBytes);

        return sign;
    }

    /**
     * 公钥签名验证
     *
     * @param publicKey 公钥
     * @param content   待签名内容
     * @param sign      签名值
     * @return 验签结果: true or false
     */
    public static boolean verify(String publicKey, String content, String sign) {
        // 非压缩模式公钥对接放是128位HEX秘钥，需要为BC库加上“04”标记
        if (publicKey.length() == 128) {
            publicKey = "04" + publicKey;
        }

        // 待签名内容
        byte[] message = Hex.decode(content);
        byte[] signData = Hex.decode(sign);

        // 获取一条SM2曲线参数
        X9ECParameters sm2ECParameters = GMNamedCurves.getByName("sm2p256v1");
        // 构造domain参数
        ECDomainParameters domainParameters = new ECDomainParameters(sm2ECParameters.getCurve(),
                sm2ECParameters.getG(),
                sm2ECParameters.getN());
        // 提取公钥点
        ECPoint pukPoint = sm2ECParameters.getCurve().decodePoint(Hex.decode(publicKey));
        // 公钥前面的02或者03表示是压缩公钥，04表示未压缩公钥, 04的时候，可以去掉前面的04
        ECPublicKeyParameters publicKeyParameters = new ECPublicKeyParameters(pukPoint, domainParameters);
        // 创建签名实例
        SM2Signer sm2Signer = new SM2Signer();
        ParametersWithID parametersWithID = new ParametersWithID(publicKeyParameters, Strings.toByteArray("1234567812345678"));
        sm2Signer.init(false, parametersWithID);
        sm2Signer.update(message, 0, message.length);
        // 验证签名结果
        boolean verify = sm2Signer.verifySignature(signData);
        return verify;
    }

    /**
     * SM2加密算法（公钥加密）
     *
     * @param publicKey 公钥
     * @param data      待加密的数据
     * @return 结果密文，BC库产生的密文带由04标识符，与非BC库对接时需要去掉开头的04
     */
    public static String encrypt(String publicKey, String data) {
        // 非压缩模式公钥对接放是128位HEX秘钥，需要为BC库加上“04”标记
        if (publicKey.length() == 128) {
            publicKey = "04" + publicKey;
        }
        // 获取一条SM2曲线参数
        X9ECParameters sm2ECParameters = GMNamedCurves.getByName("sm2p256v1");
        // 构造domain参数
        ECDomainParameters domainParameters = new ECDomainParameters(sm2ECParameters.getCurve(),
                sm2ECParameters.getG(),
                sm2ECParameters.getN());
        // 提取公钥点
        ECPoint pukPoint = sm2ECParameters.getCurve().decodePoint(Hex.decode(publicKey));
        // 公钥前面的02或者03表示是压缩公钥，04表示未压缩公钥, 04的时候，可以去掉前面的04
        ECPublicKeyParameters publicKeyParameters = new ECPublicKeyParameters(pukPoint, domainParameters);

        SM2Engine sm2Engine = new SM2Engine(SM2Engine.Mode.C1C3C2);
        sm2Engine.init(true, new ParametersWithRandom(publicKeyParameters, new SecureRandom()));

        byte[] arrayOfBytes = null;
        try {
            byte[] in = data.getBytes(StandardCharsets.UTF_8);
            arrayOfBytes = sm2Engine.processBlock(in, 0, in.length);
            return Hex.toHexString(arrayOfBytes);
        } catch (Exception e) {
            log.error("SM2加密时出现异常: ", e);
            return null;
        }
    }


    /**
     * SM2解密算法（私钥解密）
     *
     * @param privateKey 私钥
     * @param cipherData 密文数据
     * @return 解密结果
     */
    public static String decrypt(String privateKey, String cipherData) {
        // 使用BC库加解密时密文以04开头，传入的密文前面没有04则补上
        if (!cipherData.startsWith("04")) {
            cipherData = "04" + cipherData;
        }
        byte[] cipherDataByte = Hex.decode(cipherData);

        // 获取一条SM2曲线参数
        X9ECParameters sm2ECParameters = GMNamedCurves.getByName("sm2p256v1");
        // 构造domain参数
        ECDomainParameters domainParameters = new ECDomainParameters(sm2ECParameters.getCurve(),
                sm2ECParameters.getG(), sm2ECParameters.getN());

        BigInteger privateKeyD = new BigInteger(privateKey, 16);
        ECPrivateKeyParameters privateKeyParameters = new ECPrivateKeyParameters(privateKeyD, domainParameters);

        SM2Engine sm2Engine = new SM2Engine(SM2Engine.Mode.C1C3C2);
        // 设置sm2为解密模式
        sm2Engine.init(false, privateKeyParameters);

        try {
            byte[] arrayOfBytes = sm2Engine.processBlock(cipherDataByte, 0, cipherDataByte.length);
            return new String(arrayOfBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("SM2解密时出现异常:", e);
            return null;
        }
    }


    public static void main(String[] args) {
        Map<String, String> smKeyPair = genKeyPair(false);

        String priKey = smKeyPair.get("priKey");
        System.out.println("私钥：" + priKey);

        String pubKey = smKeyPair.get("pubKey");
        System.out.println("公钥：" + pubKey);


        // 明文
        String text = "我是小可爱";
        System.out.println("明文文本：" + text);


        // 1、签名验签测试
        String sign = "";
        try {
            sign = sign(priKey, Hex.toHexString(text.getBytes()));
        } catch (CryptoException e) {
            e.printStackTrace();
        }
        System.out.println("生成签名：" + sign);
        boolean verify = verify(pubKey, Hex.toHexString(text.getBytes()), sign);
        System.out.println("验签结果：" + verify);


        // 2、加解密测试，公钥加密，私钥解密
        String encryptData = encrypt(pubKey, text);
        System.out.println("加密结果：" + encryptData);

        String decryptData = decrypt(priKey, encryptData);
        System.out.println("解密结果：" + decryptData);
    }

}
