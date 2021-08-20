package com.xzq.module_base.utils;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.Utils;
import com.xzq.module_base.R;
import com.xzq.module_base.utils.divider.Divider;
import com.xzq.module_base.utils.divider.ItemDivider;

/**
 * 分割线工厂类
 * Created by xzq on 2018/12/19.
 */
public class DividerFactory {

    private static final int DEFAULT_COLOR = R.color.color_divider;

    private static Context c = Utils.getApp();

    /**
     * RecyclerView垂直分割线
     */
    public static RecyclerView.ItemDecoration VERTICAL
            = Divider.create(Divider.with(c).colorRes(DEFAULT_COLOR).orientation(ItemDivider.VERTICAL));

    /**
     * RecyclerView横向分割线
     */
    public static RecyclerView.ItemDecoration HORIZONTAL
            = Divider.create(Divider.with(c).colorRes(DEFAULT_COLOR).orientation(ItemDivider.HORIZONTAL));

    /**
     * RecyclerView垂直分割线,最后一个item没有分割线
     */
    public static RecyclerView.ItemDecoration VERTICAL_NO_LAST
            = Divider.create(Divider.with(c).colorRes(DEFAULT_COLOR).lastItemNoDivider(true));
}
