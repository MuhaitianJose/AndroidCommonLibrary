package com.muhaitain.commonlibraty.base.examples01;

import java.lang.ref.WeakReference;

/**
 * Created by Muhaitian on 2017/12/20.
 */

public class BasePresenter<T> {
    private WeakReference<T> mViewReference;

    public void attcachView(T t) {
        mViewReference = new WeakReference<>(t);
    }

    public T getView() {
        if (mViewReference != null) {
            return mViewReference.get();
        }
        return null;
    }

    public boolean isAttachView() {
        return !(mViewReference == null || mViewReference.get() == null);
    }

    public void destoty() {
        if (mViewReference != null) {
            mViewReference.clear();
            mViewReference = null;
        }
    }
}
