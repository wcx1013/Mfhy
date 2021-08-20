package com.xzq.module_base.base;

import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.xzq.module_base.R;
import com.xzq.module_base.mvp.MvpContract;


/**
 * MVP Activity基类
 *
 * @author xzq
 */

public abstract class BasePresenterActivity<P extends MvpContract.CommonPresenter> extends BaseActivity {

    protected P presenter;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        presenter = createPresenter();
        presenter.attachView(this);
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.color_0F0C3A));


    }

    @SuppressWarnings("unchecked")
    protected P createPresenter() {
        return (P) new MvpContract.CommonPresenter();
    }

    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.onDestroy();
        }
        super.onDestroy();
    }

}
