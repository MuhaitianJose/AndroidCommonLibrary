package com.muhaitain.commonlibraty.glide.glideloader;

import android.content.Context;

import com.muhaitain.commonlibraty.glide.config.RequestOptionConfig;

/**
 * Created by Muhaitian on 2017/12/22.
 */

public class ImageLoader {

    public static RequestOptionConfig with(Context context){
        return new RequestOptionConfig(context);
    }
}
