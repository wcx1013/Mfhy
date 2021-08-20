package com.yd.mofanghuanyuans.main.biz;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.yd.mofanghuanyuans.main.api.ApiService;
import com.xzq.module_base.base.BasePresenterActivity;
import com.xzq.module_base.bean.FeedBackBean;
import com.xzq.module_base.bean.TokenBean;
import com.xzq.module_base.config.Config;
import com.xzq.module_base.mvp.BaseJsonParam;
import com.xzq.module_base.utils.CommUtils;
import com.xzq.module_base.utils.JsonParamUtils;
import com.yd.mofanghuanyuans.R;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HelpFeedbackActivity extends BasePresenterActivity {
    @BindView(R.id.et_context)
    EditText etContext;
    @BindView(R.id.tv_input_sum)
    TextView tvInputSum;
    @BindView(R.id.et_lxfs)
    EditText etLxfs;
    @BindView(R.id.tv_fs)
    TextView tvFs;
    @BindView(R.id.tv_wx)
    TextView tvWx;
    @BindView(R.id.tv_wx_fz)
    TextView tvWxFz;
    @BindView(R.id.tv_qq)
    TextView tvQq;
    @BindView(R.id.tv_qq_fz)
    TextView tvQqFz;

    private FeedBackBean mFeedBackBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_help_feedback;
    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
    //    setDarkFont();
        setToolbar("帮助反馈");
     //  StatusBarUtils.setWindowStatusBarColor(this, "#0F0C3A");
//        ImmersionBar.with(this).fitsSystemWindows(true)
//                .statusBarColor(R.color.transparent)
//                .statusBarDarkFont(false).init();

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.black));

        ImmersionBar.with(this)
                .fitsSystemWindows(false)
                .statusBarColor(R.color.transparent)
                .statusBarDarkFont(true).init();

        CommUtils.setEditTextInhibitInputSpaceAndTextLength(etContext, 500);
        etContext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvInputSum.setText(String.valueOf(etContext.getText().toString().trim().length()));
            }
        });
        String feednr = etContext.getText().toString();
        String lianxi = etLxfs.getText().toString();
        mFeedBackBean = new FeedBackBean();
        FeedBackBean.FeedBackInfo feedBackInfo = new FeedBackBean.FeedBackInfo();
        feedBackInfo.setContact(lianxi);
        feedBackInfo.setContent(feednr);
        mFeedBackBean.setParam(feedBackInfo);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_fs, R.id.tv_wx_fz, R.id.tv_qq_fz})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_fs:

                SharedPreferences token = getSharedPreferences("token", MODE_PRIVATE);
                String result = token.getString("result", null);
                if (!etContext.getText().toString().isEmpty() && !etLxfs.getText().toString().isEmpty()) {

                    FeedBackBean feedBackBean = new FeedBackBean();
                    FeedBackBean.FeedBackInfo feedBackInfo = new FeedBackBean.FeedBackInfo();
                    feedBackInfo.setContent(etContext.getText().toString());
                    feedBackInfo.setContact(etLxfs.getText().toString());
                    feedBackBean.setParam(feedBackInfo);

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(Config.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    ApiService service = retrofit.create(ApiService.class);
                    Call<TokenBean> tokenBeanCall = service.getfeedBack(result, BaseJsonParam.create(JsonParamUtils.feekjson(feedBackBean)));
                    tokenBeanCall.enqueue(new Callback<TokenBean>() {
                        @Override
                        public void onResponse(Call<TokenBean> call, Response<TokenBean> response) {
                            ToastUtils.showLong("提交成功");
                            finish();
                        }

                        @Override
                        public void onFailure(Call<TokenBean> call, Throwable t) {
                            ToastUtils.showLong("提交失败，请重新提交");
                        }
                    });

                } else {
                    ToastUtils.showLong("请输入反馈内容及联系方式");
                }
                break;
            case R.id.tv_wx_fz:
                String contentWx = tvWx.getText().toString().split("：")[1];
                // qq复制
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(contentWx);
                ToastUtils.showShort("复制成功");
                break;
            case R.id.tv_qq_fz:
                String content = tvQq.getText().toString().split("：")[1];
                // qq复制
                ClipboardManager cm1 = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm1.setText(content);
                ToastUtils.showShort("复制成功");
                break;
        }
    }

}
