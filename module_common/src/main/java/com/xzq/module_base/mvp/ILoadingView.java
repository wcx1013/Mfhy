package com.xzq.module_base.mvp;

/**
 * 状态接口，每次请求都会回调{@link #onShowLoading(java.lang.String loadingMessage)}
 * 和{@link #onHideLoading()}
 *
 * @author xzq
 */
public interface ILoadingView {
    /**
     * 显示Loading回调
     *
     * @param loadingMessage 加载提示
     */
    void onShowLoading(String loadingMessage);

    /**
     * 隐藏Loading回调
     */
    void onHideLoading();
}
