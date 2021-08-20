package com.xzq.module_base.bean;

import android.graphics.drawable.Drawable;

public class MarketPkgsBean {
    private String pkgName;
    private String appName;
    private Drawable icon;

    public MarketPkgsBean(String appName, String pkgName, Drawable icon) {
        this.pkgName = pkgName;
        this.appName = appName;
        this.icon = icon;
    }

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
