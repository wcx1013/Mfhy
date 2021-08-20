package com.yd.mofanghuanyuans.main.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.xzq.module_base.base.BasePresenterFragment;
import com.xzq.module_base.bean.HuiyuanBean;
import com.xzq.module_base.bean.MarketPkgsBean;
import com.xzq.module_base.bean.YoBean;
import com.xzq.module_base.utils.CommUtils;
import com.xzq.module_base.utils.GlideUtils;
import com.xzq.module_base.utils.MyToast;
import com.xzq.module_base.views.DrawableTextView;
import com.yd.mofanghuanyuans.R;
import com.yd.mofanghuanyuans.main.adapter.GwhpPopupwindowAdapter;
import com.yd.mofanghuanyuans.main.biz.AboutUsActivity;
import com.yd.mofanghuanyuans.main.biz.CancelaccountActivity;
import com.yd.mofanghuanyuans.main.biz.HelpFeedbackActivity;
import com.yd.mofanghuanyuans.main.biz.LoginActivity;
import com.yd.mofanghuanyuans.main.biz.MembershipcenterActivity;
import com.yd.mofanghuanyuans.main.biz.NoScrollGridView;
import com.yd.mofanghuanyuans.main.biz.RetrofitUtils;
import com.yd.mofanghuanyuans.main.biz.WebUrlActivity;
import com.yd.mofanghuanyuans.main.biz.ZhuxsuccessActivity;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import am.widget.shapeimageview.ShapeImageView;
import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * MeFragment
 * Created by xzq on 2020/8/4.
 */
public class MeFragment extends BasePresenterFragment {

    @BindView(R.id.view4)
    View view4;
    @BindView(R.id.tousxing)
    ShapeImageView tousxing;
    //    @BindView(R.id.jiarutuandui)
//    DrawableTextView jiarutuandui;
//    @BindView(R.id.fan1)
//    ImageView fan1;
    @BindView(R.id.meshoucang)
    DrawableTextView meshoucang;
    @BindView(R.id.fan2)
    ImageView fan2;
    @BindView(R.id.myyinhangka)
    DrawableTextView myyinhangka;
    @BindView(R.id.fan8)
    ImageView fan8;
    @BindView(R.id.shouhuodizhi)
    DrawableTextView shouhuodizhi;
    @BindView(R.id.fan3)
    ImageView fan3;
    @BindView(R.id.shiyongjilu)
    DrawableTextView shiyongjilu;
    @BindView(R.id.fan4)
    ImageView fan4;
    @BindView(R.id.kefu)
    DrawableTextView kefu;
    @BindView(R.id.fan5)
    ImageView fan5;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.daotime)
    TextView daotime;
    @BindView(R.id.chakan)
    ImageView chakan;

    @BindView(R.id.zxzh)
    DrawableTextView zxzh;
    @BindView(R.id.fan6)
    ImageView fan6;
    @BindView(R.id.tuilogin)
    TextView tuilogin;
    private ArrayList<MarketPkgsBean> installedMarketPkgs;
    private int level;
    private String id;


    private long mDay = 23;// 天
    private long mHour = 11;//小时,
    private long mMin = 56;//分钟,
    private long mSecond = 32;//秒

    private Timer mTimer;

    private Handler timeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                computeTime();
                daotime.setText("距离会员到期还有"+mDay);//天数不用补位
//                mHours_Tv.setText(getTv(mHour));
//                mMinutes_Tv.setText(getTv(mMin));
//                mSeconds_Tv.setText(getTv(mSecond));
                if (mSecond == 0 &&  mDay == 0 && mHour == 0 && mMin == 0 ) {
                    mTimer.cancel();
                }
            }
        }
    };
    private String type;

    private String getTv(long l){
        if(l>=10){
            return l+"";
        }else{
            return "0"+l;//小于10,,前面补位一个"0"
        }
    }




    @Override
    protected void loadData() {

    }

    @Override
    protected int getLayoutId(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_me;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        ViewGroup.LayoutParams layoutParams = view4.getLayoutParams();
        layoutParams.height = ImmersionBar.getStatusBarHeight(me);
        view4.setLayoutParams(layoutParams);

        installedMarketPkgs = CommUtils.getInstalledMarketPkgs(getContext());
        hyrefit();

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
                    id = response.body().getResult().getId();
                    level = response.body().getResult().getLevel();
                    userrefit(id,level);
                }

            }

            @Override
            public void onFailure(Call<HuiyuanBean> call, Throwable t) {

            }
        });
    }

    public void userrefit(String id,int level){
        SharedPreferences token = getContext().getSharedPreferences("token", getContext().MODE_PRIVATE);
        String result = token.getString("result", null);
        Call<YoBean> getpo = new RetrofitUtils().retrofit().getpo(result, id);
        getpo.enqueue(new Callback<YoBean>() {
            @Override
            public void onResponse(Call<YoBean> call, Response<YoBean> response) {

                String headImgUrl = response.body().getResult().getHeadImgUrl();

                String nickName = response.body().getResult().getNickName();
                type = response.body().getResult().getType();
                String account = response.body().getResult().getAccount();
                // if(!headImgUrl.equals(null)){
                    GlideUtils.loadHead(tousxing,headImgUrl);
               // }
                if(type.equals("0")){
                    name.setText(account);
                }else if(type.equals("2")){
                    name.setText("游客YY");
                }else if(type.equals("3")){
                    name.setText(nickName);
                }
                if(level==0){
                    daotime.setText("普通用户");
                }else if(level==2){
                    //startRun();
                    daotime.setText("月度会员");
                }else if(level==4){
                   // startRun();
                    daotime.setText("年度会员");
                } else {
                    daotime.setText("永久会员");
                }
            }

            @Override
            public void onFailure(Call<YoBean> call, Throwable t) {

            }
        });
    }

    @OnClick({R.id.meshoucang, R.id.myyinhangka, R.id.shouhuodizhi, R.id.shiyongjilu, R.id.kefu, R.id.chakan, R.id.zxzh,R.id.tuilogin})
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.jiarutuandui:
//                break;
            case R.id.meshoucang:
                MyToast.showSucceed("已是最新版本");
                //MineActivityPermissionsDispatcher.initPermissonWithPermissionCheck(this);
                break;
            case R.id.myyinhangka:
                Intent intent = new Intent(getContext(), AboutUsActivity.class);
                startActivity(intent);
                break;
            case R.id.shouhuodizhi://隐私政策
                Intent intent7 = new Intent(getContext(), WebUrlActivity.class);
                intent7.putExtra("url", "file:///android_asset/PrivacyAgreement.html");
                intent7.putExtra("title", "隐私政策");
                startActivity(intent7);
                break;
            case R.id.shiyongjilu://意见反馈
                Intent intent8 = new Intent(getContext(), HelpFeedbackActivity.class);
                startActivity(intent8);
                break;
            case R.id.kefu://给我好评
                if (installedMarketPkgs != null && installedMarketPkgs.size() > 0) {
                    showSelectDialog();
                } else {
                    // ToastUtils.shortShowStr(this, "手机暂无应用商店");
                    ToastUtils.showShort("手机暂无应用商店");
                }
                break;
            case R.id.chakan:
                if(type.equals("2")){
                    MyToast.show("游客模式无法购买会员，请选择账号密码或微信登录!");
                }else {
                    Intent intent2 = new Intent(getContext(), MembershipcenterActivity.class);
                    startActivity(intent2);
                }

                break;
            case R.id.zxzh:
                Intent intent1 = new Intent(getContext(), CancelaccountActivity.class);
                intent1.putExtra("id",id);
                startActivity(intent1);
                break;
            case R.id.tuilogin:

                SharedPreferences mySharedPreferences = getContext().getSharedPreferences("token", getContext().MODE_PRIVATE);
                SharedPreferences.Editor edit = mySharedPreferences.edit();
                edit.putString("result", "wcx");
                edit.commit();
                Intent intent3 = new Intent(getContext(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent3);
                break;




        }
    }


    /**
     * 跳转应用商店弹窗
     */
    private void showSelectDialog() {
        final AlertDialog dialog;
        View diaView = View.inflate(getContext(), R.layout.gwhp_popupwindow, null);
        NoScrollGridView grid_view = diaView.findViewById(R.id.grid_view);

        GwhpPopupwindowAdapter gwhpPopupwindowAdapter = new GwhpPopupwindowAdapter(getContext(), installedMarketPkgs);
        grid_view.setAdapter(gwhpPopupwindowAdapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(diaView);
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        //   Display display = getWindowManager().getDefaultDisplay();

        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setWindowAnimations(R.style.popupAnimation);
        lp.gravity = Gravity.BOTTOM;
        // lp.width = (int) (display.getWidth()); //设置宽度
        dialog.getWindow().setAttributes(lp);

        grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 跳转应用商店
                MarketPkgsBean marketPkgsBean = installedMarketPkgs.get(position);
                CommUtils.launchAppDetail(getContext(), CommUtils.getApkPkgName(getContext()), marketPkgsBean.getPkgName());
                dialog.dismiss();
            }
        });
    }

    /**
     * 开启倒计时
     *  //time为Date类型：在指定时间执行一次。
     *        timer.schedule(task, time);
     *  //firstTime为Date类型,period为long，表示从firstTime时刻开始，每隔period毫秒执行一次。
     *       timer.schedule(task, firstTime,period);
     *  //delay 为long类型：从现在起过delay毫秒执行一次。
     *       timer.schedule(task, delay);
     *  //delay为long,period为long：从现在起过delay毫秒以后，每隔period毫秒执行一次。
     *       timer.schedule(task, delay,period);
     */
    private void startRun() {
        TimerTask mTimerTask = new TimerTask() {
            @Override
            public void run() {
                Message message = Message.obtain();
                message.what = 1;
                timeHandler.sendMessage(message);
            }
        };
        mTimer.schedule(mTimerTask,0,1000);
    }

    /**
     * 倒计时计算
     */
    private void computeTime() {
        mSecond--;
        if (mSecond < 0) {
            mMin--;
            mSecond = 59;
            if (mMin < 0) {
                mMin = 59;
                mHour--;
                if (mHour < 0) {
                    // 倒计时结束
                    mHour = 23;
                    mDay--;
                    if(mDay < 0){
                        // 倒计时结束
                        mDay = 0;
                        mHour= 0;
                        mMin = 0;
                        mSecond = 0;
                    }
                }
            }
        }
    }

}
