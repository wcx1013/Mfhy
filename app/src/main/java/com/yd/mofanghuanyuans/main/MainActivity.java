package com.yd.mofanghuanyuans.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.jpeng.jptabbar.JPTabBar;
import com.jpeng.jptabbar.OnTabSelectListener;
import com.xzq.module_base.base.BaseActivity;
import com.xzq.module_base.dialog.PrivateDialog;
import com.xzq.module_base.sp.ConfigSPManager;
import com.xzq.module_base.utils.MyToast;
import com.xzq.module_base.views.NoScrollViewPager;
import com.yd.mofanghuanyuans.R;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements OnTabSelectListener {

    @BindView(R.id.viewPager)
    NoScrollViewPager viewPager;
    @BindView(R.id.tabbar)
    JPTabBar tabbar;
    private MainPagerAdapter mPageAdapter;

    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        hideToolbar();
        mPageAdapter = new MainPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mPageAdapter);
        viewPager.setOffscreenPageLimit(mPageAdapter.getCount());
        tabbar.setTabListener(this);
        tabbar.setTitles(mPageAdapter.getTitles());
        tabbar.setNormalIcons(mPageAdapter.getNorIcons());
        tabbar.setSelectedIcons(mPageAdapter.getSeleIcons());
        tabbar.generate();
        tabbar.setContainer(viewPager);
//        if (!ConfigSPManager.isAgreePrivate()) {
//            viewPager.postDelayed(privateShowTask, 1000);
//        }
    }

    private boolean canFinish = false;
    private final Runnable mCancelFinishTask = () -> canFinish = false;

    @Override
    public void onBackPressed() {
        if (canFinish) {
            ToastUtils.cancel();
            super.onBackPressed();
        } else {
            canFinish = true;
            MyToast.show("再按一次退出程序~");
            viewPager.postDelayed(mCancelFinishTask, 2000);
        }
    }

    @Override
    public void onTabSelect(int index) {
//        if (mPageAdapter.isMePos(index)
//               /* || mPageAdapter.isHomePos(index)
//                || mPageAdapter.isClassifyPos(index)
//                || mPageAdapter.isShoppingCartPos(index)*/) {
//            ImmersionBar.with(this).fitsSystemWindows(false)
//                    .statusBarColor(R.color.transparent)
//                    .statusBarDarkFont(false).init();
//        } else {
//            ImmersionBar.with(this)
//                    .fitsSystemWindows(false)
//                    .statusBarColor(R.color.transparent)
//                    .statusBarDarkFont(true).init();
//        }
        ImmersionBar.with(this).fitsSystemWindows(false)
                .statusBarColor(R.color.transparent)
                .statusBarDarkFont(false).init();
    }

    @Override
    public boolean onInterruptSelect(int index) {
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getResources();
    }

    @Override
    protected void onDestroy() {
        viewPager.removeCallbacks(privateShowTask);
        super.onDestroy();
    }

    private final Runnable privateShowTask = new Runnable() {
        @Override
        public void run() {
            PrivateDialog.show(me, new PrivateDialog.OnOkClickListener() {
                @Override
                public void onDialogOkClicked() {
                    ConfigSPManager.putAgreePrivate(true);
                }
            }, new PrivateDialog.OnCancelClickListener() {
                @Override
                public void onDialogCancelClicked() {
                    AppUtils.exitApp();
                }
            });
        }
    };





}
