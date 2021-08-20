package com.xzq.module_base.utils;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;

/**
 * AnimatorUtils
 * Created by xzq on 2018/8/3.
 */

public class AnimatorUtils {

    public static void alphaShow(View target, long duration) {
        alpha(true, target, duration, null);
    }

    public static void alphaShow(View target, long duration, Animator.AnimatorListener listener) {
        alpha(true, target, duration, listener);
    }

    public static void alphaHide(View target, long duration, Animator.AnimatorListener listener) {
        alpha(false, target, duration, listener);
    }

    public static void alphaHide(View target, long duration) {
        alpha(false, target, duration, null);
    }

    private static void alpha(boolean isShow, View target, long duration, Animator.AnimatorListener listener) {
        if (target == null) {
            return;
        }
        float from = isShow ? 0 : 1;
        float to = isShow ? 1 : 0;
        ObjectAnimator alpha = ObjectAnimator.ofFloat(target, View.ALPHA, from, to);
        alpha.setDuration(duration);
        if (listener != null) {
            alpha.addListener(listener);
        }
        alpha.start();
    }

    public static void translationY(View target) {
        if (target == null) {
            return;
        }
        ObjectAnimator alpha = ObjectAnimator.ofFloat(target, View.TRANSLATION_Y, 0, -100, 0);
        alpha.setDuration(1000);
        alpha.start();
    }

    public static void translationY(View target, int duration, int from, int to) {
        if (target == null) {
            return;
        }
        ObjectAnimator alpha = ObjectAnimator.ofFloat(target, View.TRANSLATION_Y, from, to);
        alpha.setDuration(duration);
        alpha.start();
    }

    public static void scaleXY(View target) {
        if (target == null) {
            return;
        }
        PropertyValuesHolder s1 = PropertyValuesHolder.ofFloat("scaleX", 1, 1.1f, 1);
        PropertyValuesHolder s2 = PropertyValuesHolder.ofFloat("scaleY", 1, 1.1f, 1);
        ObjectAnimator.ofPropertyValuesHolder(target, s1, s2).setDuration(800).start();
    }
}
