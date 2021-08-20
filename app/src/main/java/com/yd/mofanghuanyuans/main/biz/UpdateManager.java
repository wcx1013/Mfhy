package com.yd.mofanghuanyuans.main.biz;

import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.multidex.BuildConfig;

import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.DownloadBuilder;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.callback.CustomVersionDialogListener;
import com.allenliu.versionchecklib.v2.callback.RequestVersionListener;
import com.blankj.utilcode.util.AppUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.xzq.module_base.bean.UpdateResult;
import com.xzq.module_base.config.Config;
import com.xzq.module_base.utils.DialogClass;
import com.xzq.module_base.utils.MyToast;
import com.xzq.module_base.utils.divider.PermissionUtil;

import org.apache.http.params.HttpParams;

public class UpdateManager {
//    private static DownloadBuilder mBuilder;
//    private static boolean mForced;
//    private String status;
//    public static void update(Activity activity) {
//        PermissionUtil.requestStorage(() -> {
//            //                diyUpdate();
//            //拿到服务器返回的数据，解析，拿到downloadUrl和一些其他的UI数据
//            //如果是最新版本直接return null
//            com.allenliu.versionchecklib.core.http.HttpParams httpParams = new com.allenliu.versionchecklib.core.http.HttpParams();
//          //  HttpParams httpParams = new HttpParams();
//
//            httpParams.put("type", 4);
//            String updateUrl = Config.BASE_URL + "app-v/check";
//             mBuilder = AllenVersionChecker.getInstance().requestVersion().setRequestParams(httpParams).setRequestUrl(updateUrl)
//                    .request(new RequestVersionListener() {
//
//
//
//                        @Nullable
//                        @Override
//                        public UIData onRequestVersionSuccess(String result) {
//                            if (result.contains("html")) return null;
//                            //拿到服务器返回的数据，解析，拿到downloadUrl和一些其他的UI数据
//                            //如果是最新版本直接return null
//                            Gson gson = new Gson();
//
//                            UpdateResult updateResult = gson.fromJson(result, new TypeToken<UpdateResult>() {
//                            }.getType());
//                            if (updateResult == null) {
//                                return null;
//                            }
//                            UpdateResult.ResultDTO result1 = updateResult.getResult();
//                         //   UpdateResult.DataBean dataBean = updateResult.data;
//                            if (result1 == null) {
//                                return null;
//                            }
//                        //    mForced = dataBean.forced;
//                          //  status = result1.getStatus();
//                            if (result1.getVersion() > BuildConfig.VERSION_CODE) {
//                              UIData uiData = UIData
//                                      .create()
//                                      .setDownloadUrl(result1.getUrl())
//                                      .setTitle("更新提示")
//                                      .setContent(result1.getUpdateContent());
//                              return uiData;
//                          }else {
//                               return null;
//
//                        }
//
//
//                    }
//
//                        @Override
//                        public void onRequestVersionFailure(String message) {
//                            MyToast.show(message);
//                        }
//                    });
//
//            mBuilder.setForceUpdateListener(() -> {
//                if (status) {
//                    activity.finish();
//                }
//            })
//                    .setCustomVersionDialogListener(createCustomDialogOne())
//                    .setShowDownloadingDialog(false)
////                       .setDownloadAPKPath(getExternalCacheDir() + "/ekzt_" + System.currentTimeMillis() + ".apk")
//                    .setApkName(AppUtils.getAppName() + "_" + System.currentTimeMillis())
//                    .executeMission(activity);
//        }, "请打开存储权限");
//    }
//
//    /**
//     * 务必用库传回来的context 实例化你的dialog
//     * 自定义的dialog UI参数展示，使用versionBundle
//     *
//     * @return
//     */
//    private static CustomVersionDialogListener createCustomDialogOne() {
//        return (context, versionBundle) -> {
//            return DialogClass.showDialogUpdate(context, versionBundle.getContent());
//        };
//    }
}
