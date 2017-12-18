package com.muhaitain.commonlibraty.okhttp.base;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Muhaitian on 2017/12/18.
 */

public class OkHttpCallBack<T> implements Callback{
    private INetCallBack<T> iNetCallBack;

    public OkHttpCallBack(INetCallBack<T> iNetCallBack) {
        this.iNetCallBack = iNetCallBack;
    }

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if(response==null){
//            onNetFailure();
        }
    }

    protected void onNetFailure(final int code,final String tips){

    }
}
