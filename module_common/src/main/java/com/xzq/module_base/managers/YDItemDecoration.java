package com.xzq.module_base.managers;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Px;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * 通用的{@link RecyclerView}Item装饰器{@link RecyclerView.ItemDecoration}
 * 支持以下布局管理器
 * {@link StaggeredGridLayoutManager}
 * {@link GridLayoutManager}
 * {@link LinearLayoutManager}
 *
 * @author xzq
 */
public class YDItemDecoration extends RecyclerView.ItemDecoration {

    private final Builder builder;
    private boolean addOnScrollListener = false;

    private YDItemDecoration(@NonNull Builder builder) {
        this.builder = builder;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect,
                               @NonNull View view,
                               @NonNull RecyclerView parent,
                               @NonNull RecyclerView.State state) {
        int layoutPos = parent.getChildLayoutPosition(view);
        int spanCount = builder.spanCount;
        int left = builder.rect.left;
        int top = builder.rect.top;
        int right = builder.rect.right;
        int verticalSpace = builder.verticalSpace;
        int halfVerticalSpace = verticalSpace / 2;
        int horizontalSpace = builder.horizontalSpace;
        int halfHorizontalSpace = horizontalSpace / 2;
        if (spanCount <= 1) {
            outRect.left = left;
            outRect.right = right;
            outRect.top = layoutPos == 0 ? top : halfVerticalSpace;
            outRect.bottom = halfVerticalSpace;
            return;
        }
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        int pos;
        if (lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            //spanIndex并非pos
            pos = ((StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams()).getSpanIndex();
            if (spanCount > 2 && !addOnScrollListener) {
                addOnScrollListener = true;
                parent.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        // 滑动停止，刷新布局与分割线
                        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                            if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                                ((StaggeredGridLayoutManager) recyclerView.getLayoutManager()).invalidateSpanAssignments();
                                recyclerView.invalidateItemDecorations();
                            }
                        }
                    }
                });
            }
        } else if (lp instanceof GridLayoutManager.LayoutParams) {
            pos = layoutPos;
        } else {
            outRect.left = left;
            outRect.right = right;
            outRect.top = layoutPos == 0 ? top : halfVerticalSpace;
            outRect.bottom = halfVerticalSpace;
            return;
        }
        int index = pos % spanCount;
        boolean isEdge = index == 0 || index == spanCount - 1;
        outRect.top = layoutPos < spanCount ? top : halfVerticalSpace;
        outRect.bottom = halfVerticalSpace;
        if (isEdge) {//边缘item
            outRect.left = index == 0 ? left : halfHorizontalSpace;
            outRect.right = index == 0 ? halfHorizontalSpace : right;
        } else { //居中的item
            outRect.left = halfHorizontalSpace;
            outRect.right = halfHorizontalSpace;
        }
    }

    public int getSpanCount() {
        return builder.spanCount;
    }

    public static YDItemDecoration create(@Px int offset) {
        return YDItemDecoration.create(offset, 1);
    }

    /**
     * 使用{@link Builder}方式一创建一个等同偏移量的的ItemDecoration
     *
     * @param offset    .
     * @param spanCount .
     * @return .
     */
    public static YDItemDecoration create(@Px int offset, int spanCount) {
        return new Builder()
                .spanCount(spanCount)
                .offset(offset)
                .build();
    }

    /**
     * 使用方式一创建一个等同偏移量的{@link RecyclerView.ItemDecoration}
     * <p>
     * RecyclerView.addItemDecoration(itemDecoration);
     * ItemDecoration itemDecoration=new YDItemDecoration.Builder()
     * .spanCount(spanCount)
     * .offset(offset)
     * .build()
     * <p>
     * 使用方式二创建一个不同偏移量的{@link RecyclerView.ItemDecoration}
     * <p>
     * RecyclerView.addItemDecoration(itemDecoration);
     * ItemDecoration itemDecoration=new YDItemDecoration.Builder()
     * .spanCount(spanCount)
     * .verticalSpace(verticalSpace)
     * .horizontalSpace(horizontalSpace)
     * .top(top)
     * .left(left)
     * .right(right)
     * .build();
     */
    public static class Builder {
        private final Rect rect = new Rect();
        private int spanCount = 0;
        private int verticalSpace = 0;
        private int horizontalSpace = 0;

        /**
         * 设置spanCount
         *
         * @param spanCount .
         * @return .
         */
        public Builder spanCount(int spanCount) {
            this.spanCount = spanCount;
            return this;
        }

        /**
         * 设置垂直方向的偏移量
         *
         * @param verticalSpace .
         * @return .
         */
        public Builder verticalSpace(@Px int verticalSpace) {
            this.verticalSpace = verticalSpace;
            return this;
        }

        /**
         * 设置水平方向的偏移量
         *
         * @param horizontalSpace .
         * @return .
         */
        public Builder horizontalSpace(@Px int horizontalSpace) {
            this.horizontalSpace = horizontalSpace;
            return this;
        }

        /**
         * 设置top偏移量
         *
         * @param top .
         * @return .
         */
        public Builder top(@Px int top) {
            rect.top = top;
            return this;
        }

        /**
         * 设置left偏移量
         *
         * @param left .
         * @return .
         */
        public Builder left(@Px int left) {
            rect.left = left;
            return this;
        }

        /**
         * 设置right偏移量
         *
         * @param right .
         * @return .
         */
        public Builder right(@Px int right) {
            rect.right = right;
            return this;
        }

        /**
         * 调用此方法设置新的偏移量之后会覆盖其它方法设置的偏移量
         *
         * @param offset 偏移量
         * @return .
         */
        public Builder offset(@Px int offset) {
            rect.set(offset, offset, offset, offset);
            verticalSpace(offset);
            horizontalSpace(offset);
            return this;
        }

        /**
         * build ItemDecoration
         *
         * @return .
         */
        public YDItemDecoration build() {
            return new YDItemDecoration(this);
        }
    }
}
