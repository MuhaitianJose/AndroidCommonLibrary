package com.muhaitain.commonlibraty.okhttp.callback;

import android.text.TextUtils;

import com.muhaitain.commonlibraty.okhttp.Rxhandler;
import com.muhaitain.commonlibraty.okhttp.base.INetCallBack;
import com.muhaitain.commonlibraty.okhttp.bean.BaseResp;
import com.muhaitain.commonlibraty.okhttp.bean.ServerTips;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Muhaitian on 2017/12/19.
 */

public abstract class NetPraseCallNack<T> implements INetCallBack<T>{
    protected  Class<T> entityClass = (Class<T>) BaseResp.class;
    public NetPraseCallNack(){
        try {

        //Type是 Java 编程语言中所有类型的公共高级接口。它们包括原始类型、参数化类型、数组类型、类型变量和基本类型。
        //getClass().getGenericSuperclass()返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type
        Type type = getClass().getGenericSuperclass();
        //对于代码级别需要做个判断： 如果没有实现ParameterizedType接口，即不支持泛型，直接返回Object.class
        if(!(type instanceof ParameterizedType)){
            entityClass = (Class<T>) Object.class;
        }else {
            Type[] params =( (ParameterizedType) type).getActualTypeArguments();
            if(params.length<0){
                entityClass = (Class<T>) Object.class;
            }else if(!(params[0]instanceof Class)){
                entityClass = (Class<T>) Object.class;
            }else {
                entityClass = (Class<T>) params[0];
            }
        }
        }catch (Exception e){
            entityClass = (Class<T>) Object.class;
        }
    }

    @Override
    public T praseResponse(String content) {

        if(TextUtils.isEmpty(content)){
            return null;
        }

        return null;
    }

    private void onNetFailure(final int code,final String msg){
        Observable.just("")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Rxhandler<String>(){
                    @Override
                    public void onNext(String s) {
                        onFailure(new ServerTips(code,msg));
                    }
                });
    }
}
