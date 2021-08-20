package com.xzq.module_base.utils.cipher;


import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;


/**
 * 密钥工具类
 * Created by Alex on 2016/4/29.
 */
class KeyUtil {

    private final static String SKF_ALGORITHM = "PBKDF2WithHmacSHA1";
    private final static int ITERATION = 2048;

    /**
     * PBE口令密钥
     *
     * @param algorithm      SecretKeyFactory算法
     * @param password       口令
     * @param salt           盐
     * @param iterationCount 迭代次数
     * @param size           密钥长度
     * @return 密钥字节
     * @throws NoSuchAlgorithmException 异常
     * @throws InvalidKeySpecException  异常
     */
    private static byte[] getPBEKey(String algorithm, char[] password, byte[] salt,
                                    int iterationCount, int size) throws
            NoSuchAlgorithmException,
            InvalidKeySpecException {
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(algorithm);
        PBEKeySpec keySpec = new PBEKeySpec(password, salt, iterationCount, size);
        SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);
        return secretKey.getEncoded();
    }

    /**
     * PBE口令密钥
     *
     * @param password 口令
     * @param salt     盐
     * @param size     密钥长度
     * @return 密钥字节
     * @throws NoSuchAlgorithmException 异常
     * @throws InvalidKeySpecException  异常
     */
    static byte[] getPBEKey(char[] password, byte[] salt, int size) throws
            NoSuchAlgorithmException,
            InvalidKeySpecException {
        return getPBEKey(SKF_ALGORITHM, password, salt, ITERATION, size);
    }
}
