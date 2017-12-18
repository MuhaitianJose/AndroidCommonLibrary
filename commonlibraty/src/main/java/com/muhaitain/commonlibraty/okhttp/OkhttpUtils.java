package com.muhaitain.commonlibraty.okhttp;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Muhaitian on 2017/12/18.
 */

public class OkhttpUtils {

    private static OkHttpClient okHttpClient;

    public static void initClient() {
        if (okHttpClient == null) {
            synchronized (OkhttpUtils.class) {
                if (okHttpClient == null) {
                    okHttpClient = new OkHttpClient.Builder()
                            .connectTimeout(15, TimeUnit.SECONDS)
                            .readTimeout(15, TimeUnit.SECONDS)
                            .writeTimeout(20, TimeUnit.SECONDS)
                            .build();
                }
            }
        }
    }

    public static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            throw new NullPointerException("http client is null, please init first");
        }
        return okHttpClient;
    }
}
