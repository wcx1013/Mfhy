package com.xzq.module_base.adapter;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewPager适配器基类
 *
 * @author xzq
 */
@SuppressWarnings("all")
public abstract class BasePagerAdapter<T, VH extends RecyclePagerAdapter.PagerViewHolder>
        extends RecyclePagerAdapter<VH> {

    protected List<T> mData = new ArrayList<>();
    private LayoutInflater mInflater = null;

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }
        View itemView = null;
        if (getItemLayoutId() > 0) {
            itemView = mInflater.inflate(getItemLayoutId(), parent, false);
        }
        return onCreateViewHolder(parent, itemView, viewType);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        T data = mData.get(position);
        holder.bindData(data, position);
        onConvert(holder, data, position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @NonNull
    public abstract VH onCreateViewHolder(ViewGroup parent, View itemView, int viewType);

    @LayoutRes
    protected abstract int getItemLayoutId();

    public abstract void onConvert(@NonNull VH holder, T data, int position);

    public void setData(List<T> newData) {
        if (newData != null) {
            mData.clear();
            mData.addAll(newData);
            notifyDataSetChanged();
        }
    }

}
