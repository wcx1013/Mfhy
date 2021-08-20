package com.xzq.module_base.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import androidx.core.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.xzq.module_base.R;


/**
 * DividerLinearLayout
 * Created by xzq on 2018/9/15.
 */

public class DividerLinearLayout extends LinearLayout {

    private int mDividerHeight;
    private int mDividerWidth;

    public DividerLinearLayout(Context context) {
        super(context);
    }

    public DividerLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DividerLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setDividerDrawable(Drawable divider) {
        if (divider != null) {
            mDividerWidth = divider.getIntrinsicWidth();
            mDividerHeight = divider.getIntrinsicHeight();
        } else {
            mDividerWidth = 0;
            mDividerHeight = 0;
        }
        super.setDividerDrawable(divider);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getDividerDrawable() != null) {
            if (getOrientation() == VERTICAL) {
                drawDividersVertical(canvas);
            } else {
                drawDividersHorizontal(canvas);
            }
        }
    }

    void drawDividersVertical(Canvas canvas) {
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child != null && child.getVisibility() != GONE) {
                if (hasDividerBeforeChildAt(i)) {
                    final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                    if (lp.previousShow()) {
                        final int top = child.getTop() - lp.topMargin - mDividerHeight;
                        drawHorizontalDivider(canvas, top);
                    }
                }
            }
        }

        if (hasDividerBeforeChildAt(count)) {
            final View child = getLastNonGoneChild();
            int bottom = 0;
            if (child == null) {
                bottom = getHeight() - getPaddingBottom() - mDividerHeight;
            } else {
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                bottom = child.getBottom() + lp.bottomMargin;
            }
            drawHorizontalDivider(canvas, bottom);
        }
    }

    void drawDividersHorizontal(Canvas canvas) {
        final int count = getChildCount();
        final boolean isLayoutRtl = isLayoutRtl(this);
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child != null && child.getVisibility() != GONE) {
                if (hasDividerBeforeChildAt(i)) {
                    final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                    if (lp.previousShow()) {
                        final int position;
                        if (isLayoutRtl) {
                            position = child.getRight() + lp.rightMargin;
                        } else {
                            position = child.getLeft() - lp.leftMargin - mDividerWidth;
                        }
                        drawVerticalDivider(canvas, position);
                    }
                }
            }
        }

        if (hasDividerBeforeChildAt(count)) {
            final View child = getLastNonGoneChild();
            int position;
            if (child == null) {
                if (isLayoutRtl) {
                    position = getPaddingLeft();
                } else {
                    position = getWidth() - getPaddingRight() - mDividerWidth;
                }
            } else {
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (isLayoutRtl) {
                    position = child.getLeft() - lp.leftMargin - mDividerWidth;
                } else {
                    position = child.getRight() + lp.rightMargin;
                }
            }
            drawVerticalDivider(canvas, position);
        }
    }


    void drawHorizontalDivider(Canvas canvas, int top) {
        Drawable mDivider = getDividerDrawable();
        int mDividerPadding = getDividerPadding();
        mDivider.setBounds(getPaddingLeft() + mDividerPadding, top,
                getWidth() - getPaddingRight() - mDividerPadding, top + mDividerHeight);
        mDivider.draw(canvas);
    }

    void drawVerticalDivider(Canvas canvas, int left) {
        Drawable mDivider = getDividerDrawable();
        int mDividerPadding = getDividerPadding();
        mDivider.setBounds(left, getPaddingTop() + mDividerPadding,
                left + mDividerWidth, getHeight() - getPaddingBottom() - mDividerPadding);
        mDivider.draw(canvas);
    }

    /**
     * Determines where to position dividers between children.
     *
     * @param childIndex Index of child to check for preceding divider
     * @return true if there should be a divider before the child at childIndex
     */
    protected boolean hasDividerBeforeChildAt(int childIndex) {

        int mShowDividers = getShowDividers();

        if (childIndex == getChildCount()) {
            // Check whether the end divider should draw.
            return (mShowDividers & SHOW_DIVIDER_END) != 0;
        }
        boolean allViewsAreGoneBefore = allViewsAreGoneBefore(childIndex);
        if (allViewsAreGoneBefore) {
            // This is the first view that's not gone, check if beginning divider is enabled.
            return (mShowDividers & SHOW_DIVIDER_BEGINNING) != 0;
        } else {
            return (mShowDividers & SHOW_DIVIDER_MIDDLE) != 0;
        }
    }

    /**
     * Checks whether toAll (virtual) child views before the given index are gone.
     */
    private boolean allViewsAreGoneBefore(int childIndex) {
        for (int i = childIndex - 1; i >= 0; i--) {
            final View child = getChildAt(i);
            if (child != null && child.getVisibility() != GONE) {
                return false;
            }
        }
        return true;
    }

    /**
     * Finds the last child that is not gone. The last child will be used as the reference for
     * where the end divider should be drawn.
     */
    private View getLastNonGoneChild() {
        for (int i = getChildCount() - 1; i >= 0; i--) {
            final View child = getChildAt(i);
            if (child != null && child.getVisibility() != GONE) {
                return child;
            }
        }
        return null;
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    /**
     * Per-child layout information associated with ViewLinearLayout.
     */
    public static class LayoutParams extends LinearLayout.LayoutParams {

        boolean previousHide;

        public boolean previousShow() {
            return !previousHide;
        }

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            TypedArray a =
                    c.obtainStyledAttributes(attrs, R.styleable.DividerLinearLayout);
            previousHide = a.getBoolean(R.styleable.DividerLinearLayout_layout_divider_previous_hide, false);
            a.recycle();
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public void setPreviousHide(boolean previousHide) {
            this.previousHide = previousHide;
        }
    }

    public static boolean isLayoutRtl(View view) {
        return ViewCompat.getLayoutDirection(view) == ViewCompat.LAYOUT_DIRECTION_RTL;
    }

}
