package com.xzq.module_base.dialog.photo;


import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.xzq.module_base.R;
import com.xzq.module_base.utils.AnimatorUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * 图片显示对话框
 * Created by xzq on 2018/10/9.
 */

public class PhotoDialog extends Dialog implements ViewPager.OnPageChangeListener {

    private final PhotosAdapter adapter = new PhotosAdapter();
    private ViewPager vpPhotos;
    private TextView tvCount;
    private int totalCount;
    private View vBottom;

    public PhotoDialog(@NonNull Context context) {
        super(context, Build.VERSION.SDK_INT >= 21 ?
                R.style.BottomTransparentFullscreenDialogStyle
                : R.style.BottomTransparentDialogStyle);
        setContentView(R.layout.dialog_photo);
        getWindow().setGravity(Gravity.CENTER);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        if (Build.VERSION.SDK_INT >= 28) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            getWindow().setAttributes(lp);
        }
        vpPhotos = findViewById(R.id.dp_viewpager);
        vpPhotos.setPageMargin(AdaptScreenUtils.pt2Px(20));
        vpPhotos.addOnPageChangeListener(this);
        vpPhotos.setAdapter(adapter);
        tvCount = findViewById(R.id.dt_tv_count);
        vBottom = findViewById(R.id.dt_rlyt_bottom);
        adapter.setPhotoClickListener(v -> dismiss());
    }

    public void show(List<String> photos, String curr) {
        if (photos == null || photos.isEmpty()) {
            return;
        }
        int index = photos.indexOf(curr);
        totalCount = photos.size();
        adapter.setData(photos);
        vpPhotos.setCurrentItem(index);
        tvCount.setText(String.format(Locale.getDefault(), "%1$d/%2$d", index + 1, totalCount));
        AnimatorUtils.alphaShow(vBottom, 1000);
        super.show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mOnPageChangeListeners != null) {
            for (ViewPager.OnPageChangeListener listener :
                    mOnPageChangeListeners) {
                listener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }
    }

    @Override
    public void onPageSelected(int position) {
        tvCount.setText(String.format(Locale.getDefault(), "%1$d/%2$d", position + 1, totalCount));
        if (mOnPageChangeListeners != null) {
            for (ViewPager.OnPageChangeListener listener :
                    mOnPageChangeListeners) {
                listener.onPageSelected(position);
            }
        }
        PagerAdapter photosAdapter = vpPhotos.getAdapter();
        if (photosAdapter instanceof PhotosAdapter) {
            ((PhotosAdapter) photosAdapter).notifyItemChanged(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (mOnPageChangeListeners != null) {
            for (ViewPager.OnPageChangeListener listener :
                    mOnPageChangeListeners) {
                listener.onPageScrollStateChanged(state);
            }
        }
    }

    private List<ViewPager.OnPageChangeListener> mOnPageChangeListeners;

    public void addOnPageChangeListener(@NonNull ViewPager.OnPageChangeListener listener) {
        if (mOnPageChangeListeners == null) {
            mOnPageChangeListeners = new ArrayList<>();
        }
        mOnPageChangeListeners.add(listener);
    }

    public void removeOnPageChangeListener(@NonNull ViewPager.OnPageChangeListener listener) {
        if (mOnPageChangeListeners != null) {
            mOnPageChangeListeners.remove(listener);
        }
    }
}
