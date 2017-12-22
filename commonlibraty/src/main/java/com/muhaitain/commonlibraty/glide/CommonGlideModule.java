package com.muhaitain.commonlibraty.glide;



import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.module.GlideModule;

import java.lang.annotation.Annotation;

/**
 * Created by Muhaitian on 2017/12/22.
 */

public class CommonGlideModule implements GlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context,"glide_cache",250*1024*1024));
    }

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {

    }
}
