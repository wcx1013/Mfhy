package com.xzq.module_base.arouter;

import android.text.TextUtils;

/**
 * 登录页Path
 *
 * @author xzq
 */
public class LoginPath {

    /**
     * 页面Path是否归属登录组
     *
     * @param group 组名称
     * @return 是否归属登录组
     */
    public static boolean isLoginGroup(String group) {
        return TextUtils.equals(GROUP, group);
    }

    //登录组名
    public static final String GROUP = "login";

    //个人信息
    public static final String USER_INFO = "/" + GROUP + "/user_info";

}
