package com.yd.mofanghuanyuans.main.biz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.xzq.module_base.base.BasePresenterActivity;
import com.xzq.module_base.bean.CodeBean;
import com.xzq.module_base.bean.ForgetBean;
import com.xzq.module_base.bean.TokenBean;
import com.xzq.module_base.mvp.BaseJsonParam;
import com.xzq.module_base.utils.JsonParamUtils;
import com.xzq.module_base.utils.MyToast;
import com.xzq.module_base.views.CountDownButton;
import com.yd.mofanghuanyuans.R;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BindnumberActivity extends BasePresenterActivity {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.bars)
    Toolbar bars;
    @BindView(R.id.shoujihao)
    EditText shoujihao;
    @BindView(R.id.yangzhengmaa)
    EditText yangzhengmaa;
    @BindView(R.id.huoquyanzhengma)
    CountDownButton huoquyanzhengma;
    @BindView(R.id.queding)
    TextView queding;
    @BindView(R.id.gou)
    CheckBox gou;
    @BindView(R.id.tv_pro)
    TextView tvPro;
    @BindView(R.id.tv_pros)
    TextView tvPros;
    private String result;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bindnumber;
    }

    @Override
    protected void initViews(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        hideToolbar();
        SharedPreferences token = getSharedPreferences("token", MODE_PRIVATE);
        result = token.getString("result", null);
    }


    @OnClick({R.id.queding, R.id.gou, R.id.huoquyanzhengma,R.id.tv_pro,R.id.tv_pros})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.queding:
                if (gou.isChecked() == true && !yangzhengmaa.getText().toString().isEmpty()) {
                    ForgetBean forgetBean = new ForgetBean();
                    ForgetBean.ParamBean paramBean = new ForgetBean.ParamBean();
                    paramBean.setPhone(shoujihao.getText().toString());
                    paramBean.setVerifyCode(yangzhengmaa.getText().toString());
                    paramBean.setAppId("wx8jf845hf30gr3h3");

                    forgetBean.setParam(paramBean);
                    Call<TokenBean> getbind = new RetrofitUtils().retrofit().getbind(result, BaseJsonParam.create(JsonParamUtils.getforget(forgetBean)));
                    getbind.enqueue(new Callback<TokenBean>() {
                        @Override
                        public void onResponse(Call<TokenBean> call, Response<TokenBean> response) {


                            MyToast.show("绑定成功");
//                            Intent intent = new Intent(BindnumberActivity.this,LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<TokenBean> call, Throwable t) {

                        }
                    });
                } else {
                    MyToast.show("请输入完整");
                }
                break;
            case R.id.gou:

                break;
            case R.id.huoquyanzhengma:
                if (!shoujihao.getText().toString().isEmpty() && shoujihao.getText().toString().length() == 11) {
                    Call<CodeBean> getcode = new RetrofitUtils().retrofit().getcode(shoujihao.getText().toString());
                    getcode.enqueue(new Callback<CodeBean>() {
                        @Override
                        public void onResponse(Call<CodeBean> call, Response<CodeBean> response) {

                        }

                        @Override
                        public void onFailure(Call<CodeBean> call, Throwable t) {

                        }
                    });
                }
                break;
            case R.id.tv_pro:
                //协议
                Intent intent2 = new Intent(this, WebUrlActivity.class);
                intent2.putExtra("url", "file:///android_asset/UserProtocol.html");
                intent2.putExtra("title", "用户协议");
                startActivity(intent2);
                break;
            case R.id.tv_pros:
                Intent intent7 = new Intent(this, WebUrlActivity.class);
                intent7.putExtra("url", "file:///android_asset/PrivacyAgreement.html");
                intent7.putExtra("title", "隐私政策");
                startActivity(intent7);
                break;
        }
    }


}
