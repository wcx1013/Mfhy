package com.xzq.module_base.base;

import android.os.Bundle;
import androidx.annotation.Nullable;

import com.xzq.module_base.views.StateFrameLayout2;

/**
 * LazyLoadFragment
 * Created by sean on 2016/11/30.
 */

public abstract class LazyLoadFragment extends BaseFragment {

    // 当前Fragment是否可见
    protected boolean isVisibleToUser = false;
    // 是否加载过数据
    protected boolean isLoadData = false;
    // 是否UI初始化完毕
    protected boolean isViewInitialized = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        isVisibleToUser(isVisibleToUser);
        preLoadData(false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.isViewInitialized = true;
        //因为setUserVisibleHint比onActivityCreated优先调用，首次初始化的时候，UI还没有初始化，故在此为当前可见页面加载一次数据
        if (loadDataWhenCreate() && getUserVisibleHint()) {
            preLoadData(false);
        }
    }

    /**
     * 当UI初始化成功&&当前Fragment可见&&并且没有加载过数据的时候，加载数据
     *
     * @param forceLoad 强制加载数据
     */
    public void preLoadData(boolean forceLoad) {
        if ((!isLoadData || forceLoad) && isViewInitialized && isVisibleToUser) {
            loadData();
            isLoadData = true;
        }
    }

    @Override
    public void onErrorClick(StateFrameLayout2 layout) {
        super.onErrorClick(layout);
        preLoadData(isLoadData);
    }

    /**
     * 子类加载数据
     */
    protected abstract void loadData();

    /**
     * 子类刷新数据
     */
    public void refreshData() {
    }

    /**
     * 判断是否已经加载数据
     *
     * @return 是否已经加载数据
     */
    public boolean isLoadData() {
        return isLoadData;
    }

    protected boolean loadDataWhenCreate() {
        return false;
    }

    protected void isVisibleToUser(boolean isVisibleToUser) {

    }
}
