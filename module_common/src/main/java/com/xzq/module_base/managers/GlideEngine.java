package com.xzq.module_base.managers;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.engine.ImageEngine;
import com.luck.picture.lib.listener.OnImageCompleteCallback;
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView;
import com.xzq.module_base.utils.GlideUtils;

/**
 * GlideEngine
 * Created by xzq on 2020/7/17.
 */
public class GlideEngine {

    private static class ImageLoaderHolder {
        private static ImageLoader sImageLoader = new ImageLoader();
    }

    private static class ImageLoader implements ImageEngine {

        @Override
        public void loadImage(@NonNull Context context,
                              @NonNull String url,
                              @NonNull ImageView imageView) {
            loadImg(imageView, url);
        }

        @Override
        public void loadImage(@NonNull Context context,
                              @NonNull String url,
                              @NonNull ImageView imageView,
                              SubsamplingScaleImageView longImageView,
                              OnImageCompleteCallback callback) {
            GlideUtils.loadImage(imageView, url);
        }

        @Override
        public void loadImage(@NonNull Context context,
                              @NonNull String url,
                              @NonNull ImageView imageView,
                              SubsamplingScaleImageView longImageView) {
            GlideUtils.loadImage(imageView, url);
        }

        @Override
        public void loadFolderImage(@NonNull Context context,
                                    @NonNull String url,
                                    @NonNull ImageView imageView) {
            loadImg(imageView, url);
        }

        @Override
        public void loadAsGifImage(@NonNull Context context,
                                   @NonNull String url,
                                   @NonNull ImageView imageView) {
            loadImg(imageView, url);
        }

        @Override
        public void loadGridImage(@NonNull Context context,
                                  @NonNull String url,
                                  @NonNull ImageView imageView) {
            GlideUtils.loadImage(imageView, url);
        }
    }

    private static RequestOptions options = new RequestOptions().centerCrop();
    private static DrawableTransitionOptions transitionOptions = new DrawableTransitionOptions().crossFade();

    private static void loadImg(@NonNull ImageView iv, @NonNull String url) {
        Glide.with(iv.getContext()).load(url)
                .transition(transitionOptions)
                .apply(options).into(iv);
    }

    public static ImageEngine createGlideEngine() {
        return ImageLoaderHolder.sImageLoader;
    }
}
