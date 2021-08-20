package com.yd.mofanghuanyuans.main.biz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.xzq.module_base.base.BasePresenterActivity;
import com.xzq.module_base.bean.LoginInfoBean;
import com.xzq.module_base.bean.TokenBean;
import com.xzq.module_base.bean.UserInfo;
import com.xzq.module_base.bean.WxTokenBean;
import com.xzq.module_base.bean.ZhuceBean;
import com.xzq.module_base.config.Config;
import com.xzq.module_base.eventbus.MessageEvent;
import com.xzq.module_base.mvp.BaseJsonParam;
import com.xzq.module_base.utils.CommUtils;
import com.xzq.module_base.utils.EntitySerializerUtils;
import com.xzq.module_base.utils.JsonParamUtils;
import com.xzq.module_base.utils.MyToast;
import com.xzq.module_base.utils.PermissionUtil;
import com.xzq.module_base.utils.XZQLog;
import com.xzq.module_base.views.DrawableTextView;
import com.yd.mofanghuanyuans.R;
import com.yd.mofanghuanyuans.main.MainActivity;
import com.yd.mofanghuanyuans.main.api.ApiService;
import com.yd.mofanghuanyuans.wxapi.ThirdLoginManager;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends BasePresenterActivity {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.bars)
    Toolbar bars;
    @BindView(R.id.shoujihao)
    EditText shoujihao;
    @BindView(R.id.mima)
    EditText mima;
    @BindView(R.id.denglu)
    TextView denglu;
    @BindView(R.id.wangjimiam)
    TextView wangjimiam;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.wxlogin)
    DrawableTextView wxlogin;
    @BindView(R.id.yklogin)
    DrawableTextView yklogin;
    @BindView(R.id.gou)
    CheckBox gou;
    @BindView(R.id.tv_pro)
    TextView tvPro;
    @BindView(R.id.tv_pros)
    TextView tvPros;
    @BindView(R.id.wx)
    LinearLayout wx;
    private PopupWindow popupWindow;
    private SharedPreferences.Editor editor;
    public  static  final String WX_APPID="wx4996b923f732e636";
    public  static  final String WX_APPSECRET="8fb30a07b3b9e1d2f3d16522b6130402";
    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViews(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        hideToolbar();
        //判断是否第一次
        SharedPreferences sharedPreferences = getSharedPreferences("share", MODE_PRIVATE);
        //这里。让他获取，是不是第一次。默认是第一的。true
        boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
        editor = sharedPreferences.edit();
        if (isFirstRun) {
            wx.post(new Runnable() {
                @Override
                public void run() {
                    Pupupwindow();
                }
            });

        }
    }


    @OnClick({R.id.denglu, R.id.wangjimiam, R.id.tv_register, R.id.wxlogin, R.id.yklogin, R.id.gou, R.id.tv_pro, R.id.tv_pros})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.denglu:
                //登录
                if (!shoujihao.getText().toString().isEmpty() && !mima.getText().toString().isEmpty()) {
                    if (gou.isChecked() == true) {
                        Zhucerefit();
                    } else {
                        MyToast.show("请勾选同意");
                    }

                } else {
                    MyToast.show("请输入完整!");
                }
                break;
            case R.id.wangjimiam:
                Intent intent1 = new Intent(this, ForgetpasswordActivity.class);
                startActivity(intent1);
                break;
            case R.id.tv_register:
                //注册页
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.wxlogin:
                ThirdLoginManager.sendWechatReq();
                break;
            case R.id.yklogin:
                if (gou.isChecked() == true) {
                    Tokenrefit();
                } else {
                    MyToast.show("请勾选同意");
                }

                break;
            case R.id.gou:
                break;
            case R.id.tv_pro:
                //协议
                Intent intent2 = new Intent(this, WebUrlActivity.class);
                intent2.putExtra("url", "file:///android_asset/UserProtocol.html");
                intent2.putExtra("title", "用户协议");
                startActivity(intent2);
                break;
            case R.id.tv_pros:
                //隐私
                Intent intent7 = new Intent(this, WebUrlActivity.class);
                intent7.putExtra("url", "file:///android_asset/PrivacyAgreement.html");
                intent7.putExtra("title", "隐私政策");
                startActivity(intent7);
                break;
        }
    }

    private void Tokenrefit() {
        //获取设备号
        String uniqueID = CommUtils.getUniqueID(this);
        UserInfo userInfo = new UserInfo();
        UserInfo.ParamBean paramDTO = new UserInfo.ParamBean();
        paramDTO.setAppId("wx8jf845hf30gr3h3");
        paramDTO.setChannel(null);
        paramDTO.setType("2");
        paramDTO.setUnionInfo(uniqueID);
        userInfo.setParam(paramDTO);
        Log.d("wcx", userInfo.toString());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService service = retrofit.create(ApiService.class);
        Call<TokenBean> getlogin = service.getlogin(BaseJsonParam.create(JsonParamUtils.Userjson(userInfo)));
        getlogin.enqueue(new Callback<TokenBean>() {
            @Override
            public void onResponse(Call<TokenBean> call, Response<TokenBean> response) {
                TokenBean body = response.body();
                String mResult = body.getResult();
                //  PreferenceUtils.putToken(result);
                SharedPreferences mySharedPreferences = getSharedPreferences("token", MODE_PRIVATE);
                SharedPreferences.Editor edit = mySharedPreferences.edit();
                edit.putString("result", mResult);
                edit.commit();

                MainActivity.start(LoginActivity.this);


            }

            @Override
            public void onFailure(Call<TokenBean> call, Throwable t) {

            }
        });
    }

    private void Zhucerefit() {
        LoginInfoBean loginInfoBean = new LoginInfoBean();
        LoginInfoBean.ParamBean paramBean = new LoginInfoBean.ParamBean();
        paramBean.setAccount(shoujihao.getText().toString());
        paramBean.setPassword(mima.getText().toString());
        paramBean.setAppId("wx8jf845hf30gr3h3");
        loginInfoBean.setParam(paramBean);
        Call<TokenBean> tokenBeanCall = new RetrofitUtils().retrofit().loginByPassword(BaseJsonParam.create(JsonParamUtils.getlog(loginInfoBean)));
        tokenBeanCall.enqueue(new Callback<TokenBean>() {
            @Override
            public void onResponse(Call<TokenBean> call, Response<TokenBean> response) {
                String result = response.body().getResult();
                int code = response.body().getCode();
                if(code==0){
                    SharedPreferences mySharedPreferences = getSharedPreferences("token", MODE_PRIVATE);
                    SharedPreferences.Editor edit = mySharedPreferences.edit();
                    edit.putString("result", result);
                    edit.commit();
                    MainActivity.start(LoginActivity.this);

                }

            }

            @Override
            public void onFailure(Call<TokenBean> call, Throwable t) {

            }
        });
    }

    @Override
    public void onMessageEvent(@NonNull MessageEvent event) {
        super.onMessageEvent(event);
        if (event.equals(ThirdLoginManager.EVENT_ACTION_WECHAT_RESP)) {
            SendAuth.Resp resp = (SendAuth.Resp) event.getData();

            showPostLoading();

            //getAccessToken(resp.code);
            ThirdLoginManager.getAccessToken(resp.code, new Runnable() {
                @Override
                public void run() {

                     hidePostLoading();
                }
            }, new ThirdLoginManager.AccessTokenCallback() {
                @Override
                public void onAccessTokenCallback(WxTokenBean wxTokenBean) {
                    ThirdLoginManager.getUserInfo(wxTokenBean, new Runnable() {
                        @Override
                        public void run() {
                            String access_token = wxTokenBean.access_token;

                            //Log.d("haha",access_token);

                            hidePostLoading();

                        }
                    }, new ThirdLoginManager.WxUserInfoCallback() {
                        @Override
                        public void onWxUserInfoCallback(String nickname, String imageUrl) {

                            ThreadUtil.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    //获取设备号
                                    String uniqueID = CommUtils.getUniqueID(LoginActivity.this);
                                    UserInfo userInfo = new UserInfo();
                                    UserInfo.ParamBean paramDTO = new UserInfo.ParamBean();
                                    paramDTO.setAppId("wx8jf845hf30gr3h3");
                                    paramDTO.setChannel(null);
                                    paramDTO.setNickName(nickname);
                                    paramDTO.setHeadImgUrl(imageUrl);
                                    paramDTO.setType("3");
                                    paramDTO.setUnionInfo(uniqueID);
                                    userInfo.setParam(paramDTO);
                                    Log.d("wcx", userInfo.toString());

                                    Retrofit retrofit = new Retrofit.Builder()
                                            .baseUrl(Config.BASE_URL)
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .build();
                                    ApiService service = retrofit.create(ApiService.class);
                                    Call<TokenBean> getlogin = service.getlogin(BaseJsonParam.create(JsonParamUtils.Userjson(userInfo)));
                                    getlogin.enqueue(new Callback<TokenBean>() {
                                        @Override
                                        public void onResponse(Call<TokenBean> call, Response<TokenBean> response) {
                                            TokenBean body = response.body();
                                            String mResult = body.getResult();
                                            //  PreferenceUtils.putToken(result);
                                            SharedPreferences mySharedPreferences = getSharedPreferences("token", MODE_PRIVATE);
                                            SharedPreferences.Editor edit = mySharedPreferences.edit();
                                            edit.putString("result", mResult);
                                            edit.commit();
                                              MainActivity.start(LoginActivity.this);


                                        }

                                        @Override
                                        public void onFailure(Call<TokenBean> call, Throwable t) {

                                        }
                                    });


                                  //  MainActivity.start(LoginActivity.this);
                                    Log.d("jiji",nickname);
                                    Log.d("qwqw",imageUrl);
                                }
                            });
                        }
                    });
                }
            });
        }
    }


    //通过 code 获取 access_token
    public static void getAccessToken(String code) {//, Runnable done, AccessTokenCallback callback
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
        okhttp3.Call call = okHttpClient.newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
               // done.run();
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                try {

                    String responseInfo = response.body().string();
                    XZQLog.debug("responseInfo = " + responseInfo);

                    WxTokenBean wxTokenBean = EntitySerializerUtils.deserializerEntity(responseInfo, WxTokenBean.class);
//                    if (callback != null) {
//                        callback.onAccessTokenCallback(wxTokenBean);
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                   // done.run();
                }
            }
        });
    }



    @SuppressLint("ResourceAsColor")
    private void Pupupwindow() {
        View view = LayoutInflater.from(me).inflate(R.layout.layout_first, null);
        TextView no = view.findViewById(R.id.zaixiangxiang);
        TextView ok = view.findViewById(R.id.tongyi);
        TextView tongyi = view.findViewById(R.id.tongyis);

        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(wx, Gravity.CENTER, 0, 0);

        SpannableStringBuilder spannable = new SpannableStringBuilder("感谢您信任并使用魔方还原的产品和服务。在您使用魔方还原App前，请认真阅读并了解我们的《用户协议》和《隐私政策》。");
        //设置文字的前景色，2、4分别表示可以点击文字的起始和结束位置。
        spannable.setSpan(new ForegroundColorSpan(R.color.zhihu_item_checkCircle_backgroundColor), 43, 49, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new ForegroundColorSpan(R.color.zhihu_item_checkCircle_backgroundColor), 50, 56, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //这个一定要记得设置，不然点击不生效ResUtil.getColor(R.color.zhihu_item_checkCircle_backgroundColor)
        tongyi.setMovementMethod(LinkMovementMethod.getInstance());
        spannable.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                //用户协议
                Intent intent7 = new Intent(LoginActivity.this, WebUrlActivity.class);
                intent7.putExtra("url", "file:///android_asset/UserProtocol.html");
                intent7.putExtra("title", "用户协议");
                startActivity(intent7);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);// 高亮文字颜色
                ds.setUnderlineText(false);// 不要下划线
                ds.clearShadowLayer();
            }
        }, 43, 49, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                //隐私政策
                Intent intent7 = new Intent(LoginActivity.this, WebUrlActivity.class);
                intent7.putExtra("url", "file:///android_asset/PrivacyAgreement.html");
                intent7.putExtra("title", "隐私政策");
                startActivity(intent7);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(R.color.zhihu_item_checkCircle_backgroundColor);// 高亮文字颜色
                ds.setUnderlineText(false);// 不要下划线
                ds.clearShadowLayer();
            }
        }, 50, 56, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tongyi.setText(spannable);


        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                finish();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putBoolean("isFirstRun", false);//我得做个标记。改成false。
                //提交到本地，存起来
                editor.commit();
//                //获取设备号
//                String uniqueID = CommUtils.getUniqueID(getContext());
//                UserInfo userInfo = new UserInfo();
////otFsw6rcDzgU3wcjc3dLl0i04Zrk
//                UserInfo.ParamDTO paramDTO = new UserInfo.ParamDTO();
//                paramDTO.setAppId("wx8jf845hf30gr3h3");
//                paramDTO.setChannel(null);
//                paramDTO.setType(3);
//                paramDTO.setUnionInfo(uniqueID);
//                userInfo.setParam(paramDTO);
//
//                Retrofit retrofit = new Retrofit.Builder()
//                        .baseUrl(Config.BASE_URL)
////                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .build();
//                ApiService service = retrofit.create(ApiService.class);
//                Call<TokenBean> getlogin = service.getlogin(BaseJsonParam.create(JsonParamUtils.Userjson(userInfo)));
//                getlogin.enqueue(new Callback<TokenBean>() {
//                    @Override
//                    public void onResponse(Call<TokenBean> call, Response<TokenBean> response) {
//                        TokenBean body = response.body();
//                        mResult = body.getResult();
//                        //  PreferenceUtils.putToken(result);
//                        SharedPreferences mySharedPreferences = getSharedPreferences("token", MODE_PRIVATE);
//                        SharedPreferences.Editor edit = mySharedPreferences.edit();
//                        edit.putString("result", mResult);
//                        edit.commit();
//
//
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<TokenBean> call, Throwable t) {
//
//                    }
//                });

                PermissionUtil.requestLocationAndStorage(() -> {

                });
                popupWindow.dismiss();
            }
        });
    }


}
