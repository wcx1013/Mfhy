package com.xzq.module_base.utils.divider;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.util.TypedValue;

/**
 * 分割线辅助类
 *
 * @author xzq
 */

public class Divider {

    /**
     * 创建一个Builder，可以使用此Builder配置自己的分割线样式
     *
     * @param context Context
     * @return Builder
     */
    public static Builder with(Context context) {
        return new Builder(context);
    }

    public static class Builder {
        private Context context;
        //分割线drawable，默认值为null时则使用的是android.R.attr.listDivider
        private Drawable drawable = null;
        //上下左右的padding，默认值是0xp，(单位px)
        private Rect rect = new Rect();
        //分割线方向，默认竖直方向
        private int orientation = ItemDivider.VERTICAL;
        //分割线的宽/高，默认值是2px（单位px）
        private int size = 2;
        //最后一个item是否没有分割线，默认有分割线
        private boolean lastItemNoDivider = false;

        Builder(Context context) {
            this.context = context;
        }

        /**
         * drawable形式设置分割线
         *
         * @param drawable Drawable
         * @return Builder
         */
        public Builder drawable(@Nullable Drawable drawable) {
            this.drawable = drawable;
            return this;
        }

        /**
         * Drawable资源id{@link DrawableRes}形式设置分割线
         *
         * @param resId drawableId
         * @return Builder
         */
        public Builder drawableRes(@DrawableRes int resId) {
            return drawable(ContextCompat.getDrawable(context, resId));
        }

        /**
         * Color资源id{@link ColorRes}形式设置分割线
         *
         * @param resId colorId
         * @return Builder
         */
        public Builder colorRes(@ColorRes int resId) {
            return colorHex(ContextCompat.getColor(context, resId));
        }

        /**
         * 16进制颜色值{@link ColorInt}形式设置分割线
         *
         * @param color color
         * @return Builder
         */
        public Builder colorHex(@ColorInt int color) {
            this.drawable = new ColorDrawable(color);
            return this;
        }

        /**
         * 设置padding，单位dp
         *
         * @param dpRect Rect
         * @return Builder
         */
        public Builder dpPadding(Rect dpRect) {
            if (dpRect != null) {
                int leftPadding = dp2px(context, dpRect.left);
                int topPadding = dp2px(context, dpRect.top);
                int rightPadding = dp2px(context, dpRect.right);
                int bottomPadding = dp2px(context, dpRect.bottom);
                this.rect.set(leftPadding, topPadding, rightPadding, bottomPadding);
            }
            return this;
        }

        /**
         * 设置padding，单位px
         *
         * @param pxRect Rect
         * @return Builder
         */
        public Builder pxPadding(Rect pxRect) {
            if (pxRect != null) {
                this.rect.set(pxRect);
            }
            return this;
        }

        /**
         * 设置padding，单位dp
         *
         * @param dpVal dp值
         * @return Builder
         */
        public Builder dpPadding(int dpVal) {
            int padding = dp2px(context, dpVal);
            return pxPadding(padding);
        }

        /**
         * 设置padding，单位px
         *
         * @param pxVal px值
         * @return Builder
         */
        public Builder pxPadding(int pxVal) {
            this.rect.set(pxVal, pxVal, pxVal, pxVal);
            return this;
        }

        /**
         * 设置方向
         *
         * @param orientation 方向
         * @return Builder
         */
        public Builder orientation(int orientation) {
            this.orientation = orientation;
            return this;
        }

        /**
         * 设置宽/高
         *
         * @param pxVal px值
         * @return Builder
         */
        public Builder pxSize(int pxVal) {
            this.size = pxVal;
            return this;
        }

        /**
         * 设置宽/高
         *
         * @param dpVal dp值
         * @return Builder
         */
        public Builder dpSize(int dpVal) {
            this.size = dp2px(context, dpVal);
            return this;
        }

        /**
         * 设置最后一项是否没有分割线
         *
         * @param lastItemNoDivider 最后一项是否没有分割线
         * @return Builder
         */
        public Builder lastItemNoDivider(boolean lastItemNoDivider) {
            this.lastItemNoDivider = lastItemNoDivider;
            return this;
        }

        public Drawable getDrawable() {
            return drawable;
        }

        public Rect getRect() {
            return rect;
        }

        public int getOrientation() {
            return orientation;
        }

        public int getSize() {
            return size;
        }

        public boolean isLastItemNoDivider() {
            return lastItemNoDivider;
        }

        public RecyclerView.ItemDecoration build() {
            return Divider.create(this);
        }
    }

    private static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

    /**
     * 根据Builder配置参数构建分割线
     *
     * @param builder Builder
     * @return 分割线
     */
    public static RecyclerView.ItemDecoration create(Builder builder) {
        ItemDivider divider = new ItemDivider(builder.context, builder.orientation);
        divider.setSize(builder.size);
        divider.setPadding(builder.rect);
        divider.setLastItemNoDivider(builder.lastItemNoDivider);
        if (builder.drawable != null) {
            divider.setDrawable(builder.drawable);
        }
        return divider;
    }

    /**
     * 构建一个默认的分割线
     *
     * @param context Context
     * @return 分割线
     */
    public static RecyclerView.ItemDecoration create(Context context) {
        return create(new Builder(context));
    }

}
