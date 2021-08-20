package com.yd.mofanghuanyuans.main.biz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gyf.immersionbar.ImmersionBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
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
import com.yd.mofanghuanyuans.main.adapter.MofAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.yd.mofanghuanyuans.MyApp.getContext;

// extends BaseListActivity<MvpContract.MofPresenter, MofangBean> implements MvpContract.MofView
public class MofangrmActivity extends BasePresenterActivity {


    @BindView(R.id.recycrmlist)
    RecyclerView recycrmlist;
    @BindView(R.id.smart)
    SmartRefreshLayout smart;
    private List<MofangBean.ListDTO> list;
    private ArrayList<MofangBean.ListDTO> mListDTOS;
    private MofAdapter mMofAdapter;
    private int level;
    private int ifPay;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_rm;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
      //  tab.setTitle("魔方入门");
        setToolbar("魔方入门");

                ImmersionBar.with(this).fitsSystemWindows(false)
                .statusBarColor(R.color.transparent)
                .statusBarDarkFont(false).init();

        Kaigrefit();
        SharedPreferences token = getContext().getSharedPreferences("token", getContext().MODE_PRIVATE);
        String result = token.getString("result", null);
        Call<HuiyuanBean> gethuiyuan = new RetrofitUtils().retrofit().gethuiyuan(result);
        gethuiyuan.enqueue(new Callback<HuiyuanBean>() {
            @Override
            public void onResponse(Call<HuiyuanBean> call, Response<HuiyuanBean> response) {
                level = response.body().getResult().getLevel();
            }

            @Override
            public void onFailure(Call<HuiyuanBean> call, Throwable t) {

            }
        });


        GridLayoutManager layoutManager = new GridLayoutManager(me, 2);
        recycrmlist.setLayoutManager(layoutManager);
        recycrmlist.setNestedScrollingEnabled(false);
        mListDTOS = new ArrayList<>();
        refoite();
        mMofAdapter = new MofAdapter(mListDTOS, this);
        recycrmlist.setAdapter(mMofAdapter);
        mMofAdapter.setJiekouhuidiaos(new MofAdapter.Jiekouhuidiao() {
            @Override
            public void OnClike(int position) {
                if (ifPay==0){
                    String content = list.get(position).getContent();
                    Intent intent = new Intent(MofangrmActivity.this, WebActivity.class);
                    intent.putExtra("url", content);
                    startActivity(intent);
                }else {

                    if (level == 0) {
                        if (position == 0) {
                            String content = list.get(position).getContent();
                            Intent intent = new Intent(MofangrmActivity.this, WebActivity.class);
                            intent.putExtra("url", content);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(getContext(), MembershipcenterActivity.class);
                            startActivity(intent);
                        }
                    } else {
                        String content = list.get(position).getContent();
                        Intent intent = new Intent(MofangrmActivity.this, WebActivity.class);
                        intent.putExtra("url", content);
                        startActivity(intent);
                    }
                }

            }
        });


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



    public static void start(Context context) {
        Intent intent = new Intent(context, MofangrmActivity.class);
        context.startActivity(intent);
    }

    private void refoite(){
        SharedPreferences token = getSharedPreferences("token", getContext().MODE_PRIVATE);
        String result = token.getString("result", null);
        rmcanBean rmcanBeans = new rmcanBean();
        rmcanBeans.setPageNum(1);
        rmcanBeans.setPageSize(10);
        rmcanBean.ParamDTO paramDTO = new rmcanBean.ParamDTO();
        paramDTO.setType("145");
      rmcanBeans.setParam(paramDTO);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService service = retrofit.create(ApiService.class);
        Call<MofangBean> mofangBeanCall = service.getmfList(result,BaseJsonParam.create(JsonParamUtils.mfrmjson(rmcanBeans)));
        mofangBeanCall.enqueue(new Callback<MofangBean>() {
            @Override
            public void onResponse(Call<MofangBean> call, Response<MofangBean> response) {
                MofangBean body = response.body();
                list = body.getList();
               // Log.d("QAZ",body.toString());
                if(list!=null){
                    mListDTOS.clear();
                    mListDTOS.addAll(list);
                    mMofAdapter.notifyDataSetChanged();
                }

            
               

            }

            @Override
            public void onFailure(Call<MofangBean> call, Throwable t) {

            }
        });



    }
}
