package com.xzq.module_base.mvp;

import com.xzq.module_base.User;
import com.xzq.module_base.api.ApiCallback;
import com.xzq.module_base.api.ApiService;
import com.xzq.module_base.api.NetBean;
import com.xzq.module_base.bean.DeviceDto;
import com.xzq.module_base.bean.TokenBean;
import com.xzq.module_base.managers.LubanManager;
import com.xzq.module_base.utils.XZQLog;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * MvpContract
 * Created by xzq on 2019/7/2.
 */
public interface MvpContract {

    interface CommonView extends IStateView, IPostLoadingView {
    }

    interface LoginView {
        /**
         * 登录成功
         *
         * @param data .
         */
       // void loginSucceed(LoginDto data);
        void loginSucceed(TokenBean data);

    }

    interface UploadImgView {
        /**
         * 图片上传成功
         *
         * @param remotePath 远程图片相对地址
         * @param code       .
         */
        void onUploadImgSucceed(String remotePath, int... code);
    }

    interface DeviceView {
        void onGetDeviceSucceed(List<DeviceDto> list);
    }

    class CommonPresenter<T extends CommonView> extends AbsPresenter<CommonView> {

        /**
         * 上传图片
         *
         * @param localImgPath 本地图片路径
         * @param code         .
         */
        public void uploadImg(String localImgPath, int... code) {
            mView.onShowPostLoading(null);
            LubanManager.compressSingle(localImgPath, result -> {
                for (String p : result) {
                    File file = new File(p);
                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
                    doAnyRequest(api -> api.uploadImg(body), String.class)
                            .subscribe(new PostLoadingCallback<String>() {
                                @Override
                                protected void onSuccess(NetBean<String> response, String data, int page) {
                                    XZQLog.debug("onUploadImgSucceed url = " + data);
                                    if (mView instanceof UploadImgView) {
                                        ((UploadImgView) mView).onUploadImgSucceed(data, code);
                                    }
                                }
                            });
                }
            });
        }

        /**
         * 登录
         *private  String appId;
         *     private String channel;
         *     private int type;
         *     private  String unionInfo;
         * .
         */
//        public void login(UserInfo user) {//String appId,String channel,int type,String unionInfo
////appId,channel,type,unionInfo
//            doAnyRequest(api -> api.getlogin(BaseJsonParam.create(JsonParamUtils.Userjson(user))), TokenBean.class).subscribe(new PostLoadingCallback<TokenBean>() {
//                @Override
//                protected void onSuccess(NetBean<TokenBean> response, TokenBean data, int page) {
//                   // Log.d("qwe",response.getData().getResult());
//                //    User.login(data);
//                    if (mView instanceof LoginView) {
//                        String result = data.getResult();
//
//                        ((LoginView) mView).loginSucceed(data);
//                    }
//                }
//            });
//        }

        /**
         * 获取显示设备
         */
        public void getDevices() {
            doListRequest(new ApiCallback<List<DeviceDto>>() {
                @Override
                public Observable<NetBean<List<DeviceDto>>> getApi(ApiService api) {
                    return api.getDevices(User.getToken(), mPage, LIMIT);
                }
            }).subscribe(new StateCallback<List<DeviceDto>>() {
                @Override
                protected void onSuccess(NetBean<List<DeviceDto>> response, List<DeviceDto> data) {
                }

                @Override
                protected void onList(NetBean<List<DeviceDto>> response, List<DeviceDto> data, int page) {
                    if (mView instanceof DeviceView) {
                        ((DeviceView) mView).onGetDeviceSucceed(data);
                    }
                }
            });
        }

        /**
         * 获取场景列表
         */
        public void getSceneList() {
            doPagingListRequest(api -> api.getSceneList(User.getToken(), mPage, LIMIT));
        }

        /**
         * 获取世界排名
         */
        public void getRakingGlobal() {
            doPagingListRequest(api -> api.getRakingGlobal(User.getToken(), mPage, LIMIT));
        }

        /**
         * 获取我在世界的排名
         */
        public void getRakingMySelf() {
            doPagingListRequest(api -> api.getRakingMySelf(User.getToken(), 1, 7));
        }

    }

//    class MofPresenter extends CommonPresenter<MofView> {
//        public void Mf(int type) {
//
//            doPagingListRequest(api -> api.getmfList(PreferenceUtils.getToken(),mPage, LIMIT, type));
//        }
//    }
//
//    interface MofView extends CommonView {
//    }


//    class LoginsPresenter extends CommonPresenter<LoginsView> {
//        public void getlogin(Userbear user) {
//            doAnyRequest(api -> api.getlogin(user),Object.class).subscribe(new PostLoadingCallback<Object>() {
//                @Override
//                protected void onSuccess(NetBean<Object> response, Object data, int page) {
//                        mView.loginbackcall(data);
//                }
//            });
//        }
//    }
//
//        interface LoginsView extends CommonView{
//            void loginbackcall(Object data);
//        }


}
