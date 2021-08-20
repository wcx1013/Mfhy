package com.xzq.module_base.managers;

import android.content.Context;
import androidx.recyclerview.widget.GridLayoutManager;
import android.util.AttributeSet;

/**
 * 禁止垂直方向可以接受触摸事件的GridLayoutManager
 * Created by Wesley on 2017/12/18.
 */

public class MyGridLayoutManager extends GridLayoutManager {

    public MyGridLayoutManager(Context context, AttributeSet attrs,
                               int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public MyGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public MyGridLayoutManager(Context context, int spanCount,
                               int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    @Override
    public boolean canScrollVertically() {
        return false;//返回false禁止掉
    }
}
