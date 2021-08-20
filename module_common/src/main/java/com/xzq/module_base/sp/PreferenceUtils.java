package com.xzq.module_base.sp;

import com.xzq.module_base.User;



public class PreferenceUtils {
    private static String KEY_TOKEN = "KEY_TOKEN";
    public static void putToken(String token) {
        SharedPreferencesClient.putString(KEY_TOKEN, token);

        User.init();
    }
    public static String getToken() {
        return SharedPreferencesClient.sp().getString(KEY_TOKEN, "");
    }
}
