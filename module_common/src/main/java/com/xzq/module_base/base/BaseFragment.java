package com.xzq.module_base.base;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xzq.module_base.R;
import com.xzq.module_base.config.Config;
import com.xzq.module_base.dialog.PostLoadingDialog;
import com.xzq.module_base.eventbus.EventUtil;
import com.xzq.module_base.eventbus.MessageEvent;
import com.xzq.module_base.mvp.MvpContract;
import com.xzq.module_base.utils.XZQLog;
import com.xzq.module_base.views.StateFrameLayout2;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Fragment基础类
 *
 * @author xzq
 */
public abstract class BaseFragment extends Fragment implements
        OnRefreshListener,
        StateFrameLayout2.OnStateClickListener,
        MvpContract.CommonView {

    private Unbinder unbinder;

    public enum FragmentState {
        CREATE, START, RESUME, PAUSE, STOP, DESTROY
    }

    private FragmentState mState = FragmentState.CREATE;
    protected Activity me;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mState = FragmentState.CREATE;
        me = getActivity();
        EventUtil.register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        mState = FragmentState.START;
        super.onStart();
    }

    @Override
    public void onResume() {
        mState = FragmentState.RESUME;
        super.onResume();
    }

    @Override
    public void onPause() {
        mState = FragmentState.PAUSE;
        super.onPause();
    }

    @Override
    public void onStop() {
        mState = FragmentState.STOP;
        super.onStop();
    }

    @Override
    public void onDestroy() {
        mState = FragmentState.DESTROY;
        super.onDestroy();
        EventUtil.unregister(this);
    }

    @Override
    public void onDestroyView() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        hidePostLoading();
        super.onDestroyView();
    }

    protected StateFrameLayout2 sfl;
    protected SmartRefreshLayout refreshLayout;
    protected StateConfig stateConfig = new StateConfig();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final int layoutId = getLayoutId(inflater, container, savedInstanceState);
        View rootView = LayoutInflater.from(me)
                .inflate(getBaseLayoutId(), container, false);
        sfl = rootView.findViewById(R.id.sfl);
        sfl.setOnStateClickListener(this);
        if (stateConfig.loadingLayoutId > 0) {
            sfl.setLoadingView(stateConfig.getViewById(sfl, stateConfig.loadingLayoutId));
        }
        if (stateConfig.emptyLayoutId > 0) {
            sfl.setEmptyView(stateConfig.getViewById(sfl, stateConfig.emptyLayoutId));
        }
        if (stateConfig.errorLayoutId > 0) {
            sfl.setErrorView(stateConfig.getViewById(sfl, stateConfig.errorLayoutId));
        }
        refreshLayout = rootView.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(this);
        setRefreshEnable(false);
        if (layoutId > 0) {
            View src = LayoutInflater.from(me)
                    .inflate(layoutId, sfl, false);
            sfl.addView(src, 0);
        }
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    protected void setRefreshEnable(boolean enabled) {
        refreshLayout.setEnabled(enabled);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews(savedInstanceState);
        onErrorClick(null);
    }

    /**
     * 设置内容Layout
     * 返回的必须是资源 layout ID
     *
     * @param inflater           布局容器
     * @param container          根View
     * @param savedInstanceState 保存的状态
     * @return 资源id
     */
    @LayoutRes
    protected abstract int getLayoutId(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    protected int getBaseLayoutId() {
        return R.layout.fragment_base;
    }

    /**
     * 初始化
     */
    protected abstract void initViews(Bundle savedInstanceState);

    /**
     * Look for a child view with the given id. If this view has the given id,
     * return this view.
     *
     * @param id The id to search for.
     * @return The view that has the given id in the hierarchy or null
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T findViewById(@IdRes int id) {
        if (getView() == null) {
            throw new IllegalStateException("Fragment " + this
                    + " not attached to Activity");
        }
        return (T) getView().findViewById(id);
    }

    /**
     * 获取是否为Resume状态
     *
     * @return 是否为Resume状态
     */
    public final boolean isResume() {
        return mState == FragmentState.RESUME;
    }

    /**
     * EventBus订阅方法
     *
     * @param event 消息实体
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(@NonNull MessageEvent event) {
    }

    /**
     * EventBus粘性订阅方法
     *
     * @param event 消息实体
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageStickyEvent(@NonNull MessageEvent event) {
    }

    @Override
    public void onStateLoading(String loadingMessage) {
        if (!isRefresh) {
            sfl.loading();
        }
        if (Config.DBG_STATE)
            XZQLog.debug("onStateLoading  loadingMessage = " + loadingMessage);
    }

    @Override
    public void onStateError(int page, String error) {
        this.<TextView>findViewById(R.id.tv_error_view)
                .setText(String.format(Locale.getDefault(), "%1$s\n点击重新加载", error));
        sfl.error();
        if (Config.DBG_STATE)
            XZQLog.debug("onStateLoading  error = " + error);
    }

    @Override
    public void onStateEmpty() {
        sfl.empty();
        if (Config.DBG_STATE)
            XZQLog.debug("onStateEmpty");
    }

    @Override
    public void onStateNormal() {
        refreshLayout.finishRefresh();
        sfl.normal();
        if (Config.DBG_STATE)
            XZQLog.debug("onStateNormal");
    }

    @Override
    public void onStateLoadMoreEmpty() {
        if (Config.DBG_STATE)
            XZQLog.debug("onStateLoadMoreEmpty");
    }

    @Override
    public void onStateLoadMoreError(int page, String error) {
        if (Config.DBG_STATE)
            XZQLog.debug("onStateLoadMoreError error = " + error + " page = " + page);
    }

    @Override
    public void onShowLoading(String loadingMessage) {
        if (Config.DBG_STATE)
            XZQLog.debug("onShowLoading loadingMessage = " + loadingMessage);
    }

    @Override
    public void onHideLoading() {
        if (Config.DBG_STATE)
            XZQLog.debug("onHideLoading");
    }

    @Override
    public void onShowPostLoading(String loadingMessage) {
        if (Config.DBG_STATE)
            XZQLog.debug("onShowPostLoading loadingMessage = " + loadingMessage);
        showPostLoading();
    }

    @Override
    public void onHidePostLoading() {
        if (Config.DBG_STATE)
            XZQLog.debug("onHidePostLoading");
        hidePostLoading();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        isRefresh = true;
        getFirstPageData();
    }

    @Override
    public void onErrorClick(StateFrameLayout2 layout) {
        isRefresh = false;
        getFirstPageData();
    }

    protected boolean isRefresh = false;

    protected void getFirstPageData() {
    }

    private PostLoadingDialog mLoadingDialog;

    public void showPostLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new PostLoadingDialog(me);
        }
        mLoadingDialog.show();
    }

    public void hidePostLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }

}
