package com.yd.mofanghuanyuans.main.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTNativeExpressAd;
import com.example.administrator.magiccube.MainActivity;
import com.gyf.immersionbar.ImmersionBar;
import com.xzq.module_base.bean.HuiyuanBean;
import com.xzq.module_base.bean.HuoappBean;
import com.xzq.module_base.bean.TokenBean;
import com.xzq.module_base.bean.UserInfo;
import com.xzq.module_base.utils.CommUtils;
import com.yd.mofanghuanyuans.main.api.ApiService;
import com.xzq.module_base.base.BasePresenterFragment;
import com.xzq.module_base.bean.GgBean;
import com.xzq.module_base.bean.GuanggaoBean;
import com.xzq.module_base.bean.MofangBean;
import com.xzq.module_base.bean.rmcanBean;
import com.xzq.module_base.config.Config;
import com.xzq.module_base.mvp.BaseJsonParam;
import com.xzq.module_base.utils.JsonParamUtils;
import com.xzq.module_base.utils.PermissionUtil;
import com.xzq.module_base.views.DrawableTextView;
import com.yd.mofanghuanyuans.R;
import com.yd.mofanghuanyuans.main.adapter.MfrmAdapter;
import com.yd.mofanghuanyuans.main.adapter.MofAdapter;
import com.yd.mofanghuanyuans.main.adapter.MofleyuanAdapter;

import com.yd.mofanghuanyuans.main.biz.LoginActivity;
import com.yd.mofanghuanyuans.main.biz.MembershipcenterActivity;
import com.yd.mofanghuanyuans.main.biz.MofanggsActivity;
import com.yd.mofanghuanyuans.main.biz.MofanglyActivity;
import com.yd.mofanghuanyuans.main.biz.MofangrmActivity;
import com.yd.mofanghuanyuans.main.biz.MofangzsActivity;
import com.yd.mofanghuanyuans.main.biz.RetrofitUtils;
import com.yd.mofanghuanyuans.main.biz.VideoActivity;
import com.yd.mofanghuanyuans.main.biz.WebActivity;
import com.yd.mofanghuanyuans.main.biz.WebUrlActivity;
import com.yd.mofanghuanyuans.main.biz.ZhuxsuccessActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.yd.mofanghuanyuans.MyApp.getContext;

/**
 * HomeFragment
 * Created by xzq on 2020/8/4.
 */
public class HomeFragment extends BasePresenterFragment {//implements MvpContract.LoginView

    @BindView(R.id.view)
    View view;
    @BindView(R.id.monimf)
    ImageView monimf;
    @BindView(R.id.mofgs)
    ImageView mofgs;
    @BindView(R.id.mofzs)
    ImageView mofzs;
    @BindView(R.id.tit)
    TextView tit;
    @BindView(R.id.rm)
    DrawableTextView rm;
    @BindView(R.id.titleyuan)
    TextView titleyuan;
    @BindView(R.id.ly)
    DrawableTextView ly;
    @BindView(R.id.leyuanlist)
    RecyclerView leyuanlist;
    @BindView(R.id.recyc)
    RecyclerView recyc;
    //    @BindView(R.id.wx)
//    LinearLayout wx;
    private String mResult;
    private ArrayList<MofangBean.ListDTO> mListDTOS;
    private MofAdapter mMofAdapter;
    private MofleyuanAdapter mMofleyuanAdapter;
    private List<MofangBean.ListDTO> lists;
    private List<MofangBean.ListDTO> list;
    private MfrmAdapter mfrmAdapter;
    private TTNativeExpressAd mTTAd;
    private TTAdNative mTTAdNative;
    private long startTime;

    private int level;
    private int ifPay;

    @Override
    protected void loadData() {

    }

    @Override
    protected int getLayoutId(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_home;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ImmersionBar.with(this).fitsSystemWindows(false)
                .statusBarColor(R.color.transparent)
                .statusBarDarkFont(false).init();

        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = ImmersionBar.getStatusBarHeight(me);
        view.setLayoutParams(layoutParams);

        Kaigrefit();
        hyrefit();
        Mfrmrefit();
        guanggao();
    }

    @Override
    public void onResume() {
        super.onResume();
        Kaigrefit();
        hyrefit();
    }
    public void Kaigrefit(){
        SharedPreferences token = getContext().getSharedPreferences("token", getContext().MODE_PRIVATE);
        String result = token.getString("result", null);
        Call<HuoappBean> gethq = new RetrofitUtils().retrofit().gethq(result);
        gethq.enqueue(new Callback<HuoappBean>() {
            @Override
            public void onResponse(Call<HuoappBean> call, Response<HuoappBean> response) {
                String code = response.body().getCode();
                if (code.equals("996") || code.equals("999")) {
                    Intent intent = new Intent(getContext(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    ifPay = response.body().getResult().getIfPay();

                }
            }

            @Override
            public void onFailure(Call<HuoappBean> call, Throwable t) {

            }
        });
    }

    public void hyrefit() {
        SharedPreferences token = getContext().getSharedPreferences("token", getContext().MODE_PRIVATE);
        String result = token.getString("result", null);
        Call<HuiyuanBean> gethuiyuan = new RetrofitUtils().retrofit().gethuiyuan(result);
        gethuiyuan.enqueue(new Callback<HuiyuanBean>() {
            @Override
            public void onResponse(Call<HuiyuanBean> call, Response<HuiyuanBean> response) {

                String code = response.body().getCode();
                if (code.equals("996") || code.equals("999")) {
                    Intent intent = new Intent(getContext(), com.yd.mofanghuanyuans.main.biz.LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {

                    level = response.body().getResult().getLevel();


                }

            }

            @Override
            public void onFailure(Call<HuiyuanBean> call, Throwable t) {

            }
        });
    }


    @OnClick({R.id.rm, R.id.ly, R.id.mofgs, R.id.mofzs, R.id.monimf})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rm:
                MofangrmActivity.start(getContext());
                break;
            case R.id.ly:
                MofanglyActivity.start(getContext());
                break;
            case R.id.mofgs:
                MofanggsActivity.start(getContext());
                break;
            case R.id.mofzs:
                MofangzsActivity.start(getContext());
                break;
            case R.id.monimf:
                if(ifPay==0){
                    Intent intent = new Intent();
                    intent.setClass(getContext(), MainActivity.class);
                    startActivity(intent);
                }else {
                    if (level == 0) {
                        Intent intent = new Intent(getContext(), MembershipcenterActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent();
                        intent.setClass(getContext(), MainActivity.class);
                        startActivity(intent);
                    }
                }
                break;
        }
    }






    private void Mfrmrefit() {
        SharedPreferences token = getContext().getSharedPreferences("token", getContext().MODE_PRIVATE);
        String result = token.getString("result", null);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyc.setLayoutManager(layoutManager);
        mListDTOS = new ArrayList<>();
        mfrmAdapter = new MfrmAdapter(mListDTOS, getContext());
        recyc.setAdapter(mfrmAdapter);
        rmcanBean rmcanBeans = new rmcanBean();
        rmcanBeans.setPageNum(1);
        rmcanBeans.setPageSize(4);
        rmcanBean.ParamDTO paramDTO1 = new rmcanBean.ParamDTO();
        paramDTO1.setType("145");
        rmcanBeans.setParam(paramDTO1);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService service = retrofit.create(ApiService.class);
        Call<MofangBean> mofangBeanCall = service.getmfList(result, BaseJsonParam.create(JsonParamUtils.mfrmjson(rmcanBeans)));
        mofangBeanCall.enqueue(new Callback<MofangBean>() {
            @Override
            public void onResponse(Call<MofangBean> call, Response<MofangBean> response) {
                MofangBean body = response.body();
                lists = body.getList();
                if (lists!=null) {
                    if (lists.size() != 0) {
                        mListDTOS.clear();
                        mListDTOS.addAll(lists);
                        mfrmAdapter.notifyDataSetChanged();
                    }
                    Mfleyuanrefit();
                }

            }

            @Override
            public void onFailure(Call<MofangBean> call, Throwable t) {

            }
        });
        mfrmAdapter.setJiekouhuidiaos(new MfrmAdapter.Jiekouhuidiao() {
            @Override
            public void OnClike(int position) {
                if(ifPay==0){
                    String content = lists.get(position).getContent();
                    Intent intent = new Intent(getContext(), WebActivity.class);
                    intent.putExtra("url", content);
                    startActivity(intent);
                }else {
                    if (level == 0) {
                        if (position == 0) {
                            String content = lists.get(position).getContent();
                            Intent intent = new Intent(getContext(), WebActivity.class);
                            intent.putExtra("url", content);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(getContext(), MembershipcenterActivity.class);
                            startActivity(intent);
                        }
                    } else {
                        String content = lists.get(position).getContent();
                        Intent intent = new Intent(getContext(), WebActivity.class);
                        intent.putExtra("url", content);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    private void Mfleyuanrefit() {
        SharedPreferences token = getContext().getSharedPreferences("token", getContext().MODE_PRIVATE);
        String result = token.getString("result", null);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        leyuanlist.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        // layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        leyuanlist.setLayoutManager(layoutManager);
        mListDTOS = new ArrayList<>();
        mMofleyuanAdapter = new MofleyuanAdapter(mListDTOS, getContext());
        leyuanlist.setAdapter(mMofleyuanAdapter);
        rmcanBean rmcanBeans = new rmcanBean();
        rmcanBeans.setPageNum(1);
        rmcanBeans.setPageSize(3);
        rmcanBean.ParamDTO paramDTO1 = new rmcanBean.ParamDTO();
        paramDTO1.setType("144");
        rmcanBeans.setParam(paramDTO1);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService service = retrofit.create(ApiService.class);
        Call<MofangBean> mofangBeanCall = service.getmfList(result, BaseJsonParam.create(JsonParamUtils.mfrmjson(rmcanBeans)));
        mofangBeanCall.enqueue(new Callback<MofangBean>() {
            @Override
            public void onResponse(Call<MofangBean> call, Response<MofangBean> response) {
                MofangBean body = response.body();
                list = body.getList();
                if (list!=null) {
                    if (list.size() != 0) {
                        mListDTOS.clear();
                        mListDTOS.addAll(list);
                        mMofleyuanAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<MofangBean> call, Throwable t) {

            }
        });
        mMofleyuanAdapter.setJiekouhuidiaos(new MofleyuanAdapter.Jiekouhuidiao() {
            @Override
            public void OnClike(int position) {


                if(ifPay==0){
                    String content = lists.get(position).getContent();
                    Intent intent = new Intent(getContext(), WebActivity.class);
                    intent.putExtra("url", content);
                    startActivity(intent);
                }else {
                    if (level == 0) {
                        if (position == 0) {
                            String content = list.get(position).getContent();
                            Intent intent = new Intent(getContext(), VideoActivity.class);
                            intent.putExtra("url", content);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(getContext(), MembershipcenterActivity.class);
                            startActivity(intent);
                        }
                    } else {
                        String content = list.get(position).getContent();
                        Intent intent = new Intent(getContext(), VideoActivity.class);
                        intent.putExtra("url", content);
                        startActivity(intent);
                    }

                }



            }
        });
    }

    private void guanggao() {
        SharedPreferences token = getContext().getSharedPreferences("token", getContext().MODE_PRIVATE);
        String result = token.getString("result", null);
        GgBean ggBean = new GgBean();
        GgBean.ParamBean param = ggBean.getParam();
//        param.setAppId("");
//        param.setChannel("");
//        param.setCurrentV("");
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
                        //NONE ???  CSJ ?????????  SYS ?????????
                        if ("NONE".equals(startAdFlag)) {
                            // goToMainActivity();
                        } else if ("CSJ".equals(startAdFlag)) {
                            csjSplash();

                        } else if ("SYS".equals(startAdFlag)) {
//                            getTheme().applyStyle(R.style.MySplashTheme, true);
//                            mSplashContainer.removeAllViews();

//                                View inflate = FrameLayout.inflate(mContext, R.layout.activity_splash_my, null);
//                                ImageView img_logo = inflate.findViewById(R.id.img_logo);
//                                mSplashContainer.addView(inflate);


//                                adType = result.getAdType();// ????????????
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
                                // goToMainActivity();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            // goToMainActivity();
                        }
                    } else {
                        //  goToMainActivity();
                    }
                } else {
                    // goToMainActivity();
                }
            }

            @Override
            public void onFailure(Call<GuanggaoBean> call, Throwable t) {

            }
        });
    }


    /**
     * ?????????
     */
    private void csjSplash() {
        //step2:??????TTAdNative??????
        mTTAdNative = TTAdSdk.getAdManager().createAdNative(getContext());

        //??????????????????
        loadInteractionAd();
    }

    /**
     * ??????????????????
     */
    private void loadInteractionAd() {
        //step4:????????????????????????AdSlot,???????????????setNativeAdtype???????????????????????????????????????
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(Config.CSJ_CODE_ID_CHAPING)
                .setSupportDeepLink(true)
                .setAdCount(1) //?????????????????????1???3???
                .setExpressViewAcceptedSize(300, 300)//?????????????????????dp???
                .setNativeAdType(AdSlot.TYPE_INTERACTION_AD)//?????????????????????????????????????????????????????????????????????TYPE_BANNER???TYPE_INTERACTION_AD
                .build();

        //step5:??????????????????????????????????????????????????????
        mTTAdNative.loadInteractionExpressAd(adSlot, new TTAdNative.NativeExpressAdListener() {


            //??????????????????
            @Override
            public void onError(int code, String message) {
                System.out.println("load error : " + code + ", " + message);
            }

            //??????????????????
            @Override
            public void onNativeExpressAdLoad(List<TTNativeExpressAd> ads) {

                if (ads == null || ads.size() == 0) {
                    return;
                }
                mTTAd = ads.get(0);
                bindAdListener(mTTAd);
                startTime = System.currentTimeMillis();
                showAd();
            }
        });

    }

    private void showAd() {
        if (mTTAd != null) {
            mTTAd.render();
        } else {
            System.out.println("??????????????????");
        }
    }

    boolean mHasShowDownloadActive = false;

    private void bindAdListener(TTNativeExpressAd ad) {
        ad.setExpressInteractionListener(new TTNativeExpressAd.AdInteractionListener() {
            @Override
            public void onAdDismiss() {
                System.out.println("????????????");
            }

            @Override
            public void onAdClicked(View view, int type) {
                System.out.println("???????????????");
            }

            @Override
            public void onAdShow(View view, int type) {
                System.out.println("????????????");
            }

            @Override
            public void onRenderFail(View view, String msg, int code) {
                Log.e("ExpressView", "render fail:" + (System.currentTimeMillis() - startTime));
//                System.out.println(msg + " code:" + code);
            }

            @Override
            public void onRenderSuccess(View view, float width, float height) {
                Log.e("ExpressView", "render suc:" + (System.currentTimeMillis() - startTime));
                //??????view????????? ?????? dp
                System.out.println("????????????");
                mTTAd.showInteractionExpressAd(getActivity());
            }
        });
//        bindDislike(ad, false);
        if (ad.getInteractionType() != TTAdConstant.INTERACTION_TYPE_DOWNLOAD) {
            return;
        }
        ad.setDownloadListener(new TTAppDownloadListener() {
            @Override
            public void onIdle() {
                System.out.println("??????????????????");
            }

            @Override
            public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                if (!mHasShowDownloadActive) {
                    mHasShowDownloadActive = true;
                    System.out.println("????????????????????????");
                }
            }

            @Override
            public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
                System.out.println("???????????????????????????");
            }

            @Override
            public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
                System.out.println("?????????????????????????????????");
            }

            @Override
            public void onInstalled(String fileName, String appName) {
                System.out.println("?????????????????????????????????");
            }

            @Override
            public void onDownloadFinished(long totalBytes, String fileName, String appName) {
                System.out.println("????????????");
            }
        });
    }


}
