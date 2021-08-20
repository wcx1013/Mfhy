package com.yd.mofanghuanyuans.main.biz;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadUtil {
    private static ExecutorService sExec = Executors.newCachedThreadPool();
    private static Handler mainHandler = new Handler(Looper.getMainLooper());

    public static void runOnUiThread(Runnable r) {
        mainHandler.post(r);
    }

    public static void runOnUiThread(Runnable r, long t) {
        mainHandler.postDelayed(r, t);
    }

    public static void runOnWorkerThread(Runnable r) {
        sExec.execute(r);
    }
}
