package com.yd.mofanghuanyuans.wxapi;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xzq.module_base.bean.WxTokenBean;
import com.xzq.module_base.utils.AppUtils;
import com.xzq.module_base.utils.EntitySerializerUtils;
import com.xzq.module_base.utils.MyToast;
import com.xzq.module_base.utils.XZQLog;


import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 第三方登录工具
 * Created by xzq on 2020/6/24.
 */
public class ThirdLoginManager {

    public static final String EVENT_ACTION_WECHAT_RESP = "event_action_wechat_resp";
    public static final String WECHAT_STATE_LOGIN = "wechat_login";
    public  static  final String WX_APPID="wx4996b923f732e636";
    public  static  final String WX_APPSECRET="8fb30a07b3b9e1d2f3d16522b6130402";
    /**
     * 发起微信授权请求
     */
    public static void sendWechatReq() {
        IWXAPI api = WXAPIFactory.createWXAPI(AppUtils.getTopActivityOrApp(), WX_APPID);//BuildConfig.
        if (api.isWXAppInstalled()) {
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = WECHAT_STATE_LOGIN;
            api.sendReq(req);
        } else {
            MyToast.show("请先安装微信");
        }
    }

    public interface AccessTokenCallback {
        void onAccessTokenCallback(WxTokenBean wxTokenBean);
    }

    //通过 code 获取 access_token
    public static void getAccessToken(String code, Runnable done, AccessTokenCallback callback) {
        //获取授权

        OkHttpClient okHttpClient = new OkHttpClient();
        String loginUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=" + WX_APPID +
                "&secret=" + WX_APPSECRET +
                "&code=" + code +
                "&grant_type=authorization_code";
        final Request request = new Request.Builder()
                .url(loginUrl)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                done.run();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String responseInfo = response.body().string();
                    XZQLog.debug("responseInfo = " + responseInfo);
                    WxTokenBean wxTokenBean = EntitySerializerUtils.deserializerEntity(responseInfo, WxTokenBean.class);
                    if (callback != null) {
                        callback.onAccessTokenCallback(wxTokenBean);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    done.run();
                }
            }
        });
    }

    public interface WxUserInfoCallback {
        void onWxUserInfoCallback(String nickname, String userInfo);
    }

    //获取微信用户信息
    public static void getUserInfo(WxTokenBean wxTokenBean, Runnable done, WxUserInfoCallback wxUserInfoCallback) {
        String getUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo?" +
                "access_token=" + wxTokenBean.access_token +
                "&openid=" + wxTokenBean.openid;
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(getUserInfoUrl)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                done.run();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String responseInfo = response.body().string();
                    XZQLog.debug("userInfo = " + responseInfo);
                    JSONObject jsonObject = new JSONObject(responseInfo);
                    String nickname = jsonObject.getString("nickname");
                    String headimgurl = jsonObject.getString("headimgurl");
                    if (wxUserInfoCallback != null) {
                        wxUserInfoCallback.onWxUserInfoCallback(nickname, headimgurl);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                done.run();
            }
        });
    }
}
