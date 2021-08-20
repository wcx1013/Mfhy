package com.xzq.module_base.managers.share;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.Utils;
import com.xzq.module_base.utils.MyToast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.os.Environment.DIRECTORY_PICTURES;
import static android.os.Environment.getExternalStoragePublicDirectory;

/**
 * ShareToolUtil
 * Created by xzq on 2020/7/24.
 */
public class ShareToolUtil {
    private static String sharePicName = "share_pic.jpg";
    private static String sharePicPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "UmeBrowser" + File.separator + "sharepic" + File.separator;

    /**
     * 保存图片，并返回一个File类型的文件
     * 如今Android版本已经高达28了，但在使用该方法时，涉及到权限问题，本人在创建文件夹时遇到文件夹创建失败问题，遂将原因及解决方法记录如下：
     * 问题：Android6.0以后，文件夹创建失败。也就是使用file.mkdirs方法.
     * 解决方法：1.读写sdcard需要权限，但仅在manifest.xml里面添加是不够的，需要动态申请权限。2.可以将targetSdkVersion改为21或22或以下。
     * 否则在分享过程中获取不到图片就会弹出“获取资源失败”这样的提示。
     */
    public static File saveSharePic(Context context, Bitmap bitmap) {
        if (/*FileUtil.isSDcardExist()*/true) {
            File file = new File(sharePicPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            File filePic = new File(sharePicPath, sharePicName);
            if (filePic.exists()) {
                filePic.delete();
            }
            try {
                FileOutputStream out = new FileOutputStream(filePic);
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return filePic;
        }
        return null;
    }

    public static void saveAsImage(Bitmap bitmap) {
        String dir = "shooting";
        File imageFolder = new File(getExternalStoragePublicDirectory(DIRECTORY_PICTURES), dir);
        String filename = "scoreexport" + System.currentTimeMillis() + ".jpg";
        File imageFile = new File(imageFolder, filename);
        boolean isSaveSucceed = ImageUtils.save(bitmap, imageFile, Bitmap.CompressFormat.JPEG);
        if (isSaveSucceed) {
            MyToast.show("图片已保存至/sdcard/" + Environment.DIRECTORY_PICTURES + "/" + dir + "文件夹");
            Uri uri = Uri.fromFile(imageFile);
            Utils.getApp().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
        }
    }
}
