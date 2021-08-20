package com.xzq.module_base.views;

import android.util.AttributeSet;
import android.content.Context;

import cn.jzvd.JzvdStd;

public class MyVideoPlayer extends JzvdStd {
    public MyVideoPlayer(Context context) {
        super(context);
    }

    public MyVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void changeUiToPlayingClear() {
        super.changeUiToPlayingClear();
        bottomProgressBar.setVisibility(INVISIBLE);
    }




    @Override
    public void dissmissControlView() {
        super.dissmissControlView();
        bottomProgressBar.setVisibility(INVISIBLE);
    }
}
