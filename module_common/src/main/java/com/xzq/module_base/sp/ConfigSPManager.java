package com.xzq.module_base.sp;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.blankj.utilcode.util.Utils;
import com.google.gson.reflect.TypeToken;
import com.xzq.module_base.utils.EntitySerializerUtils;

import java.util.List;

/**
 * 配置SP数据
 * Created by xzq on 2019/5/20.
 */

public class ConfigSPManager {

    private static final String SP_CONFIG_INFO = "config_info";
    private static final String KEY_SEARCH_KEYWORDS = "search_keywords";
    private static final String KEY_IS_AGREE_PRIVATE = "is_agree_private";
    private static final String KEY_LAST_VER_CODE = "last_ver_code";
    private static final String KEY_LOGIN_ACCOUNT = "login_account";
    private static final String KEY_LOGIN_PWD = "login_pwd";
    private static final String KEY_IS_FIRST_START_TRAIN = "is_first_start_train";
    private static final String KEY_NEED_SHOW_DEVICE = "need_show_device";
    private static final String KEY_KEYBOARD_HEIGHT = "keyboard_height";

    /**
     * 获取配置信息 SharedPreferences
     *
     * @return SharedPreferences
     */
    private static SharedPreferences getConfigInfoSharedPreferences() {
        final Context context = Utils.getApp();
        return context.getSharedPreferences(SP_CONFIG_INFO, Context.MODE_PRIVATE);
    }

    /**
     * 存储用户搜索记录
     *
     * @param keys 用户搜索记录
     */
    public static void putKeywords(List<String> keys) {
        if (keys == null || keys.isEmpty()) {
            getConfigInfoSharedPreferences().edit()
                    .putString(KEY_SEARCH_KEYWORDS, null).apply();
            return;
        }
        String keywords;
        try {
            keywords = EntitySerializerUtils.serializerList(keys);
        } catch (Exception e) {
            keywords = null;
        }
        getConfigInfoSharedPreferences().edit()
                .putString(KEY_SEARCH_KEYWORDS, keywords).apply();
    }

    /**
     * 获取用户搜索记录
     *
     * @return 用户搜索记录
     */
    public static List<String> getKeywords() {
        String keywords = getConfigInfoSharedPreferences()
                .getString(KEY_SEARCH_KEYWORDS, null);
        if (TextUtils.isEmpty(keywords))
            return null;
        List<String> listKeys;
        try {
            listKeys = EntitySerializerUtils.deserializerType(keywords,
                    new TypeToken<List<String>>() {
                    }.getType());
        } catch (Exception e) {
            listKeys = null;
        }
        return listKeys;
    }

    /**
     * 存储是否同意隐私协议
     *
     * @param agree .
     */
    public static void putAgreePrivate(boolean agree) {
        getConfigInfoSharedPreferences().edit().putBoolean(KEY_IS_AGREE_PRIVATE, agree).apply();
    }

    /**
     * 是否同意隐私协议
     *
     * @return .
     */
    public static boolean isAgreePrivate() {
        return getConfigInfoSharedPreferences().getBoolean(KEY_IS_AGREE_PRIVATE, false);
    }

    /**
     * 存储最新版本号
     *
     * @param verCode .
     */
    public static void putLastVerCode(int verCode) {
        getConfigInfoSharedPreferences().edit().putInt(KEY_LAST_VER_CODE, verCode).apply();
    }

    /**
     * 是否需要显示引导页
     *
     * @param newVerCode 新版本号
     * @return .
     */
    public static boolean needShowGuide(int newVerCode) {
        int lastVerCode = getConfigInfoSharedPreferences().getInt(KEY_LAST_VER_CODE, -1);
        return lastVerCode == -1 || newVerCode > lastVerCode;
    }

    /**
     * 存储登录账号
     *
     * @param account .
     */
    public static void putAccount(String account) {
        getConfigInfoSharedPreferences().edit().putString(KEY_LOGIN_ACCOUNT, account).apply();
    }

    /**
     * 获取登录账号
     *
     * @return .
     */
    public static String getAccount() {
        return getConfigInfoSharedPreferences().getString(KEY_LOGIN_ACCOUNT, null);
    }

    /**
     * 存储登录密码
     *
     * @param pwd .
     */
    public static void putPwd(String pwd) {
        getConfigInfoSharedPreferences().edit().putString(KEY_LOGIN_PWD, pwd).apply();
    }

    /**
     * 获取登录密码
     *
     * @return .
     */
    public static String getPwd() {
        return getConfigInfoSharedPreferences().getString(KEY_LOGIN_PWD, null);
    }

    /**
     * 存储首次进入训练场景
     */
    public static void putHasStartTrain() {
        getConfigInfoSharedPreferences().edit().putBoolean(KEY_IS_FIRST_START_TRAIN, false).apply();
    }

    /**
     * 是否是首次进入训练场景
     *
     * @return .
     */
    public static boolean isFirstStartTrain() {
        boolean isFirst = getConfigInfoSharedPreferences().getBoolean(KEY_IS_FIRST_START_TRAIN, true);
        if (isFirst) {
            ConfigSPManager.putHasStartTrain();
        }
        return isFirst;
    }

    /**
     * 是否需要显示设备列表页
     *
     * @return .
     */
    public static boolean needShowDevice() {
        return getConfigInfoSharedPreferences().getBoolean(KEY_NEED_SHOW_DEVICE, true);
    }

    /**
     * 存储是否需要显示设备页
     *
     * @param needShow 是否显示
     */
    public static void putNeedShowDevice(boolean needShow) {
        getConfigInfoSharedPreferences().edit().putBoolean(KEY_NEED_SHOW_DEVICE, needShow).apply();
    }

    /**
     * 存储键盘高度
     *
     * @param keyboardHeight .
     */
    public static void putKeyboardHeight(int keyboardHeight) {
        getConfigInfoSharedPreferences().edit().putInt(KEY_KEYBOARD_HEIGHT, keyboardHeight).apply();
    }

    /**
     * 获取键盘高度
     *
     * @return .
     */
    public static int getKeyboardHeight() {
        return getConfigInfoSharedPreferences().getInt(KEY_KEYBOARD_HEIGHT, 0);
    }


}
