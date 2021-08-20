package com.xzq.module_base.act;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.bm.library.Info;
import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.xzq.module_base.R;
import com.xzq.module_base.dialog.photo.PhotosAdapter;
import com.xzq.module_base.eventbus.EventUtil;
import com.xzq.module_base.eventbus.MessageEvent;
import com.xzq.module_base.utils.AnimatorUtils;
import com.xzq.module_base.utils.ImageSaveUtils;
import com.xzq.module_base.utils.PermissionUtil;
import com.xzq.module_base.utils.RxUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 查看大图
 *
 * @author xzq
 */
public class ViewImageActivity extends Activity implements
        View.OnClickListener {

    private static final int DEF_DURATION = 200;
    private View mBg;
    private PhotoView photoView;
    private ViewPager vpPhotos;
    private Map<String, Info> map;
    private String currentPath;
    private final List<String> photos = new ArrayList<>();
    private final PhotosAdapter adapter = new PhotosAdapter();

    public static void multiple(Context context, String currentPath, Map<String, Info> map, boolean showSave) {
        if (map.size() == 1) {
            single(context, currentPath, map.get(currentPath), showSave);
        } else {
            start(context, currentPath, map, DEF_DURATION, R.layout.activity_view_image, showSave);
        }
    }

    public static void single(Context context, String currentPath, Info info) {
        single(context, currentPath, info, false);
    }

    public static void single(Context context, String currentPath, Info info, boolean showSave) {
        ViewSingleImageActivity.start(context, currentPath, info, showSave);
    }

    /**
     * 启动
     *
     * @param context     .
     * @param currentPath 当前路径
     * @param map         K：path V:Info
     * @param duration    动画持续时间
     * @param layoutId    布局id，在自己的布局中可以调整指示器，保存按钮位置
     * @param showSave    是否显示保存按钮
     */
    public static void start(Context context,
                             String currentPath,
                             Map<String, Info> map,
                             int duration,
                             int layoutId,
                             boolean showSave) {
        if (ObjectUtils.isEmpty(map)) {
            return;
        }
        EventUtil.postSticky("map", map);
        Intent starter = new Intent(context, ViewImageActivity.class);
        starter.putExtra("currentPath", currentPath);
        starter.putExtra("duration", duration);
        starter.putExtra("layoutId", layoutId);
        starter.putExtra("showSave", showSave);
        context.startActivity(starter);
    }

    @SuppressWarnings("unchecked")
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageStickyEvent(@NonNull MessageEvent event) {
        if (event.equals("map")) {
            map = (Map<String, Info>) event.getData();
            EventUtil.removeStickyEvent(event);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this)
                .fitsSystemWindows(false)
                .hideBar(BarHide.FLAG_HIDE_STATUS_BAR)
                .statusBarColor(R.color.transparent)
                .statusBarDarkFont(false).init();
        overridePendingTransition(0, 0);
        EventUtil.register(this);
        final int layoutId = getIntent().getIntExtra("layoutId", R.layout.activity_view_image);
        setContentView(layoutId);

        currentPath = getIntent().getStringExtra("currentPath");
        final int duration = getIntent().getIntExtra("duration", DEF_DURATION);

        photos.addAll(map.keySet());
        int currentIndex = photos.indexOf(currentPath);
        setBottom(duration, currentIndex);

        vpPhotos = findViewById(R.id.viewpager);
        mBg = findViewById(R.id.bg);
        photoView = findViewById(R.id.photoView);

        setCurrentPhotoView();

        photoView.setAnimaDuring(duration);
        photoView.animaFrom(map.get(currentPath));

        RxUtils.post(duration + 200, new Runnable() {
            @Override
            public void run() {
                vpPhotos.setVisibility(View.VISIBLE);
                vpPhotos.setPageMargin(AdaptScreenUtils.pt2Px(20));
                vpPhotos.addOnPageChangeListener(pageChangeListener);
                vpPhotos.setAdapter(adapter);
                adapter.setData(photos);
                adapter.setPhotoClickListener(ViewImageActivity.this);
                vpPhotos.setCurrentItem(currentIndex, false);
                photoView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        animToFinish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventUtil.unregister(this);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    private boolean finishing = false;

    private void animToFinish() {
        if (photoView.getVisibility() == View.VISIBLE || finishing) {
            return;
        }
        finishing = true;
        View vBottom = findViewById(R.id.img_rlyt_bottom);
        if (vBottom != null) {
            vBottom.setVisibility(View.GONE);
        }
        vpPhotos.setVisibility(View.GONE);
        photoView.setVisibility(View.VISIBLE);
        photoView.disenable();
        AnimatorUtils.alphaHide(mBg, photoView.getAnimaDuring());
        Info to = map.get(currentPath);
        if (to != null && photoView.getDrawable() != null) {
            photoView.animaTo(to, finishTask);
        } else {
            finishTask.run();
        }
    }

    @Override
    public void onClick(View view) {
        animToFinish();
    }

    private final Runnable finishTask = new Runnable() {
        @Override
        public void run() {
            photoView.setVisibility(View.GONE);
            finish();
        }
    };

    private final ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            currentPath = photos.get(position);
            setCount(position);
            setCurrentPhotoView();
            PagerAdapter photosAdapter = vpPhotos.getAdapter();
            if (photosAdapter instanceof PhotosAdapter) {
                ((PhotosAdapter) photosAdapter).notifyItemChanged(position);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };


    private final View.OnClickListener saveListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            PermissionUtil.requestStorage(() -> ImageSaveUtils.save(currentPath), "请先打开存储权限再保存");
        }
    };

    private void setCurrentPhotoView() {
        Object tag = photoView.getTag(photoView.getId());
        if (tag == null || !TextUtils.equals(tag.toString(), currentPath)) {
            photoView.setTag(photoView.getId(), currentPath);
            Glide.with(this).load(currentPath).into(photoView);
        }
    }

    private void setBottom(int duration, int currIndex) {
        View vBottom = findViewById(R.id.img_rlyt_bottom);
        AnimatorUtils.alphaShow(vBottom, duration);
        View btnSave = findViewById(R.id.img_btn_save);
        if (btnSave != null) {
            boolean showSave = getIntent().getBooleanExtra("showSave", false);
            btnSave.setVisibility(showSave ? View.VISIBLE : View.GONE);
            btnSave.setOnClickListener(saveListener);
        }
        setCount(currIndex);
    }

    private void setCount(int position) {
        TextView tvCount = findViewById(R.id.img_tv_count);
        if (tvCount != null && tvCount.getVisibility() == View.VISIBLE) {
            tvCount.setVisibility(photos.size() > 1 ? View.VISIBLE : View.GONE);
            tvCount.setText(String.format(Locale.getDefault(), "%1$d/%2$d", position + 1, photos.size()));
        }
    }

}
