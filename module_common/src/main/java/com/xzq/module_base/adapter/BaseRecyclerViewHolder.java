package com.xzq.module_base.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import butterknife.internal.DebouncingOnClickListener;

/**
 * BaseRecyclerViewHolder
 *
 * @author xzq
 */

public abstract class BaseRecyclerViewHolder<T> extends RecyclerView.ViewHolder {

    protected T data;
    protected int position;
    private OnItemClickListener<T> itemClickListener;

    public OnItemClickListener<T> getItemClickListener() {
        return itemClickListener;
    }

    public BaseRecyclerViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doClick(View v) {
                if (itemClickListener != null && data != null) {
                    itemClickListener.onItemClicked(v, data, position);
                }
            }
        });
    }

    void bindData(OnItemClickListener<T> itemClickListener, T data, int position) {
        this.itemClickListener = itemClickListener;
        this.data = data;
        this.position = position;
    }

    public abstract void setData(T data);

    public interface OnItemClickListener<T> {
        void onItemClicked(View v, T data, int pos);
    }

}
