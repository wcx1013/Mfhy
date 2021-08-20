package com.xzq.module_base.api;


import java.util.Collections;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * RequestUtils
 *
 * @author xzq
 */
@SuppressWarnings("all")
public class RequestUtils {

    private static <T> ObservableTransformer<T, T> schedulersTransformer() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 发起网络请求，响应体{@link NetBean}中data字段为任意对象
     *
     * @param cls         任意对象的class
     * @param apiCallback Api回调
     * @param <Any>       任意对象类型
     * @return Observable
     */
    public static <Any> Observable<NetBean<Any>> doAny(Class<Any> cls, ApiCallback<Any> apiCallback) {
        ApiService api = NetManager.defApi();
        return apiCallback.getApi(api)
                .compose(schedulersTransformer())
                .map(netBean -> {
                    Any data = netBean.getData();
                    if (data == null) {
                        //data为空处理 {"code":0,"msg":"成功","data":null}
                        NetBean<Any> bean = new NetBean<>();
                        bean.copyFrom(netBean);
                        bean.setData(cls.newInstance());
                        return bean;
                    }
                    return netBean;
                });
    }

    /**
     * 发起网络请求，响应体{@link NetBean}中data字段为{@link java.util.List}
     *
     * @param apiCallback Api回调
     * @param <List>      列表
     * @return Observable
     */
    public static <List> Observable<NetBean<List>> doList(ApiCallback<List> apiCallback) {
        ApiService api = NetManager.defApi();
        return apiCallback.getApi(api)
                .compose(schedulersTransformer())
                .map(netBean -> {
                    List data = netBean.getData();
                    if (data == null) {
                        //data为空处理 {"code":0,"msg":"成功","data":null}
                        //注意：此处不检查数组元素类型，只做空列表处理
                        NetBean bean = new NetBean<>();
                        bean.copyFrom(netBean);
                        bean.setData(Collections.EMPTY_LIST);
                        return bean;
                    }
                    return netBean;
                });
    }

    /**
     * 发起网络请求，响应体{@link NetBean}中data字段为{@link BaseListBean<Any>}
     *
     * @param baseListBean    {@link BaseListBean<Any>}
     * @param apiCallback Api回调
     * @param <T>         .
     * @return Observable
     */
    public static <T> Observable<NetBean<T>> doAnyRequestRespWithBaseListBean(T baseListBean, ApiCallback<T> apiCallback) {
        ApiService api = NetManager.defApi();
        return apiCallback.getApi(api)
                .compose(schedulersTransformer())
                .map(netBean -> {
                    T data = netBean.getData();
                    if (data == null) {
                        //data为空处理 {"code":0,"msg":"成功","data":null}
                        NetBean<T> bean = new NetBean<>();
                        bean.copyFrom(netBean);
                        bean.setData(baseListBean);
                        return bean;
                    }
                    return netBean;
                });
    }
}
