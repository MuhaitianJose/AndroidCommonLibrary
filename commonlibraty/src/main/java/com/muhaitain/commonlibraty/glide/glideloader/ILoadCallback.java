package com.muhaitain.commonlibraty.glide.glideloader;

/**
 * Created by Muhaitian on 2017/12/22.
 */

public interface ILoadCallback<T> {
    void onLoadSuccessful(T data);
    void onLoadFailure();
}
