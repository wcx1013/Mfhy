package com.xzq.module_base.utils.cipher;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加密解密工具类
 *
 * @author Alex
 */
public class AESUtil {

    private final static String ALGORITHM = "AES";
    private final static String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private final static int SIZE = 256;


    /**
     * 加密
     *
     * @param key   密钥字节
     * @param clear 明文字节
     * @return 密文字节
     * @throws NoSuchAlgorithmException           异常
     * @throws NoSuchPaddingException             异常
     * @throws InvalidKeyException                异常
     * @throws InvalidAlgorithmParameterException 异常
     * @throws IllegalBlockSizeException          异常
     * @throws BadPaddingException                异常
     */
    public static byte[] encrypt(byte[] key, byte[] clear) throws
            NoSuchAlgorithmException,
            NoSuchPaddingException,
            InvalidKeyException,
            InvalidAlgorithmParameterException,
            IllegalBlockSizeException,
            BadPaddingException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, ALGORITHM);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, new IvParameterSpec(
                new byte[cipher.getBlockSize()]));
        return cipher.doFinal(clear);
    }

    /**
     * 解密
     *
     * @param key       密钥字节
     * @param encrypted 密文字节
     * @return 明文字节
     * @throws NoSuchAlgorithmException           异常
     * @throws NoSuchPaddingException             异常
     * @throws InvalidKeyException                异常
     * @throws InvalidAlgorithmParameterException 异常
     * @throws IllegalBlockSizeException          异常
     * @throws BadPaddingException                异常
     */
    public static byte[] decrypt(byte[] key, byte[] encrypted) throws
            NoSuchAlgorithmException,
            NoSuchPaddingException,
            InvalidKeyException,
            InvalidAlgorithmParameterException,
            IllegalBlockSizeException,
            BadPaddingException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, ALGORITHM);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(
                new byte[cipher.getBlockSize()]));
        return cipher.doFinal(encrypted);
    }

    /**
     * PBE口令密钥
     *
     * @param password 口令
     * @param salt     盐
     * @return 密钥字节
     * @throws Exception 异常
     */
    public static byte[] getPBEKey(char[] password, byte[] salt) throws Exception {
        return KeyUtil.getPBEKey(password, salt, SIZE);
    }
}