package com.xzq.module_base.dialog;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.scwang.smartrefresh.layout.internal.ProgressDrawable;
import com.xzq.module_base.R;

/**
 * 进度ImageView
 * Created by xzq on 2018/10/9.
 */

public class LoadingImageView extends AppCompatImageView {

    public LoadingImageView(Context context) {
        super(context);
        init(context);
    }

    public LoadingImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadingImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        ProgressDrawable progressDrawable = new ProgressDrawable();
        progressDrawable.setColor(ContextCompat.getColor(context, R.color.color_333333));
        setImageDrawable(progressDrawable);
    }

    public void setColor(@ColorRes int color) {
        Drawable d = getDrawable();
        if (d instanceof ProgressDrawable) {
            ((ProgressDrawable) d).setColor(ContextCompat.getColor(getContext(), color));
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        setVisibility(GONE);
        super.onDetachedFromWindow();
    }

    @Override
    public void setVisibility(int visibility) {
        if (visibility == VISIBLE) {
            Drawable drawable = getDrawable();
            if (drawable instanceof Animatable) {
                ((Animatable) drawable).start();
            }
        } else {
            Drawable drawable = getDrawable();
            if (drawable instanceof Animatable) {
                ((Animatable) drawable).stop();
            }
        }
        super.setVisibility(visibility);
    }
}
