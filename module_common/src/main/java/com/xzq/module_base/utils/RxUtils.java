package com.xzq.module_base.utils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * RxUtils
 */
@SuppressWarnings("all")
public class RxUtils {

    public static void post(long delay, Runnable r) {
        Single.timer(delay, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread()).subscribe(aLong -> {
            if (r != null) {
                r.run();
            }
        });
    }

    public static void just(Runnable workerTask, Runnable uiTask) {
        Observable.just(1).map(new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer integer) throws Exception {
                if (workerTask != null) {
                    workerTask.run();
                }
                return 1;
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        if (uiTask != null) {
                            uiTask.run();
                        }
                    }
                });
    }
}
