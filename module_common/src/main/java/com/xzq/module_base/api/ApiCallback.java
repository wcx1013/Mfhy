package com.xzq.module_base.api;

import io.reactivex.Observable;

/**
 * Api回调
 *
 * @author xzq
 */
public interface ApiCallback<T> {
    /**
     * 获取调用方法
     *
     * @param api Api服务
     * @return Observable
     */
    Observable<NetBean<T>> getApi(ApiService api);
}
