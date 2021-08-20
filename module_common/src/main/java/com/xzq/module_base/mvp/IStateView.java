package com.xzq.module_base.mvp;

/**
 * 状态接口，适用于页面首次加载时显示对应的状态
 * 比如 loading、empty、error、normal
 * <p>
 * 如果你的页面需要这种状态，网络回调应该使用
 * {@link com.xzq.module_base.mvp.AbsPresenter.StateCallback}
 * 作为观察者
 *
 * @author xzq
 */

public interface IStateView extends ILoadingView {

    /**
     * 加载中
     */
    void onStateLoading(String loadingMessage);

    /**
     * 加载出错
     */
    void onStateError(int page, String error);

    /**
     * 加载为空
     */
    void onStateEmpty();

    /**
     * 加载完成
     */
    void onStateNormal();

    /**
     * 加载更多数据为空回调
     */
    void onStateLoadMoreEmpty();

    /**
     * 加载更多错误回调
     */
    void onStateLoadMoreError(int page, String error);

}
