package com.xzq.module_base.managers;


import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.Utils;
import com.xzq.module_base.luban.Luban;
import com.xzq.module_base.luban.OnCompressListener;
import com.xzq.module_base.utils.LogUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * 鲁班压缩管理器
 *
 * @author xzq
 */

public class LubanManager {

    private static final String TAG = "LubanManager";
    private static final boolean DEBUG = AppUtils.isAppDebug();

    public interface OnCompressResultListener<T> {
        void onFinish(List<T> result);
    }

    public static <T> void compressSingle(T t, final OnCompressResultListener<T> listener) {
        if (t == null) {
            return;
        }
        List<T> paths = new ArrayList<>();
        paths.add(t);
        compress(paths, listener);
    }

    public static <T> void compress(final List<T> src,
                                    final OnCompressResultListener<T> listener) {

        if (src == null || src.isEmpty()) {
            if (listener != null) {
                listener.onFinish(new ArrayList<>());
            }
            return;
        }

        if (DEBUG) {
            for (int i = 0; i < src.size(); i++) {
                T t = src.get(i);
                File oldFile = t instanceof File ? (File) t : new File((String) t);
                if (!oldFile.exists()) {
                    continue;
                }
                LogUtils.debug(TAG, "压缩前\t" + "第" + (i + 1)
                        + "个图片的大小为：" + oldFile.length() / 1024 + "KB");
                LogUtils.debug(TAG, "压缩前\t" + "第" + (i + 1)
                        + "个图片的路径为：" + oldFile.getAbsolutePath());
            }
        }

        final T firstElementType = src.get(0);

        final List<T> resultList = new ArrayList<>();

        final OnCompressListener compressListener = new OnCompressListener() {

            int count = 0;

            @Override
            public void onStart() {
                count++;
            }

            @SuppressWarnings("unchecked")
            @Override
            public void onSuccess(File file) {
                if (DEBUG) {
                    //超过阈值的文件不压缩,原文件返回
                    LogUtils.debug(TAG, "压缩后\t" + "第" + count +
                            "个图片的大小为：" + file.length() / 1024 + "KB");
                    LogUtils.debug(TAG, "压缩后\t" + "第" + count +
                            "个图片的路径为：" + file.getAbsolutePath());
                }
                T t = firstElementType instanceof String ? (T) file.getAbsolutePath() : firstElementType;
                resultList.add(t);
                onFinish();
            }

            @Override
            public void onError(Throwable e) {
                onFinish();
            }

            private void onFinish() {
                if (count == src.size() && listener != null) {
                    listener.onFinish(resultList);
                }
            }
        };

        //启动鲁班压缩
        Luban.with(Utils.getApp())
                .load(src)
                .ignoreBy(100)
                .setCompressListener(compressListener)
                .setFocusAlpha(false)
                .quality(60)
                .launch();
    }

}
