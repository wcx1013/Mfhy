package com.xzq.module_base.utils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.TextUtils;

import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;

/**
 * ImageSaveUtils
 * Created by xzq on 2019/9/26.
 */

public class ImageSaveUtils {

    private static final String FOLDER_NAME = "qixing";

    private static String sStorePath = Environment.getExternalStorageDirectory().getAbsolutePath()
            + File.separator + FOLDER_NAME + File.separator;

    public static void save(String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        Glide.with(Utils.getApp()).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                String path = sStorePath + "img_export" + System.currentTimeMillis() + ".jpg";
                boolean isSaveSucceed = ImageUtils.save(resource, path, Bitmap.CompressFormat.JPEG);
                if (isSaveSucceed) {
                    MyToast.showSucceed("保存成功");
                    Uri uri = Uri.fromFile(new File(path));
                    Utils.getApp().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                } else {
                    MyToast.showFailed("保存失败");
                }
            }
        });
    }

}
