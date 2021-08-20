package com.xzq.module_base.adapter;

import android.content.Context;
import android.util.TypedValue;

import com.scwang.smartrefresh.layout.internal.ProgressDrawable;


/**
 * FooterProgressDrawable
 * Created by xzq on 2018/9/29.
 */
public class FooterProgressDrawable extends ProgressDrawable {

    private int sizeOfPx;

    public FooterProgressDrawable(Context context) {
        this(context, 20);
    }

    public FooterProgressDrawable(Context context, int size) {
        this.sizeOfPx = dp2px(context, size);
    }

    @Override
    public int getIntrinsicWidth() {
        return sizeOfPx;
    }

    @Override
    public int getIntrinsicHeight() {
        return sizeOfPx;
    }

    private static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }
}
