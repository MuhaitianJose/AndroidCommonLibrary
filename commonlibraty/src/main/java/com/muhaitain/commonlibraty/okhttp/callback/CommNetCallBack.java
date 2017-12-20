package com.muhaitain.commonlibraty.okhttp.callback;

import android.text.TextUtils;

import com.muhaitain.commonlibraty.base.examples01.INetBaseView;
import com.muhaitain.commonlibraty.okhttp.bean.ServerTips;

import java.lang.ref.WeakReference;

/**
 * Created by Muhaitian on 2017/12/20.
 */

public class CommNetCallBack<T> extends NetPraseCallNack<T> {

    private WeakReference<INetBaseView> iNetBaseView;
    private String tips;

    public CommNetCallBack(WeakReference<INetBaseView> iNetBaseView, String tips) {
        this.iNetBaseView = iNetBaseView;
        this.tips = tips;
    }

    private boolean isViewAttached(){
        return (iNetBaseView!=null&&iNetBaseView.get()!=null);
    }

    @Override
    public void onStart() {
        if (isViewAttached()&& !TextUtils.isEmpty(tips)){
            iNetBaseView.get().showLoading(tips);
        }
    }

    @Override
    public void onSuccess(T t) {
        if (isViewAttached()){
            iNetBaseView.get().onRequestSuccess();
        }
        onComplete();
    }

    @Override
    public void onFailure(ServerTips tips) {
        if (isViewAttached()){
            iNetBaseView.get().onRequestError(tips.error_code,tips.error_msg);
        }
        onComplete();
    }

    @Override
    public void onNetNotConnected() {
        if (isViewAttached()){
            iNetBaseView.get().onNetNotConnected();
        }
        onComplete();
    }

    @Override
    public void onComplete() {
        if (isViewAttached()){
            iNetBaseView.get().dismissLoading();
        }
    }
}
