package com.xzq.module_base.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.xzq.module_base.R;

import java.lang.ref.SoftReference;
import java.util.List;

import am.widget.stateframelayout.StateFrameLayout;

/**
 * 列表适配器基类，带加载更多功能
 *
 * @author xzq
 */

@SuppressWarnings("all")
public class BaseRecyclerFooterAdapter<T>
        extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements IAdapter<T>,
        StateFrameLayout.OnStateClickListener {

    private static final int TYPE_FOOTER = 464564876;
    private LayoutInflater mInflater = null;
    private boolean hasNext = false;//是否有下一页
    private boolean showFooter = false;//是否显示页脚
    private boolean alwaysShowFooter = false;//是否总是显示页脚
    private boolean isLoadingMore = false;//是否正在加载更多,防止多次回调
    protected OnLoadMoreCallback loadMoreCallback;//加载更多回调
    private StateFrameLayout sflLoadMore;
    private final RecyclerView.Adapter mUserAdapter;

    public BaseRecyclerFooterAdapter(RecyclerView.Adapter adapter) {
        this(null, adapter);
    }

    public BaseRecyclerFooterAdapter(OnLoadMoreCallback loadMoreCallback, RecyclerView.Adapter adapter) {
        if (adapter == null) {
            throw new NullPointerException("adapter == null");
        }
        this.loadMoreCallback = loadMoreCallback;
        this.mUserAdapter = adapter;
        this.mUserAdapter.registerAdapterDataObserver(new MyAdapterDataObserver(new SoftReference<RecyclerView.Adapter>(this)));
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }

        switch (viewType) {
            default: {
                return mUserAdapter.onCreateViewHolder(parent, viewType);
            }

            case TYPE_FOOTER: {
                View itemView = mInflater.inflate(R.layout.item_common_loadmore, parent, false);
                sflLoadMore = (StateFrameLayout) itemView;
                sflLoadMore.setLoadingDrawable(new FooterProgressDrawable(parent.getContext()));
                sflLoadMore.setOnStateClickListener(this);
                return onCreateFooterViewHolder(itemView);
            }
        }
    }

    RecyclerView.ViewHolder onCreateFooterViewHolder(View itemView) {
        return new LoadMoreViewHolder(itemView);
    }

    public void setAlwaysShowFooter(boolean allwaysShowFooter) {
        this.alwaysShowFooter = allwaysShowFooter;
    }

    class LoadMoreViewHolder extends RecyclerView.ViewHolder {

        public LoadMoreViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //empty
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (isLastItem(position)) {
            if (hasNext) {
                if (loadMoreCallback != null && !isLoadingMore) {
                    isLoadingMore = true;
                    sflLoadMore.loading();
                    loadMoreCallback.onAutoLoadMore(sflLoadMore);
                }
            } else {
                sflLoadMore.normal();
                sflLoadMore.empty();
            }
        } else {
            mUserAdapter.onBindViewHolder(holder, position, payloads);
        }
    }

    @Override
    public int getItemCount() {
        return getItemCountOfAdapter() + getItemCountOfFooter();
    }

    public int getItemCountOfAdapter() {
        return mUserAdapter == null ? 0 : mUserAdapter.getItemCount();
    }

    public int getItemCountOfFooter() {
        return showFooter ? 1 : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return isLastItem(position) ? TYPE_FOOTER : mUserAdapter.getItemViewType(position);
    }

    public boolean isLastItem(int position) {
        return showFooter && (position == getItemCount() - 1);
    }

    public boolean isNormalItem(int position) {
        return mUserAdapter != null && mUserAdapter.getItemViewType(position) == 0;
    }

    @Override
    public void setData(List<T> data) {
        setData(data, false);
    }

    @Override
    public void setData(List<T> data, boolean hasNext) {
        isLoadingMore = false;
        if (data != null) {
            this.hasNext = hasNext;
            this.showFooter = alwaysShowFooter || hasNext;
            if (mUserAdapter instanceof IAdapter) {
                ((IAdapter) mUserAdapter).setData(data, hasNext);
            }
        }
    }

    @Override
    public boolean addData(List<T> data, boolean hasNext) {
        isLoadingMore = false;
        boolean notifyFooter = false;
        if (data != null) {
            this.hasNext = hasNext;
            this.showFooter = alwaysShowFooter || hasNext;
            if (mUserAdapter instanceof IAdapter) {
                notifyFooter = ((IAdapter) mUserAdapter).addData(data, hasNext);
            }
            if (notifyFooter) {
                notifyItemChanged(getItemCount() - 1);
            }
        }
        return notifyFooter;
    }

    @Override
    public void onError() {
        isLoadingMore = false;
        sflLoadMore.error();
    }

    @Override
    public void onEmpty() {
        isLoadingMore = false;
        this.hasNext = false;
        notifyItemChanged(getItemCount() - 1);
    }

    @Override
    public void clear() {
        showFooter = false;
        if (mUserAdapter instanceof IAdapter) {
            ((IAdapter) mUserAdapter).clear();
        }
    }

    @Override
    public void onErrorClick(StateFrameLayout layout) {
        if (layout != null) {
            layout.loading();
        }
        if (loadMoreCallback != null) {
            loadMoreCallback.onReloadMore(layout);
        }
    }

    /**
     * 加载更多回调
     */
    public interface OnLoadMoreCallback {

        /**
         * 自动加载更多
         *
         * @param loadMore StateFrameLayout
         */
        void onAutoLoadMore(StateFrameLayout loadMore);

        /**
         * 点击重新加载更多
         *
         * @param loadMore StateFrameLayout
         */
        void onReloadMore(StateFrameLayout loadMore);

    }

    public void setLoadMoreCallback(OnLoadMoreCallback loadMoreCallback) {
        this.loadMoreCallback = loadMoreCallback;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager lm = recyclerView.getLayoutManager();
        if (lm instanceof GridLayoutManager) {
            final GridLayoutManager glm = (GridLayoutManager) lm;
            final GridLayoutManager.SpanSizeLookup originLookup = glm.getSpanSizeLookup();
            glm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (isLastItem(position) || !isNormalItem(position)) return glm.getSpanCount();
                    if (originLookup != null) return originLookup.getSpanSize(position);
                    return 1;
                }
            });
        }
        if (mUserAdapter != null) mUserAdapter.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        if (mUserAdapter != null) mUserAdapter.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        int pos = holder.getLayoutPosition();
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams p =
                    (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(isLastItem(pos) || !isNormalItem(pos));
        }
    }

    private class MyAdapterDataObserver extends RecyclerView.AdapterDataObserver {

        private final SoftReference<RecyclerView.Adapter> adapterSoftReference;

        MyAdapterDataObserver(SoftReference<RecyclerView.Adapter> adapterSoftReference) {
            this.adapterSoftReference = adapterSoftReference;
        }

        public void onChanged() {
            if (alive()) {
                adapterSoftReference.get().notifyDataSetChanged();
            }
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
            if (alive()) {
                adapterSoftReference.get().notifyItemRangeChanged(positionStart, itemCount, payload);
            }
        }

        public void onItemRangeChanged(int positionStart, int itemCount) {
            if (alive()) {
                adapterSoftReference.get().notifyItemRangeChanged(positionStart, itemCount);
            }
        }

        public void onItemRangeInserted(int positionStart, int itemCount) {
            if (alive()) {
                adapterSoftReference.get().notifyItemRangeInserted(positionStart, itemCount);
            }
        }

        public void onItemRangeRemoved(int positionStart, int itemCount) {
            if (alive()) {
                adapterSoftReference.get().notifyItemRangeRemoved(positionStart, itemCount);
            }
        }

        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            if (alive()) {
                adapterSoftReference.get().notifyItemMoved(fromPosition, toPosition);
            }
        }

        private boolean alive() {
            return adapterSoftReference != null && adapterSoftReference.get() != null;
        }
    }

}
