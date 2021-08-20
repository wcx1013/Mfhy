package com.xzq.module_base.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;

import androidx.core.view.ViewCompat;

/**
 * 树布局
 * Created by Alex on 2016/12/8.
 */

public class TreeLayout extends ViewGroup {

    private static final int OFFSET_DEFAULT = 92;
    private static final int PADDING_DEFAULT = 0;
    private boolean mExpand = false;
    private boolean right = false;
    private int offset;
    private int padding;
    private long itemDuration = 300;
    private long itemDuration2 = 200;
    private final OvershootInterpolator interpolator = new OvershootInterpolator(0);
    private final OvershootInterpolator interpolator2 = new OvershootInterpolator(0);
    private final AnimationListener listener = new AnimationListener();
    private long durationDelay = 20;
    private Animation lastAnimation;

    public TreeLayout(Context context) {
        super(context);
        offset = (int) (OFFSET_DEFAULT * getResources().getDisplayMetrics().density);
        padding = (int) (PADDING_DEFAULT * getResources().getDisplayMetrics().density);
        setRight(false);
    }

    public TreeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        offset = (int) (OFFSET_DEFAULT * getResources().getDisplayMetrics().density);
        padding = (int) (PADDING_DEFAULT * getResources().getDisplayMetrics().density);
        setRight(false);
    }

    @TargetApi(11)
    public TreeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        offset = (int) (OFFSET_DEFAULT * getResources().getDisplayMetrics().density);
        padding = (int) (PADDING_DEFAULT * getResources().getDisplayMetrics().density);
        setRight(false);
    }

    @TargetApi(21)
    public TreeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        offset = (int) (OFFSET_DEFAULT * getResources().getDisplayMetrics().density);
        padding = (int) (PADDING_DEFAULT * getResources().getDisplayMetrics().density);
        setRight(false);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    /**
     * Returns a set of layout parameters with a width of
     * {@link ViewGroup.LayoutParams#WRAP_CONTENT},
     * a height of {@link ViewGroup.LayoutParams#WRAP_CONTENT} and no spanning.
     */
    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    // Override to allow type-checking of LayoutParams.
    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
        if (lp instanceof LayoutParams) {
            return new LayoutParams((LayoutParams) lp);
        } else {
            return new LayoutParams(lp);
        }
    }

    public static class LayoutParams extends ViewGroup.LayoutParams {
        private int mLeft, mTop, mRight, mBottom;
        private int mRootLeft, mRootTop;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(LayoutParams source) {
            super(source);
        }

        void setLayoutRect(int left, int top, int right, int bottom) {
            mLeft = left;
            mTop = top;
            mRight = right;
            mBottom = bottom;
        }

        private void layout(View child) {
            child.layout(mLeft, mTop, mRight, mBottom);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        final int paddingStart = ViewCompat.getPaddingStart(this);
        final int paddingTop = getPaddingTop();
        final int paddingEnd = ViewCompat.getPaddingEnd(this);
        final int paddingBottom = getPaddingBottom();
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
                LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
                layoutParams.setLayoutRect(0, 0, 0, 0);
            }
        }
        int width = 0;
        int height = 0;
        int lastViewIndex = childCount - 1;
        if (!mExpand) {
            View child = getChildAt(lastViewIndex);
            if (child != null) {
                final int childWidth = child.getMeasuredWidth();
                final int childHeight = child.getMeasuredHeight();
                width = childWidth;
                height = childHeight;
                LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
                layoutParams.setLayoutRect(paddingStart, paddingTop,
                        paddingStart + childWidth, paddingTop + childHeight);
            }
        } else {
            View childRoot = getChildAt(lastViewIndex);
            final int childRootWidth = childRoot == null ? 0 : childRoot.getMeasuredWidth();
            final int childRootHeight = childRoot == null ? 0 : childRoot.getMeasuredHeight();

            View childTop = getChildAt(0);
            final int childTopHeight = childTop == null ? 0 : childTop.getMeasuredHeight();

            View childCenter = getChildAt(2);
            final int childCenterWidth = childCenter == null ? 0 : childCenter.getMeasuredWidth();

            View childBottom = getChildAt(4);
            final int childBottomHeight = childBottom == null ? 0 : childBottom.getMeasuredHeight();

            width = (int) (childCenterWidth * 0.5f + offset + childRootWidth * 0.5f);
            height = (int) (childTopHeight * 0.5f + offset + offset + childBottomHeight * 0.5f);

        }
        width += paddingStart + paddingEnd;
        height += paddingTop + paddingBottom;
        setMeasuredDimension(resolveSize(Math.max(width, getSuggestedMinimumWidth()), widthMeasureSpec),
                resolveSize(Math.max(height, getSuggestedMinimumWidth()), heightMeasureSpec));

        if (mExpand) {
            //mExpand不可设置为true，child数量不足
            final int measuredWidth = getMeasuredWidth();
            final int measuredHeight = getMeasuredHeight();
            final float centerY = paddingTop + (measuredHeight - paddingTop - paddingBottom) * 0.5f;
            View childRoot = getChildAt(5);
            final int childRootWidth = childRoot == null ? 0 : childRoot.getMeasuredWidth();
            final int childRootHeight = childRoot == null ? 0 : childRoot.getMeasuredHeight();

            View childTop = getChildAt(0);
            final int childTopWidth = childTop == null ? 0 : childTop.getMeasuredWidth();
            final int childTopHeight = childTop == null ? 0 : childTop.getMeasuredHeight();

            View childCenter = getChildAt(2);
            final int childCenterWidth = childCenter == null ? 0 : childCenter.getMeasuredWidth();
            final int childCenterHeight = childCenter == null ? 0 : childCenter.getMeasuredHeight();

            View childBottom = getChildAt(4);
            final int childBottomWidth = childCenter == null ? 0 : childCenter.getMeasuredWidth();
            final int childBottomHeight = childBottom == null ? 0 : childBottom.getMeasuredHeight();

            final double offset45 = offset * Math.sin(Math.toRadians(45));

            if (right) {
                final float rootCenterX = measuredWidth - paddingEnd - childRootWidth * 0.5f;
                if (childRoot != null) {
                    LayoutParams layoutParams = (LayoutParams) childRoot.getLayoutParams();
                    final int left = measuredWidth - paddingEnd - childRootWidth;
                    final int top = (int) (centerY - childRootHeight * 0.5f);
                    layoutParams.setLayoutRect(left, top,
                            left + childRootWidth, top + childRootHeight);
                }
                if (childTop != null) {
                    LayoutParams layoutParams = (LayoutParams) childTop.getLayoutParams();
                    final int left = measuredWidth - paddingEnd - childTopWidth;
                    final int top = paddingTop;
                    layoutParams.setLayoutRect(left, top,
                            left + childTopWidth, top + childTopHeight);
                }
                View leftTop = getChildAt(1);
                if (leftTop != null) {

                    final int leftTopWidth = leftTop.getMeasuredWidth();
                    final int leftTopHeight = leftTop.getMeasuredHeight();
                    LayoutParams layoutParams = (LayoutParams) leftTop.getLayoutParams();
                    final int left = (int) (rootCenterX - offset45 - leftTopWidth * 0.5f);
                    final int top = (int) (centerY - offset45 - leftTopHeight * 0.5f);
                    layoutParams.setLayoutRect(left, top,
                            left + leftTopWidth, top + leftTopHeight);
                }
                if (childCenter != null) {
                    LayoutParams layoutParams = (LayoutParams) childCenter.getLayoutParams();
                    final int left = paddingStart;
                    final int top = (int) (centerY - childCenterHeight * 0.5f);
                    layoutParams.setLayoutRect(left, top,
                            left + childCenterWidth, top + childCenterHeight);
                }
                View rightBottom = getChildAt(3);
                if (rightBottom != null) {
                    final int rightBottomWidth = rightBottom.getMeasuredWidth();
                    final int rightBottomHeight = rightBottom.getMeasuredHeight();
                    LayoutParams layoutParams = (LayoutParams) rightBottom.getLayoutParams();
                    final int left = (int) (rootCenterX - offset45 - rightBottomWidth * 0.5f);
                    final int top = (int) (centerY + offset45 - rightBottomHeight * 0.5f);
                    layoutParams.setLayoutRect(left, top,
                            left + rightBottomWidth, top + rightBottomHeight);
                }
                if (childBottom != null) {
                    LayoutParams layoutParams = (LayoutParams) childBottom.getLayoutParams();
                    final int left = measuredWidth - paddingEnd - childBottomWidth;
                    final int top = measuredHeight - paddingBottom - childBottomHeight;
                    layoutParams.setLayoutRect(left, top,
                            left + childBottomWidth, top + childBottomHeight);
                }
            } else {
                final float rootCenterX = paddingStart + childRootWidth * 0.5f;
                if (childRoot != null) {
                    LayoutParams layoutParams = (LayoutParams) childRoot.getLayoutParams();
                    final int left = paddingStart;
                    final int top = (int) (centerY - childRootHeight * 0.5f);
                    layoutParams.setLayoutRect(left, top,
                            left + childRootWidth, top + childRootHeight);
                }
                if (childTop != null) {
                    LayoutParams layoutParams = (LayoutParams) childTop.getLayoutParams();
                    final int left = paddingStart;
                    final int top = paddingTop;
                    layoutParams.setLayoutRect(left, top,
                            left + childTopWidth, top + childTopHeight);
                }
                View leftTop = getChildAt(1);
                if (leftTop != null) {
                    final int leftTopWidth = leftTop.getMeasuredWidth();
                    final int leftTopHeight = leftTop.getMeasuredHeight();
                    LayoutParams layoutParams = (LayoutParams) leftTop.getLayoutParams();
                    final int left = (int) (rootCenterX + offset45 - leftTopWidth * 0.5f);
                    final int top = (int) (centerY - offset45 - leftTopHeight * 0.5f);
                    layoutParams.setLayoutRect(left, top,
                            left + leftTopWidth, top + leftTopHeight);
                }
                if (childCenter != null) {
                    LayoutParams layoutParams = (LayoutParams) childCenter.getLayoutParams();
                    final int left = measuredWidth - paddingEnd - childRootWidth;
                    final int top = (int) (centerY - childCenterHeight * 0.5f);
                    layoutParams.setLayoutRect(left, top,
                            left + childCenterWidth, top + childCenterHeight);
                }
                View rightBottom = getChildAt(3);
                if (rightBottom != null) {
                    final int rightBottomWidth = rightBottom.getMeasuredWidth();
                    final int rightBottomHeight = rightBottom.getMeasuredHeight();
                    LayoutParams layoutParams = (LayoutParams) rightBottom.getLayoutParams();
                    final int left = (int) (rootCenterX + offset45 - rightBottomWidth * 0.5f);
                    final int top = (int) (centerY + offset45 - rightBottomHeight * 0.5f);
                    layoutParams.setLayoutRect(left, top,
                            left + rightBottomWidth, top + rightBottomHeight);
                }
                if (childBottom != null) {
                    LayoutParams layoutParams = (LayoutParams) childBottom.getLayoutParams();
                    final int left = paddingStart;
                    final int top = measuredHeight - paddingBottom - childBottomHeight;
                    layoutParams.setLayoutRect(left, top,
                            left + childBottomWidth, top + childBottomHeight);
                }
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
                layoutParams.layout(child);
            }
        }
    }

    /**
     * 设置是否展开
     *
     * @param expand 展开
     */
    public void setExpand(boolean expand) {
        setExpand(expand, true);
    }

    /**
     * 设置是否展开
     *
     * @param expand 展开
     */
    public void setExpand(boolean expand, boolean animator) {
        if (mExpand == expand)
            return;
        mExpand = expand;
        if (animator & !mExpand) {
            // 关闭动画
            float toX;
            float toY;
            int index;
            index = 0;
            View childTop = getChildAt(index);
            if (childTop != null) {
                toX = 0;
                toY = offset;
                childTop.startAnimation(getAnimationClose(0, toX, 0, toY,
                        durationDelay * index, index));
            }
            index = 1;
            View childLeftTop = getChildAt(index);
            if (childLeftTop != null) {
                final float offset45 = (float) (offset * Math.sin(Math.toRadians(45)));
                toX = right ? offset45 : -offset45;
                toY = offset45;
                childLeftTop.startAnimation(getAnimationClose(0, toX, 0, toY,
                        durationDelay * index, index));
            }
            index = 2;
            View childCenter = getChildAt(index);
            if (childCenter != null) {
                toX = right ? offset : -offset;
                toY = 0;
                childCenter.startAnimation(getAnimationClose(0, toX, 0, toY,
                        durationDelay * index, index));
            }
            index = 3;
            View childRightBottom = getChildAt(index);
            if (childRightBottom != null) {
                final float offset45 = (float) (offset * Math.sin(Math.toRadians(45)));
                toX = right ? offset45 : -offset45;
                toY = -offset45;
                childRightBottom.startAnimation(getAnimationClose(0, toX, 0, toY,
                        durationDelay * index, index));
            }
            index = 4;
            View childBottom = getChildAt(index);
            if (childBottom != null) {
                toX = 0;
                toY = -offset;
                childBottom.startAnimation(getAnimationClose(0, toX, 0, toY,
                        durationDelay * index, index));
            }
            return;
        }
        if (animator & mExpand) {
            if (right) {
                setPadding(padding, padding, 0, padding);
            } else {
                setPadding(0, padding, padding, padding);
            }
            // 打开动画
            float formX;
            float formY;
            int index;
            index = 0;
            View childTop = getChildAt(index);
            if (childTop != null) {
                formX = 0;
                formY = offset;
                childTop.startAnimation(getAnimationOpen(formX, 0, formY, 0,
                        durationDelay * index, index));
            }
            index = 1;
            View childLeftTop = getChildAt(index);
            if (childLeftTop != null) {
                final float offset45 = (float) (offset * Math.sin(Math.toRadians(45)));
                formX = right ? offset45 : -offset45;
                formY = offset45;
                childLeftTop.startAnimation(getAnimationOpen(formX, 0, formY, 0,
                        durationDelay * index, index));
            }
            index = 2;
            View childCenter = getChildAt(index);
            if (childCenter != null) {
                formX = right ? offset : -offset;
                formY = 0;
                childCenter.startAnimation(getAnimationOpen(formX, 0, formY, 0,
                        durationDelay * index, index));
            }
            index = 3;
            View childRightBottom = getChildAt(index);
            if (childRightBottom != null) {
                final float offset45 = (float) (offset * Math.sin(Math.toRadians(45)));
                formX = right ? offset45 : -offset45;
                formY = -offset45;
                childRightBottom.startAnimation(getAnimationOpen(formX, 0, formY, 0,
                        durationDelay * index, index));
            }
            index = 4;
            View childBottom = getChildAt(index);
            if (childBottom != null) {
                formX = 0;
                formY = -offset;
                childBottom.startAnimation(getAnimationOpen(formX, 0, formY, 0,
                        durationDelay * index, index));
            }
        }
        requestLayout();
    }

    private Animation getAnimationOpen(float formX, float toX, float formY, float toY,
                                       long startOffset, int index) {
        TranslateAnimation translate = new TranslateAnimation(formX, toX, formY, toY);
        RotateAnimation rotate = new RotateAnimation(-360, 0,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        AnimationSet set = new AnimationSet(true, index);
        set.addAnimation(rotate);
        set.addAnimation(translate);
        set.setDuration(itemDuration);
        set.setInterpolator(interpolator);
        set.setStartOffset(startOffset);
        return set;
    }

    private Animation getAnimationClose(float formX, float toX, float formY, float toY,
                                        long startOffset, int index) {
        TranslateAnimation translate = new TranslateAnimation(formX, toX, formY, toY);
        RotateAnimation rotate = new RotateAnimation(0, -360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        AnimationSet set = new AnimationSet(true, index);
        set.addAnimation(rotate);
        set.addAnimation(translate);
        set.setDuration(itemDuration2);
        set.setInterpolator(interpolator2);
        set.setStartOffset(startOffset);
        set.setAnimationListener(listener);
        lastAnimation = set;
        return set;

    }

    protected class AnimationSet extends android.view.animation.AnimationSet {

        int index;

        AnimationSet(boolean shareInterpolator, int index) {
            super(shareInterpolator);
            this.index = index;
        }
    }

    protected class AnimationListener implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if (animation instanceof AnimationSet) {
                AnimationSet animationSet = (AnimationSet) animation;
                View child = getChildAt(animationSet.index);
                if (child != null)
                    child.setVisibility(INVISIBLE);
            }
            if (animation == lastAnimation) {
                requestLayout();
                int itemCount = getChildCount();
                for (int i = 0; i < itemCount; i++)
                    getChildAt(i).setVisibility(VISIBLE);
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

    /**
     * 判断是否已展开
     *
     * @return 是否已展开
     */
    public boolean isExpand() {
        return mExpand;
    }

    public void setRight(boolean right) {
        if (this.right != right) {
            this.right = right;
            requestLayout();
        }
    }
}
