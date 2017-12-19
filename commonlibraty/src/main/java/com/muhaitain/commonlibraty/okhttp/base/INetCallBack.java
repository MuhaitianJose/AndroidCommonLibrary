package com.muhaitain.commonlibraty.okhttp.base;

import com.muhaitain.commonlibraty.okhttp.bean.ServerTips;

/**
 * Created by Muhaitian on 2017/12/18.
 */

public interface INetCallBack<T> {
    void onStart();
    T praseResponse(String content);
    void onSuccess(T t);
    void onFailure(ServerTips tips);
    void onNetNotConnected();
    void onComplete();

}
