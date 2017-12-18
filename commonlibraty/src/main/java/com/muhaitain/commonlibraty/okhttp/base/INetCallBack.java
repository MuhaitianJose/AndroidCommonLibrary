package com.muhaitain.commonlibraty.okhttp.base;

/**
 * Created by Muhaitian on 2017/12/18.
 */

public interface INetCallBack<T> {
    void onStart();
    T praseResponse(String content);
    void onSuccess(T t);
    void onFailure(String tip);
    void onNetNotConnected();
    void onComplete();

}
