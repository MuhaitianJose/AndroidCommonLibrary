package com.muhaitain.commonlibraty.glide.config;

import android.content.Context;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.MemoryCategory;

/**
 * Created by Muhaitian on 2017/12/22.
 */

public class GlobalConfig {
    /**
     * 上下文
     */
    public static Context context;

    public static int cacheSize;
    public static int winHeight;
    public static int winWidth;

    public static void init(Context context, int cacheSize, MemoryCategory memoryCategory, boolean isInternalCd) {
        GlobalConfig.context = context;
        GlobalConfig.cacheSize = cacheSize;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        GlobalConfig.winHeight = windowManager.getDefaultDisplay().getHeight();
        GlobalConfig.winWidth = windowManager.getDefaultDisplay().getWidth();
    }


}
