package com.xzq.module_base.dialog.photo;

import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.xzq.module_base.R;
import com.xzq.module_base.adapter.RecyclePagerAdapter;
import com.xzq.module_base.dialog.LoadingImageView;


/**
 * PhotosViewHolder
 * Created by xzq on 2018/10/9.
 */
public class PhotosViewHolder extends RecyclePagerAdapter.PagerViewHolder<String> implements RequestListener<Drawable> {

    private PhotoView photoView;
    private LoadingImageView ivLoading;

    PhotosViewHolder(View itemView, View.OnClickListener listener) {
        super(itemView);
        photoView = itemView.findViewById(R.id.iv_pic);
        photoView.enable();
        photoView.setOnClickListener(listener);
        ivLoading = itemView.findViewById(R.id.iv_loading);
        ivLoading.setColor(R.color.white);
    }

    @Override
    public void setData(String path) {
        photoView.setImageDrawable(null);
        if (!TextUtils.isEmpty(path)) {
            ivLoading.setVisibility(View.VISIBLE);
            Glide.with(photoView.getContext()).load(path).listener(this).into(photoView);
        }
    }

    @Override
    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target,
                                boolean isFirstResource) {
        ivLoading.setVisibility(View.GONE);
        return false;
    }

    @Override
    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
                                   DataSource dataSource, boolean isFirstResource) {
        ivLoading.setVisibility(View.GONE);
        return false;
    }
}
