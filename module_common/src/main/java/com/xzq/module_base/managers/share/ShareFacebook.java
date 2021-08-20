package com.xzq.module_base.managers.share;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.zhy.base.fileprovider.FileProvider7;

import java.io.File;

/**
 * ShareFacebook
 * Created by xzq on 2020/7/24.
 */
public class ShareFacebook {

    public static void shareFacebookMoment(Context context, File picFile) {
        if (PlatformUtil.checkFacebookInstallAndToast()) {
            Intent intent = new Intent();
            //分享到Facebook的发布页面
            //intent.setComponent(comp);
//            intent.setAction(Intent.ACTION_SEND_MULTIPLE);// 分享多张图片时使用
            intent.setPackage("com.facebook.katana");
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("image/*");
            //添加Uri图片地址--用于添加多张图片
            //ArrayList<Uri> imageUris = new ArrayList<>();
            //intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
            if (picFile != null) {
                if (picFile.isFile() && picFile.exists()) {
                    Uri uri = FileProvider7.getUriForFile(context, picFile);
                    intent.putExtra(Intent.EXTRA_STREAM, uri);
                }
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(intent);
            context.startActivity(Intent.createChooser(intent, "Select"));
        }
    }

}
