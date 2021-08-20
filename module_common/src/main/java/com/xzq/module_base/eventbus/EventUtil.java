package com.xzq.module_base.eventbus;

import org.greenrobot.eventbus.EventBus;

/**
 * EventBus工具
 *
 * @author xzq
 */

public class EventUtil {

    public static void register(Object subscriber) {
        EventBus.getDefault().register(subscriber);
    }

    public static void unregister(Object subscriber) {
        EventBus.getDefault().unregister(subscriber);
    }

    public static void post(String msg) {
        EventBus.getDefault().post(new MessageEvent(msg));
    }

    public static void post(Object obj) {
        EventBus.getDefault().post(new MessageEvent(null, obj));
    }

    public static void post(String msg, Object data) {
        EventBus.getDefault().post(new MessageEvent(msg, data));
    }

    public static void postSticky(String msg) {
        EventBus.getDefault().postSticky(new MessageEvent(msg));
    }

    public static void postSticky(Object obj) {
        EventBus.getDefault().postSticky(new MessageEvent(null, obj));
    }

    public static void postSticky(String msg, Object data) {
        EventBus.getDefault().postSticky(new MessageEvent(msg, data));
    }

    public static void removeStickyEvent(Object event) {
        EventBus.getDefault().removeStickyEvent(event);
    }

    public static void removeAllStickyEvents() {
        EventBus.getDefault().removeAllStickyEvents();
    }

}
