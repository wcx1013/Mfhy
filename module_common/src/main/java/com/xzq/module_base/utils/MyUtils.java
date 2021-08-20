package com.xzq.module_base.utils;

import androidx.annotation.NonNull;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用工具
 * Created by xzq on 2020/7/2.
 */
public class MyUtils {

    /**
     * 获取价格，占位符为逗号,
     *
     * @param srcPrice .
     * @return .
     */
    @NonNull
    public static String getPriceWithComma(String srcPrice) {
        return "¥ " + getPriceWithPlaceholder(srcPrice, ",");
    }

    /**
     * 获取价格，大于1000的价格每三位依次加占位符，例如添加逗号：1000,000,000.00
     *
     * @param srcPrice    源价格
     * @param placeholder 占位符
     * @return .
     */
    @NonNull
    public static String getPriceWithPlaceholder(String srcPrice, String placeholder) {
        if (TextUtils.isEmpty(srcPrice)) {
            return "";
        }
        int dotIndex = srcPrice.indexOf(".");
        String decimalStr = "";
        String intStr = srcPrice;
        if (dotIndex != -1) {
            intStr = srcPrice.substring(0, dotIndex);
            decimalStr = srcPrice.substring(dotIndex);
        }
        List<String> list = new ArrayList<>();
        rAppend(intStr, list);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            builder.append(list.get(i));
            if (i < list.size() - 1) {
                builder.append(placeholder);
            }
        }
        return builder.toString() + decimalStr;
    }

    private static final int BIT_COUNT = 3;

    private static void rAppend(@NonNull String src, List<String> list) {
        int len = src.length();
        if (len > BIT_COUNT) {
            final int subIndex = len - BIT_COUNT;
            String remain = src.substring(0, subIndex);
            String last = src.substring(subIndex);
            list.add(0, last);
            rAppend(remain, list);
        } else {
            list.add(0, src);
        }
    }
}
