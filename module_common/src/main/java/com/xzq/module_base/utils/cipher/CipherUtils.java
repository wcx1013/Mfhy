package com.xzq.module_base.utils.cipher;

import android.text.TextUtils;
import android.util.Base64;

import com.xzq.module_base.utils.EntitySerializerUtils;


/**
 * 加密工具
 * Created by Alex on 2017/1/25.
 */

public class CipherUtils {

    public static String encryptAES(String password, String salt, Object data) {
        byte[] cipher;
        byte[] key;
        try {
            key = AESUtil.getPBEKey(password.toCharArray(), salt.getBytes());
        } catch (Exception e) {
            return null;
        }
        try {
            String json = EntitySerializerUtils.serializerEntity(data);
            cipher = AESUtil.encrypt(key, json.getBytes());
        } catch (Exception e) {
            return null;
        }
        return Base64.encodeToString(cipher, Base64.DEFAULT);
    }

    public static <T> T decryptAES(String password, String salt, String cipher,
                                   Class<T> targetCls) {
        if (TextUtils.isEmpty(cipher))
            return null;
        byte[] result;
        byte[] key;
        try {
            key = AESUtil.getPBEKey(password.toCharArray(), salt.getBytes());
        } catch (Exception e) {
            return null;
        }
        try {
            result = AESUtil.decrypt(key, Base64.decode(cipher, Base64.DEFAULT));
        } catch (Exception e) {
            return null;
        }
        try {
            return EntitySerializerUtils.deserializerEntity(new String(result), targetCls);
        } catch (Exception e) {
            return null;
        }
    }
}
