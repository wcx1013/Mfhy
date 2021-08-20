package com.xzq.module_base.sp;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.blankj.utilcode.util.Utils;
import com.xzq.module_base.bean.LoginDto;
import com.xzq.module_base.config.Config;
import com.xzq.module_base.utils.cipher.CipherUtils;

/**
 * 用户SP数据
 * Created by Wesley on 2018/7/9.
 */

public class UserSPManager {

    private static final String SP_USER_INFO = "user_info";// 用户数据，退出登录后删除
    private static final String KEY_LOGIN_DATA = "login_data";// 登录数据

    /**
     * 获取用户信息 SharedPreferences
     *
     * @return SharedPreferences
     */
    private static SharedPreferences getUserInfoSharedPreferences() {
        final Context context = Utils.getApp();
        return context.getSharedPreferences(SP_USER_INFO, Context.MODE_PRIVATE);
    }

    /**
     * 用户退出登录，清除所有用户数据
     */
    public static void clearUserData() {
        getUserInfoSharedPreferences().edit().clear().apply();
    }

    /**
     * 存储登录数据
     *
     * @param data 登录数据
     */
    public static void putLoginData(LoginDto data) {
        if (data == null) {
            getUserInfoSharedPreferences().edit().remove(KEY_LOGIN_DATA).apply();
            return;
        }
        String cipher = CipherUtils.encryptAES(Config.CACHE_PASSWORD, Config.CACHE_SALT, data);
        if (cipher == null)
            return;
        getUserInfoSharedPreferences().edit().putString(KEY_LOGIN_DATA, cipher).apply();
    }

    /**
     * 获取登录数据
     *
     * @return 登录数据
     */
    public static LoginDto getLoginData() {
        String cipher = getUserInfoSharedPreferences().getString(KEY_LOGIN_DATA, null);
        if (TextUtils.isEmpty(cipher))
            return null;
        return CipherUtils.decryptAES(Config.CACHE_PASSWORD, Config.CACHE_SALT,
                cipher, LoginDto.class);
    }

}
