package com.xzq.module_base.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 状态配置
 *
 * @author xzq
 */
public class StateConfig {
    public int loadingLayoutId;
    public int emptyLayoutId;
    public int errorLayoutId;

    View getViewById(ViewGroup parent, int id) {
        return LayoutInflater.from(parent.getContext()).inflate(id, parent, false);
    }
}
