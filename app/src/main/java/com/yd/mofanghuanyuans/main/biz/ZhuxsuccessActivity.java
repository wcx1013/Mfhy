package com.yd.mofanghuanyuans.main.biz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.xzq.module_base.base.BasePresenterActivity;
import com.yd.mofanghuanyuans.R;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ZhuxsuccessActivity extends BasePresenterActivity {
    @BindView(R.id.fn)
    ImageView fn;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_zxsuccess;
    }

    @Override
    protected void initViews(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        hideToolbar();
    }


    @OnClick(R.id.fn)
    public void onClick() {
        Intent intent = new Intent(ZhuxsuccessActivity.this,LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
