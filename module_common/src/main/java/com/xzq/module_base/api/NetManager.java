package com.xzq.module_base.api;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import com.blankj.utilcode.util.Utils;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.xzq.module_base.BuildConfig;
import com.xzq.module_base.config.Config;
import com.xzq.module_base.utils.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.platform.Platform;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
 *  创建者:   ddz
 *  创建时间:  2017/12/23 15:16
 *  描述：    TODO
 */
public class NetManager {

    private static Retrofit retrofit;

    static {

        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();

        //Debug模式下打印请求log日志
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    message = StringUtils.toEmptyIfNull(message);
                    if (message.startsWith("{") && message.endsWith("}")) {
                        //此处debug时可以打断点，copy json
                        final String json = StringUtils.unicode2Chinese(message);
                        message = !TextUtils.isEmpty(json) ? json : message;
                    } else if (message.contains("=")) {
                        try {
                            message = URLDecoder.decode(message, "UTF-8");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    Platform.get().log(Platform.INFO, message, null);
                }
            });
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpBuilder.addInterceptor(loggingInterceptor);
        }

        //cache url
        File httpCacheDirectory = new File(Utils.getApp().getCacheDir(), "responses");
        // 50 MiB
        int cacheSize = 50 * 1024 * 1024;
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        OkHttpClient client = httpBuilder
                .readTimeout(15L, TimeUnit.SECONDS)
                .writeTimeout(15L, TimeUnit.SECONDS)
                .connectTimeout(15L, TimeUnit.SECONDS)
//                .addInterceptor(new AddCacheInterceptor())
//                .cache(cache)
                .build();
        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(Config.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }

    private static class AddCacheInterceptor implements Interceptor {
        private Context context;

        AddCacheInterceptor() {
            super();
            this.context = Utils.getApp();
        }

        @Override
        public Response intercept(Chain chain) throws IOException {

            CacheControl.Builder cacheBuilder = new CacheControl.Builder();
            cacheBuilder.maxAge(0, TimeUnit.SECONDS);
            cacheBuilder.maxStale(365, TimeUnit.DAYS);
            CacheControl cacheControl = cacheBuilder.build();
            Request request = chain.request();
            if (!isNetworkConnected(context)) {
                request = request.newBuilder()
                        .cacheControl(cacheControl)
                        .build();
            }
            Response originalResponse = chain.proceed(request);
            if (isNetworkConnected(context)) {
                // read from cache
                int maxAge = 0;
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public ,max-age=" + maxAge)
                        .build();
            } else {
                // tolerate 4-weeks stale
                int maxStale = 60 * 60 * 24 * 28;
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    }

    public static Retrofit retrofit() {
        return retrofit;
    }

    private static ApiService apiService;

    public static ApiService defApi() {
        if (apiService == null) {
            apiService = NetManager.retrofit().create(ApiService.class);
        }
        return apiService;
    }

    /**
     * 判断网络是否连通
     */
    public static boolean isNetworkConnected(Context context) {
        try {
            if (context != null) {
                @SuppressWarnings("static-access")
                ConnectivityManager cm = (ConnectivityManager) context
                        .getSystemService(context.CONNECTIVITY_SERVICE);
                NetworkInfo info = cm.getActiveNetworkInfo();
                return info != null && info.isConnected();
            } else {
                /**如果context为空，就返回false，表示网络未连接*/
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }

}
