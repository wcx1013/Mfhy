package com.xzq.module_base.arouter;

import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.xzq.module_base.utils.AppUtils;

/**
 * 路由工具
 *
 * @author xzq
 */
public class RouterUtils {

    private static void navigation(String path) {
        final Context context = AppUtils.getTopActivityOrApp();
        ARouter.getInstance().build(path).navigation(context);
    }

    /**
     * 打开登录页
     */
    public static void openLogin() {
        navigation(RouterPath.LOGIN);
    }

    /**
     * 打开我的个人信息
     */
    public static void openUserInfo() {
        navigation(LoginPath.USER_INFO);
    }

}
