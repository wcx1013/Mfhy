package com.xzq.module_base.utils;

/**
 * Log包装类
 * Created by xzq on 2018/12/19.
 */
public class XZQLog {

    /**
     * 自定义 TAG
     *
     * @param tag  TAG
     * @param msg  消息
     * @param args 格式化参数
     */
    public static void debug(String tag, String msg, Object... args) {
        XTimber.debug(tag, msg, args);
    }

    /**
     * 自动把当前类名作为TAG（XZQLog）
     *
     * @param msg  消息
     * @param args 格式化参数
     */
    public static void debug(String msg, Object... args) {
        XTimber.debug(msg, args);
    }

}
