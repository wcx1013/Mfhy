package com.yd.mofanghuanyuans.main.biz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.xzq.module_base.base.BasePresenterActivity;
import com.xzq.module_base.bean.CodeBean;
import com.xzq.module_base.bean.ForgetBean;
import com.xzq.module_base.bean.TokenBean;
import com.xzq.module_base.mvp.BaseJsonParam;
import com.xzq.module_base.utils.CommUtils;
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

public class CancelaccountActivity extends BasePresenterActivity {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.bars)
    Toolbar bars;
    @BindView(R.id.shoujihao)
    EditText shoujihao;
    @BindView(R.id.yangzhengmaa)
    EditText yangzhengmaa;
    @BindView(R.id.huoquyanzhengm)
    CountDownButton huoquyanzhengm;
    @BindView(R.id.cancel)
    TextView cancel;
    @BindView(R.id.gou)
    CheckBox gou;
    @BindView(R.id.tv_pro)
    TextView tvPro;
    @BindView(R.id.ccv)
    LinearLayout ccv;
    private String result;
    private PopupWindow popupWindow;
    private String id;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cancelaccount;
    }

    @Override
    protected void initViews(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        hideToolbar();
        id = getIntent().getStringExtra("id");

        bars.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        SharedPreferences token = getSharedPreferences("token", MODE_PRIVATE);
        result = token.getString("result", null);

        huoquyanzhengm.setEnabled(false);
        shoujihao.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                huoquyanzhengm.setEnabled(s.length() == 11);
            }
        });
    }


    @OnClick({R.id.huoquyanzhengm, R.id.cancel, R.id.gou,R.id.tv_pro})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.huoquyanzhengm:

                if (!shoujihao.getText().toString().isEmpty() && shoujihao.getText().toString().length() == 11) {
                    Call<CodeBean> getcode = new RetrofitUtils().retrofit().getcode(shoujihao.getText().toString());
                    getcode.enqueue(new Callback<CodeBean>() {
                        @Override
                        public void onResponse(Call<CodeBean> call, Response<CodeBean> response) {
                            String code = response.body().getCode();

                        }

                        @Override
                        public void onFailure(Call<CodeBean> call, Throwable t) {

                        }
                    });
                }
                break;
            case R.id.cancel:
                PopupWindows();

                break;
            case R.id.gou:
                break;
            case R.id.tv_pro:
                //须知
                break;
        }
    }

    private void PopupWindows() {
        View view = LayoutInflater.from(me).inflate(R.layout.layout_zx, null);
        TextView no = view.findViewById(R.id.qx);
        TextView ok = view.findViewById(R.id.qd);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(ccv, Gravity.CENTER, 0, 0);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  String uniqueID = CommUtils.getUniqueID(CancelaccountActivity.this);
                if (!yangzhengmaa.getText().toString().isEmpty()) {
                    ForgetBean forgetBean = new ForgetBean();
                    ForgetBean.ParamBean paramBean = new ForgetBean.ParamBean();
                    paramBean.setPhone(shoujihao.getText().toString());
                    paramBean.setVerifyCode(yangzhengmaa.getText().toString());
                    paramBean.setUserId(id);
                    paramBean.setAppId("wx8jf845hf30gr3h3");
                    forgetBean.setParam(paramBean);
                    Call<TokenBean> getbind = new RetrofitUtils().retrofit().getclear(result, BaseJsonParam.create(JsonParamUtils.getforget(forgetBean)));
                    getbind.enqueue(new Callback<TokenBean>() {
                        @Override
                        public void onResponse(Call<TokenBean> call, Response<TokenBean> response) {
                            Intent intent = new Intent(CancelaccountActivity.this, ZhuxsuccessActivity.class);
                            startActivity(intent);

                        }

                        @Override
                        public void onFailure(Call<TokenBean> call, Throwable t) {

                        }
                    });
                } else {
                    MyToast.show("请输入完整");
                }





            }
        });
    }


}
