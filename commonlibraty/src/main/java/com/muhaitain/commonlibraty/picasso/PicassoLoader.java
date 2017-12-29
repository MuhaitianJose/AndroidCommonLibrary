package com.muhaitain.commonlibraty.picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.File;

/**
 * Created by Muhaitian on 2017/12/25.
 */

public class PicassoLoader {

    private static final String TAG = PicassoLoader.class.getSimpleName();

    public static void loadImageSimaple(Context context, String url, ImageView imageView) {
        Picasso.with(context)
                .load(url)
                .into(imageView);

    }

    public static void loadImageSimaple(Context context, int resourceId, ImageView imageView) {
        Picasso.with(context)
                .load(resourceId)
                .into(imageView);

    }

    public static void loadImageSimapleFile(Context context, String paths, ImageView imageView) {
        File file = null;
        try {
            file = new File(paths);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (file.exists()) {
            Picasso.with(context)
                    .load(file)
                    .into(imageView);
        }

    }

    public static void loadImageResize(Context context, String url, int width, int height, ImageView imageView) {
        Picasso.with(context)
                .load(url)
                .resize(width, height)
                .fit()
                .centerCrop()
                .into(imageView);

    }

    public static void loadImageResize(Context context, int url, int width, int height, ImageView imageView) {
        Picasso.with(context)
                .load(url)
                .resize(width, height)
                .centerCrop()
                .into(imageView);

    }


    public static void loadImageByTransform(Context context, String url, ImageView imageView, Transformation transformation) {

        Picasso.with(context)
                .load(url)
                .fit()
                .centerCrop()
                .transform(transformation)
                .into(imageView);

    }

    /**
     * 修改图片形状的操作没有生效，自定义类裁剪图片是成功。
     * @param context
     * @param url
     * @param imageView
     * @param transformation
     */
    public static void loadImageByTransform(Context context, int url, ImageView imageView, Transformation transformation) {

        Picasso.with(context)
                .load(url)
                .transform(transformation)
                .into(imageView);

    }

    public static class CircleTransformation implements Transformation {
        ImageView imageView;
        public CircleTransformation(ImageView imageView) {
            this.imageView  = imageView;
        }

        @Override
        public Bitmap transform(Bitmap source) {
            Log.d(TAG, "transform: ");
            int size = Math.min(source.getHeight(), source.getWidth());
            Log.d(TAG, "transform: "+source.getHeight()+source.getWidth());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            Bitmap circle = Bitmap.createBitmap(source, x, y, size, size);
            if (circle != source) {
                source.recycle();
            }

            Bitmap bm = Bitmap.createBitmap(size, size, source.getConfig());
            Canvas canvas = new Canvas(bm);
            Paint paint = new Paint();
            BitmapShader bitmapShader = new BitmapShader(circle, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
            paint.setAntiAlias(true);
            paint.setShader(bitmapShader);
            float radius = size / 2f;
            Log.d(TAG, "transform: size="+size);
            Log.d(TAG, "transform: radius="+radius);
            canvas.drawCircle(radius, radius, 50, paint);
            circle.recycle();
            imageView.setImageBitmap(bm);
            Log.d(TAG, "transform:  imageView.setImageBitmap(bm)");
            return bm;
        }

        @Override
        public String key() {
            return "circle";
        }
    }

//    public static void loadImageSimaple(Context context, String url, ImageView imageView) {
//        Picasso.with(context).load(url).into(imageView);
//
//    }
//
//    public static void loadImageSimaple(Context context, String url, ImageView imageView) {
//        Picasso.with(context).load(url).into(imageView);
//
//    }
}

