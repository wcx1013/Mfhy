package com.xzq.module_base.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;

import androidx.annotation.BoolRes;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.IntegerRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.xzq.module_base.BaseApplication;

public class ResUtil {
    public static float getDimension(@DimenRes int id){
        return BaseApplication.getContext().getResources().getDimension(id);
    }

    @NonNull
    public static String getString(int stringId) {
        return BaseApplication.getContext().getString(stringId);
    }

    @NonNull
    public static String getString(int stringId, Object... formatArgs) {
        return BaseApplication.getContext().getString(stringId, formatArgs);
    }

    public static int getColor(@ColorRes int color) {
        return ContextCompat.getColor(BaseApplication.getContext(), color);
    }

    public static boolean getBoolean(@BoolRes int bool){
        return BaseApplication.getContext().getResources().getBoolean(bool);
    }

    public static int getInteger(@IntegerRes int integer){
        return BaseApplication.getContext().getResources().getInteger(integer);
    }

    public static Drawable getDrawable(@DrawableRes int drawableRes){
        return ContextCompat.getDrawable(BaseApplication.getContext(),drawableRes);
    }

    /**
     * 从属性中获取颜色，application样式
     * @param color 属性颜色id
     * @return 属性相应颜色
     */
    public static int getColorOfAttr(int color) {
        return getColorOfAttr(BaseApplication.getContext(), color);
    }

    /**
     * 从属性中获取颜色，context对应样式
     * @param color 属性颜色id
     * @return 属性相应颜色
     */
    public static int getColorOfAttr(Context context, int color) {
        int defaultColor = 0xFF000000;
        int[] attrsArray = { color };
        TypedArray typedArray = context.obtainStyledAttributes(attrsArray);
        int retColor = typedArray.getColor(0, defaultColor);
        // don't forget the resource recycling
        typedArray.recycle();
        return retColor;
    }
}
