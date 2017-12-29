package com.muhaitain.commonlibraty.glide.config;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.muhaitain.commonlibraty.glide.exception.LoadException;
import com.muhaitain.commonlibraty.glide.glideloader.GlideLoader;
import com.muhaitain.commonlibraty.glide.glideloader.ILoadCallback;

import java.io.File;


/**
 * Created by Muhaitian on 2017/12/22.
 */

public class RequestOptionConfig {
    private static final String TAG = RequestOptionConfig.class.getSimpleName();
    private RequestOptions mRequestOptions;
    private Context context;

    public RequestOptionConfig(Context context) {
        this.context = context;
        this.mRequestOptions = new RequestOptions();
    }

    /**
     * 裁剪图片尺寸，宽高相同
     *
     * @param size
     * @return
     */
    public RequestOptionConfig reSize(int size) {
        this.reSize(size, size);
        return this;
    }

    /**
     * 裁剪图片尺寸，自定义宽高
     *
     * @param width
     * @param height
     * @return
     */
    public RequestOptionConfig reSize(int width, int height) {
        Log.d(TAG, "reSize: "+width);
        mRequestOptions.override(width, height);
        return this;
    }

    /**
     * 修改显示图片的形状
     *
     * @param transformation
     * @return
     */
    public RequestOptionConfig setTransformation(Transformation<Bitmap> transformation) {
        mRequestOptions.transform(transformation);
        return this;
    }

    /**
     * 设置加载错误索要显示的图片
     *
     * @param errorHolder
     * @return
     */
    public RequestOptionConfig setErrorHolder(@DrawableRes int errorHolder) {
        mRequestOptions.error(errorHolder);
        return this;
    }

    /**
     * 设置加载中的图片
     *
     * @param placeHolder
     * @return
     */
    public RequestOptionConfig setPlaceHolder(@DrawableRes int placeHolder) {
        mRequestOptions.placeholder(placeHolder);
        return this;
    }

    /**
     * @param cacheStrategy DiskCacheStrategy ALL:默认缓存全部尺寸的图片
     *                      DiskCacheStrategy AUTOMATIC: 自动选择缓存策略
     *                      DiskCacheStrategy DATA: 在它被解码之前，将检索到的数据直接写到磁盘缓存中。
     *                      DiskCacheStrategy RESOURCE:本地缓存原始的全尺寸图片
     *                      DiskCacheStrategy NONE:本地什么都不缓存
     * @return
     */
    public RequestOptionConfig setCacheStrategy(DiskCacheStrategy cacheStrategy) {
        mRequestOptions.diskCacheStrategy(cacheStrategy);

        return this;
    }

    /**
     * 设置图片的格式
     *
     * @param decodeFormat 参数是枚举类型
     * @return
     */
    public RequestOptionConfig setIDecodeFormat(DecodeFormat decodeFormat) {
        mRequestOptions.format(decodeFormat);
        return this;
    }

    /**
     * 通过url显示图片
     *
     * @param url
     * @param imageView
     */
    public void showByUrl(String url, ImageView imageView) {
        if (!TextUtils.isEmpty(url)) {
            GlideLoader.getLoader(context).load(url).apply(mRequestOptions).into(imageView);
        } else {
            throw new NullPointerException("url is empty!");
        }
    }

    /**
     * 根据图片id显示
     *
     * @param imageId   图片id
     * @param imageView
     */
    public void showByResource(int imageId, ImageView imageView) {
        if (imageId <= 0) {
            throw new LoadException("imageId is error!");
        } else {

            GlideLoader.getLoader(context).load(imageId).apply(mRequestOptions).into(imageView);

        }
    }

    /**
     * 显示文件图片
     *
     * @param file
     * @param imageView
     */
    public void showByUrl(File file, ImageView imageView) {
        if (file.exists()) {
            GlideLoader.getLoader(context).load(file).apply(mRequestOptions).into(imageView);
        } else {
            throw new LoadException("file not exists!");
        }
    }

    public void asBitmap(String url, final ILoadCallback<Bitmap> loadCallback) {
        GlideLoader.getLoader(context).asBitmap()
                .load(url)
                .apply(mRequestOptions)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        if (loadCallback != null && resource != null) {
                            loadCallback.onLoadSuccessful(resource);
                        }
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        if (loadCallback != null) {
                            loadCallback.onLoadFailure();
                        }
                    }
                });

    }

    public void asDrawable(String url, final ILoadCallback<Drawable> loadCallback) {
        GlideLoader.getLoader(context).asDrawable()
                .load(url)
                .apply(mRequestOptions)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        if (loadCallback != null && resource != null) {
                            loadCallback.onLoadSuccessful(resource);
                        }
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        if (loadCallback != null) {
                            loadCallback.onLoadFailure();
                        }
                    }
                });
    }

    public void asGifDrawable(String url, final ILoadCallback<GifDrawable> loadCallback) {
        GlideLoader.getLoader(context).asGif()
                .load(url)
                .apply(mRequestOptions)
                .into(new SimpleTarget<GifDrawable>() {
                    @Override
                    public void onResourceReady(GifDrawable resource, Transition<? super GifDrawable> transition) {
                        if (loadCallback != null && resource != null) {
                            loadCallback.onLoadSuccessful(resource);
                        }
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        if (loadCallback != null) {
                            loadCallback.onLoadFailure();
                        }
                    }
                });
    }

    public RequestOptions getmRequestOptions() {
        return mRequestOptions;
    }
}
