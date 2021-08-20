package com.xzq.module_base.managers.share;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.blankj.utilcode.util.AppUtils;
import com.xzq.module_base.R;
import com.xzq.module_base.utils.MyToast;

import java.util.List;

/**
 * PlatformUtil
 * Created by xzq on 2020/7/24.
 */
public class PlatformUtil {
    public static final String PACKAGE_FACEBOOK = "com.facebook.katana";
    public static final String PACKAGE_WECHAT = "com.tencent.mm";
    public static final String PACKAGE_MOBILE_QQ = "com.tencent.mobileqq";
    public static final String PACKAGE_QZONE = "com.qzone";
    public static final String PACKAGE_SINA = "com.sina.weibo";

    // 判断是否安装指定app
    public static boolean isInstallApp(Context context, String app_package) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pInfo = packageManager.getInstalledPackages(0);
        if (pInfo != null) {
            for (int i = 0; i < pInfo.size(); i++) {
                String pn = pInfo.get(i).packageName;
                if (app_package.equals(pn)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean checkWechatInstallAndToast() {
        boolean isInstall = AppUtils.isAppInstalled(PlatformUtil.PACKAGE_WECHAT);
        if (!isInstall) {
            MyToast.show(R.string.str_wechat_install);
        }
        return isInstall;
    }

    public static boolean checkFacebookInstallAndToast() {
        boolean isInstall = AppUtils.isAppInstalled(PlatformUtil.PACKAGE_FACEBOOK);
        if (!isInstall) {
            MyToast.show(R.string.str_facebook_install);
        }
        return isInstall;
    }
}
