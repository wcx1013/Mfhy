package com.xzq.module_base.dialog.photo;

import androidx.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.xzq.module_base.R;
import com.xzq.module_base.adapter.BasePagerAdapter;


/**
 * PhotosAdapter
 * Created by xzq on 2018/10/9.
 */
public class PhotosAdapter extends BasePagerAdapter<String, PhotosViewHolder> {

    private View.OnClickListener onPhotoClickListener;

    public void setPhotoClickListener(View.OnClickListener onPhotoClickListener) {
        this.onPhotoClickListener = onPhotoClickListener;
    }

    @NonNull
    @Override
    public PhotosViewHolder onCreateViewHolder(ViewGroup parent, View itemView, int viewType) {
        return new PhotosViewHolder(itemView, onPhotoClickListener);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_common_photos;
    }

    @Override
    public void onConvert(@NonNull PhotosViewHolder holder, String data, int position) {
        holder.setData(data);
    }
}
