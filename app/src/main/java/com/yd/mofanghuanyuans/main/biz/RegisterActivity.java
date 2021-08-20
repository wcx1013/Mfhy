package com.yd.mofanghuanyuans.main.biz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.xzq.module_base.base.BasePresenterActivity;
import com.xzq.module_base.bean.TokenBean;
import com.xzq.module_base.bean.ZhuceBean;
import com.xzq.module_base.mvp.BaseJsonParam;
import com.xzq.module_base.utils.JsonParamUtils;
import com.xzq.module_base.utils.MyToast;
import com.yd.mofanghuanyuans.R;
import com.yd.mofanghuanyuans.main.MainActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends BasePresenterActivity {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.bars)
    Toolbar bars;
    @BindView(R.id.shoujihao)
    EditText shoujihao;
    @BindView(R.id.mima)
    EditText mima;
    @BindView(R.id.zhuce)
    TextView zhuce;
    @BindView(R.id.dl)
    TextView dl;
    @BindView(R.id.gou)
    CheckBox gou;
    @BindView(R.id.tv_pro)
    TextView tvPro;
    @BindView(R.id.tv_pros)
    TextView tvPros;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initViews(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        hideToolbar();
        bars.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick({R.id.zhuce, R.id.dl, R.id.gou,R.id.tv_pro,R.id.tv_pros})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.zhuce:
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
            case R.id.dl:
                finish();
                break;
            case R.id.gou:
                break;
            case R.id.tv_pro:
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

    private void Zhucerefit() {
        ZhuceBean zhuceBean = new ZhuceBean();
        ZhuceBean.ParamBean paramBean = new ZhuceBean.ParamBean();
        paramBean.setAccount(shoujihao.getText().toString());
        paramBean.setPassword(mima.getText().toString());
        paramBean.setAppId("wx8jf845hf30gr3h3");

        zhuceBean.setParam(paramBean);
        Call<TokenBean> getzc = new RetrofitUtils().retrofit().getzc(BaseJsonParam.create(JsonParamUtils.getzc(zhuceBean)));
        getzc.enqueue(new Callback<TokenBean>() {
            @Override
            public void onResponse(Call<TokenBean> call, Response<TokenBean> response) {

                String mResult = response.body().getResult();
                SharedPreferences mySharedPreferences = getSharedPreferences("token", MODE_PRIVATE);
                SharedPreferences.Editor edit = mySharedPreferences.edit();
                edit.putString("result", mResult);
                edit.commit();
                MainActivity.start(RegisterActivity.this);


            }

            @Override
            public void onFailure(Call<TokenBean> call, Throwable t) {

            }
        });
    }


}
