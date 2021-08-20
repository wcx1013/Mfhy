package com.yd.mofanghuanyuans.main;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.yd.mofanghuanyuans.R;
import com.yd.mofanghuanyuans.main.fragments.HomeFragment;
import com.yd.mofanghuanyuans.main.fragments.MeFragment;
import com.yd.mofanghuanyuans.main.fragments.Tab1Fragment;
import com.yd.mofanghuanyuans.main.fragments.Tab2Fragment;
import com.yd.mofanghuanyuans.main.fragments.Tab3Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * MainPagerAdapter
 * Created by xzq on 2020/5/20.
 */
public class MainPagerAdapter extends FragmentPagerAdapter {

    public static class PageConfig {
        boolean showHome = true;
        boolean showClassify = true;
       // boolean showDemand = true;
       // boolean showShoppingCart = true;
        boolean showMe = true;

        int rCheckShowCount() {
            int showCount = 0;
            if (showHome) showCount++;
            if (showClassify) showCount++;
//            if (showDemand) showCount++;
//            if (showShoppingCart) showCount++;
            if (showMe) showCount++;
            if (showCount == 0) {
                //主页标签数不可为0，为0时就全部显示
                showHome = true;
                showClassify = true;
//                showDemand = true;
//                showShoppingCart = true;
                showMe = true;
                return rCheckShowCount();
            }
            return showCount;
        }
    }

    public static final PageConfig PAGE_CONFIG = new PageConfig();
    private List<Fragment> list;
    private int[] titles;
    private int[] norIcons;
    private int[] seleIcons;

    public MainPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list = list;
    }

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
        final int totalCount = PAGE_CONFIG.rCheckShowCount();
        titles = new int[totalCount];
        norIcons = new int[totalCount];
        seleIcons = new int[totalCount];
        list = new ArrayList<>(totalCount);
        if (PAGE_CONFIG.showHome) {
            list.add(new HomeFragment());
            int lastIndex = list.size() - 1;
            titles[lastIndex] = R.string.main_tab_home;
            norIcons[lastIndex] = R.drawable.ic_home;
            seleIcons[lastIndex] = R.drawable.shouyetabm;
        }
        if (PAGE_CONFIG.showClassify) {
            list.add(new Tab1Fragment());
            int lastIndex = list.size() - 1;
            titles[lastIndex] = R.string.main_tab_classify;
            norIcons[lastIndex] = R.drawable.jiaochengtaba;
            seleIcons[lastIndex] = R.drawable.ic_classify;
        }
//        if (PAGE_CONFIG.showDemand) {
//            list.add(new Tab2Fragment());
//            int lastIndex = list.size() - 1;
//            titles[lastIndex] = R.string.main_tab_demand;
//            norIcons[lastIndex] = R.drawable.ic_demand_nor;
//            seleIcons[lastIndex] = R.drawable.ic_demand_sele;
//        }
//        if (PAGE_CONFIG.showShoppingCart) {
//            list.add(new Tab3Fragment());
//            int lastIndex = list.size() - 1;
//            titles[lastIndex] = R.string.main_tab_shopping_cart;
//            norIcons[lastIndex] = R.drawable.ic_shopping_cart_nor;
//            seleIcons[lastIndex] = R.drawable.ic_shopping_cart_sele;
//        }
        if (PAGE_CONFIG.showMe) {
            list.add(new MeFragment());
            int lastIndex = list.size() - 1;
            titles[lastIndex] = R.string.main_tab_me;
            norIcons[lastIndex] = R.drawable.metaba;
            seleIcons[lastIndex] = R.drawable.ic_me;
        }
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    private int getPagePos(int titleResId) {
        for (int i = 0; i < titles.length; i++) {
            int ids = titles[i];
            if (ids == titleResId) {
                return i;
            }
        }
        return -1;
    }

    public boolean isHomePos(int pos) {
        return getHomePos() == pos;
    }

    public boolean isClassifyPos(int pos) {
        return getClassifyPos() == pos;
    }

    public boolean isDemandPos(int pos) {
        return getDemandPos() == pos;
    }

    public boolean isShoppingCartPos(int pos) {
        return getShoppingCartPos() == pos;
    }

    public boolean isMePos(int pos) {
        return getMePos() == pos;
    }

    public int getHomePos() {
        return getPagePos(R.string.main_tab_home);
    }

    public int getClassifyPos() {
        return getPagePos(R.string.main_tab_classify);
    }

    public int getShoppingCartPos() {
        return getPagePos(R.string.main_tab_shopping_cart);
    }

    public int getDemandPos() {
        return getPagePos(R.string.main_tab_demand);
    }

    public int getMePos() {
        return getPagePos(R.string.main_tab_me);
    }

    public int[] getTitles() {
        return titles;
    }

    public int[] getNorIcons() {
        return norIcons;
    }

    public int[] getSeleIcons() {
        return seleIcons;
    }

}
