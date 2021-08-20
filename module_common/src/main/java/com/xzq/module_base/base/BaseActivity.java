package com.xzq.module_base.base;

import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.RomUtils;
import com.gyf.immersionbar.ImmersionBar;
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
 * Activity基础类
 *
 * @author xzq
 */

public abstract class BaseActivity extends AppCompatActivity implements
        OnRefreshListener,
        StateFrameLayout2.OnStateClickListener,
        MvpContract.CommonView {

    //Activity生命周期
    private ActivityState mState = ActivityState.CREATE;
    protected final BaseActivity me = this;
    private Unbinder unbinder;

    /**
     * Activity生命周期
     */
    private enum ActivityState {
        CREATE, START, RESTART, RESUME, PAUSE, STOP, DESTROY
    }

    protected StateFrameLayout2 sfl;
    protected SmartRefreshLayout refreshLayout;
    protected StateConfig stateConfig = new StateConfig();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mState = ActivityState.CREATE;
        XZQLog.debug("BaseActivity", getClass().getSimpleName());
        boolean isMain = "com.yd.dengteng.main.MainActivity".equals(getClass().getName());
        if (isMain) {
            overridePendingTransition(R.anim.activity_open_enter_main, R.anim.activity_open_exit);
        } else {
            overridePendingTransition(R.anim.activity_open_enter, R.anim.activity_open_exit);
        }
        EventUtil.register(this);
        final int layoutId = getLayoutId();
        ViewGroup contentParent = findViewById(android.R.id.content);
        View contentView = LayoutInflater.from(this)
                .inflate(getBaseLayoutId(), contentParent, false);
        sfl = contentView.findViewById(R.id.sfl);
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
        refreshLayout = contentView.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(this);
        setRefreshEnable(false);
        if (layoutId > 0) {
            View src = LayoutInflater.from(this)
                    .inflate(layoutId, sfl, false);
            sfl.addView(src, 0);
        }
        unbinder = ButterKnife.bind(this, contentView);

        setContentView(contentView);
        initViews(savedInstanceState);
        int statusBarColorInt = ContextCompat.getColor(me, R.color.transparent);
        ImmersionBar.with(this)
                .fitsSystemWindows(!isMain)
                .statusBarColorInt(statusBarColorInt)
                .statusBarDarkFont(false).init();
        onErrorClick(null);
    }

    protected void hideToolbar() {
        if (findViewById(R.id.toolbar) != null) {
            findViewById(R.id.toolbar).setVisibility(View.GONE);
        }
    }

    protected void showTopLine() {
        findViewById(R.id.v_top_line).setVisibility(View.VISIBLE);
    }

    protected void setRefreshEnable(boolean enabled) {
        refreshLayout.setEnabled(enabled);
    }

    /**
     * 获取布局资源ID
     *
     * @return 布局资源ID
     */
    @LayoutRes
    protected abstract int getLayoutId();

    protected int getBaseLayoutId() {
        return R.layout.activity_base;
    }

    /**
     * 初始化资源
     *
     * @param savedInstanceState savedInstanceState
     */
    protected abstract void initViews(@Nullable Bundle savedInstanceState);

    @Override
    protected void onStart() {
        super.onStart();
        mState = ActivityState.START;
    }

    @Override
    protected void onRestart() {
        mState = ActivityState.RESTART;
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mState = ActivityState.RESUME;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mState = ActivityState.PAUSE;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mState = ActivityState.STOP;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventUtil.unregister(this);
        mState = ActivityState.DESTROY;
        if (unbinder != null) {
            unbinder.unbind();
        }
        hidePostLoading();
    }

    protected boolean isActivityResume() {
        return mState == ActivityState.RESUME;
    }

    @SuppressWarnings("all")
    public void setToolbar(String title, String rightTxt, int rightIconResId) {
        View toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            //标题
            TextView tvTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
            tvTitle.setText(title);
            //右边文字按钮
            final boolean hasRightTxt = !TextUtils.isEmpty(rightTxt);
            TextView tvRight = (TextView) toolbar.findViewById(R.id.toolbar_btn_right_txt);
            tvRight.setVisibility(hasRightTxt ? View.VISIBLE : View.GONE);
            if (hasRightTxt) {
                tvRight.setText(rightTxt);
                tvRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onRightClick(v);
                    }
                });
            }
            //右边图标按钮
            final boolean hasRightIcon = rightIconResId > 0;
            ImageView ivRight = (ImageView) toolbar.findViewById(R.id.toolbar_btn_right_icon);
            ivRight.setVisibility(hasRightIcon ? View.VISIBLE : View.GONE);
            if (hasRightIcon) {
                ivRight.setImageResource(rightIconResId);
                ivRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onRightClick(v);
                    }
                });
            }
            //返回按钮
            View btnBack = toolbar.findViewById(R.id.toolbar_btn_back);
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackClick();
                }
            });

        }
    }

    public void setToolbar(@StringRes int resId) {
        setToolbar(getString(resId));
    }

    public void setToolbar(String title) {
        setToolbar(title, null, 0);
    }

    public void setToolbar(String title, String rightTxt) {
        setToolbar(title, rightTxt, 0);
    }

    public void setToolbar(String title, int rightIconResId) {
        setToolbar(title, null, rightIconResId);
    }

    /**
     * Toolbar 返回按钮点击,若要做返回逻辑，子Activity需重写onBackPressed()
     */
    protected void onBackClick() {
        onBackPressed();
    }

    protected void onRightClick(View v) {

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

    private boolean isRefresh = false;

    protected void getFirstPageData() {
    }

    private PostLoadingDialog mLoadingDialog;

    public void showPostLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new PostLoadingDialog(this);
        }
        mLoadingDialog.show();
    }

    public void hidePostLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    public void finish() {
        super.finish();
        activityExit();
    }

    @Override
    public Resources getResources() {
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            return AdaptScreenUtils.adaptHeight(super.getResources(), 375);
        } else {
            return AdaptScreenUtils.adaptWidth(super.getResources(), 375);
        }
    }

    protected void activityExit() {
        int actCount = ActivityUtils.getActivityList().size();
        if (actCount > 1) {
            overridePendingTransition(RomUtils.isXiaomi() ? 0 : R.anim.activity_close_enter, R.anim.activity_close_exit);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (isShouldClearFocus() && ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                KeyboardUtils.hideSoftInput(v);
                v.clearFocus();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */
    protected boolean isShouldHideInput(View v, MotionEvent event) {
        if (v instanceof EditText) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    protected boolean isShouldClearFocus() {
        return true;
    }

    protected void setDarkFont() {
        ImmersionBar.with(this).statusBarDarkFont(true).navigationBarColor(R.color.color_0F0C3A).init();

    }

}
