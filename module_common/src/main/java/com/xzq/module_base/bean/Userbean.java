package com.xzq.module_base.bean;

import com.xzq.module_base.bean.UserInfo;

import java.io.Serializable;

public class Userbean implements Serializable {
    private UserInfo param;

    public UserInfo getParam() {
        return param;
    }

    public void setParam(UserInfo param) {
        this.param = param;
    }
}
