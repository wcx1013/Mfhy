package com.yd.mofanghuanyuans.main.biz;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.gyf.immersionbar.ImmersionBar;
import com.xzq.module_base.base.BasePresenterActivity;
import com.xzq.module_base.utils.GlideUtils;
import com.xzq.module_base.utils.ResUtil;
import com.xzq.module_base.views.MyVideoPlayer;
import com.yd.mofanghuanyuans.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.Jzvd;

import static cn.jzvd.Jzvd.SCREEN_NORMAL;

public class VideoActivity extends BasePresenterActivity {
    @BindView(R.id.jz_video)
    MyVideoPlayer mJzvdStd;
    @BindView(R.id.view)
    View mView;
    @BindView(R.id.iv_back)
    ImageView ivBack;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        String url = getIntent().getStringExtra("url");
        hideToolbar();
        ViewGroup.LayoutParams layoutParams = mView.getLayoutParams();
        layoutParams.height = ImmersionBar.getStatusBarHeight(this);
        mView.setLayoutParams(layoutParams);
       // ImmersionBar.with(this).fitsSystemWindows(false).statusBarColorInt(ResUtil.getColor(R.color.transparent)).statusBarDarkFont(false).init();
        mJzvdStd.setUp(url, "", SCREEN_NORMAL);
        ImageView posterImageView = mJzvdStd.posterImageView;
        posterImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        GlideUtils.loadImage(posterImageView, url);
        mJzvdStd.startVideo();
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.goOnPlayOnPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Jzvd.goOnPlayOnResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Jzvd.releaseAllVideos();
    }



    @OnClick(R.id.iv_back)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackClick();
                break;
        }
    }
}
