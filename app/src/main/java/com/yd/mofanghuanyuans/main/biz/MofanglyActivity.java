package com.yd.mofanghuanyuans.main.biz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gyf.immersionbar.ImmersionBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.xzq.module_base.bean.HuiyuanBean;
import com.xzq.module_base.bean.HuoappBean;
import com.yd.mofanghuanyuans.main.api.ApiService;
import com.xzq.module_base.base.BasePresenterActivity;
import com.xzq.module_base.bean.MofangBean;
import com.xzq.module_base.bean.TuijianBean;
import com.xzq.module_base.bean.rmcanBean;
import com.xzq.module_base.config.Config;
import com.xzq.module_base.mvp.BaseJsonParam;
import com.xzq.module_base.utils.JsonParamUtils;
import com.yd.mofanghuanyuans.R;
import com.yd.mofanghuanyuans.main.adapter.MflikeAdapter;
import com.yd.mofanghuanyuans.main.adapter.MofAdapter;
import com.yd.mofanghuanyuans.main.adapter.YIkanAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.yd.mofanghuanyuans.MyApp.getContext;

public class MofanglyActivity extends BasePresenterActivity {


    @BindView(R.id.recyclylist)
    RecyclerView recyclylist;
    @BindView(R.id.smarts)
    SmartRefreshLayout smarts;
    @BindView(R.id.moflikerecyc)
    RecyclerView moflikerecyc;
    private YIkanAdapter mYIkanAdapter;
    private ArrayList<MofangBean.ListDTO> mListDTOS;
    private MofAdapter mMofAdapter;
    private MflikeAdapter mMflikeAdapter;
    private List<MofangBean.ListDTO> mList;
    private List<MofangBean.ListDTO> list;
    private int pageNum=1;
    private int level;
    private int ifPay;
    public static void start(Context context) {
        Intent intent = new Intent(context, MofanglyActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_mfly;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        setToolbar("魔方乐园");
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




        mflylistrefit();
        smarts.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                smarts.finishLoadMore();
                pageNum++;
                // KonwledgePresenter konwledgePresenter = new KonwledgePresenter(this);
                //konwledgePresenter.setJson(page, 10);
                mflylistrefit();
                // mYIkanAdapter.notifyDataSetChanged();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                smarts.finishRefresh();
                pageNum=1;
                mflylistrefit();
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

    public void mflylistrefit() {
        SharedPreferences token = getSharedPreferences("token", MODE_PRIVATE);
        String result = token.getString("result", null);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclylist.setLayoutManager(layoutManager);
        mListDTOS = new ArrayList<>();
        mYIkanAdapter = new YIkanAdapter(mListDTOS, this);
        recyclylist.setAdapter(mYIkanAdapter);
        rmcanBean rmcanBeans = new rmcanBean();

        rmcanBeans.setPageNum(pageNum);
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
                if(mList!=null){
                    mListDTOS.clear();
                    mListDTOS.addAll(mList);
                    mYIkanAdapter.notifyDataSetChanged();
                }
                mflikerefit();

            }

            @Override
            public void onFailure(Call<MofangBean> call, Throwable t) {

            }
        });
        mYIkanAdapter.setJiekouhuidiaos(new YIkanAdapter.Jiekouhuidiao() {
            @Override
            public void OnClike(int position) {
            if (ifPay==0){
                String videourl = mList.get(position).getContent();
                Intent intent = new Intent(MofanglyActivity.this, VideoActivity.class);
                intent.putExtra("url", videourl);
                startActivity(intent);
            }else {


                if (level == 0) {
                    if (position == 0) {
                        String videourl = mList.get(position).getContent();
                        Intent intent = new Intent(MofanglyActivity.this, VideoActivity.class);
                        intent.putExtra("url", videourl);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getContext(), MembershipcenterActivity.class);
                        startActivity(intent);
                    }
                } else {
                    String videourl = mList.get(position).getContent();
                    Intent intent = new Intent(MofanglyActivity.this, VideoActivity.class);
                    intent.putExtra("url", videourl);
                    startActivity(intent);
                }
            }




            }
        });

    }
    public void mflikerefit(){
        SharedPreferences token = getSharedPreferences("token", MODE_PRIVATE);
        String result = token.getString("result", null);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        moflikerecyc.setLayoutManager(layoutManager);
        mListDTOS = new ArrayList<>();
        mMflikeAdapter = new MflikeAdapter(mListDTOS, this);
        moflikerecyc.setAdapter(mMflikeAdapter);
        TuijianBean tuijianBean = new TuijianBean();
        tuijianBean.setPageNum(1);
        tuijianBean.setPageSize(4);

        TuijianBean.ParamDTO paramDTO = new TuijianBean.ParamDTO();
        paramDTO.setResType("168");
        tuijianBean.setParam(paramDTO);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.BASE_URL)

                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService service = retrofit.create(ApiService.class);
        Call<MofangBean> mofangBeanCall = service.getmflikeList(result, BaseJsonParam.create(JsonParamUtils.mftuijianjson(tuijianBean)));
        mofangBeanCall.enqueue(new Callback<MofangBean>() {
            @Override
            public void onResponse(Call<MofangBean> call, Response<MofangBean> response) {
                MofangBean body = response.body();
                list = body.getList();
                // Log.d("QAZ",body.toString());
                if(list !=null){
                    mListDTOS.clear();
                    mListDTOS.addAll(list);
                    mMflikeAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<MofangBean> call, Throwable t) {

            }
        });
        mMflikeAdapter.setJiekouhuidiaos(new MflikeAdapter.Jiekouhuidiao() {
            @Override
            public void OnClike(int position) {
        if (ifPay==0){
            String content = list.get(position).getContent();
            if (content.contains(".mp4") == true) {
                Intent intent = new Intent(MofanglyActivity.this, VideoActivity.class);
                intent.putExtra("url", content);
                startActivity(intent);
            } else {
                Intent intent = new Intent(MofanglyActivity.this, WebActivity.class);
                intent.putExtra("url", content);
                startActivity(intent);
            }
        }else {
            if (level == 0) {
                if (position == 0) {
                    String content = list.get(position).getContent();
                    if (content.contains(".mp4") == true) {
                        Intent intent = new Intent(MofanglyActivity.this, VideoActivity.class);
                        intent.putExtra("url", content);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(MofanglyActivity.this, WebActivity.class);
                        intent.putExtra("url", content);
                        startActivity(intent);
                    }
                } else {
                    Intent intent = new Intent(getContext(), MembershipcenterActivity.class);
                    startActivity(intent);
                }
            } else {
                String content = list.get(position).getContent();
                if (content.contains(".mp4") == true) {
                    Intent intent = new Intent(MofanglyActivity.this, VideoActivity.class);
                    intent.putExtra("url", content);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MofanglyActivity.this, WebActivity.class);
                    intent.putExtra("url", content);
                    startActivity(intent);
                }
            }
        }

            }
        });
    }

}
