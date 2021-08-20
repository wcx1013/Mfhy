package com.xzq.module_base.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.xzq.module_base.R;

/**
 * 图片文本控件
 * 支持给TextView设置左上右下图片的时候设置固定宽高
 *
 * @author xzq
 */

public class DrawableTextView extends androidx.appcompat.widget.AppCompatTextView {

    public DrawableTextView(Context context) {
        super(context);
        init(context, null);
    }

    public DrawableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public DrawableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DrawableTextView);
        final Drawable drawableLeft = a.getDrawable(R.styleable.DrawableTextView_dtv_drawableLeft);
        final Drawable drawableTop = a.getDrawable(R.styleable.DrawableTextView_dtv_drawableTop);
        final Drawable drawableRight = a.getDrawable(R.styleable.DrawableTextView_dtv_drawableRight);
        final Drawable drawableBottom = a.getDrawable(R.styleable.DrawableTextView_dtv_drawableBottom);
        final int drawableWidth = a.getDimensionPixelOffset(R.styleable.DrawableTextView_dtv_drawableWidth, 0);
        final int drawableHeight = a.getDimensionPixelOffset(R.styleable.DrawableTextView_dtv_drawableHeight, 0);

        if (drawableWidth > 0 && drawableHeight > 0) {

            if (drawableLeft != null) {
                drawableLeft.setBounds(0, 0, drawableWidth, drawableHeight);
            }
            if (drawableTop != null) {
                drawableTop.setBounds(0, 0, drawableWidth, drawableHeight);
            }
            if (drawableRight != null) {
                drawableRight.setBounds(0, 0, drawableWidth, drawableHeight);
            }
            if (drawableBottom != null) {
                drawableBottom.setBounds(0, 0, drawableWidth, drawableHeight);
            }
        }

        if (drawableLeft != null || drawableTop != null || drawableRight != null || drawableBottom != null) {
            setCompoundDrawables(drawableLeft, drawableTop, drawableRight, drawableBottom);
        }

        a.recycle();
    }

    public void setDrawableLeft(Drawable drawableLeft, int drawableWidth, int drawableHeight) {
        if (drawableLeft != null) {
            drawableLeft.setBounds(0, 0, drawableWidth, drawableHeight);
        }
        setCompoundDrawables(drawableLeft, null, null, null);
    }

    public void setDrawableTop(Drawable drawableTop, int drawableWidth, int drawableHeight) {
        if (drawableTop != null) {
            drawableTop.setBounds(0, 0, drawableWidth, drawableHeight);
        }
        setCompoundDrawables(null, drawableTop, null, null);
    }

    public void setDrawableRight(Drawable drawableRight, int drawableWidth, int drawableHeight) {
        if (drawableRight != null) {
            drawableRight.setBounds(0, 0, drawableWidth, drawableHeight);
        }
        setCompoundDrawables(null, null, drawableRight, null);
    }

    public void setDrawableBottom(Drawable drawableBottom, int drawableWidth, int drawableHeight) {
        if (drawableBottom != null) {
            drawableBottom.setBounds(0, 0, drawableWidth, drawableHeight);
        }
        setCompoundDrawables(null, null, null, drawableBottom);
    }

    public void setCompoundDrawables(@Nullable Drawable left, @Nullable Drawable top,
                                     @Nullable Drawable right, @Nullable Drawable bottom,
                                     int drawableWidth, int drawableHeight) {
        if (left != null) {
            left.setBounds(0, 0, drawableWidth, drawableHeight);
        }
        if (top != null) {
            top.setBounds(0, 0, drawableWidth, drawableHeight);
        }
        if (right != null) {
            right.setBounds(0, 0, drawableWidth, drawableHeight);
        }
        if (bottom != null) {
            bottom.setBounds(0, 0, drawableWidth, drawableHeight);
        }
        setCompoundDrawables(left, top, right, bottom);
    }

}

