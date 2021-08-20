package com.yd.mofanghuanyuans.main.biz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.blankj.utilcode.util.AppUtils;
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTNativeExpressAd;
import com.bytedance.sdk.openadsdk.TTSplashAd;
import com.gyf.immersionbar.ImmersionBar;
import com.yd.mofanghuanyuans.main.api.ApiService;
import com.xzq.module_base.base.BasePresenterActivity;
import com.xzq.module_base.bean.GgBean;
import com.xzq.module_base.bean.GuanggaoBean;
import com.xzq.module_base.bean.TokenBean;
import com.xzq.module_base.bean.UserInfo;
import com.xzq.module_base.config.Config;
import com.xzq.module_base.mvp.BaseJsonParam;
import com.xzq.module_base.utils.JsonParamUtils;
import com.xzq.module_base.utils.MyToast;
import com.yd.mofanghuanyuans.R;
import com.yd.mofanghuanyuans.main.MainActivity;
import com.yd.mofanghuanyuans.main.chansj.TTAdManagerHolder;

import androidx.annotation.MainThread;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.yd.mofanghuanyuans.MyApp.getContext;

public class WelcomeActivity extends BasePresenterActivity implements Runnable {

    private TTAdNative mTTAdNative;

    //是否强制跳转到主页面
    private boolean mForceGoMain;

    //开屏广告加载超时时间,建议大于3000,这里为了冷启动第一次加载到广告并且展示,示例设置了3000ms
    private static final int AD_TIME_OUT = 5000;
    private String mCodeId = Config.CSJ_CODE_ID;
    private boolean mIsExpress = false; //是否请求模板广告

    @BindView(R.id.splash_container)
    FrameLayout mSplashContainer;



    @Override
    protected int getLayoutId() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        //去掉系统状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_splash;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initViews(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        getWindow().getDecorView().postDelayed(this, 1500);


        hideToolbar();
        ImmersionBar.with(this).fitsSystemWindows(false)
                .statusBarColor(R.color.transparent)
                .statusBarDarkFont(false).init();

       // Tokenrefit();
        SharedPreferences token = getSharedPreferences("token", getContext().MODE_PRIVATE);
        String result = token.getString("result", null);
        if (result!=null){
            initData(result);
        }

    }



    /**
     * 初始化数据
     */
    private void initData(String result) {

        GgBean ggBean = new GgBean();
        GgBean.ParamBean param = ggBean.getParam();
        ggBean.setParam(param);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService service = retrofit.create(ApiService.class);
        Call<GuanggaoBean> guanggaoBeanCall = service.getgg(result, BaseJsonParam.create(JsonParamUtils.ggjson(ggBean)));
        guanggaoBeanCall.enqueue(new Callback<GuanggaoBean>() {
            @Override
            public void onResponse(Call<GuanggaoBean> call, Response<GuanggaoBean> response) {
                GuanggaoBean body = response.body();
                GuanggaoBean.ResultBean result = body.getResult();
                // VersionUpdataResponse.VersionUpdataInfo result = response.getResult();
                if (result != null) {
                    String appConfig = result.getAppConfig();
                    if (!TextUtils.isEmpty(appConfig)) {
                        //AppConfigBean appConfigBean = JSON.parseObject(appConfig, AppConfigBean.class);
                        String startAdFlag = result.getStartAdFlag();
                        //  String startAdFlag = appConfigBean.getStartAdFlag();
                        //NONE 无  CSJ 穿山甲  SYS 自系统
                        if ("NONE".equals(startAdFlag)) {
                            goToMainActivity();
                        } else if ("CSJ".equals(startAdFlag)) {
                            csjSplash();

                        } else if ("SYS".equals(startAdFlag)) {
                            getTheme().applyStyle(R.style.MySplashTheme, true);
                            mSplashContainer.removeAllViews();
//                                View inflate = FrameLayout.inflate(mContext, R.layout.activity_splash_my, null);
//                                ImageView img_logo = inflate.findViewById(R.id.img_logo);
//                                mSplashContainer.addView(inflate);


//                                adType = result.getAdType();// 广告类型
//                                adImgUrl = result.getAdImgUrl();
//                                adResUrl = result.getAdResUrl();
//
//                                Util.displayBlendImgView(mContext, img_logo, adImgUrl, R.mipmap.ic_logo);

//                        public static void displayBlendImgView(Context context, ImageView view, String imgUrl, int img_dt) {
//                            Glide.with(context)
//                                    .load(imgUrl)
//                                    .placeholder(img_dt)
//                                    .into(view);
//                        }
                            try {
                                Thread.sleep(3000);
                                goToMainActivity();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            goToMainActivity();
                        }
                    } else {
                        goToMainActivity();
                    }
                } else {
                    goToMainActivity();
                }
            }

            @Override
            public void onFailure(Call<GuanggaoBean> call, Throwable t) {

            }
        });

    }

    /**
     * 穿山甲
     */
    private void csjSplash() {


        //step2:创建TTAdNative对象
        mTTAdNative = TTAdManagerHolder.get().createAdNative(this);

        getExtraInfo();

        //加载开屏广告
        loadSplashAd();

    }


    private void getExtraInfo() {
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        String codeId = intent.getStringExtra("splash_rit");
        if (!TextUtils.isEmpty(codeId)) {
            mCodeId = codeId;
        }
        mIsExpress = intent.getBooleanExtra("is_express", false);
    }

    @Override
    protected void onResume() {
        //判断是否该跳转到主页面
        if (mForceGoMain) {
            goToMainActivity();
        }
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mForceGoMain = true;
    }

    /**
     * 加载开屏广告
     */
    private void loadSplashAd() {
        //step3:创建开屏广告请求参数AdSlot,具体参数含义参考文档
        AdSlot adSlot = null;
        if (mIsExpress) {
            //个性化模板广告需要传入期望广告view的宽、高，单位dp，请传入实际需要的大小，
            //比如：广告下方拼接logo、适配刘海屏等，需要考虑实际广告大小
            //float expressViewWidth = UIUtils.getScreenWidthDp(this);
            //float expressViewHeight = UIUtils.getHeight(this);
            adSlot = new AdSlot.Builder()
                    .setCodeId(mCodeId)
                    //模板广告需要设置期望个性化模板广告的大小,单位dp,代码位是否属于个性化模板广告，请在穿山甲平台查看
                    //view宽高等于图片的宽高
                    .setExpressViewAcceptedSize(1080, 1920)
                    .build();
        } else {
            adSlot = new AdSlot.Builder()
                    .setCodeId(mCodeId)
                    .setImageAcceptedSize(1080, 1920)
                    .build();
        }

        //step4:请求广告，调用开屏广告异步请求接口，对请求回调的广告作渲染处理
        mTTAdNative.loadSplashAd(adSlot, new TTAdNative.SplashAdListener() {
            @Override
            @MainThread
            public void onError(int code, String message) {
                //   ToastUtils.shortShowStr(mContext, message);
                goToMainActivity();
            }

            @Override
            @MainThread
            public void onTimeout() {
                MyToast.show("开屏广告加载超时");
                // ToastUtils.shortShowStr(mContext, "开屏广告加载超时");
                goToMainActivity();
            }

            @Override
            @MainThread
            public void onSplashAdLoad(TTSplashAd ad) {
                if (ad == null) {
                    return;
                }
                //获取SplashView
                View view = ad.getSplashView();
                if (mSplashContainer != null && !WelcomeActivity.this.isFinishing()) {
                    mSplashContainer.removeAllViews();
                    //把SplashView 添加到ViewGroup中,注意开屏广告view：width >=70%屏幕宽；height >=50%屏幕高
                    mSplashContainer.addView(view);
                    //设置不开启开屏广告倒计时功能以及不显示跳过按钮,如果这么设置，您需要自定义倒计时逻辑
                    //ad.setNotAllowSdkCountdown();
                } else {
                    goToMainActivity();
                }

                //设置SplashView的交互监听器
                ad.setSplashInteractionListener(new TTSplashAd.AdInteractionListener() {
                    @Override
                    public void onAdClicked(View view, int type) {
//                        Log.d(TAG, "onAdClicked");
//                        showToast("开屏广告点击");
                    }

                    @Override
                    public void onAdShow(View view, int type) {
//                        Log.d(TAG, "onAdShow");
//                        showToast("开屏广告展示");
                    }

                    @Override
                    public void onAdSkip() {
//                        Log.d(TAG, "onAdSkip");
//                        showToast("开屏广告跳过");
                        goToMainActivity();

                    }

                    @Override
                    public void onAdTimeOver() {
//                        Log.d(TAG, "onAdTimeOver");
//                        showToast("开屏广告倒计时结束");
                        goToMainActivity();
                    }
                });
                if (ad.getInteractionType() == TTAdConstant.INTERACTION_TYPE_DOWNLOAD) {
                    ad.setDownloadListener(new TTAppDownloadListener() {
                        boolean hasShow = false;

                        @Override
                        public void onIdle() {
                        }

                        @Override
                        public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                            if (!hasShow) {
//                                showToast("下载中...");
                                hasShow = true;
                            }
                        }

                        @Override
                        public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
//                            showToast("下载暂停...");

                        }

                        @Override
                        public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
//                            showToast("下载失败...");

                        }

                        @Override
                        public void onDownloadFinished(long totalBytes, String fileName, String appName) {
//                            showToast("下载完成...");

                        }

                        @Override
                        public void onInstalled(String fileName, String appName) {
//                            ToastUtils.shortShowStr(mContext, "安装完成...");
                        }
                    });
                }
            }
        }, AD_TIME_OUT);
    }

    /**
     * 跳转到主页面
     */
    private void goToMainActivity() {
        SharedPreferences token = getSharedPreferences("token", MODE_PRIVATE);
       String result = token.getString("result", null);
        if(result==null||result.equals("")||result.equals("wcx")){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }else {
            MainActivity.start(this);
        }

//        mSplashContainer.removeAllViews();
        this.finish();
    }

    @Override
    public void run() {
        if (isFinishing() || !AppUtils.isAppForeground()) {
            finish();
            return;
        }
        MainActivity.start(this);
        finish();
    }





}
