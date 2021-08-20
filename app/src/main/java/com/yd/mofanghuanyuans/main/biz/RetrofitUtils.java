package com.yd.mofanghuanyuans.main.biz;

import com.yd.mofanghuanyuans.main.api.ApiService;
import com.xzq.module_base.config.Config;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtils {
    public ApiService retrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService service = retrofit.create(ApiService.class);
        return service;
    }
}
