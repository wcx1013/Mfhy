package com.xzq.module_base.act;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;

import com.bm.library.Info;
import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.xzq.module_base.R;
import com.xzq.module_base.dialog.LoadingImageView;
import com.xzq.module_base.eventbus.EventUtil;
import com.xzq.module_base.eventbus.MessageEvent;
import com.xzq.module_base.utils.AnimatorUtils;
import com.xzq.module_base.utils.ImageSaveUtils;
import com.xzq.module_base.utils.PermissionUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 查看单个大图
 *
 * @author xzq
 */
public class ViewSingleImageActivity extends Activity implements RequestListener<Drawable> {

    private LoadingImageView ivLoading;
    private PhotoView photoView;
    private String path;
    private View mBg;
    private Info info;
    private static final int DURATION = 200;

    public static void start(Context context, String path, Info info, boolean showSave) {
        start(context, path, info, DURATION, showSave);
    }

    public static void start(Context context, String path, Info info, int duration, boolean showSave) {
        EventUtil.postSticky("fromInfo", info);
        Intent starter = new Intent(context, ViewSingleImageActivity.class);
        starter.putExtra("path", path);
        starter.putExtra("duration", duration);
        starter.putExtra("showSave", showSave);
        context.startActivity(starter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageStickyEvent(@NonNull MessageEvent event) {
        if (event.equals("fromInfo")) {
            info = (Info) event.getData();
            EventUtil.removeStickyEvent(event);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this)
                .fitsSystemWindows(false)
                .hideBar(BarHide.FLAG_HIDE_STATUS_BAR)
                .statusBarColor(R.color.transparent)
                .statusBarDarkFont(false).init();
        overridePendingTransition(0, 0);
        EventUtil.register(this);
        if (info == null) {
            finish();
            return;
        }
        setContentView(R.layout.activity_view_big_image);

        View btnSave = findViewById(R.id.img_btn_save);
        if (btnSave != null) {
            boolean showSave = getIntent().getBooleanExtra("showSave", false);
            btnSave.setVisibility(showSave ? View.VISIBLE : View.GONE);
            btnSave.setOnClickListener(saveListener);
        }
        mBg = findViewById(R.id.bg);
        photoView = findViewById(R.id.photoView);
        photoView.enable();
        path = getIntent().getStringExtra("path");
        ivLoading = findViewById(R.id.v_loading);
        ivLoading.setColor(R.color.white);
        ivLoading.setVisibility(View.VISIBLE);
        Glide.with(this).load(path).listener(this).into(photoView);
        photoView.setAnimaDuring(getIntent().getIntExtra("duration", DURATION));
        photoView.animaFrom(info);
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animToFinish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        animToFinish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventUtil.unregister(this);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    private boolean finishing = false;

    private void animToFinish() {
        if (finishing) {
            return;
        }
        finishing = true;
        photoView.disenable();
        AnimatorUtils.alphaHide(mBg, photoView.getAnimaDuring());
        if (photoView.getDrawable() != null) {
            photoView.animaTo(info, finishTask);
        } else {
            finishTask.run();
        }
    }

    private final Runnable finishTask = new Runnable() {
        @Override
        public void run() {
            photoView.setVisibility(View.GONE);
            finish();
        }
    };

    @Override
    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target,
                                boolean isFirstResource) {
        ivLoading.setVisibility(View.GONE);
        return false;
    }

    @Override
    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
                                   DataSource dataSource, boolean isFirstResource) {
        ivLoading.setVisibility(View.GONE);
        return false;
    }

    private final View.OnClickListener saveListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            PermissionUtil.requestStorage(() -> ImageSaveUtils.save(path), "请先打开存储权限再保存");
        }
    };

}
