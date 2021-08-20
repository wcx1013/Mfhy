package com.xzq.module_base.utils;

import android.app.Activity;
import android.text.TextUtils;

import androidx.appcompat.app.AlertDialog;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.xzq.module_base.R;

import java.util.List;

public class PermissionUtil {

    public interface PermissionCallback {
        void onGotPermission();
    }

    public static void requestCamera(PermissionCallback callback, String msg) {
        realRequest(callback, msg, PermissionConstants.CAMERA);
    }

    public static void requestCamera(PermissionCallback callback) {
        realRequest(callback, "同时打开相机和存储权限才可以拍照", PermissionConstants.STORAGE, PermissionConstants.CAMERA);
    }

    public static void requestAlbum(PermissionCallback callback) {
        requestStorage(callback, "打开存储权限才可以访问您的相册");
    }
    public static void requestLocationAndStorage(PermissionCallback callback) {
        realRequest(callback, "", PermissionConstants.LOCATION, PermissionConstants.STORAGE);
    }

    public static void requestStorage(PermissionCallback callback, String msg) {
        realRequest(callback, msg, PermissionConstants.STORAGE);
    }

    public static void requestMicrophone(PermissionCallback callback) {
        realRequest(callback, "打开相机和麦克风权限才可以正常进行直播和连麦", PermissionConstants.CAMERA, PermissionConstants.MICROPHONE);
    }

    private static void realRequest(PermissionCallback callback, String msg,
                                    @PermissionConstants.Permission final String... permissions) {
        final PermissionUtils pu = PermissionUtils.permission(permissions);
        final PermissionUtils.FullCallback fullCallback = new PermissionUtils.FullCallback() {
            @Override
            public void onGranted(List<String> permissionsGranted) {
                //授权成功
                if (callback != null) {
                    callback.onGotPermission();
                }
            }

            @Override
            public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                if (!permissionsDeniedForever.isEmpty()) {
                    //但凡有被永久拒绝的权限就引导用户去打开设置页面
                    showOpenAppSettingDialog(msg);
                    return;
                }
                if (!permissionsDenied.isEmpty()) {
                    //有拒绝的权限，弹出对话框，点击确定的时候重新申请
                    showRationaleDialog(() -> realRequest(callback, msg, permissions), null, msg);
                }
            }
        };
        pu.callback(fullCallback);
        pu.request();
    }

    private static void showOpenAppSettingDialog(String msg) {
        showOpenAppSettingDialog(null, msg);
    }

    private static void showOpenAppSettingDialog(String title, String msg) {
        Activity topActivity = ActivityUtils.getTopActivity();
        if (topActivity == null) return;
        AlertDialog dialog = new AlertDialog.Builder(topActivity, R.style.Theme_AppCompat_Light_Dialog_Alert)
                .setTitle(TextUtils.isEmpty(title) ? "权限申请" : title)
                .setMessage(msg)
                .setPositiveButton("确定", (dialog13, which) -> PermissionUtils.launchAppDetailsSettings())
                .setNegativeButton("取消", null)
                .create();
        dialog.show();
    }

    private static void showRationaleDialog(Runnable p, String title, String msg) {
        Activity topActivity = ActivityUtils.getTopActivity();
        if (topActivity == null) return;
        AlertDialog dialog = new AlertDialog.Builder(topActivity, R.style.Theme_AppCompat_Light_Dialog_Alert)
                .setTitle(TextUtils.isEmpty(title) ? "权限申请" : title)
                .setMessage(msg)
                .setPositiveButton("确定", (dialog13, which) -> p.run())
                .setNegativeButton("取消", null)
                .create();
        dialog.show();
    }
}
