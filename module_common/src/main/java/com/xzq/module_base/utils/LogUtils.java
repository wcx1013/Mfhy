package com.xzq.module_base.utils;

import android.util.Log;

import com.xzq.module_base.BuildConfig;


/**
 * 日志工具
 * Created by Wesley on 2017/12/13.
 */

public class LogUtils {

    //默认打印模式
    private static final Mode MODE = Mode.METHOD;

    /**
     * Log输出模式
     */
    private enum Mode {
        /**
         * 打印所有
         */
        ALL,
        /**
         * 打印方法
         */
        METHOD,
        /**
         * 不打印
         */
        NONE
    }

    /**
     * 输出VERBOSE日志消息
     *
     * @param tag 标签
     * @param msg 消息
     */
    public static void v(String tag, String msg) {
        log(tag, msg, Log.VERBOSE);
    }

    /**
     * 输出DEBUG日志消息
     *
     * @param tag 标签
     * @param msg 消息
     */
    public static void d(String tag, String msg) {
        log(tag, msg, Log.DEBUG);
    }

    /**
     * 输出ERROR日志消息
     *
     * @param tag 标签
     * @param msg 消息
     */
    public static void e(String tag, String msg) {
        log(tag, msg, Log.ERROR);
    }


    /**
     * 输出INFO日志消息
     *
     * @param tag 标签
     * @param msg 消息
     */
    public static void i(String tag, String msg) {
        log(tag, msg, Log.INFO);
    }

    /**
     * 输出WARN日志消息
     *
     * @param tag 标签
     * @param msg 消息
     */
    public static void w(String tag, String msg) {
        log(tag, msg, Log.WARN);
    }

    /**
     * 输出日志消息
     *
     * @param tag 标签
     * @param msg 消息
     * @param lv  日志级别
     */
    private static void log(String tag, String msg, int lv) {

        if (tag != null) {

            msg = msg == null ? "null" : msg;

            msg = MODE == Mode.NONE ? msg : getTrace(new StringBuilder()).append(msg).toString();

            switch (lv) {

                case Log.VERBOSE:
                    Log.v(tag, msg);
                    break;

                case Log.DEBUG:
                    Log.d(tag, msg);
                    break;

                case Log.INFO:
                    Log.i(tag, msg);
                    break;

                case Log.ERROR:
                    Log.e(tag, msg);
                    break;

                case Log.WARN:
                    Log.w(tag, msg);
                    break;

                default:
                    break;
            }
        }

    }

    /**
     * 获取代码执行轨迹
     *
     * @param sb 拼接器
     * @return 拼接器
     */
    private static StringBuilder getTrace(StringBuilder sb) {

        StackTraceElement[] stackTraceList = Thread.currentThread().getStackTrace();

        if (stackTraceList != null && stackTraceList.length > 5) {

            StackTraceElement stackTraceElement = stackTraceList[5];

            String className = stackTraceElement.getClassName();
            String methodName = stackTraceElement.getMethodName();
            String fileName = stackTraceElement.getFileName();
            int lineNumber = stackTraceElement.getLineNumber();

            sb.append("at")
                    .append("\t")
                    .append(MODE == Mode.ALL ? className : "")
                    .append(MODE == Mode.ALL ? "." : "")
                    .append(methodName)
                    .append("(")
                    .append(fileName)
                    .append(":")
                    .append(lineNumber)
                    .append(")")
                    .append("\t");

        }

        return sb;

    }

    /**
     * 打印调试日志，正式版自动关闭
     *
     * @param tag 标签
     * @param msg 消息
     */
    public static void debug(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            log(tag, msg, Log.DEBUG);
        }
    }

}
