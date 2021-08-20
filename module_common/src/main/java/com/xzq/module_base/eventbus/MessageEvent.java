package com.xzq.module_base.eventbus;

import android.text.TextUtils;

/**
 * EventBus消息载体
 *
 * @author xzq
 */
public class MessageEvent {

    private String message;
    private Object data;

    MessageEvent(String message) {
        this.message = message;
    }

    MessageEvent(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public boolean equals(String msg) {
        return TextUtils.equals(message, msg);
    }
}

