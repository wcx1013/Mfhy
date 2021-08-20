package com.yd.mofanghuanyuans.main.biz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gyf.immersionbar.ImmersionBar;
import com.xzq.module_base.bean.HuiyuanBean;
import com.xzq.module_base.bean.HuoappBean;
import com.yd.mofanghuanyuans.main.api.ApiService;
import com.xzq.module_base.base.BasePresenterActivity;
import com.xzq.module_base.bean.MofangBean;
import com.xzq.module_base.bean.rmcanBean;
import com.xzq.module_base.config.Config;
import com.xzq.module_base.mvp.BaseJsonParam;
import com.xzq.module_base.utils.JsonParamUtils;
import com.yd.mofanghuanyuans.R;
import com.yd.mofanghuanyuans.main.adapter.MfzsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MofangzsActivity extends BasePresenterActivity {
    @BindView(R.id.mfzs)
    RecyclerView mfzs;
    private ArrayList<MofangBean.ListDTO> mListDTOS;
    private MfzsAdapter mMfzsAdapter;
    private List<MofangBean.ListDTO> list;
    private int level;
    private int ifPay;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_mf_zhishi;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {

        setToolbar("魔方知识");
        ImmersionBar.with(this).fitsSystemWindows(false)
                .statusBarColor(R.color.transparent)
                .statusBarDarkFont(false).init();

        zsrefit();
        hyrefit();
        Kaigrefit();
    }

    public void Kaigrefit(){
        SharedPreferences token = getSharedPreferences("token", MODE_PRIVATE);
        String result = token.getString("result", null);
        Call<HuoappBean> gethq = new RetrofitUtils().retrofit().gethq(result);
        gethq.enqueue(new Callback<HuoappBean>() {
            @Override
            public void onResponse(Call<HuoappBean> call, Response<HuoappBean> response) {
                String code = response.body().getCode();

                ifPay = response.body().getResult().getIfPay();


            }

            @Override
            public void onFailure(Call<HuoappBean> call, Throwable t) {

            }
        });
    }


    public void hyrefit() {
        SharedPreferences token = getSharedPreferences("token", MODE_PRIVATE);
        String result = token.getString("result", null);
        Call<HuiyuanBean> gethuiyuan = new RetrofitUtils().retrofit().gethuiyuan(result);
        gethuiyuan.enqueue(new Callback<HuiyuanBean>() {
            @Override
            public void onResponse(Call<HuiyuanBean> call, Response<HuiyuanBean> response) {

                String code = response.body().getCode();
//                if (code.equals("996") || code.equals("999")) {
//                    Intent intent = new Intent(this, com.yd.mofanghuanyuans.main.biz.LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                } else {

                    level = response.body().getResult().getLevel();


               // }

            }

            @Override
            public void onFailure(Call<HuiyuanBean> call, Throwable t) {

            }
        });
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, MofangzsActivity.class);
        context.startActivity(intent);
    }

    public void zsrefit() {
        SharedPreferences token = getSharedPreferences("token", MODE_PRIVATE);
        String result = token.getString("result", null);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mfzs.setLayoutManager(layoutManager);
        mListDTOS = new ArrayList<>();
        mMfzsAdapter = new MfzsAdapter(mListDTOS, this);
        mfzs.setAdapter(mMfzsAdapter);
        rmcanBean rmcanBeans = new rmcanBean();
        rmcanBeans.setPageNum(1);
        rmcanBeans.setPageSize(10);
        rmcanBean.ParamDTO paramDTO1 = new rmcanBean.ParamDTO();
        paramDTO1.setType("143");
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
                if (list !=null){
                    mListDTOS.clear();
                    mListDTOS.addAll(list);
                    mMfzsAdapter.notifyDataSetChanged();
                }



            }

            @Override
            public void onFailure(Call<MofangBean> call, Throwable t) {

            }
        });
        mMfzsAdapter.setJiekouhuidiaos(new MfzsAdapter.Jiekouhuidiao() {
            @Override
            public void OnClike(int position) {
                if (ifPay==0){

                    String content = list.get(position).getContent();
                    Intent intent = new Intent(MofangzsActivity.this, WebActivity.class);
                    intent.putExtra("url", content);
                    startActivity(intent);
                }else {


                    if (level == 0) {
                        if (position == 0) {

                            String content = list.get(position).getContent();
                            Intent intent = new Intent(MofangzsActivity.this, WebActivity.class);
                            intent.putExtra("url", content);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(MofangzsActivity.this, MembershipcenterActivity.class);
                            startActivity(intent);
                        }
                    } else {

                        String content = list.get(position).getContent();
                        Intent intent = new Intent(MofangzsActivity.this, WebActivity.class);
                        intent.putExtra("url", content);
                        startActivity(intent);
                    }


                }

            }
        });
    }


}
