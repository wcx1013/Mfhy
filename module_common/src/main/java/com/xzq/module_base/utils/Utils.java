package com.xzq.module_base.utils;


import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * <p>Utils初始化相关 </p>
 */
public class Utils {

    private static final String TAG = "Utils";

    public static boolean isOtherProcess(Context context) {
        // 获取当前进程 id 并取得进程名
        String processAppName = getAppName(context);
        Log.d(TAG, "processAppName = " + processAppName);
        String mainPkgName = context.getPackageName();
        Log.d(TAG, "mainPkgName = " + mainPkgName);
        return processAppName == null || !processAppName.equalsIgnoreCase(mainPkgName);
    }

    /**
     * 根据Pid获取当前进程的名字，一般就是当前app的包名
     *
     * @param context Context
     * @return 返回进程的名字
     */
    public static String getAppName(Context context) {
        int pid = android.os.Process.myPid();
        Log.d(TAG, "pid = " + pid);
        String processName;
        ActivityManager activityManager =
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager == null) {
            return null;
        }
        List<ActivityManager.RunningAppProcessInfo> list = activityManager.getRunningAppProcesses();
        if (list == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo info : list) {
            try {
                if (info.pid == pid) {
                    // 根据进程的信息获取当前进程的名字
                    processName = info.processName;
                    // 返回当前进程名
                    return processName;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 没有匹配的项，返回为null
        return null;
    }

}