package com.xzq.module_base.arouter;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.xzq.module_base.User;

/**
 * 登录拦截器，拦截需要登录才能开启的页面
 *
 * @author xzq
 */
@Interceptor(priority = 1, name = "login")
public class LoginInterceptor implements IInterceptor {

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        String group = postcard.getGroup();
        boolean isLoginGroup = LoginPath.isLoginGroup(group);
        if (isLoginGroup && !User.isLogin()) {
            callback.onInterrupt(null);
            RouterUtils.openLogin();
            return;
        }
        callback.onContinue(postcard);
    }

    @Override
    public void init(Context context) {
    }
}
