package com.xzq.module_base.api;

import com.xzq.module_base.bean.CodeBean;
import com.xzq.module_base.bean.DeviceDto;
import com.xzq.module_base.bean.DianjicountBean;
import com.xzq.module_base.bean.GuanggaoBean;
import com.xzq.module_base.bean.LoginDto;
import com.xzq.module_base.bean.LoginInfoBean;
import com.xzq.module_base.bean.MofangBean;
import com.xzq.module_base.bean.RakingDto;
import com.xzq.module_base.bean.SceneDto;
import com.xzq.module_base.bean.ShangplistBean;
import com.xzq.module_base.bean.TokenBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * ApiService
 *
 * @author xzq
 */
public interface ApiService {

    @Multipart
    @POST("/qixing-app/app/upload/uploadImg")
    Observable<NetBean<String>> uploadImg(@Part MultipartBody.Part file);

    @FormUrlEncoded
    @POST("/qixing-app/app/user/login")
    Observable<NetBean<LoginDto>> login(
            @Field("userName") String userName,
            @Field("password") String password
    );

    @GET("/qixing-app/app/goods/goodsList")
    Observable<NetBean<List<DeviceDto>>> getDevices(@Query("token") String token,
                                                    @Query("page") int page,
                                                    @Query("limit") int limit
    );

    @GET("/qixing-app/app/goods/goodsList")
    Observable<NetBean<List<SceneDto>>> getSceneList(@Query("token") String token,
                                                     @Query("page") int page,
                                                     @Query("limit") int limit
    );

    @GET("/qixing-app/app/goods/goodsList")
    Observable<NetBean<List<RakingDto>>> getRakingGlobal(@Query("token") String token,
                                                         @Query("page") int page,
                                                         @Query("limit") int limit
    );

    @GET("/qixing-app/app/goods/goodsList")
    Observable<NetBean<List<RakingDto>>> getRakingMySelf(@Query("token") String token,
                                                         @Query("page") int page,
                                                         @Query("limit") int limit
    );
  //  @FormUrlEncoded
  @Headers("Content-type:application/json; charset=utf-8")
    @POST("res-ext/list")
    Call <MofangBean> getmfList(@Header("Authorization") String Authorization, @Body RequestBody requestBody);//@Body String Authorization,

@Headers("Content-type:application/json; charset=utf-8")
@POST("res-ext/top-list")
Call <MofangBean> getmflikeList(@Header("Authorization") String Authorization, @Body RequestBody requestBody);//@Body String Authorization

    // 登录获取token
    @Headers("Content-type:application/json; charset=utf-8")
    @POST("user/login")
    Call<TokenBean> getlogin(@Body RequestBody requestBody);

    // 帮助反馈
    @Headers("Content-type:application/json; charset=utf-8")
    @POST("feedback/")
    Call<TokenBean> getfeedBack(@Header("Authorization") String Authorization,@Body RequestBody requestBody);

    //广告
    @Headers("Content-type:application/json; charset=utf-8")
    @POST("app/v-check")
    Call<GuanggaoBean> getgg(@Header("Authorization") String Authorization, @Body RequestBody requestBody);

    //点击次数
    @Headers("Content-type:application/json; charset=utf-8")
    @GET("res-ext/{id}")
    Call<DianjicountBean> getlookcount(@Header("Authorization") String Authorization,@Path("id") String id);

    //注册/登录
    @Headers("Content-type:application/json; charset=utf-8")
    @GET("")
    Call<TokenBean> getzc(@Body RequestBody requestBody);

    //获取验证码
    @Headers("Content-type:application/json; charset=utf-8")
    @GET("user/{phone}")
    Call<CodeBean> getcode(@Header("Authorization") String Authorization,@Path("phone") String phone);

    //忘记密码
    @Headers("Content-type:application/json; charset=utf-8")
    @GET("user/phone/pwd/")
    Call<TokenBean> getforgetpassword(@Header("Authorization") String Authorization,@Body RequestBody requestBody);

    //绑定手机号
    @Headers("Content-type:application/json; charset=utf-8")
    @PUT("user/phone/account")
    Call<TokenBean> getbind(@Header("Authorization") String Authorization,@Body RequestBody requestBody);

    //用户手机号注销
    @Headers("Content-type:application/json; charset=utf-8")
    @PUT("user/phone/logout/")
    Call<TokenBean> getclear(@Header("Authorization") String Authorization,@Body RequestBody requestBody);

    //获取商品列表
    @Headers("Content-type:application/json; charset=utf-8")
    @PUT("goods/list")
    Call<ShangplistBean> getsplist(@Header("Authorization") String Authorization);

    //添加订单
    @Headers("Content-type:application/json; charset=utf-8")
    @PUT("order/")
    Call<TokenBean> getadd(@Header("Authorization") String Authorization,@Body RequestBody requestBody);


    @Headers("Content-type:application/json; charset=utf-8")
    @PUT("user/me/")
    Call<TokenBean> gethuiyuan(@Header("Authorization") String Authorization);


    // 账号密码登录
    @Headers("Content-type:application/json; charset=utf-8")
    @POST("phone-user/login")
    Call<TokenBean> loginByPassword(@Body RequestBody requestBody);
}
