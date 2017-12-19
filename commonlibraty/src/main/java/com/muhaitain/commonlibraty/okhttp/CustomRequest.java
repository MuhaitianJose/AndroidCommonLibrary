package com.muhaitain.commonlibraty.okhttp;

import com.muhaitain.commonlibraty.okhttp.base.BaseRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Muhaitian on 2017/12/19.
 */

public class CustomRequest<T> extends BaseRequest<T> {
    public CustomRequest(RequestBuilder<T> mBuilder) {
        super(mBuilder);
    }

    @Override
    protected String buildGetUrl(String url, HashMap<String, Object> params) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            stringBuilder
                    .append(entry.getKey())
                    .append("=")
                    .append(entry.getValue()).append("&");
        }
        String str = stringBuilder.toString();
        url = url + "?" + str.substring(0, str.length() - 1);
        return url;
    }

    @Override
    protected String buildPostUrl(String url) {
        return url;
    }


    @Override
    protected String buildDeleteUrl() {
        return null;
    }

    @Override
    protected String buildUpdateUrl() {
        return null;
    }
}
