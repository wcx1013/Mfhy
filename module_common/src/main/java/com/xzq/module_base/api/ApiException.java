package com.xzq.module_base.api;

/**
 * 接口返回异常
 *
 * @author xzq
 */
public class ApiException extends Exception {

    private int code;

    ApiException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
