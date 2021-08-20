package com.xzq.module_base;

import com.xzq.module_base.bean.LoginDto;
import com.xzq.module_base.eventbus.EventAction;
import com.xzq.module_base.eventbus.EventUtil;
import com.xzq.module_base.sp.UserSPManager;


/**
 * 登录信息管理
 *
 * @author xzq
 */

public class User {

    private static LoginDto sLogin = null;

    public static LoginDto init() {
        sLogin = UserSPManager.getLoginData();
        return sLogin;
    }

    /**
     * 用户登录成功后设置登录数据
     *
     * @param data 登录数据
     */
    public static void login(LoginDto data) {
        sLogin = data;
        EventUtil.post(EventAction.LOGIN_SUCCEED);
        //保存到SP
        UserSPManager.putLoginData(data);
    }

    /**
     * 用户登出、用户登录凭证失效时清除用户数据
     */
    public static void logout() {
        sLogin = null;
        EventUtil.post(EventAction.LOGOUT_SUCCEED);
        UserSPManager.clearUserData();
    }

    /**
     * 用户是否已经登录
     *
     * @return .
     */
    public static boolean isLogin() {
        return sLogin != null;
    }

    /**
     * 获取用户登录凭证
     *
     * @return .
     */
    public static String getToken() {
        return sLogin != null ? sLogin.token : null;
    }

}
