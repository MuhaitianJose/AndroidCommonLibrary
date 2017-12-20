package com.muhaitain.commonlibraty.okhttp.base;

import android.support.annotation.NonNull;

import com.muhaitain.commonlibraty.okhttp.Rxhandler;
import com.muhaitain.commonlibraty.okhttp.bean.ServerTips;
import com.muhaitain.commonlibraty.okhttp.constant.NetErrorCode;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Muhaitian on 2017/12/18.
 */

public class OkHttpCallBack<T> implements Callback {
    private INetCallBack<T> iNetCallBack;

    public OkHttpCallBack(INetCallBack<T> iNetCallBack) {
        this.iNetCallBack = iNetCallBack;
    }

    @Override
    public void onFailure(@NonNull Call call, @SuppressWarnings("NullableProblems") IOException e) {
        onNetFailure(NetErrorCode.NET_ERROR_EXCEPTION, "请求异常");
    }

    @Override
    public void onResponse(@SuppressWarnings("NullableProblems") Call call, @SuppressWarnings("NullableProblems") Response response) throws IOException {
        if (response == null) {
            onNetFailure(NetErrorCode.NET_ERROR_NULL_RESPONE, "数据返回为空");
        }
        if (iNetCallBack == null) {
            throw new NullPointerException("iNetCallBack is null");
        }
        if (response.isSuccessful()) {
            try {
                T data = iNetCallBack.praseResponse(response.body().toString());
                if (data != null) {
                    iNetCallBack.onSuccess(data);
                }
            } catch (Exception e) {
                onNetFailure(NetErrorCode.NET_ERROR_EXCEPTION, "请求内容出现异常");
            }
        }
    }

    protected void onNetFailure(final int code, final String tips) {
        Observable.just("")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Rxhandler<String>() {
                    @Override
                    public void onNext(String s) {
                        iNetCallBack.onFailure(new ServerTips(code, tips));
                    }
                });

    }

    protected void onNetSuccessful(final T data) {
        Observable.just("")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Rxhandler<String>() {
                    @Override
                    public void onNext(String s) {
                        iNetCallBack.onSuccess(data);
                    }
                });
    }
}
