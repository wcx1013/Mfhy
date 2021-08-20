package com.yd.mofanghuanyuans;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.CrashUtils;
import com.umeng.commonsdk.UMConfigure;
import com.xzq.module_base.User;
import com.xzq.module_base.utils.RefreshLayoutInitializer;
import com.xzq.module_base.utils.Utils;
import com.xzq.module_base.utils.XTimber;
import com.yd.mofanghuanyuans.main.chansj.TTAdManagerHolder;

/**
 * MyApp
 * Created by xzq on 2020/7/9.
 */
public class MyApp extends Application {

    private static final String TAG = "MyApp";
    private static Application sInstance;
    private static boolean isDebug;

    public static boolean isDebug() {
        return isDebug;
    }

    public static Application getContext() {
        return sInstance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        if (Utils.isOtherProcess(this)) {
            Log.d(TAG, "attachBaseContext call by other process , stop init");
            return;
        }
        // dex突破65535的限制
        MultiDex.install(this);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onCreate() {
        super.onCreate();
        if (Utils.isOtherProcess(this)) {
            Log.d(TAG, "onCreate call by other process , stop init");
            return;
        }
        sInstance = this;
        CrashUtils.init();
        isDebug = AppUtils.isAppDebug();
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
        User.init();


        //穿山甲SDK初始化
        TTAdManagerHolder.init(this);

        // 友盟初始化
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "");
    }

}
