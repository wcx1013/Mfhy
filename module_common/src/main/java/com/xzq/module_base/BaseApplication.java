package com.xzq.module_base;

import android.app.Application;

import com.xzq.module_base.sp.ConfigSPManager;
import com.xzq.module_base.utils.SizeUtils;
import com.xzq.module_base.utils.XZQLog;

public class BaseApplication extends Application {
    private static int keyboardHeight = 0;

    public static void setKeyboardHeight(int keyboardHeight) {
        final int prevKeyboardHeight =ConfigSPManager.getKeyboardHeight();
        if (keyboardHeight > 0 && prevKeyboardHeight != keyboardHeight) {
            ConfigSPManager.putKeyboardHeight(keyboardHeight);
            BaseApplication.keyboardHeight = keyboardHeight;
//            XZQLog.debugHyphenate("save keyboardHeight = " + keyboardHeight);

        }
    }

    public static int getKeyboardHeight() {
        if (keyboardHeight > 0) {
            return keyboardHeight;
        }
        keyboardHeight = SizeUtils.heightPixels(getContext()) / 5 * 2;
       // XZQLog.debugHyphenate("getKeyboardHeight = " + keyboardHeight);
        return keyboardHeight;
    }

    private static BaseApplication sInstance;

    public static BaseApplication getContext() {
        return sInstance;
    }

    @Override
    public void onCreate() {

        super.onCreate();
        sInstance = this;
        keyboardHeight = ConfigSPManager.getKeyboardHeight();

 /*       Utils.init(this);
        User.init();
        isDebug = Utils.isAppDebug();
        if (isDebug) {
            //初始化懒人log
            XTimber.plant(new XTimber.DebugTree());

            //开启InstantRun之后，一定要在ARouter.init之前调用openDebug
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);
        //初始化下拉刷新
        RefreshLayoutInitializer.initHeader();
        initAppStatusListener();*/

    }
}
