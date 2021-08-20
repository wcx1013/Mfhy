package com.xzq.module_base.utils;

import android.content.Context;

/**
 * 尺寸单位转换工具
 * Created by Tse on 2016/8/7.
 */
public class SizeUtils {


    /**
     * dp 2 px
     *
     * @param dpValue dp
     */
    public static int dp2px(Context context, float dpValue) {
        final float density = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5f);
    }

    /**
     * px 2 dp
     *
     * @param pxValue px
     */
    public static int px2dp(Context context, float pxValue) {
        final float density = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / density + 0.5f);
    }

    /**
     * sp 2 px
     *
     * @param spValue sp
     */
    public static int sp2px(Context context, float spValue) {
        final float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scaledDensity + 0.5f);
    }

    /**
     * px 2 sp
     *
     * @param pxValue px
     */
    public static int px2sp(Context context, float pxValue) {
        final float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / scaledDensity + 0.5f);
    }

    /**
     * The absolute width of the available display size in pixels.
     */
    public static int widthPixels(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * The absolute height of the available display size in pixels.
     */
    public static int heightPixels(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

}
