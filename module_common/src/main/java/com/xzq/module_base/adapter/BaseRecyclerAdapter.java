package com.xzq.module_base.adapter;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 列表适配器基类
 *
 * @author xzq
 */

@SuppressWarnings("all")
public abstract class BaseRecyclerAdapter<T, VH extends BaseRecyclerViewHolder<T>>
        extends RecyclerView.Adapter<VH> implements IAdapter<T> {

    protected List<T> mData = new ArrayList<>();
    private LayoutInflater mInflater = null;

    private BaseRecyclerViewHolder.OnItemClickListener<T> itemClickListener;

    public BaseRecyclerAdapter setOnItemClickListener(BaseRecyclerViewHolder.OnItemClickListener<T> itemClickListener) {
        this.itemClickListener = itemClickListener;
        return this;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }
        View itemView = null;
        final int layoutId = getItemLayoutId(viewType);
        if (layoutId > 0) {
            itemView = mInflater.inflate(layoutId, parent, false);
        }
        return onCreateViewHolder(parent, itemView, viewType);
    }

    @NonNull
    public abstract VH onCreateViewHolder(@NonNull ViewGroup parent, @Nullable View itemView, int viewType);

    @LayoutRes
    protected abstract int getItemLayoutId(int viewType);

    public abstract void onConvert(@NonNull VH holder, T data, int position, @NonNull List<Object> payload);

    @Override
    public void onBindViewHolder(VH holder, int position) {
        onBindViewHolder(holder, position, Collections.EMPTY_LIST);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position, @NonNull List<Object> payloads) {
        T data = getDataAt(position);
        holder.bindData(itemClickListener, data, position);
        onConvert(holder, data, position, payloads);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public List<T> getData() {
        return mData;
    }

    public boolean isEmpty() {
        return mData.isEmpty();
    }

    public T getDataAt(int index) {
        if (!checkIndex(index))
            return null;
        return mData.get(index);
    }

    public T getLastItem() {
        if (mData.isEmpty())
            return null;
        return mData.get(mData.size() - 1);
    }

    public boolean checkIndex(int index) {
        return index >= 0 && index < mData.size();
    }

    public int indexOf(T item) {
        return mData.indexOf(item);
    }

    public int removeItem(T item) {
        int index = indexOf(item);
        if (checkIndex(index)) {
            mData.remove(item);
            notifyItemRemoved(index);
            return index;
        }
        return -1;
    }

    public T removeItem(int index) {
        if (checkIndex(index)) {
            T t = mData.remove(index);
            notifyItemRemoved(index);
            return t;
        }
        return null;
    }

    @Override
    public void setData(List<T> data) {
        setData(data, false);
    }

    @Override
    public void setData(List<T> data, boolean hasNext) {
        if (data != null) {
            this.mData.clear();
            this.mData.addAll(data);
            if (hasNext) {
                notifyDataSetChanged();
            } else {
                notifyItemChanged(0, getItemCount());
            }
        }
    }

    @Override
    public boolean addData(List<T> data, boolean hasNext) {
        boolean hasChange = false;
        if (data != null) {
            int positionStart = getItemCount();
            int itemCount = data.size();
            if (mData.addAll(data)) {
                hasChange = true;
                notifyItemRangeChanged(positionStart, itemCount);
            }
        }
        return hasChange;
    }

    @Override
    public void onError() {
        //empty

    }

    @Override
    public void onEmpty() {
        //empty
    }

    @Override
    public void clear() {
        this.mData.clear();
        notifyDataSetChanged();
    }

}
