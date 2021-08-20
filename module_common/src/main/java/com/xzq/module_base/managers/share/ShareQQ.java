package com.xzq.module_base.managers.share;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

/**
 * ShareQQ
 * Created by xzq on 2020/7/24.
 */
public class ShareQQ {

    /**
     * 直接分享纯文本内容至QQ好友
     * @param mContext
     * @param content
     */
    public static void shareQQ(Context mContext, String content) {
        if (PlatformUtil.isInstallApp(mContext,PlatformUtil.PACKAGE_MOBILE_QQ)) {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
            intent.putExtra(Intent.EXTRA_TEXT, content);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(new ComponentName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity"));
            mContext.startActivity(intent);
        } else {
            Toast.makeText(mContext, "您需要安装QQ客户端", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 分享图片给QQ好友
     *
     * @param bitmap
     */
    public void shareImageToQQ(Context mContext,Bitmap bitmap) {
        if (PlatformUtil.isInstallApp(mContext,PlatformUtil.PACKAGE_MOBILE_QQ)) {
            try {
                Uri uriToImage = Uri.parse(MediaStore.Images.Media.insertImage(
                        mContext.getContentResolver(), bitmap, null, null));
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, uriToImage);
                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                shareIntent.setType("image/*");
                // 遍历所有支持发送图片的应用。找到需要的应用
                ComponentName componentName = new ComponentName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity");

                shareIntent.setComponent(componentName);
                // mContext.startActivity(shareIntent);
                mContext.startActivity(Intent.createChooser(shareIntent, "Share"));
            } catch (Exception e) {
//            ContextUtil.getInstance().showToastMsg("分享图片到**失败");
            }
        }
    }

    //之前有同学说在分享QQ和微信的时候，发现只要QQ或微信在打开的情况下，再调用分享只是打开了QQ和微信，却没有调用选择分享联系人的情况，这里，我要感觉一下@[努力搬砖]同学，他找出了原因。
    //解决办法如下：
     //       mActivity.startActivity(intent);//如果微信或者QQ已经唤醒或者打开，这样只能唤醒微信，不能分享
   // 请使用 mActivity.startActivity(Intent.createChooser(intent, "Share"));
}
