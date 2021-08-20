package com.xzq.module_base.api;

import android.net.ParseException;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;

import com.google.gson.JsonParseException;
import com.xzq.module_base.User;
import com.xzq.module_base.mvp.IPostLoadingView;
import com.xzq.module_base.mvp.IStateView;
import com.xzq.module_base.utils.MyToast;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * 网络回调
 *
 * @author xzq
 */
@SuppressWarnings("all")
public abstract class NetCallback<T> implements Observer<NetBean<T>> {

    public static int FIRST_PAGE_INDEX = 1;
    //本地自定义错误码
    public static final int CODE_JSON = -125;
    public static final int CODE_TIMEOUT = -126;
    public static final int CODE_NET_BROKEN = -127;
    public static final int CODE_UNKNOWN = -128;
    private static final String DEF_LOADING_MSG = "加载中...";
    private String mLoadingMessage;
    private int mPage = FIRST_PAGE_INDEX;
    private boolean onComplete = false;
    private IPostLoadingView mPostLoading;
    private IStateView mStateLoading;

    public NetCallback() {
    }

    public NetCallback(IPostLoadingView mPostLoading) {
        this.mPostLoading = mPostLoading;
        this.mLoadingMessage = DEF_LOADING_MSG;
    }

    public NetCallback(IStateView mStateLoading, int page) {
        this.mStateLoading = mStateLoading;
        this.mPage = page;
        this.mLoadingMessage = DEF_LOADING_MSG;
    }

    public NetCallback(IPostLoadingView mPostLoading, int page) {
        this.mPostLoading = mPostLoading;
        this.mPage = page;
        this.mLoadingMessage = DEF_LOADING_MSG;
    }

    @Override
    @CallSuper
    public void onSubscribe(Disposable d) {
        callbackLoading();
    }

    @Override
    public void onComplete() {
        if (!onComplete) {
            callbackComplete();
            onComplete = true;
        }
    }

    @Override
    public void onNext(@NonNull NetBean<T> response) {
        final String msg = response.getMsg();
        final int code = response.getCode();
        //{"code":10104,"msg":"登录超时","data":null}
        if (code == 10104) {
            MyToast.show(msg);
            User.logout();
            return;
        }
        if (response.isOk()) {
            onComplete();
            T entity = response.getData();
            boolean isEmpty = response.isDataEmpty();
            if (isEmpty) {
                callbackEmpty();
            }
            onSuccess(response, entity, mPage);
        } else {
            onError(new ApiException(msg, code));
        }
    }

    @Override
    public void onError(Throwable e) {
        //返回错误信息
        String error;
        int code;
        if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            error = "数据解析异常";
            code = CODE_JSON;
        } else if (e instanceof SocketTimeoutException
                || e instanceof HttpException) {
            error = "请求超时";
            code = CODE_TIMEOUT;
        } else if (e instanceof UnknownHostException
                || e instanceof ConnectException) {
            error = "网络已断开";
            code = CODE_NET_BROKEN;
        } else if (e instanceof ApiException) {
            ApiException ae = (ApiException) e;
            error = ae.getMessage();
            code = ae.getCode();
        } else {
            error = e.getMessage();
            code = CODE_UNKNOWN;
        }

        onError(error, code);
        onComplete();

        callbackError(error);
    }

    /**
     * 回调加载中
     */
    private void callbackLoading() {
        if (mPostLoading != null) {
            mPostLoading.onShowPostLoading(mLoadingMessage);
        }
        if (mStateLoading != null) {
            mStateLoading.onShowLoading(mLoadingMessage);
            if (isFirstPage()) {
                mStateLoading.onStateLoading(mLoadingMessage);
            }
        }
    }

    /**
     * 回调加载完成
     */
    private void callbackComplete() {
        if (mPostLoading != null) {
            mPostLoading.onHidePostLoading();
        }
        if (mStateLoading != null) {
            mStateLoading.onHideLoading();
            if (isFirstPage()) {
                mStateLoading.onStateNormal();
            }
        }
    }

    /**
     * 回调加载空
     */
    private void callbackEmpty() {
        if (mStateLoading != null) {
            if (isFirstPage()) {
                mStateLoading.onStateEmpty();
            } else {
                mStateLoading.onStateLoadMoreEmpty();
            }
        }
    }

    /**
     * 回调加载错误
     *
     * @param error 错误信息
     */
    private void callbackError(String error) {
        if (mStateLoading != null) {
            if (isFirstPage()) {
                mStateLoading.onStateError(mPage, error);
            } else {
                mStateLoading.onStateLoadMoreError(mPage, error);
            }
        }
    }

    /**
     * 是否是第一页
     *
     * @return 是否是第一页
     */
    protected boolean isFirstPage() {
        return mPage == FIRST_PAGE_INDEX;
    }

    /**
     * 请求返回成功
     *
     * @param response 最外层数据实体
     * @param data     数据实体
     * @param page     page 当前请求页码
     */
    protected abstract void onSuccess(NetBean<T> response, T data, int page);

    /**
     * 显示默认错误信息，没有回调状态布局就toast一下
     *
     * @param error 错误信息
     * @param code  错误码
     */
    protected void onError(String error, int code) {
        if (mStateLoading == null) {
            MyToast.showFailed(error);
        }
    }
}
