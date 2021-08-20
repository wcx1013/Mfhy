package com.xzq.module_base.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.xzq.module_base.bean.MarketPkgsBean;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommUtils {
    /**
     * 获取版本名字
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {

        String version;
        try {
            // 获取packagemanager的实例
            PackageManager packageManager = context.getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            version = packInfo.versionName;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取当前手机上的应用商店
     *
     * @param context
     * @return
     */
    public static ArrayList<MarketPkgsBean> getInstalledMarketPkgs(Context context) {
        ArrayList<MarketPkgsBean> pkgs = new ArrayList<>();
        if (context == null)
            return pkgs;
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(Uri.parse("market://details?id="));
        PackageManager pm = context.getPackageManager();
        // 通过queryIntentActivities获取ResolveInfo对象
        List<ResolveInfo> infos = pm.queryIntentActivities(intent,
                0);
        if (infos == null || infos.size() == 0)
            return pkgs;
        int size = infos.size();
        for (int i = 0; i < size; i++) {
            String appName = "";
            String pkgName = "";
            Drawable icon = null;
            try {
                ActivityInfo activityInfo = infos.get(i).activityInfo;

                icon = pm.getApplicationIcon(activityInfo.applicationInfo);
                appName = pm.getApplicationLabel(activityInfo.applicationInfo).toString();
                pkgName = activityInfo.packageName;

            } catch (Exception e) {
                e.printStackTrace();
            }
            // com.android.vending 谷歌应用商店
            if (!TextUtils.isEmpty(pkgName) && !"com.android.vending".equals(pkgName))
                pkgs.add(new MarketPkgsBean(appName, pkgName, icon));
        }
        return pkgs;
    }


    /**
     * 获取该软件版本
     *
     * @param context
     * @return
     */
    public static int getVersionNumber(Context context) {

        int version = 0;
        try {
            // 获取packagemanager的实例
            PackageManager packageManager = context.getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            version = packInfo.versionCode;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    /**
     * 获取包名
     *
     * @param context
     * @return
     */
    public static String getApkPkgName(Context context) {

        String pkg;
        try {
            // 获取packagemanager的实例
            PackageManager packageManager = context.getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            pkg = packInfo.packageName;
            return pkg;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    //获取沉浸式布局
    public static void setImmerseLayout(View view, Activity activity) {// view为标题栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏图标和文字颜色为暗色
            }
            int statusBarHeight = getStatusBarHeight(activity);
            view.setPadding(0, statusBarHeight, 0, 0);
        }
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 禁止EditText输入空格和固定长度
     *
     * @param editText
     */
    public static void setEditTextInhibitInputSpaceAndTextLength(EditText editText, int length) {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                if (source.equals(" ")) {
                    return "";
                } else {
                    return null;
                }
            }
        };
        editText.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(length)});
    }

    /**
     * 获取当前手机上的应用商店
     *
     * @param context
     * @return
     */
    /*public static ArrayList<MarketPkgsBean> getInstalledMarketPkgs(Context context) {
        ArrayList<MarketPkgsBean> pkgs = new ArrayList<>();
        if (context == null)
            return pkgs;
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(Uri.parse("market://details?id="));
        PackageManager pm = context.getPackageManager();
        // 通过queryIntentActivities获取ResolveInfo对象
        List<ResolveInfo> infos = pm.queryIntentActivities(intent,
                0);
        if (infos == null || infos.size() == 0)
            return pkgs;
        int size = infos.size();
        for (int i = 0; i < size; i++) {
            String appName = "";
            String pkgName = "";
            Drawable icon = null;
            try {
                ActivityInfo activityInfo = infos.get(i).activityInfo;

                icon = pm.getApplicationIcon(activityInfo.applicationInfo);
                appName = pm.getApplicationLabel(activityInfo.applicationInfo).toString();
                pkgName = activityInfo.packageName;

            } catch (Exception e) {
                e.printStackTrace();
            }
            // com.android.vending 谷歌应用商店
            if (!TextUtils.isEmpty(pkgName) && !"com.android.vending".equals(pkgName))
                pkgs.add(new MarketPkgsBean(appName, pkgName, icon));
        }
        return null;
    }*/

    /**
     * 启动到应用商店app详情界面
     *
     * @param appPkg    目标App的包名
     * @param marketPkg 应用商店包名 ,如果为""则由系统弹出应用商店列表供用户选择,否则调转到目标市场的应用详情界面，某些应用商店可能会失败
     */
    public static void launchAppDetail(Context context, String appPkg, String marketPkg) {
        try {
            if (TextUtils.isEmpty(appPkg)) return;

            Uri uri = Uri.parse("market://details?id=" + appPkg);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            if (!TextUtils.isEmpty(marketPkg)) {
                intent.setPackage(marketPkg);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            //ToastUtils.shortShowStr(context, "打开商店失败,App没有没有搜索到！");
        }
    }

    /**
     * 检查是否有网络
     */
    public static boolean isNetworkAvailable(Context context) {

        NetworkInfo info = getNetworkInfo(context);
        if (info != null) {
            return info.isAvailable();
        }
       // ToastUtils.shortShowStr(context, "当前网络不可使用");
        return false;
    }

    private static NetworkInfo getNetworkInfo(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    /**
     * android获取设备ID
     *
     * @param context
     * @return
     */
    public static String getUniqueID(Context context) {
        String id = null;
        final String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        if (!TextUtils.isEmpty(androidId) && !"9774d56d682e549c".equals(androidId)) {
            try {
                UUID uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8"));
                id = uuid.toString();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        if (TextUtils.isEmpty(id)) {
            id = getUUID();
        }

        return TextUtils.isEmpty(id) ? UUID.randomUUID().toString() : id;
    }

    private static String getUUID() {
        String serial = null;

        String m_szDevIDShort = "35" +
                Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +

                Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +

                Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +

                Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +

                Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +

                Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +

                Build.USER.length() % 10; //13 位

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                serial = Build.getSerial();
            } else {
                serial = Build.SERIAL;
            }
            //API>=9 使用serial号
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception exception) {
            serial = "serial"; // 随便一个初始化
        }

        //使用硬件信息拼凑出来的15位号码
        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }

    /**
     * 判断是否是快速点击
     */
    private static long lastClickTime;

    public static boolean checkDoubleClick() {
        //点击时间
        long clickTime = SystemClock.uptimeMillis();
        //如果当前点击间隔小于500毫秒
        if (lastClickTime >= clickTime - 500) {
            return true;
        }
        //记录上次点击时间
        lastClickTime = clickTime;
        return false;
    }

    /**
     * 获取应用是否登录
     * @param mContext
     * @return
     */
//    public static boolean isLogin (Context mContext) {
//        MySharedPreferences mp = new MySharedPreferences(mContext, "Shared");
//        return (boolean) mp.getValue("isLogin", false);
//    }

    /**
     * 判断某个界面是否在前台
     *
     * @param context   Context
     * @param className 界面的类名
     * @return 是否在前台显示
     */
    public static boolean isForeground(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className))
            return false;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName()))
                return true;
        }
        return false;
    }
}
