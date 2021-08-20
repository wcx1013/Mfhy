package com.xzq.module_base.mvp;

import androidx.annotation.NonNull;

import com.xzq.module_base.api.ApiCallback;
import com.xzq.module_base.api.BaseListBean;
import com.xzq.module_base.api.RequestUtils;
import com.xzq.module_base.api.NetBean;
import com.xzq.module_base.api.NetCallback;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * 基类Presenter
 *
 * @author xzq
 */
@SuppressWarnings("all")
public abstract class AbsPresenter<V> implements BasePresenter {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    protected V mView;
    protected int mPage = NetCallback.FIRST_PAGE_INDEX;
    public static final int LIMIT = 20;

    public void attachView(@NonNull V view) {
        this.mView = view;
    }

    public void reset() {
        this.mPage = NetCallback.FIRST_PAGE_INDEX;
    }

    public void pendingNextPage() {
        this.mPage++;
    }

    public void setPage(int mPage) {
        this.mPage = mPage;
    }

    public int getPage() {
        return mPage;
    }

    public boolean isLoadMore() {
        return mPage > 1;
    }

    /**
     * POST请求网络回调
     *
     * @param <E>
     */
    public abstract class PostLoadingCallback<E> extends NetCallback<E> {

        /**
         * 创建一个显示loading的POST请求网络回调
         */
        public PostLoadingCallback() {
            this(true);
        }

        /**
         * 创建一个POST请求网络回调
         *
         * @param showLoading 是否显示loading
         */
        public PostLoadingCallback(boolean showLoading) {
            super(showLoading ? (mView instanceof IPostLoadingView ? (IPostLoadingView) mView : null) : null);
        }

        /**
         * 创建一个POST请求的网络回调
         *
         * @param page 页码
         */
        public PostLoadingCallback(int page) {
            super(mView instanceof IPostLoadingView ? (IPostLoadingView) mView : null, page);
        }

        @Override
        public void onSubscribe(Disposable d) {
            compositeDisposable.add(d);
            super.onSubscribe(d);
        }
    }

    /**
     * 带状态加载的网络回调
     */
    public abstract class StateCallback<E> extends NetCallback<E> {

        /**
         * 创建一个带状态的网络回调
         */
        public StateCallback() {
            this(mPage, false);
        }

        /**
         * 创建一个带状态的网络回调
         *
         * @param page 页码
         */
        public StateCallback(int page, boolean noLoading) {
            super(noLoading ? (IStateView) null : (mView instanceof IStateView ? (IStateView) mView : null), page);
        }

        @Override
        public void onSubscribe(Disposable d) {
            compositeDisposable.add(d);
            super.onSubscribe(d);
        }

        @Override
        protected void onSuccess(NetBean<E> response, E data, int page) {
            if (mView != null) {
                if (response.dataIsList()) {
                    response.checkHasNextPage(page);
                    onList(response, data, page);
                } else {
                    onSuccess(response, data);
                }
            }
        }

        protected abstract void onSuccess(NetBean<E> response, E data);

        protected void onList(NetBean<E> response, E data, int page) {
        }
    }

    /**
     * 发起网络请求，响应体{@link NetBean}中data字段为任意对象
     *
     * @param apiCallback Api回调
     * @param <E>         实体类型
     * @param cls         Class
     * @return Observable
     */


    protected <E> Observable<NetBean<E>> doAnyRequest(ApiCallback<E> apiCallback, Class<E> cls) {
        return RequestUtils.doAny(cls, apiCallback);
    }

    protected Observable<NetBean<Object>> doObjectRequest(ApiCallback<Object> apiCallback) {
        return doAnyRequest(apiCallback, Object.class);
    }

    /**
     * 发起列表请求（NetBean/data为{@link java.util.List}）
     *
     * @param apiCallback Api回调
     * @param <E>         实体类型
     * @return Observable
     */
    protected <E> Observable<NetBean<E>> doListRequest(ApiCallback<E> apiCallback) {
        return RequestUtils.doList(apiCallback);
    }

    /**
     * 发起分页列表请求，状态回调
     *
     * @param apiCallback Api回调
     * @param <E>
     */
    @SuppressWarnings("all")
    protected <E> void doPagingListRequest(ApiCallback<E> apiCallback) {
        RequestUtils.doList(apiCallback).subscribe(new StateCallback<E>() {

            @Override
            protected void onSuccess(NetBean<E> response, E data) {
                //do nothing
            }

            @Override
            protected void onList(NetBean<E> response, E data, int page) {
                if (mView instanceof IListView && data instanceof List) {
                    if (isFirstPage()) {
                        ((IListView) mView).setData((List) data, page, response.hasNextPage(), response.getTotalCount());
                    } else {
                        ((IListView) mView).addData((List) data, page, response.hasNextPage(), response.getTotalCount());
                    }
                }
            }
        });
    }

    @SuppressWarnings("all")
    protected <E> void doPagingListRequestNoLoading(ApiCallback<E> callback) {
        RequestUtils.doList(callback).subscribe(new StateCallback<E>(mPage, true) {

            @Override
            protected void onSuccess(NetBean<E> response, E data) {
                //do nothing
            }

            @Override
            protected void onList(NetBean<E> response, E data, int page) {
                if (mView instanceof IListView && data instanceof List) {
                    if (isFirstPage()) {
                        ((IListView) mView).setData((List) data, page, response.hasNextPage(), response.getTotalCount());
                    } else {
                        ((IListView) mView).addData((List) data, page, response.hasNextPage(), response.getTotalCount());
                    }
                }
            }
        });
    }

    @SuppressWarnings("all")
    protected <E> void doPagingListRequestPostLoading(ApiCallback<E> callback) {
        RequestUtils.doList(callback).subscribe(new PostLoadingCallback<E>(mPage) {
            @Override
            protected void onSuccess(NetBean<E> response, E data, int page) {
                if (mView instanceof IListView && data instanceof List) {
                    if (isFirstPage()) {
                        ((IListView) mView).setData((List) data, page, response.hasNextPage(), response.getTotalCount());
                    } else {
                        ((IListView) mView).addData((List) data, page, response.hasNextPage(), response.getTotalCount());
                    }
                }
            }
        });
    }

    @SuppressWarnings("all")
    protected <E> void doAnyRequestRespWithBaseListBean(ApiCallback<E> callback) {
        RequestUtils.doAnyRequestRespWithBaseListBean((E) new BaseListBean<>(), callback)
                .subscribe(new StateCallback<E>() {
                    @Override
                    protected void onSuccess(NetBean<E> response, E data) {
                        //do nothing
                    }

                    @Override
                    protected void onList(NetBean<E> response, E data, int page) {
                        if (mView instanceof IBaseListView && isFirstPage()) {
                            ((IBaseListView) mView).onNetBean(response);
                        }
                        if (mView instanceof IListView && data instanceof BaseListBean) {
                            if (isFirstPage()) {
                                ((IListView) mView).setData((List) ((BaseListBean) data).getList(), page,
                                        response.hasNextPage(), ((BaseListBean) data).getTotalCount());
                            } else {
                                ((IListView) mView).addData((List) ((BaseListBean) data).getList(), page,
                                        response.hasNextPage(), ((BaseListBean) data).getTotalCount());
                            }
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        mView = null;
        compositeDisposable.clear();
    }

    public void cancelRequest() {
        compositeDisposable.clear();
    }
}
