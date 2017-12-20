package com.muhaitain.commonlibraty.okhttp.callback;

import android.text.TextUtils;

import com.muhaitain.commonlibraty.okhttp.Rxhandler;
import com.muhaitain.commonlibraty.okhttp.base.INetCallBack;
import com.muhaitain.commonlibraty.okhttp.bean.BaseResp;
import com.muhaitain.commonlibraty.okhttp.bean.ServerTips;
import com.muhaitain.commonlibraty.okhttp.constant.NetErrorCode;
import com.muhaitain.commonlibraty.utils.JsonUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Muhaitian on 2017/12/19.
 */

public abstract class NetPraseCallNack<T> implements INetCallBack<T> {
    private Class<T> entityClass = (Class<T>) BaseResp.class;

    public NetPraseCallNack() {
        try {

            //Type是 Java 编程语言中所有类型的公共高级接口。它们包括原始类型、参数化类型、数组类型、类型变量和基本类型。
            //getClass().getGenericSuperclass()返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type
            Type type = getClass().getGenericSuperclass();
            //对于代码级别需要做个判断： 如果没有实现ParameterizedType接口，即不支持泛型，直接返回Object.class
            if (!(type instanceof ParameterizedType)) {
                entityClass = (Class<T>) Object.class;
            } else {
                Type[] params = ((ParameterizedType) type).getActualTypeArguments();
                if (params.length < 0) {
                    entityClass = (Class<T>) Object.class;
                } else if (!(params[0] instanceof Class)) {
                    entityClass = (Class<T>) Object.class;
                } else {
                    entityClass = (Class<T>) params[0];
                }
            }
        } catch (Exception e) {
            entityClass = (Class<T>) Object.class;
        }
    }

    /**
     * 根据自己的需要重新编写这一块的代码
     * @param content
     * @return
     */

    @Override
    public T praseResponse(String content) {

        if (TextUtils.isEmpty(content)) {
            onNetFailure(NetErrorCode.NET_ERROR_EMPTY, "empty data");
            return null;
        }

        BaseResp baseResp = JsonUtils.parseObject(content, BaseResp.class);
        if (baseResp == null) {
            onNetFailure(NetErrorCode.NET_ERROR_EMPTY, "empty data");
            return null;
        }
        if (baseResp.error_code == NetErrorCode.NET_OK) {
            if (baseResp.data == null || TextUtils.isEmpty(baseResp.data)) {
                return JsonUtils.parseObject(content, entityClass);
            }

            String dataStr;

            if (baseResp.data.startsWith("[")) {
                dataStr = content;
            } else if (baseResp.data.startsWith("{")) {
                dataStr = baseResp.data;
            } else {
                dataStr = content;
            }
            T data = JsonUtils.parseObject(dataStr, entityClass);
            if (data != null) {
                return data;
            }
            onNetFailure(NetErrorCode.NET_ERROR_EMPTY, "empty data");
        } else {
            onNetFailure(baseResp.error_code, baseResp.error_msg);
        }

        return null;
    }

    private void onNetFailure(final int code, final String msg) {
        Observable.just("")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Rxhandler<String>() {
                    @Override
                    public void onNext(String s) {
                        onFailure(new ServerTips(code, msg));
                    }
                });
    }
}
