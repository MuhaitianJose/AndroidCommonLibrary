package com.muhaitain.commonlibraty.okhttp;

import com.hwangjr.rxbus.annotation.Subscribe;

import rx.Subscriber;

/**
 * Created by Muhaitian on 2017/12/19.
 */

public class Rxhandler<T> extends Subscriber<T> {
    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(T t) {

    }
}
