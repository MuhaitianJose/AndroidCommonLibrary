package com.muhaitain.commonlibraty.okhttp.base;

import com.muhaitain.commonlibraty.okhttp.OkhttpUtils;
import com.muhaitain.commonlibraty.okhttp.RequestBuilder;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Request.Builder;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Muhaitian on 2017/12/18.
 */

public abstract class BaseRequest<T> implements IExcutor {

    private final int GET = 0;
    private final int POST = 1;
    private final int DELETE = 2;
    private final int UPDATE = 3;

    private RequestBuilder<T> mBuilder;
    private INetCallBack<T> iNetCallBack;
    private Call okHttpCall;

    public BaseRequest(RequestBuilder<T> mBuilder) {
        this.mBuilder = mBuilder;
        iNetCallBack = mBuilder.getINetCallBack();
    }

    public void request() {
        switch (mBuilder.getRequestType()) {
            case GET:
                get();
                break;
            case POST:
                post();
                break;
            case DELETE:
                delete();
                break;
            case UPDATE:
                update();
                break;
        }

    }

    protected abstract String buildGetUrl(String url,HashMap<String,Object> params);

    protected abstract String buildPostUrl(String url);

    protected abstract String buildDeleteUrl();

    protected abstract String buildUpdateUrl();

    private Builder getBuilder() {
        Builder builder = new Builder();
        HashMap<String, Object> header = mBuilder.getHeaders();
        if (header != null && header.size() > 0) {
            for (Map.Entry<String, Object> entry : header.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue().toString());
            }
        }
        return builder;
    }


    @Override
    public void post() {
        Request request;
        Builder builder = getBuilder();
        request = builder.url(buildPostUrl(mBuilder.getBaseUrl())).post(geterateBody(mBuilder.getParams())).build();
        execute(request);

    }

    @Override
    public void get() {
        Request request;
        Builder builder = getBuilder();
        request = builder.url(buildGetUrl(mBuilder.getBaseUrl(),mBuilder.getParams())).build();
        execute(request);
    }

    @Override
    public void delete() {

    }

    @Override
    public void update() {

    }

    protected void execute(final Request request) {


        okHttpCall = OkhttpUtils.getOkHttpClient().newCall(request);
        okHttpCall.enqueue(new OkHttpCallBack<>(iNetCallBack));
    }

    protected RequestBody geterateBody(HashMap<String, Object> params) {
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().toString();
            builder.add(key, value);

        }
        return builder.build();

    }
}
