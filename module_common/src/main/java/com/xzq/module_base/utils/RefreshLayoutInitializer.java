package com.xzq.module_base.utils;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

/**
 * 下拉刷新配置初始化器
 * Created by xzq on 2018/9/25.
 */

public class RefreshLayoutInitializer {

    public static void initHeader() {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            layout.setReboundDuration(300);
            layout.setEnableOverScrollBounce(false);
            return new ClassicsHeader(context);
        });
    }
}
