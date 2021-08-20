package com.yd.mofanghuanyuans.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;
    public  static  final String WX_APPID="wx4996b923f732e636";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         api = WXAPIFactory.createWXAPI(this, WX_APPID, false);
         //api.registerApp(WX_APPID);
        try {
            api.handleIntent(getIntent(), this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        int result = 0;
        //有时候支付结果还需要发送给服务器确认支付状态
        if (resp.getType()== ConstantsAPI.COMMAND_PAY_BY_WX){
            if (resp.errCode==0){
                Toast.makeText(this,"支付成功",Toast.LENGTH_LONG).show();
            }else if (resp.errCode==-2){
                Toast.makeText(this,"取消支付",Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this,"支付失败",Toast.LENGTH_LONG).show();
            }
            finish();
        }
    }


}
