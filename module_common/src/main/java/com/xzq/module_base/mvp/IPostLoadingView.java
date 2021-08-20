package com.xzq.module_base.mvp;

/**
 * 加载框接口，适用于发送类似Post请求时显示加载框
 * <p>
 * 网络回调使用{@link io.reactivex.Observer}的子类
 * {@link com.xzq.module_base.mvp.AbsPresenter.PostLoadingCallback}
 * 作为观察者时会触发{@link #onShowPostLoading(java.lang.String loadingMessage)}
 * 和{@link #onHidePostLoading()}回调
 *
 * @author xzq
 */
public interface IPostLoadingView {
    /**
     * 显示PostLoading回调
     *
     * @param loadingMessage 加载提示
     */
    void onShowPostLoading(String loadingMessage);

    /**
     * 隐藏PostLoading回调
     */
    void onHidePostLoading();
}
