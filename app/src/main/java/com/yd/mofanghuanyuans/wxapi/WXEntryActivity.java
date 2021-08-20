package com.yd.mofanghuanyuans.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xzq.module_base.eventbus.EventUtil;
import com.xzq.module_base.utils.MyToast;
import com.xzq.module_base.utils.XZQLog;


public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI iwxapi;
    private int scene;
    public  static  final String WX_APPID="wx4996b923f732e636";
    public  static  final String WX_APPSECRET="8fb30a07b3b9e1d2f3d16522b6130402";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iwxapi = WXAPIFactory.createWXAPI(this, WX_APPID);
        iwxapi.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        iwxapi.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        if (baseReq != null && baseReq instanceof SendMessageToWX.Req) {
            SendMessageToWX.Req req = (SendMessageToWX.Req) baseReq;
            scene = req.scene;
        }

    }

    @Override
    public void onResp(BaseResp resp) {

        if (resp.getType() == ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX) {
            final boolean done = resp.errCode == BaseResp.ErrCode.ERR_OK;
            switch (scene) {
                case SendMessageToWX.Req.WXSceneSession:
                    // 分享给微信好友
                    //ToastUtils.show(done ? "分享微信好友成功" : "分享微信好友失败");
                    break;
                case SendMessageToWX.Req.WXSceneTimeline:
                    // 分享到朋友圈
                    //ToastUtils.show(done ? "分享微信朋友圈成功" : "分享微信朋友圈失败");
                    break;
                default:
                    break;
            }
            finish();
        }

        if (resp instanceof SendAuth.Resp) {
            SendAuth.Resp newResp = (SendAuth.Resp) resp;
            XZQLog.debug("code = " + newResp.code
                    + " country = " + newResp.country
                    + " lang = " + newResp.lang
                    + " state = " + newResp.state
            );
            if (ThirdLoginManager.WECHAT_STATE_LOGIN.equals(newResp.state)) {
                switch (resp.errCode) {
                    case BaseResp.ErrCode.ERR_OK:
                        //授权成功

                       EventUtil.post(ThirdLoginManager.EVENT_ACTION_WECHAT_RESP, resp);
                        break;
                    case BaseResp.ErrCode.ERR_USER_CANCEL:
                        //授权取消
                        break;
                    case BaseResp.ErrCode.ERR_AUTH_DENIED:
                        //授权被拒绝
                        break;
                    default:
                        break;
                }
            }

            finish();
        }

    }





}
