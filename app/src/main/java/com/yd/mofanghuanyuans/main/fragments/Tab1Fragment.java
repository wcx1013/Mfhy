package com.yd.mofanghuanyuans.main.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gyf.immersionbar.ImmersionBar;
import com.xzq.module_base.bean.HuiyuanBean;
import com.xzq.module_base.bean.HuoappBean;
import com.xzq.module_base.utils.CommUtils;
import com.yd.mofanghuanyuans.main.api.ApiService;
import com.xzq.module_base.base.BasePresenterFragment;
import com.xzq.module_base.bean.DianjicountBean;
import com.xzq.module_base.bean.MofangBean;
import com.xzq.module_base.bean.TokenBean;
import com.xzq.module_base.bean.UserInfo;
import com.xzq.module_base.bean.rmcanBean;
import com.xzq.module_base.config.Config;
import com.xzq.module_base.mvp.BaseJsonParam;
import com.xzq.module_base.utils.JsonParamUtils;
import com.yd.mofanghuanyuans.R;
import com.yd.mofanghuanyuans.main.adapter.MofxiaozhanAdapter;
import com.yd.mofanghuanyuans.main.adapter.YIkanAdapter;
import com.yd.mofanghuanyuans.main.biz.LoginActivity;
import com.yd.mofanghuanyuans.main.biz.MembershipcenterActivity;
import com.yd.mofanghuanyuans.main.biz.RetrofitUtils;
import com.yd.mofanghuanyuans.main.biz.VideoActivity;
import com.yd.mofanghuanyuans.main.biz.WebActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Tab1Fragment
 * Created by xzq on 2020/8/4.
 */
public class Tab1Fragment extends BasePresenterFragment {

    @BindView(R.id.view)
    View view;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.mfxiaozhan)
    RecyclerView mfxiaozhan;
    @BindView(R.id.mfvideo)
    RecyclerView mfvideo;

    private ArrayList<MofangBean.ListDTO> mListDTOS;
    private List<MofangBean.ListDTO> mList;
    private YIkanAdapter mYIkanAdapter;
    private MofxiaozhanAdapter mMofxiaozhanAdapter;
    private List<MofangBean.ListDTO> list;
    private int viewTimes;
    private int level;
    private int ifPay;
    @Override
    protected void loadData() {

    }

    @Override
    protected int getLayoutId(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_tab1;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = ImmersionBar.getStatusBarHeight(me);
        view.setLayoutParams(layoutParams);
        Kaigrefit();
        hyrefit();
        Mfxiaozhan();

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
                    Intent intent = new Intent(getContext(), com.yd.mofanghuanyuans.main.biz.LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
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

    public void hyrefit(){
        SharedPreferences token = getContext().getSharedPreferences("token", getContext().MODE_PRIVATE);
        String result = token.getString("result", null);
        Call<HuiyuanBean> gethuiyuan = new RetrofitUtils().retrofit().gethuiyuan(result);
        gethuiyuan.enqueue(new Callback<HuiyuanBean>() {
            @Override
            public void onResponse(Call<HuiyuanBean> call, Response<HuiyuanBean> response) {
              String code = response.body().getCode();
                if(code.equals("996")||code.equals("999")){
                    Intent intent = new Intent(getContext(), com.yd.mofanghuanyuans.main.biz.LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else {

                    level = response.body().getResult().getLevel();

                }

            }

            @Override
            public void onFailure(Call<HuiyuanBean> call, Throwable t) {

            }
        });
    }

//    private void Tokenrefit() {
//        //获取设备号
//        String uniqueID = CommUtils.getUniqueID(getContext());
//        UserInfo userInfo = new UserInfo();
//        UserInfo.ParamDTO paramDTO = new UserInfo.ParamDTO();
//        paramDTO.setAppId("wx8jf845hf30gr3h3");
//        paramDTO.setChannel(null);
//        paramDTO.setType(3);
//        paramDTO.setUnionInfo(uniqueID);
//        userInfo.setParam(paramDTO);
//        Log.d("wcx", userInfo.toString());
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Config.BASE_URL)
////                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        ApiService service = retrofit.create(ApiService.class);
//        Call<TokenBean> getlogin = service.getlogin(BaseJsonParam.create(JsonParamUtils.Userjson(userInfo)));
//        getlogin.enqueue(new Callback<TokenBean>() {
//            @Override
//            public void onResponse(Call<TokenBean> call, Response<TokenBean> response) {
//                TokenBean body = response.body();
//                mResult = body.getResult();
//                //  PreferenceUtils.putToken(result);
//                SharedPreferences mySharedPreferences = getContext().getSharedPreferences("token", getContext().MODE_PRIVATE);
//                SharedPreferences.Editor edit = mySharedPreferences.edit();
//                edit.putString("result", mResult);
//                edit.commit();
//
//
//            }
//
//            @Override
//            public void onFailure(Call<TokenBean> call, Throwable t) {
//
//            }
//        });
//    }
    public void Mfxiaozhan(){
        SharedPreferences token = getContext().getSharedPreferences("token", getContext().MODE_PRIVATE);
        String result = token.getString("result", null);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mfxiaozhan.setLayoutManager(layoutManager);
        mListDTOS = new ArrayList<>();
        mMofxiaozhanAdapter = new MofxiaozhanAdapter(mListDTOS, getContext());
        mfxiaozhan.setAdapter(mMofxiaozhanAdapter);
        rmcanBean rmcanBeans = new rmcanBean();
        rmcanBeans.setPageNum(1);
        rmcanBeans.setPageSize(3);
        rmcanBean.ParamDTO paramDTO1 = new rmcanBean.ParamDTO();
        paramDTO1.setType("145");
        rmcanBeans.setParam(paramDTO1);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService service = retrofit.create(ApiService.class);
        Call<MofangBean> mofangBeanCall = service.getmfList(result, BaseJsonParam.create(JsonParamUtils.mfrmjson(rmcanBeans)));
        mofangBeanCall.enqueue(new Callback<MofangBean>() {
            @Override
            public void onResponse(Call<MofangBean> call, Response<MofangBean> response) {
                MofangBean body = response.body();
                list = body.getList();
                // Log.d("QAZ",body.toString());
                if(list!=null){
                    mListDTOS.clear();
                    mListDTOS.addAll(list);
                    mMofxiaozhanAdapter.notifyDataSetChanged();
                }

                Mfyikan();
            }

            @Override
            public void onFailure(Call<MofangBean> call, Throwable t) {

            }
        });
        mMofxiaozhanAdapter.setJiekouhuidiaos(new MofxiaozhanAdapter.Jiekouhuidiao() {
            @Override
            public void OnClike(int position) {
if (ifPay==0){
    String content = list.get(position).getContent();
    Intent intent = new Intent(getContext(), WebActivity.class);
    intent.putExtra("url", content);
    startActivity(intent);
}else {


    if (level == 0) {
        if (position == 0) {
            String content = list.get(position).getContent();
            Intent intent = new Intent(getContext(), WebActivity.class);
            intent.putExtra("url", content);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getContext(), MembershipcenterActivity.class);
            startActivity(intent);
        }
    } else {
        String content = list.get(position).getContent();
        Intent intent = new Intent(getContext(), WebActivity.class);
        intent.putExtra("url", content);
        startActivity(intent);
    }
}



            }
        });
    }
    public void Mfyikan(){
        SharedPreferences token = getContext().getSharedPreferences("token", getContext().MODE_PRIVATE);
        String result = token.getString("result", null);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mfvideo.setLayoutManager(layoutManager);
        mListDTOS = new ArrayList<>();
        mYIkanAdapter = new YIkanAdapter(mListDTOS, getContext());
        mfvideo.setAdapter(mYIkanAdapter);
        rmcanBean rmcanBeans = new rmcanBean();
        rmcanBeans.setPageNum(1);
        rmcanBeans.setPageSize(2);
        rmcanBean.ParamDTO paramDTO1 = new rmcanBean.ParamDTO();
        paramDTO1.setType("144");
        rmcanBeans.setParam(paramDTO1);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService service = retrofit.create(ApiService.class);
        Call<MofangBean> mofangBeanCall = service.getmfList(result, BaseJsonParam.create(JsonParamUtils.mfrmjson(rmcanBeans)));
        mofangBeanCall.enqueue(new Callback<MofangBean>() {



            @Override
            public void onResponse(Call<MofangBean> call, Response<MofangBean> response) {
                MofangBean body = response.body();
                mList = body.getList();
                // Log.d("QAZ",body.toString());
                if (mList!=null){
                    mListDTOS.clear();
                    mListDTOS.addAll(mList);
                    mYIkanAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<MofangBean> call, Throwable t) {

            }
        });
        mYIkanAdapter.setJiekouhuidiaos(new YIkanAdapter.Jiekouhuidiao() {
            @Override
            public void OnClike(int position) {
                if (ifPay == 0) {
                    String id = mList.get(position).getId();
                    Mflookcount(id);
                    String videourl = mList.get(position).getContent();
                    Intent intent = new Intent(getContext(), VideoActivity.class);
                    intent.putExtra("url", videourl);
                    startActivity(intent);
                } else {
                    if (level == 0) {
                        if (position == 0) {
                            String id = mList.get(position).getId();
                            Mflookcount(id);
                            String videourl = mList.get(position).getContent();
                            Intent intent = new Intent(getContext(), VideoActivity.class);
                            intent.putExtra("url", videourl);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(getContext(), MembershipcenterActivity.class);
                            startActivity(intent);
                        }
                    } else {
                        String id = mList.get(position).getId();
                        Mflookcount(id);
                        String videourl = mList.get(position).getContent();
                        Intent intent = new Intent(getContext(), VideoActivity.class);
                        intent.putExtra("url", videourl);
                        startActivity(intent);
                    }


                }
            }
        });

    }

    public void Mflookcount(String id){
        SharedPreferences token = getContext().getSharedPreferences("token", getContext().MODE_PRIVATE);
        String result = token.getString("result", null);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService service = retrofit.create(ApiService.class);
        Call<DianjicountBean> getlookcount = service.getlookcount(result,id);
        getlookcount.enqueue(new Callback<DianjicountBean>() {
            @Override
            public void onResponse(Call<DianjicountBean> call, Response<DianjicountBean> response) {
                DianjicountBean body = response.body();
                viewTimes = body.getResult().getViewTimes();


            }

            @Override
            public void onFailure(Call<DianjicountBean> call, Throwable t) {

            }
        });



    }


}
