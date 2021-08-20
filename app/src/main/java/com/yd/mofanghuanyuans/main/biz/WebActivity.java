package com.yd.mofanghuanyuans.main.biz;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.xzq.module_base.base.BasePresenterActivity;
import com.xzq.module_base.utils.MyToast;
import com.yd.mofanghuanyuans.R;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebActivity extends BasePresenterActivity {
    @BindView(R.id.web)
    WebView web;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.iv_back)
    ImageView ivBack;

    @Override
    protected int getLayoutId() {
        return R.layout.layout_xiangt;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        String url = getIntent().getStringExtra("url");
        hideToolbar();
        // web.loadUrl(url);
        web.setWebViewClient(new WebViewClient());
        web.loadDataWithBaseURL(null, url, "text/html", "UTF-8", null);
    }




    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
