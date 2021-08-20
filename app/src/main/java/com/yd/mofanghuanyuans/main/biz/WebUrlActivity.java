package com.yd.mofanghuanyuans.main.biz;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.xzq.module_base.base.BasePresenterActivity;
import com.yd.mofanghuanyuans.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebUrlActivity extends BasePresenterActivity {


    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.fan)
    ImageView fan;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_web_url;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        hideToolbar();





        String url = getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("title");
        toolbarTitle.setText(title);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }



    @OnClick(R.id.fan)
    public void onClick() {
        finish();
    }
}
