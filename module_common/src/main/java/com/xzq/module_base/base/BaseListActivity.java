package com.xzq.module_base.base;

import android.os.Bundle;
import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xzq.module_base.R;
import com.xzq.module_base.adapter.BaseRecyclerFooterAdapter;
import com.xzq.module_base.adapter.IAdapter;
import com.xzq.module_base.api.NetBean;
import com.xzq.module_base.mvp.IBaseListView;
import com.xzq.module_base.mvp.IListView;
import com.xzq.module_base.mvp.MvpContract;
import com.xzq.module_base.utils.DividerFactory;

import java.util.List;

import am.widget.stateframelayout.StateFrameLayout;

/**
 * Activity列表基类
 *
 * @author xzq
 */

public abstract class BaseListActivity<P extends MvpContract.CommonPresenter, T>
        extends BasePresenterActivity<P>
        implements IListView<T>, IBaseListView,
        BaseRecyclerFooterAdapter.OnLoadMoreCallback {

    protected RecyclerView recyclerView;
    protected IAdapter<T> mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_list;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        setRefreshEnable(true);
        setToolbar(getPageTitle());
        BaseRecyclerFooterAdapter<T> wrapAdapter = new BaseRecyclerFooterAdapter<>(getPageAdapter());
        wrapAdapter.setAlwaysShowFooter(alwaysShowFooter());
        wrapAdapter.setLoadMoreCallback(this);
        mAdapter = wrapAdapter;
        recyclerView = findViewById(R.id.recyclerView);
        initRecyclerView(recyclerView);
        recyclerView.setAdapter(wrapAdapter);
    }

    @Override
    @CallSuper
    protected void getFirstPageData() {
        refresh();
    }

    @Override
    public void onStateEmpty() {
        super.onStateEmpty();
        if (mAdapter != null) {
            mAdapter.clear();
        }
    }

    @Override
    public void onStateError(int page, String error) {
        super.onStateError(page, error);
        if (mAdapter != null) {
            mAdapter.clear();
        }
    }

    @Override
    public void onAutoLoadMore(StateFrameLayout loadMore) {
        onPageLoad();
    }

    @Override
    public void onReloadMore(StateFrameLayout loadMore) {
        onPageLoad();
    }

    @Override
    public void onStateLoadMoreError(int page, String error) {
        super.onStateLoadMoreError(page, error);
        if (mAdapter != null) {
            mAdapter.onError();
        }
    }

    @Override
    public void onStateLoadMoreEmpty() {
        super.onStateLoadMoreEmpty();
        if (mAdapter != null) {
            mAdapter.onEmpty();
        }
    }

    @Override
    public void setData(List<T> list, int page, boolean hasNextPage, int totalCount) {
        if (hasNextPage && presenter != null) {
            presenter.pendingNextPage();
        }
        if (mAdapter != null) {
            mAdapter.setData(list, hasNextPage);
        }
    }

    @Override
    public void addData(List<T> list, int page, boolean hasNextPage, int totalCount) {
        if (hasNextPage && presenter != null) {
            presenter.pendingNextPage();
        }
        if (mAdapter != null) {
            mAdapter.addData(list, hasNextPage);
        }
    }

    @Override
    public void onNetBean(NetBean netBean) {
    }

    public void onPageLoad() {
        if (presenter != null) {
            getList();
        }
    }

    public void refresh() {
        if (presenter != null) {
            presenter.reset();
        }
        onPageLoad();
    }

    /**
     * 初始化RecyclerView
     *
     * @param recyclerView RecyclerView
     */
    protected void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.addItemDecoration(DividerFactory.VERTICAL_NO_LAST);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * 获取页面标题
     *
     * @return 标题
     */
    protected abstract String getPageTitle();

    /**
     * 获取页面适配器
     *
     * @return 适配器
     */
    protected abstract RecyclerView.Adapter getPageAdapter();

    protected void getList() {
    }

    protected boolean alwaysShowFooter() {
        return true;
    }
}
