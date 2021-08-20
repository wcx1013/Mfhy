package com.yd.mofanghuanyuans.main.biz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;
import com.xzq.module_base.base.BasePresenterActivity;
import com.xzq.module_base.bean.AddPayOrderBean;
import com.xzq.module_base.bean.HuiyuanBean;
import com.xzq.module_base.bean.PayBean;
import com.xzq.module_base.bean.ShangplistBean;
import com.xzq.module_base.bean.SignorderBean;
import com.xzq.module_base.bean.YoBean;
import com.xzq.module_base.mvp.BaseJsonParam;
import com.xzq.module_base.utils.JsonParamUtils;
import com.xzq.module_base.utils.MyToast;
import com.xzq.module_base.views.DrawableTextView;
import com.yd.mofanghuanyuans.R;
import com.yd.mofanghuanyuans.main.zhifubao.PayResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MembershipcenterActivity extends BasePresenterActivity {
    @BindView(R.id.tx)
    ImageView tx;
    @BindView(R.id.username)
    TextView username;
    @BindView(R.id.yearhy)
    TextView yearhy;
    @BindView(R.id.kait)
    ImageView kait;
    @BindView(R.id.qw)
    DrawableTextView qw;
    @BindView(R.id.er)
    DrawableTextView er;
    @BindView(R.id.ty)
    DrawableTextView ty;
    @BindView(R.id.ui)
    DrawableTextView ui;
    @BindView(R.id.danyue)
    LinearLayout danyue;
    @BindView(R.id.yongjiu)
    LinearLayout yongjiu;
    @BindView(R.id.yinian)
    LinearLayout yinian;
    @BindView(R.id.anse)
    ImageView anse;
    @BindView(R.id.liangse)
    ImageView liangse;
    @BindView(R.id.ano)
    ImageView ano;
    @BindView(R.id.liango)
    ImageView liango;
    @BindView(R.id.kthy)
    TextView kthy;
    @BindView(R.id.dyjia)
    TextView dyjia;
    @BindView(R.id.dyrjia)
    TextView dyrjia;
    @BindView(R.id.dys)
    TextView dys;
    @BindView(R.id.yjjia)
    TextView yjjia;
    @BindView(R.id.yjrjia)
    TextView yjrjia;
    @BindView(R.id.yjs)
    TextView yjs;
    @BindView(R.id.ynjia)
    TextView ynjia;
    @BindView(R.id.ynrjia)
    TextView ynrjia;
    //    @BindView(R.id.yns)
//    TextView yns;
    @BindView(R.id.dyt)
    TextView dyt;
    @BindView(R.id.yjt)
    TextView yjt;
    @BindView(R.id.ynt)
    TextView ynt;
    @BindView(R.id.ha)
    ImageView ha;
    @BindView(R.id.gmxz)
    TextView gmxz;

    private IWXAPI api;
    public static final String WX_APPID = "wx4996b923f732e636";
    private String result;
    private int wcx = 1;
    private List<ShangplistBean.ListBean> list;
    private float v;
    private float v2;
    private float v4;
    private final int SDK_PAY_FLAG = 1;

    private int zftype = 0;
    private String type;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_membershipcenter;
    }

    @Override
    protected void initViews(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        hideToolbar();
        SharedPreferences token = getSharedPreferences("token", MODE_PRIVATE);
        result = token.getString("result", null);
        ha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        hyrefit();
        spjiarefit();
        danyue.setSelected(true);
        yongjiu.setSelected(false);
        yinian.setSelected(false);
    }

    public void hyrefit() {
        Call<HuiyuanBean> gethuiyuan = new RetrofitUtils().retrofit().gethuiyuan(result);
        gethuiyuan.enqueue(new Callback<HuiyuanBean>() {
            @Override
            public void onResponse(Call<HuiyuanBean> call, Response<HuiyuanBean> response) {


                String id = response.body().getResult().getId();
                userrefit(id);

            }

            @Override
            public void onFailure(Call<HuiyuanBean> call, Throwable t) {

            }
        });
    }

    public void userrefit(String id) {
        Call<YoBean> getpo = new RetrofitUtils().retrofit().getpo(result, id);
        getpo.enqueue(new Callback<YoBean>() {
            @Override
            public void onResponse(Call<YoBean> call, Response<YoBean> response) {
                type = response.body().getResult().getType();


            }

            @Override
            public void onFailure(Call<YoBean> call, Throwable t) {

            }
        });
    }

    private void spjiarefit() {
        Call<ShangplistBean> getsplist = new RetrofitUtils().retrofit().getsplist(result);
        getsplist.enqueue(new Callback<ShangplistBean>() {
            @Override
            public void onResponse(Call<ShangplistBean> call, Response<ShangplistBean> response) {
                list = response.body().getList();
                Float aFloat = Float.valueOf(100);

                dyt.setText(list.get(0).getName());
                int price = list.get(0).getPrice();
                Float priceq = Float.valueOf(price);
                v = priceq / aFloat;
                int originalPrice = list.get(0).getOriginalPrice();
                Float aFloat1 = Float.valueOf(originalPrice);
                float v1 = aFloat1 / aFloat;
                dyjia.setText(String.valueOf(v));//实际价格
                dyrjia.setText(String.valueOf(v1));//原定价格
                // dys.setText(originalPrice-price+"");//aFloat1-priceq+""

                yjt.setText(list.get(1).getName());
                int price1 = list.get(1).getPrice();
                int originalPrice1 = list.get(1).getOriginalPrice();
                Float aFloat2 = Float.valueOf(price1);
                v2 = aFloat2 / aFloat;
                Float aFloat3 = Float.valueOf(originalPrice1);
                float v3 = aFloat3 / aFloat;
                yjjia.setText(String.valueOf(v2));
                yjrjia.setText(String.valueOf(v3));
                //   yjs.setText(aFloat3-aFloat2+"");

                ynt.setText(list.get(2).getName());
                int price2 = list.get(2).getPrice();
                int originalPrice2 = list.get(2).getOriginalPrice();
                Float aFloat4 = Float.valueOf(price2);
                v4 = aFloat4 / aFloat;
                Float aFloat5 = Float.valueOf(originalPrice2);
                float v5 = aFloat5 / aFloat;
                ynjia.setText(String.valueOf(v4));
                ynrjia.setText(String.valueOf(v5));
                // yns.setText(aFloat5-aFloat4+"");
            }

            @Override
            public void onFailure(Call<ShangplistBean> call, Throwable t) {

            }
        });
    }


    @OnClick({R.id.kait, R.id.qw, R.id.er, R.id.ty, R.id.ui, R.id.danyue, R.id.yongjiu, R.id.yinian, R.id.anse, R.id.liangse, R.id.ano, R.id.liango, R.id.kthy,R.id.gmxz})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.kait:
                break;
            case R.id.qw:
                break;
            case R.id.er:
                break;
            case R.id.ty:
                break;
            case R.id.ui:
                break;
            case R.id.danyue:
                wcx = 1;
                danyue.setSelected(true);
                yongjiu.setSelected(false);
                yinian.setSelected(false);
                break;
            case R.id.yongjiu:
                wcx = 2;
                danyue.setSelected(false);
                yongjiu.setSelected(true);
                yinian.setSelected(false);
                break;
            case R.id.yinian:
                wcx = 3;
                danyue.setSelected(false);
                yongjiu.setSelected(false);
                yinian.setSelected(true);
                break;
            case R.id.anse:
                zftype = 1;
                anse.setVisibility(View.GONE);
                ano.setVisibility(View.VISIBLE);
                liangse.setVisibility(View.VISIBLE);
                liango.setVisibility(View.GONE);
                break;
            case R.id.liangse:
//                zftype = 0;
//                anse.setVisibility(View.VISIBLE);
//                liangse.setVisibility(View.GONE);
                break;
            case R.id.ano:
                zftype = 2;
                ano.setVisibility(View.GONE);
                liango.setVisibility(View.VISIBLE);
                liangse.setVisibility(View.GONE);
                anse.setVisibility(View.VISIBLE);
                break;
            case R.id.liango:
//                zftype = 0;
//                ano.setVisibility(View.VISIBLE);
//                liango.setVisibility(View.GONE);
                break;
            case R.id.kthy:
                if (type.equals("2")) {
                    MyToast.show("游客模式无法购买会员，请选择账号密码或微信登录!");
                } else {

                    AddPayOrderBean addPayOrderBean = new AddPayOrderBean();
                    AddPayOrderBean.ParamBean paramBean = new AddPayOrderBean.ParamBean();
                    if (wcx == 1) {
                        paramBean.setAmount(Integer.valueOf(list.get(0).getPrice()));
                        paramBean.setBody(list.get(0).getName());
                        paramBean.setChannel("HW");
                        paramBean.setCurrencyType("CNY");
                        if (zftype == 1) {
                            paramBean.setRecAccount("55");
                        } else {
                            paramBean.setRecAccount("56");
                        }

                        paramBean.setSubject(list.get(0).getName());
                        AddPayOrderBean.GoodsListBean goodsListBean = new AddPayOrderBean.GoodsListBean();
                        goodsListBean.setId(list.get(0).getGoodId());
                        goodsListBean.setPrice(Integer.valueOf(list.get(0).getPrice()));
                        ArrayList<AddPayOrderBean.GoodsListBean> goodsListBeans = new ArrayList<>();
                        goodsListBeans.add(goodsListBean);
                        paramBean.setGoodsList(goodsListBeans);
                        addPayOrderBean.setParam(paramBean);
                        //  RequestBody requestBody = BaseJsonParam.create(JsonParamUtils.getdd(addPayOrderBean));
                        Gson gson = new Gson();
                        String s = gson.toJson(addPayOrderBean);
                        Log.d("JSON", s);
                    } else if (wcx == 2) {
                        paramBean.setAmount(Integer.valueOf(list.get(1).getPrice()));
                        paramBean.setBody(list.get(1).getName());
                        paramBean.setChannel("HW");
                        paramBean.setCurrencyType("CNY");
                        if (zftype == 1) {
                            paramBean.setRecAccount("55");
                        } else {
                            paramBean.setRecAccount("56");
                        }
                        paramBean.setSubject(list.get(1).getName());
                        AddPayOrderBean.GoodsListBean goodsListBean = new AddPayOrderBean.GoodsListBean();
                        goodsListBean.setId(list.get(1).getGoodId());
                        goodsListBean.setPrice(Integer.valueOf(list.get(1).getPrice()));
                        ArrayList<AddPayOrderBean.GoodsListBean> goodsListBeans = new ArrayList<>();
                        goodsListBeans.add(goodsListBean);
                        paramBean.setGoodsList(goodsListBeans);
                        addPayOrderBean.setParam(paramBean);
                    } else if (wcx == 3) {
                        paramBean.setAmount(Integer.valueOf(list.get(2).getPrice()));
                        paramBean.setBody(list.get(2).getName());
                        paramBean.setChannel("HW");
                        paramBean.setCurrencyType("CNY");
                        if (zftype == 1) {
                            paramBean.setRecAccount("55");
                        } else {
                            paramBean.setRecAccount("56");
                        }
                        paramBean.setSubject(list.get(2).getName());
                        AddPayOrderBean.GoodsListBean goodsListBean = new AddPayOrderBean.GoodsListBean();
                        goodsListBean.setId(list.get(2).getGoodId());
                        goodsListBean.setPrice(Integer.valueOf(list.get(2).getPrice()));
                        ArrayList<AddPayOrderBean.GoodsListBean> goodsListBeans = new ArrayList<>();
                        goodsListBeans.add(goodsListBean);
                        paramBean.setGoodsList(goodsListBeans);
                        addPayOrderBean.setParam(paramBean);
                    }
                    new RetrofitUtils().retrofit().getadd(result, BaseJsonParam.create(JsonParamUtils.getdd(addPayOrderBean))).enqueue(new Callback<PayBean>() {
                        @Override
                        public void onResponse(Call<PayBean> call, Response<PayBean> response) {


                            PayBean.AddPayOrderInfo result = response.body().getResult();
                            String signOrder = result.getSignOrder();

                            if (zftype == 1) {
                                SignorderBean signorderBean = JSON.parseObject(signOrder, SignorderBean.class);
                                //微信支付
                                api = WXAPIFactory.createWXAPI(MembershipcenterActivity.this, WX_APPID, true);
                                api.registerApp(WX_APPID);

                                PayReq payReq = new PayReq();
                                payReq.appId = WX_APPID;
                                payReq.partnerId = "1525928841";
                                payReq.prepayId = signorderBean.getPrepay_id();
                                payReq.packageValue = "Sign=WXPay";
                                payReq.nonceStr = signorderBean.getNonce_str();
                                String time = System.currentTimeMillis() / 1000 + "";
                                payReq.timeStamp = time;
                                SortedMap<String, String> sortedMap = new TreeMap<>();
                                sortedMap.put("appid", WX_APPID);
                                sortedMap.put("partnerid", "1525928841");
                                sortedMap.put("prepayid", signorderBean.getPrepay_id());
                                sortedMap.put("noncestr", signorderBean.getNonce_str());
                                sortedMap.put("timeStamp", time);
                                sortedMap.put("packageValue", "Sign=WXPay");
                                // payReq.sign = WeChatField.getSign(sortedMap);//重新存储了sign字段之外，再次签名，要不然唤起微信支付会返回-1，特别坑爹的是，键名一定要去掉下划线，不然返回-1
                                payReq.sign = "MD5";//重新存储了sign字段之外，再次签名，要不然唤起微信支付会返回-1，特别坑爹的是，键名一定要去掉下划线，不然返回-1
                                api.sendReq(payReq);

                            } else if (zftype == 2) {
                                //支付宝支付
                                payV2(signOrder);
                            } else {
                                MyToast.show("请先选择支付渠道！");
                            }


                        }

                        @Override
                        public void onFailure(Call<PayBean> call, Throwable t) {

                        }
                    });
                }

                break;
            case R.id.gmxz:
                //购买须知

                Intent intent7 = new Intent(this, WebUrlActivity.class);
                intent7.putExtra("url", "file:///android_asset/PurchaseNotes.html");
                intent7.putExtra("title", "购买须知");
                startActivity(intent7);
                break;
        }
    }


    /**
     * 支付宝支付业务示例
     */
    public void payV2(String orderInfo) {
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(MembershipcenterActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    switch (payResult.getResultStatus()) {
                        case "9000":
                            MobclickAgent.onEvent(MembershipcenterActivity.this, "payment_success", "付款成功");
                            // mPresenter.queryOrder(orderId, true);查询订单
                            break;
                        case "6004":
                        case "8000":
                            MyToast.show("正在处理中");

                            break;
                        case "4000":
                            MobclickAgent.onEvent(MembershipcenterActivity.this, "payment_fali", "付款失败");
                            MyToast.show("订单支付失败");

                            break;
                        case "5000":
                            MyToast.show("重复请求");

                            break;
                        case "6001":
                            MyToast.show("已取消支付");

                            break;
                        case "6002":
                            MyToast.show("网络连接出错");

                            break;
                        default:
                            MobclickAgent.onEvent(MembershipcenterActivity.this, "payment_fali", "付款失败");
                            MyToast.show("支付失败");

                            break;
                    }
                }
            }
            return false;
        }
    });



}
