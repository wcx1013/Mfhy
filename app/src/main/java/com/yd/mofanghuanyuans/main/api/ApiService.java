package com.yd.mofanghuanyuans.main.api;

import com.xzq.module_base.api.NetBean;
import com.xzq.module_base.bean.CodeBean;
import com.xzq.module_base.bean.DeviceDto;
import com.xzq.module_base.bean.DianjicountBean;
import com.xzq.module_base.bean.GuanggaoBean;
import com.xzq.module_base.bean.HuiyuanBean;
import com.xzq.module_base.bean.HuoappBean;
import com.xzq.module_base.bean.LoginDto;
import com.xzq.module_base.bean.LoginInfoBean;
import com.xzq.module_base.bean.MofangBean;
import com.xzq.module_base.bean.PayBean;
import com.xzq.module_base.bean.RakingDto;
import com.xzq.module_base.bean.SceneDto;
import com.xzq.module_base.bean.ShangplistBean;
import com.xzq.module_base.bean.TokenBean;
import com.xzq.module_base.bean.YoBean;


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

    // ????????????token
    @Headers("Content-type:application/json; charset=utf-8")
    @POST("user/login")
    Call<TokenBean> getlogin(@Body RequestBody requestBody);

    // ????????????
    @Headers("Content-type:application/json; charset=utf-8")
    @POST("feedback/")
    Call<TokenBean> getfeedBack(@Header("Authorization") String Authorization,@Body RequestBody requestBody);

    //??????
    @Headers("Content-type:application/json; charset=utf-8")
    @POST("app/v-check")
    Call<GuanggaoBean> getgg(@Header("Authorization") String Authorization, @Body RequestBody requestBody);

    //????????????
    @Headers("Content-type:application/json; charset=utf-8")
    @GET("res-ext/{id}")
    Call<DianjicountBean> getlookcount(@Header("Authorization") String Authorization,@Path("id") String id);

    //??????/??????
    @Headers("Content-type:application/json; charset=utf-8")
    @POST("user/register")
    Call<TokenBean> getzc(@Body RequestBody requestBody);

    //???????????????
    @Headers("Content-type:application/json; charset=utf-8")
    @POST("user/{phone}")
    Call<CodeBean> getcode(@Path("phone") String phone);

    //????????????
    @Headers("Content-type:application/json; charset=utf-8")
    @PUT("user/phone/pwd/")
    Call<TokenBean> getforgetpassword(@Header("Authorization") String Authorization,@Body RequestBody requestBody);

    //???????????????
    @Headers("Content-type:application/json; charset=utf-8")
    @PUT("user/phone/account")
    Call<TokenBean> getbind(@Header("Authorization") String Authorization,@Body RequestBody requestBody);

    //?????????????????????member-level/list
    @Headers("Content-type:application/json; charset=utf-8")
    @PUT("user/phone/logout/")
    Call<TokenBean> getclear(@Header("Authorization") String Authorization,@Body RequestBody requestBody);

    //??????????????????
    @Headers("Content-type:application/json; charset=utf-8")
    @GET("member-level/list")
    Call<ShangplistBean> getsplist(@Header("Authorization") String Authorization);

    //????????????
    @Headers("Content-type:application/json; charset=utf-8")
    @POST("order/")
    Call<PayBean> getadd(@Header("Authorization") String Authorization, @Body RequestBody requestBody);

    @Headers("Content-type:application/json; charset=utf-8")
    @GET("user/me/")
    Call<HuiyuanBean> gethuiyuan(@Header("Authorization") String Authorization);


    // ??????????????????
    @Headers("Content-type:application/json; charset=utf-8")
    @POST("phone-user/login")
    Call<TokenBean> loginByPassword(@Body RequestBody requestBody);

    // ??????????????????
    @Headers("Content-type:application/json; charset=utf-8")
    @GET("user/{id}")
    Call<YoBean> getpo(@Header("Authorization") String Authorization,@Path("id") String id);

    //??????APP??????
    @Headers("Content-type:application/json; charset=utf-8")
    @GET("app/last-v")
    Call<HuoappBean> gethq(@Header("Authorization") String Authorization);

}
