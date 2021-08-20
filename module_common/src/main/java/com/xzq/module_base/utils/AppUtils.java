package com.xzq.module_base.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.Utils;

/**
 * APP工具
 * Created by xzq on 2018/10/22.
 */
public class AppUtils {

    /**
     * 打开拨号界面
     *
     * @param context Context
     * @param phone   电话号码
     */
    public static void openDial(Context context, String phone) {
        if (context == null || phone == null) {
            return;
        }
        if (!phone.startsWith("tel:")) {
            phone = "tel:" + phone;
        }
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 打开系统浏览器
     *
     * @param context Context
     * @param url     网页地址
     */
    public static void openBrowser(Context context, String url) {
        if (context == null || url == null) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(intent);
    }

    /**
     * 获取App版本名称
     *
     * @param context Context
     * @return App版本名称
     */
    public static String getAppVersionName(Context context) {
        String packageName = context.getPackageName();
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    public static Context getTopActivityOrApp() {
        Activity topAct = ActivityUtils.getTopActivity();
        return topAct == null ? Utils.getApp() : topAct;
    }

}
