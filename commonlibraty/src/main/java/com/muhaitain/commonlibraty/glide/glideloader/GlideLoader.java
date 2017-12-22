package com.muhaitain.commonlibraty.glide.glideloader;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.MemoryCategory;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;

/**
 * Created by Muhaitian on 2017/12/22.
 */

public class GlideLoader  {
//    /**
//     * @param context        上下文
//     * @param cacheSizeInM   设置glidde缓存大小
//     * @param memoryCategory 调整内存缓存的大小 LOW(0.5f) ／ NORMAL(1f) ／ HIGH(1.5f);
//     * @param isInternalCD   true 缓存放在应用内/false 放在应用外
//     */
//    @Override
//    public void init(Context context, int cacheSizeInM, MemoryCategory memoryCategory, boolean isInternalCD) {
//        Glide.get(context).setMemoryCategory(memoryCategory);
//        GlideBuilder glideBuilder = new GlideBuilder();
//        if (isInternalCD) {
//            glideBuilder.setDiskCache(new InternalCacheDiskCacheFactory(context, cacheSizeInM * 1024 * 1024));
//        } else {
//            glideBuilder.setDiskCache(new ExternalCacheDiskCacheFactory(context, cacheSizeInM * 1024 * 1024));
//        }
//    }

    public static RequestManager getLoader(Context context) {
        return Glide.with(context);
    }

    public static RequestManager getLoader(Activity mActivity) {
        return Glide.with(mActivity);
    }

    public static RequestManager getLoader(Fragment fragment) {
        return Glide.with(fragment);
    }

    public static RequestManager getLoader(android.app.Fragment fragment) {
        return Glide.with(fragment);
    }

}
