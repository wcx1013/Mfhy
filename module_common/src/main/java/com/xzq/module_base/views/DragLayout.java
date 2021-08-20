package com.xzq.module_base.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcelable;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.xzq.module_base.R;


/**
 * 拖动视图
 * Created by Alex on 2016/12/7.
 */

public class DragLayout extends ViewGroup {

    private ViewDragHelper mDragHelper;
    private final Callback callback = new Callback();

    public DragLayout(Context context) {
        super(context);
        initView();
    }

    public DragLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public DragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @TargetApi(21)
    @SuppressWarnings("unused")
    public DragLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        mDragHelper = ViewDragHelper.create(this, 1f, callback);
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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int paddingStart = ViewCompat.getPaddingStart(this);
        final int paddingTop = getPaddingTop();
        final int paddingEnd = ViewCompat.getPaddingEnd(this);
        final int paddingBottom = getPaddingBottom();
        int width = 0;
        int height = 0;
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() == GONE)
                continue;
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
            final int childWidth = child.getMeasuredWidth() + layoutParams.mDragPaddingStart +
                    layoutParams.mDragPaddingEnd;
            final int childHeight = child.getMeasuredHeight() + layoutParams.mDragPaddingTop +
                    layoutParams.mDragPaddingBottom;
            width = width < childWidth ? childWidth : width;
            height = height < childHeight ? childHeight : height;
        }
        width += paddingStart + paddingEnd;
        height += paddingTop + paddingBottom;
        setMeasuredDimension(resolveSize(Math.max(width, getSuggestedMinimumWidth()),
                widthMeasureSpec),
                resolveSize(Math.max(height, getSuggestedMinimumHeight()), heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int width = getMeasuredWidth();
        final int height = getMeasuredHeight();
        final int paddingStart = ViewCompat.getPaddingStart(this);
        final int paddingTop = getPaddingTop();
        final int paddingEnd = ViewCompat.getPaddingEnd(this);
        final int paddingBottom = getPaddingBottom();

        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() == GONE)
                continue;
            final int childWidth = child.getMeasuredWidth();
            LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
            layoutParams.updateLocation(childWidth, child.getMeasuredHeight(),
                    width, height, paddingStart, paddingTop, paddingEnd, paddingBottom);
            final int childLeft = layoutParams.mLeft;
            final int childTop = layoutParams.mCenterY - child.getMeasuredHeight() / 2;
            child.layout(childLeft, childTop,
                    childLeft + childWidth, childTop + child.getMeasuredHeight());
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        return super.onSaveInstanceState();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragHelper.shouldInterceptTouchEvent(ev) | super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mDragHelper.getViewDragState() != ViewDragHelper.STATE_IDLE) {
            mDragHelper.processTouchEvent(event);
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public static class SaveLocation {
        private int mLeft = -1;
        private int mTop = -1;
        private boolean dragged = false;
        private int mCenterY = -1;
        private boolean saved = false;

        public void saveLocation(LayoutParams layoutParams) {
            mLeft = layoutParams.mLeft;
            mTop = layoutParams.mTop;
            dragged = layoutParams.dragged;
            mCenterY = layoutParams.mCenterY;
            saved = true;
        }

        public void restore(LayoutParams layoutParams) {
            if (saved) {
                layoutParams.mLeft = mLeft;
                layoutParams.mTop = mTop;
                layoutParams.dragged = dragged;
                layoutParams.mCenterY = mCenterY;
            }
        }
    }

    @SuppressWarnings("all")
    public static class LayoutParams extends ViewGroup.LayoutParams {

        private boolean draggable = true;
        private int mDragPaddingStart = 0;
        private int mDragPaddingTop = 0;
        private int mDragPaddingEnd = 0;
        private int mDragPaddingBottom = 0;
        private int mGravity = GravityCompat.START;

        private int mLeft = -1;
        private int mTop = -1;
        private boolean dragged = false;

        private int mCenterY = -1;
        private float mCenterLineX;


        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            int start = 0;
            int top = 0;
            int end = 0;
            int bottom = 0;
            int gravity = GravityCompat.START;
            TypedArray custom = c.obtainStyledAttributes(attrs, R.styleable.DragLayout_Layout);
            draggable = custom.getBoolean(R.styleable.DragLayout_Layout_alDraggable, true);
            if (custom.hasValue(R.styleable.DragLayout_Layout_dlDragPadding)) {
                final int padding = custom.getDimensionPixelOffset(
                        R.styleable.DragLayout_Layout_dlDragPadding, 0);
                start = padding;
                top = padding;
                end = padding;
                bottom = padding;
            }
            start = custom.getDimensionPixelOffset(R.styleable.DragLayout_Layout_dlDragPaddingStart,
                    start);
            top = custom.getDimensionPixelOffset(R.styleable.DragLayout_Layout_dlDragPaddingTop,
                    top);
            end = custom.getDimensionPixelOffset(R.styleable.DragLayout_Layout_dlDragPaddingEnd,
                    end);
            bottom = custom.getDimensionPixelOffset(R.styleable.DragLayout_Layout_dlDragPaddingBottom,
                    bottom);
            gravity = custom.getInt(R.styleable.DragLayout_Layout_dlLayout_gravity, gravity);
            custom.recycle();
            mDragPaddingStart = start;
            mDragPaddingTop = top;
            mDragPaddingEnd = end;
            mDragPaddingBottom = bottom;
            mGravity = gravity;
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(LayoutParams source) {
            super(source);
            draggable = source.draggable;
            mDragPaddingStart = source.mDragPaddingStart;
            mDragPaddingTop = source.mDragPaddingTop;
            mDragPaddingEnd = source.mDragPaddingEnd;
            mDragPaddingBottom = source.mDragPaddingBottom;
            mGravity = source.mGravity;
        }

        /**
         * 判断是否可拖动
         *
         * @return 是否可拖动
         */
        @SuppressWarnings("unused")
        public boolean isDraggable() {
            return draggable;
        }

        /**
         * 设置是否可拖动
         *
         * @param draggable 是否可拖动
         */
        @SuppressWarnings("unused")
        public void setDraggable(boolean draggable) {
            this.draggable = draggable;
        }

        /**
         * 设置拖动填充
         *
         * @param start  左
         * @param top    上
         * @param end    右
         * @param bottom 下
         */
        public void setDragPadding(int start, int top, int end, int bottom) {
            if (mDragPaddingStart != start || mDragPaddingTop != top ||
                    mDragPaddingEnd != end || mDragPaddingBottom != bottom) {
                mDragPaddingStart = start;
                mDragPaddingTop = top;
                mDragPaddingEnd = end;
                mDragPaddingBottom = bottom;
            }
        }

        public void setGravity(int gravity) {
            mGravity = gravity;
        }

        public void setDragged(boolean dragged) {
            this.dragged = dragged;
        }

        public boolean isDragged() {
            return dragged;
        }

        public boolean isCloseToStart() {
            return mLeft < mCenterLineX;
        }

        public void updateLocation(int childWidth, int childHeight,
                                   int parentWidth, int parentHeight,
                                   int paddingStart, int paddingTop,
                                   int paddingEnd, int paddingBottom) {
            mCenterLineX = paddingStart + mDragPaddingStart + (parentWidth - paddingStart -
                    paddingEnd - mDragPaddingStart - mDragPaddingEnd) * 0.5f;
            if (dragged)
                checkLocation(childWidth, childHeight, parentWidth, parentHeight,
                        paddingStart, paddingTop, paddingEnd, paddingBottom);
            else
                setLocationByGravity(childWidth, childHeight, parentWidth, parentHeight,
                        paddingStart, paddingTop, paddingEnd, paddingBottom);
        }

        public void setLocationByGravity(int childWidth, int childHeight,
                                         int parentWidth, int parentHeight,
                                         int paddingStart, int paddingTop,
                                         int paddingEnd, int paddingBottom) {
            switch (mGravity) {
                default:
                case GravityCompat.START:
                case Gravity.LEFT:
                case Gravity.TOP:
                case GravityCompat.START | Gravity.TOP:
                case Gravity.LEFT | Gravity.TOP:
                    // 左上角
                    mLeft = paddingStart + mDragPaddingStart;
                    mCenterY = paddingTop + mDragPaddingTop;
                    break;
                case Gravity.CENTER_HORIZONTAL:
                case Gravity.CENTER_HORIZONTAL | Gravity.TOP:
                    // 水平居中靠上
                    // TODO 贴边与不贴边区别 强制左上角
                    mLeft = paddingStart + mDragPaddingStart;
                    mCenterY = paddingTop + mDragPaddingTop;
                    break;
                case GravityCompat.END:
                case Gravity.RIGHT:
                case GravityCompat.END | Gravity.TOP:
                case Gravity.RIGHT | Gravity.TOP:
                    // 右上角
                    mLeft = parentWidth - paddingEnd - mDragPaddingEnd - childWidth;
                    mCenterY = paddingTop + mDragPaddingTop;
                    break;
                case Gravity.CENTER_VERTICAL:
                case Gravity.CENTER_VERTICAL | GravityCompat.START:
                case Gravity.CENTER_VERTICAL | Gravity.LEFT:
                    // 垂直居中靠左
                    mLeft = paddingStart + mDragPaddingStart;
                    mCenterY = paddingTop + mDragPaddingTop +
                            (parentHeight - paddingTop - paddingBottom
                                    - mDragPaddingTop - mDragPaddingBottom) / 2;
                    break;
                case Gravity.CENTER:
                    // 居中
                    // TODO 贴边与不贴边区别 强制左上角
                    mLeft = paddingStart + mDragPaddingStart;
                    mCenterY = paddingTop + mDragPaddingTop;
                    break;
                case Gravity.CENTER_VERTICAL | GravityCompat.END:
                case Gravity.CENTER_VERTICAL | Gravity.RIGHT:
                    // 垂直居中靠右
                    mLeft = parentWidth - paddingEnd - mDragPaddingEnd - childWidth;
                    mCenterY = paddingTop + mDragPaddingTop +
                            (parentHeight - paddingTop - paddingBottom
                                    - mDragPaddingTop - mDragPaddingBottom) / 2;
                    break;
                case Gravity.BOTTOM:
                case Gravity.BOTTOM | GravityCompat.START:
                case Gravity.BOTTOM | Gravity.LEFT:
                    // 左下角
                    mLeft = paddingStart + mDragPaddingStart;
                    mCenterY = parentHeight - paddingBottom - mDragPaddingBottom;
                    break;
                case Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL:
                    // 水平居中靠下
                    // TODO 贴边与不贴边区别 强制左下角
                    mLeft = paddingStart + mDragPaddingStart;
                    mCenterY = parentHeight - paddingBottom - mDragPaddingBottom;
                    break;
                case Gravity.BOTTOM | GravityCompat.END:
                case Gravity.BOTTOM | Gravity.RIGHT:
                    // 右下角
                    mLeft = parentWidth - paddingEnd - mDragPaddingEnd - childWidth;
                    mCenterY = parentHeight - paddingBottom - mDragPaddingBottom;
                    break;
            }
        }

        public void checkLocation(int childWidth, int childHeight,
                                  int parentWidth, int parentHeight,
                                  int paddingStart, int paddingTop,
                                  int paddingEnd, int paddingBottom) {
            final int start = paddingStart + mDragPaddingStart;
            final int top = paddingTop + mDragPaddingTop;
            final int end = parentWidth - paddingEnd - mDragPaddingEnd;
            final int bottom = parentHeight - paddingBottom - mDragPaddingBottom;
            mLeft = mLeft < start ? start : mLeft;
            mLeft = (mLeft + childWidth) > end ? (end - childWidth) : mLeft;
            mCenterY = mCenterY < top ? top : mCenterY;
            mCenterY = mCenterY > bottom ? bottom : mCenterY;

            // TODO 处理贴边
            if (mLeft > start && (mLeft + childWidth) < end) {
                if (mLeft - start < end - (mLeft + childWidth)) {
                    mLeft = start;
                } else {
                    mLeft = end - childWidth;
                }
            }
        }

        public void updateLocation(int[] location, float xvel, float yvel,
                                   int childWidth, int childHeight,
                                   int childLeft, int childTop,
                                   int parentWidth, int parentHeight,
                                   int paddingStart, int paddingTop,
                                   int paddingEnd, int paddingBottom) {
            final float centerLineX = mCenterLineX;


            final int dragStart = paddingStart + mDragPaddingStart;
            final int dragTop = paddingTop + mDragPaddingTop;
            final int dragEnd = parentWidth - paddingEnd - mDragPaddingEnd;
            final int dragBottom = parentHeight - paddingBottom - mDragPaddingBottom;

            final float centerX = childLeft + childWidth * 0.5f;
            final float centerY = childTop + childHeight * 0.5f;
            int velTop;
            int mEdge;
            int mCenterY;
            int finalLeft;
            int finalTop;
            if (xvel == 0 && yvel == 0) {
                // 无飞行操作
                velTop = childTop;
                if (centerX < centerLineX) {
                    // 左边
                    mEdge = dragStart;
                    finalLeft = mEdge;
                    finalTop = velTop;
                    finalTop = (float) finalTop < (dragTop - childHeight * 0.5f) ?
                            dragTop - (int) (childHeight * 0.5f) : finalTop;
                    finalTop = (float) finalTop > (dragBottom - childHeight * 0.5f) ?
                            dragBottom - (int) (childHeight * 0.5f) : finalTop;

                } else {
                    // 右边
                    mEdge = dragEnd;
                    finalLeft = mEdge - childWidth;
                    finalTop = velTop;
                    finalTop = (float) finalTop < (dragTop - childHeight * 0.5f) ?
                            dragTop - (int) (childHeight * 0.5f) : finalTop;
                    finalTop = (float) finalTop > (dragBottom - childHeight * 0.5f) ?
                            dragBottom - (int) (childHeight * 0.5f) : finalTop;
                }
            } else if (xvel == 0) {
                // 垂直飞行
                if (yvel > 0) {
                    // 向下垂直飞行
                    if (centerX < centerLineX) {
                        // 左边
                        mEdge = dragStart;
                        finalLeft = mEdge;
                        finalTop = dragBottom - (int) (childHeight * 0.5f);

                    } else {
                        // 右边
                        mEdge = dragEnd;
                        finalLeft = mEdge - childWidth;
                        finalTop = dragBottom - (int) (childHeight * 0.5f);
                    }
                } else {
                    // 向上垂直飞行
                    if (centerX < centerLineX) {
                        // 左边
                        mEdge = dragStart;
                        finalLeft = mEdge;
                        finalTop = dragTop - (int) (childHeight * 0.5f);

                    } else {
                        // 右边
                        mEdge = dragEnd;
                        finalLeft = mEdge - childWidth;
                        finalTop = dragTop - (int) (childHeight * 0.5f);
                    }
                }
            } else if (yvel == 0) {
                // 水平飞行
                velTop = childTop;
                if (xvel > 0) {
                    // 向右飞行
                    mEdge = dragEnd;
                    finalLeft = mEdge - childWidth;
                    finalTop = velTop;
                    finalTop = (float) finalTop < (dragTop - childHeight * 0.5f) ?
                            dragTop - (int) (childHeight * 0.5f) : finalTop;
                    finalTop = (float) finalTop > (dragBottom - childHeight * 0.5f) ?
                            dragBottom - (int) (childHeight * 0.5f) : finalTop;
                } else {
                    // 向左飞行
                    mEdge = dragStart;
                    finalLeft = mEdge;
                    finalTop = velTop;
                    finalTop = (float) finalTop < (dragTop - childHeight * 0.5f) ?
                            dragTop - (int) (childHeight * 0.5f) : finalTop;
                    finalTop = (float) finalTop > (dragBottom - childHeight * 0.5f) ?
                            dragBottom - (int) (childHeight * 0.5f) : finalTop;
                }
            } else if (xvel > 0) {
                // 第一象限、第四象限
                mEdge = dragEnd;
                finalLeft = mEdge - childWidth;
                if (centerX > parentWidth - paddingEnd) {
                    // 右边线外部
                    velTop = childTop;
                } else {
                    // 右边线内部
                    velTop = childTop - (int) (-yvel / xvel * (parentWidth - paddingEnd - centerX));
                }
                finalTop = velTop;
                finalTop = (float) finalTop < (dragTop - childHeight * 0.5f) ?
                        dragTop - (int) (childHeight * 0.5f) : finalTop;
                finalTop = (float) finalTop > (dragBottom - childHeight * 0.5f) ?
                        dragBottom - (int) (childHeight * 0.5f) : finalTop;
                if (centerX < centerLineX) {
                    // 在二、三象限
                    if (yvel < 0) {
                        // 飞往第一象限
                        boolean stay2 = -yvel / xvel > (centerY - dragTop) / (centerLineX - centerX);
                        if (centerY < dragTop || stay2) {
                            // 控制范围外飞行
                            // 不能飞入第一象限
                            // 留在第二象限顶部
                            mEdge = dragStart;
                            finalLeft = mEdge;
                            finalTop = dragTop - (int) (childHeight * 0.5f);
                        }
                    } else {
                        // 飞往第四象限
                        boolean stay3 = yvel / xvel > (dragBottom - centerY) / (centerLineX - centerX);
                        if (centerY > dragBottom || stay3) {
                            // 控制范围外飞行
                            // 不能飞入第四象限
                            // 留在第三象限底部
                            mEdge = dragStart;
                            finalLeft = mEdge;
                            finalTop = dragBottom - (int) (childHeight * 0.5f);
                        }
                    }
                }
            } else {
                // 第二象限、第三象限
                mEdge = dragStart;
                finalLeft = mEdge;
                if (centerX < paddingStart) {
                    // 左边线外部
                    velTop = childTop;
                } else {
                    // 左边线内部
                    velTop = childTop - (int) (yvel / xvel * (centerX - paddingStart));
                }
                finalTop = velTop;
                finalTop = (float) finalTop < (dragTop - childHeight * 0.5f) ?
                        dragTop - (int) (childHeight * 0.5f) : finalTop;
                finalTop = (float) finalTop > (dragBottom - childHeight * 0.5f) ?
                        dragBottom - (int) (childHeight * 0.5f) : finalTop;
                if (centerX > centerLineX) {
                    // 在一、四象限
                    if (yvel < 0) {
                        // 飞往第二象限
                        boolean stay1 = yvel / xvel > (centerY - dragTop) / (centerX - centerLineX);
                        if (centerY < dragTop || stay1) {
                            // 控制范围外飞行
                            // 不能飞入第二象限
                            // 留在第一象限顶部
                            mEdge = dragEnd;
                            finalLeft = mEdge - childWidth;
                            finalTop = dragTop - (int) (childHeight * 0.5f);
                        }
                    } else {
                        // 飞往第三象限
                        boolean stay4 = yvel / -xvel > (dragBottom - centerY) / (centerX - centerLineX);
                        if (centerY > dragBottom || stay4) {
                            // 控制范围外飞行
                            // 不能飞入第三象限
                            // 留在第四象限底部
                            mEdge = dragEnd;
                            finalLeft = mEdge - childWidth;
                            finalTop = dragBottom - (int) (childHeight * 0.5f);
                        }
                    }
                }

            }
            mCenterY = finalTop + (int) (childHeight * 0.5f);
            mLeft = finalLeft;
            this.mCenterY = mCenterY;
            location[0] = finalLeft;
            location[1] = finalTop;
        }
    }

    private class Callback extends ViewDragHelper.Callback {

        private int[] location = new int[2];

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
            boolean draggable = layoutParams.draggable;
            if (draggable) {
                layoutParams.setDragged(true);
            }
            return layoutParams.draggable;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return top;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            final int childWidth = releasedChild.getMeasuredWidth();
            final int childHeight = releasedChild.getMeasuredHeight();
            final int childLeft = releasedChild.getLeft();
            final int childTop = releasedChild.getTop();
            final int parentWidth = getMeasuredWidth();
            final int parentHeight = getMeasuredHeight();
            final int paddingStart = ViewCompat.getPaddingStart(DragLayout.this);
            final int paddingTop = getPaddingTop();
            final int paddingEnd = ViewCompat.getPaddingEnd(DragLayout.this);
            final int paddingBottom = getPaddingBottom();
            LayoutParams layoutParams = (LayoutParams) releasedChild.getLayoutParams();
            layoutParams.updateLocation(location, xvel, yvel,
                    childWidth, childHeight, childLeft, childTop,
                    parentWidth, parentHeight, paddingStart, paddingTop, paddingEnd, paddingBottom);
            mDragHelper.settleCapturedViewAt(location[0], location[1]);
            invalidate();
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return getMeasuredWidth();
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return getMeasuredHeight();
        }

        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
            if (state == ViewDragHelper.STATE_IDLE) {
                // 更新位置
                requestLayout();
            }
        }
    }


}
