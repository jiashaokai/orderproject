package com.order.util;


import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @Author: jiakun
 * @Date: 2020/10/23 16:53
 * @Description
 */
public class RSAUtil {

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大加文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    public static final String PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIJoK8ImjtICw7QIVPRuDDOusSHwJ9v89de7zRY9jn74iaL+FcMK34LRp8T90XZ2qbkkHh5dB23qh/8OBua6JaC3m8vz90zUbuXiqWanTRCaS7umV1SCR42gn+uKCz80zLQlNW2Z2Dk4CU1qyoD23PE/pEZGYDmrzVTPZpLf8hbVAgMBAAECgYAPRZnSS+ev6oj2vX8NfRHKv6uY2OIiHjbARAsZjq3warTbtLvhspVIYbsvE7aDFBR+TR+F2Mk08yFoGkFupeAgDmHremcxRt1cazaGtmC+isxrw7bhklM22vX/IF3doD3jP/l8eOwwOseRDvpDAd2r85LLrlyoaxG+1lrS0JqaZQJBAMHOE3X9tXRe2nOcYSh2Tgf9t+QkGCytDm/oAIFVRulEAaGCZkHYnE3Qybsr5I9PC9LQj8wxQC8q0T6Q8WfB1VsCQQCsQarfWW5YvJS0OydrthMjoki7eWe48SWxX7Dq27pibofTF9iZhOUHlHrLoIvKGIafM5mesirT/HnBi09eUwuPAkBKjDNOKpY2uhm7aMayorcnOcAXgI7P+I1aHFq9ZQkOxzr9XkRpRaHyCMLgPWUAfv5kTBdUCwnOWuD7+RLyv0uLAkEAnq2jTTnhx4Z4aYpwZu2DtfihjWlp3djugvWGUOL2UARHxHIcCld8bz4c3AVZoGJ1aKQs6B5yC3NxaZP+GgLgCwJAdV34m0mOzpy1LdZYI+aLwJRoTufA/7L+Pe4Za3JRvMDQVwh/KSeTSivPaTMQeJbVekm///Mf7toC2X8mxLahIQ==";

    public static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCCaCvCJo7SAsO0CFT0bgwzrrEh8Cfb/PXXu80WPY5++Imi/hXDCt+C0afE/dF2dqm5JB4eXQdt6of/DgbmuiWgt5vL8/dM1G7l4qlmp00Qmku7pldUgkeNoJ/rigs/NMy0JTVtmdg5OAlNasqA9tzxP6RGRmA5q81Uz2aS3/IW1QIDAQAB";

    /**
     * 获取公钥
     *
     * @param publicKey 公钥字符串
     * @return
     */
    public static PublicKey getPublicKey(String publicKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] decodedKey = Base64.decodeBase64(publicKey.getBytes());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * 获取密匙
     *
     * @param privateKey
     * @return
     */
    public static PrivateKey getPrivateKey(String privateKey) throws InvalidKeySpecException, NoSuchAlgorithmException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] decodedKey = Base64.decodeBase64(privateKey.getBytes());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * RSA加密
     *
     * @param data 待加密数据
     * @return
     */
    public static String encrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(PUBLIC_KEY));
            int inputLen = data.getBytes().length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offset = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offset > 0) {
                if (inputLen - offset > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(data.getBytes(), offset, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data.getBytes(), offset, inputLen - offset);
                }
                out.write(cache, 0, cache.length);
                i++;
                offset = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] encryptedData = out.toByteArray();
            out.close();
            // 获取加密内容使用base64进行编码,并以UTF-8为标准转化成字符串
            // 加密后的字符串
            return new String(Base64.encodeBase64String(encryptedData));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * RSA解密
     *
     * @param data 待解密数据
     * @return
     */
    public static String decrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, getPrivateKey(PRIVATE_KEY));
            byte[] dataBytes = Base64.decodeBase64(data);
            int inputLen = dataBytes.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offset = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while (inputLen - offset > 0) {
                if (inputLen - offset > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(dataBytes, offset, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(dataBytes, offset, inputLen - offset);
                }
                out.write(cache, 0, cache.length);
                i++;
                offset = i * MAX_DECRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();
            // 解密后的内容
            return new String(decryptedData, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 签名
     *
     * @param data 待签名数据
     * @return 签名
     */
    public static String sign(String data) throws Exception {
        byte[] keyBytes = getPrivateKey(PRIVATE_KEY).getEncoded();
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey key = keyFactory.generatePrivate(keySpec);
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initSign(key);
        signature.update(data.getBytes());
        return new String(Base64.encodeBase64(signature.sign()));
    }

    /**
     * 验签
     *
     * @param srcData   原始字符串
     * @param publicKey 公钥
     * @param sign      签名
     * @return 是否验签通过
     */
    public static boolean verify(String srcData, PublicKey publicKey, String sign) throws Exception {
        byte[] keyBytes = getPublicKey(PUBLIC_KEY).getEncoded();
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey key = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initVerify(key);
        signature.update(srcData.getBytes());
        return signature.verify(Base64.decodeBase64(sign.getBytes()));
    }
}
