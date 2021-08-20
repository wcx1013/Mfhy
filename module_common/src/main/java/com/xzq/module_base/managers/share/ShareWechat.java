package com.xzq.module_base.managers.share;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.zhy.base.fileprovider.FileProvider7;

import java.io.File;

/**
 * ShareWechat
 * Created by xzq on 2020/7/24.
 */
public class ShareWechat {
    /**
     * 直接分享图片到微信好友
     *
     * @param context
     * @param picFile
     */
    public static void shareWechatFriend(Context context, String content, File picFile) {
        if (PlatformUtil.checkWechatInstallAndToast())  {
            Intent intent = new Intent();
            ComponentName cop = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
            intent.setComponent(cop);
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("image/*");
            if (picFile != null) {
                if (picFile.isFile() && picFile.exists()) {
                    Uri uri = FileProvider7.getUriForFile(context, picFile);
                    intent.putExtra(Intent.EXTRA_STREAM, uri);
//                    intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, Uri);
                }
            }
//            intent.putExtra("Kdescription", !TextUtils.isEmpty(content) ? content : "");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // context.startActivity(intent);
            context.startActivity(Intent.createChooser(intent, "Share"));
        }
    }

    /**
     * 直接分享文本到微信好友
     *
     * @param context 上下文
     */
    public static void shareWechatFriend(Context context, String content) {
        if (PlatformUtil.checkWechatInstallAndToast())  {
            Intent intent = new Intent();
            ComponentName cop = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
            intent.setComponent(cop);
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra("android.intent.extra.TEXT", content);
//            intent.putExtra("sms_body", content);
            intent.putExtra("Kdescription", !TextUtils.isEmpty(content) ? content : "");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    /**
     * 直接分享文本和图片到微信朋友圈
     *
     * @param context
     * @param content
     */
    public static void shareWechatMoment(Context context, String content, File picFile) {
        if (PlatformUtil.checkWechatInstallAndToast()) {
            Intent intent = new Intent();
            //分享精确到微信的页面，朋友圈页面，或者选择好友分享页面
            ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
            intent.setComponent(comp);
//            intent.setAction(Intent.ACTION_SEND_MULTIPLE);// 分享多张图片时使用
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
            // 微信现不能进行标题同时分享
            // intent.putExtra("Kdescription", !TextUtils.isEmpty(content) ? content : "");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }
}
