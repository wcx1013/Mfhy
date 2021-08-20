package com.xzq.module_base.managers;

import android.content.Context;
import android.widget.ImageView;

import com.xzq.module_base.utils.GlideUtils;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.List;

/**
 * BannerManager
 * Created by xzq on 2020/4/29.
 */
public class BannerManager {

    public static <T> void start(Banner banner, List<T> list, OnBannerClickListener<T> listener) {
        if (banner == null || list == null || list.isEmpty()) {
            return;
        }
        banner.setImages(list)
                .setImageLoader(glideImageLoader)
                .setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        if (listener != null && position >= 0 && position < list.size()) {
                            listener.onBannerClicked(position, list.get(position));
                        }
                    }
                })
                .start();
    }

    public interface OnBannerClickListener<T> {
        void onBannerClicked(int position, T data);
    }

    public interface IBannerUrl {
        String getBannerUrl();
    }

    private static final GlideImageLoader glideImageLoader = new GlideImageLoader();

    //轮播图图片加载器
    private static class GlideImageLoader extends ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            String url = path instanceof String
                    ? (String) path
                    : path instanceof IBannerUrl ? ((IBannerUrl) path).getBannerUrl() : null;
            GlideUtils.loadImage(imageView, url);
        }
    }
}
