package com.muhaitain.commonlibraty.okhttp;

import com.muhaitain.commonlibraty.okhttp.base.INetCallBack;

import java.util.HashMap;

/**
 * Created by Muhaitian on 2017/12/18.
 */

public class RequestBuilder<T> {
    private int type;
    private String baseUrl;
    private HashMap<String, Object> headers;
    private HashMap<String, Object> params;
    private INetCallBack<T> iNetCallBack;

    public RequestBuilder<T> setRequestType(int type) {
        this.type = type;
        return this;
    }

    public int getRequestType() {
        return type;
    }

    public RequestBuilder<T> setRequestUrl(String url) {
        this.baseUrl = url;
        return this;
    }

    public String getBaseUrl(){
        return this.baseUrl;
    }

    public RequestBuilder<T> addHeader(String key, Object params) {
        if (headers == null) {
            headers = new HashMap<>();
        }
        headers.put(key, params);
        return this;
    }

    public RequestBuilder<T> setHeader(HashMap<String, Object> headers) {
        this.headers = headers;
        return this;
    }

    public HashMap<String, Object> getHeaders() {
        return headers;
    }

    public RequestBuilder<T> setParams(HashMap<String, Object> params) {
        this.params = params;
        return this;
    }

    public RequestBuilder<T> addParams(String key, Object param) {
        if (params == null) {
            params = new HashMap<>();
        }
        params.put(key, param);
        return this;
    }

    public HashMap<String,Object> getParams(){
        return  params;
    }

    public RequestBuilder<T> setINetCallback(INetCallBack<T> callback) {
        this.iNetCallBack = callback;
        return this;
    }

    public INetCallBack<T> getINetCallBack(){
        return iNetCallBack;
    }

    public CustomRequest<T> build(){
        return new CustomRequest<T>(this);
    }


}
