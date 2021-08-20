package com.xzq.module_base.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.scwang.smartrefresh.layout.internal.ProgressDrawable;
import com.xzq.module_base.R;

/**
 * LoadingDialog
 * Created by xzq on 2018/7/23.
 */

public class LoadingDialog extends Dialog {

    private ImageView mProgressView;

    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.LoadingDialogStyle);
        Window window = getWindow();
        if (window != null) {
            window.requestFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_loading);
            window.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.bg_loading_dialog));
            window.setGravity(Gravity.CENTER);
            setCanceledOnTouchOutside(false);
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.dimAmount = 0.0f;
            window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            mProgressView = findViewById(R.id.dl_loadingView);
            ProgressDrawable progressDrawable = new ProgressDrawable();
            progressDrawable.setColor(ContextCompat.getColor(context, R.color.color_cccccc));
            mProgressView.setImageDrawable(progressDrawable);
        }
    }

    @Override
    public void onAttachedToWindow() {
        final ImageView progressView = mProgressView;
        if (progressView != null) {
            Drawable drawable = progressView.getDrawable();
            if (drawable instanceof Animatable) {
                ((Animatable) drawable).start();
            }
        }
    }

    @Override
    public void onDetachedFromWindow() {
        final ImageView progressView = mProgressView;
        if (progressView != null) {
            Drawable drawable = progressView.getDrawable();
            if (drawable instanceof Animatable) {
                ((Animatable) drawable).stop();
            }
        }
    }
}
