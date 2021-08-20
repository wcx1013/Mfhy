package com.xzq.module_base.base;

import android.os.Bundle;
import androidx.annotation.Nullable;

import com.xzq.module_base.mvp.MvpContract;

/**
 * MVP Fragment基类
 *
 * @author xzq
 */

public abstract class BasePresenterFragment<P extends MvpContract.CommonPresenter> extends LazyLoadFragment {

    protected P presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        presenter = createPresenter();
        presenter.attachView(this);
        super.onCreate(savedInstanceState);
    }

    @SuppressWarnings("unchecked")
    protected P createPresenter() {
        return (P) new MvpContract.CommonPresenter();
    }

    @Override
    public void onDestroy() {
        if (presenter != null) {
            presenter.onDestroy();
        }
        super.onDestroy();
    }

}
