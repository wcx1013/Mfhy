package com.xzq.module_base.utils;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.xzq.module_base.R;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * GlideUtils
 * Created by xzq on 2019/5/22.
 */
public class GlideUtils {

    private static RequestOptions getOptions() {
        return new RequestOptions().placeholder(R.color.color_placeholder);
    }

    public static void loadImage(ImageView iv, String url) {
        loadImage(iv, url, false);
    }

    public static void loadImage(ImageView iv, String url, boolean appendImageDomain) {
        Glide.with(iv.getContext()).load(appendImageDomain ? appendImageDomain(url) : url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(getOptions()).into(iv);
    }

    public static void loadImageCenterCrop(ImageView iv, String url) {
        Glide.with(iv.getContext()).load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(getOptions().centerCrop()).into(iv);
    }

    public static void loadHead(ImageView iv, String url) {
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(iv.getContext()).load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(new RequestOptions().placeholder(R.drawable.ic_head_placeholder).centerCrop()).into(iv);
    }

    public static void loadHead(ImageView iv, String url, boolean appendImageDomain) {
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(iv.getContext()).load(appendImageDomain ? appendImageDomain(url) : url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(new RequestOptions().placeholder(R.drawable.ic_head_placeholder).centerCrop()).into(iv);
    }

    public static void loadBroder(ImageView iv, String url) {
        Glide.with(iv.getContext())
                .load(url)
                .circleCrop()
                .transform(new GlideCircleWithBorder(iv.getContext(), AdaptScreenUtils.pt2Px(2),
                        ContextCompat.getColor(iv.getContext(), R.color.color_ffd13a)))
                .into(iv);
    }

    @SuppressWarnings("all")
    public static void loadBlur(ImageView iv, String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        Observable.just(url).map(new Function<String, Bitmap>() {
            @Override
            public Bitmap apply(String url) throws Exception {
                try {
                    Bitmap bitmap = Glide.with(iv.getContext())
                            .asBitmap()
                            .load(url)
                            .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                            .get();
                    return ImageUtils.fastBlur(bitmap, 0.5f, 25);
                } catch (Exception e) {
                    return null;
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Bitmap>() {
                    @Override
                    public void accept(Bitmap bitmap) throws Exception {
                        iv.setImageBitmap(bitmap);
                    }
                });
    }


    private final static String IMAGE_DOMAIN = "http://xxx";
    private final static String IMAGE_DOMAINS = "http://xxx";

    //移除url的域名
    public static String removeImageDomain(String url) {
        if (url != null) {
            if (url.contains(IMAGE_DOMAIN)) {
                url = url.replaceAll(IMAGE_DOMAIN, "");
            }
            if (url.contains(IMAGE_DOMAINS)) {
                url = url.replaceAll(IMAGE_DOMAINS, "");
            }
        }
        return url;
    }

    //添加url的域名
    public static String appendImageDomain(String url) {
        if (url != null && !url.contains(IMAGE_DOMAINS)) {
            return IMAGE_DOMAINS + url;
        }
        return url;
    }
}
