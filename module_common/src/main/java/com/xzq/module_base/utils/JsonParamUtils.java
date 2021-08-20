package com.xzq.module_base.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.xzq.module_base.User;
import com.xzq.module_base.bean.AddPayOrderBean;
import com.xzq.module_base.bean.DianjicountBean;
import com.xzq.module_base.bean.FeedBackBean;
import com.xzq.module_base.bean.ForgetBean;
import com.xzq.module_base.bean.GgBean;
import com.xzq.module_base.bean.IdBean;
import com.xzq.module_base.bean.LoginInfoBean;
import com.xzq.module_base.bean.ShangpinlistBean;
import com.xzq.module_base.bean.TuijianBean;
import com.xzq.module_base.bean.UserInfo;
import com.xzq.module_base.bean.ZhuceBean;
import com.xzq.module_base.bean.rmcanBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonParamUtils {
    public static String Userjson(UserInfo user) {//String appId, String channel, int type, String unionInfo
        Gson gson = new Gson();
        String s = gson.toJson(user);
//        try {
//           /* postJson.put("appId", appId);
//            postJson.put("channel", channel);
//            postJson.put("type", type); @Field("pageNum") int pageNum,
//                                                    @Field("pageSize") int pageSize,
//                                                    @Field("type") int type
//            postJson.put("unionInfo", unionInfo);*/
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        return s;
    }
    public static String mfrmjson(rmcanBean rmcan) {
        Gson gson = new Gson();
        String s = gson.toJson(rmcan);
        return s;
    }
    public static String mftuijianjson(TuijianBean tuijian) {
        Gson gson = new Gson();
        String s = gson.toJson(tuijian);
        return s;
    }
    public static String feekjson(FeedBackBean feedback) {
        Gson gson = new Gson();
        String s = gson.toJson(feedback);
        return s;
    }
    public static String ggjson(GgBean ggBean) {
        Gson gson = new Gson();
        String s = gson.toJson(ggBean);
        return s;
    }
    public static String lookjson(IdBean idBean) {
        Gson gson = new Gson();
        String s = gson.toJson(idBean);
        return s;
    }
    public static String getzc(ZhuceBean zhuceBean) {
        Gson gson = new Gson();
        String s = gson.toJson(zhuceBean);
        return s;
    }
    public static String getforget(ForgetBean forgetBean) {
        Gson gson = new Gson();
        String s = gson.toJson(forgetBean);
        return s;
    }
    public static String getsp(ShangpinlistBean shangpinlistBean) {
        Gson gson = new Gson();
        String s = gson.toJson(shangpinlistBean);
        return s;
    }
    public static String getdd(AddPayOrderBean addPayOrderBean) {
        Gson gson = new Gson();
        String s = gson.toJson(addPayOrderBean);
        return s;
    }
    public static String getlog(LoginInfoBean loginInfoBean) {
        Gson gson = new Gson();
        String s = gson.toJson(loginInfoBean);
        return s;
    }

}
