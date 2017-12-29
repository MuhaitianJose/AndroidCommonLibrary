package com.muhaitain.commonlibraty.recycleview.hodler;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by Muhaitian on 2017/12/27.
 */

public class ViewHolder {

    private Context mContext;
    private SparseArray<View> sparseViews;
    private View mConvertView;

    public ViewHolder(Context context, View itemView) {
        this.mContext = context;
        mConvertView = itemView;
        sparseViews = new SparseArray<>();
        mConvertView.setTag(this);
    }

    public static ViewHolder createViewHolder(Context context, View mConvertView) {
        if (mConvertView == null) {
            throw new NullPointerException("mConvertView is null");
        }

        ViewHolder viewHolder;
        if (mConvertView.getTag() == null) {
            viewHolder = new ViewHolder(context, mConvertView);
        } else {
            viewHolder = (ViewHolder) mConvertView.getTag();
        }

        return viewHolder;
    }

    public static ViewHolder createViewHolder(Context context, ViewGroup parents, int layoutId) {
        View view = LayoutInflater.from(context).inflate(layoutId, parents, false);
        return new ViewHolder(context, view);
    }

    public <T extends View> T getView(int viewId) {
        View view = sparseViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            sparseViews.put(viewId, view);
        }
        return (T) view;
    }

    public ViewHolder setText(int viewId, String text) {
        TextView textView = getView(viewId);
        textView.setText(text);
        return this;
    }

    public ViewHolder setText(int viewId, int textid) {
        TextView textView = getView(viewId);
        textView.setText(textid);
        return this;
    }

    public ViewHolder setImageResource(int viewId, int resId) {
        ImageView imageView = getView(viewId);
        imageView.setImageResource(resId);
        return this;
    }

    public ViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView imageView = getView(viewId);
        imageView.setImageBitmap(bitmap);
        return this;
    }

    public ViewHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView imageView = getView(viewId);
        imageView.setImageDrawable(drawable);
        return this;
    }

    public ViewHolder setBackgroundColor(int viewId, int color) {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public ViewHolder setBackgroundRes(int viewId, int background) {
        View view = getView(viewId);
        view.setBackgroundResource(background);
        return this;
    }

    public ViewHolder setTextColor(int viewId, int textColor) {
        TextView textView = getView(viewId);
        textView.setTextColor(textColor);
        return this;
    }

    public ViewHolder setTextColorRes(int viewId, int textColorRes) {
        TextView textView = getView(viewId);
        textView.setTextColor(mContext.getResources().getColor(textColorRes));
        return this;
    }

    public ViewHolder setAlpha(int viewId, float value) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getView(viewId).setAlpha(value);
        } else {
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            getView(viewId).startAnimation(alpha);
        }

        return this;
    }

    public ViewHolder setVisible(int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    public ViewHolder setTypeface(Typeface typeface, int... viewIds) {
        for (int viewId : viewIds) {
            TextView textView = getView(viewId);
            textView.setTypeface(typeface);
            textView.setPaintFlags(textView.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
        return this;
    }

    public ViewHolder setProgress(int viewId, int progress) {
        ProgressBar progressBar = getView(viewId);
        progressBar.setProgress(progress);
        return this;
    }

    public ViewHolder setProgress(int viewId, int progress, int max) {
        ProgressBar progressBar = getView(viewId);
        progressBar.setMax(max);
        progressBar.setProgress(progress);
        return this;
    }

    public ViewHolder setMax(int viewId, int max) {
        ProgressBar progressBar = getView(viewId);
        progressBar.setMax(max);
        return this;
    }

    public ViewHolder setRating(int viewId, float rating) {
        RatingBar ratingBar = getView(viewId);
        ratingBar.setRating(rating);
        return this;
    }

    public ViewHolder setRating(int viewId, float rating, int max) {
        RatingBar ratingBar = getView(viewId);
        ratingBar.setMax(max);
        ratingBar.setRating(rating);
        return this;
    }

    public ViewHolder setTag(int viewId, Object tag) {
        View view = getView(viewId);
        view.setTag(tag);
        return this;
    }

    public ViewHolder setTag(int viewId, int key, Object tag) {
        View view = getView(viewId);
        view.setTag(key, tag);
        return this;
    }

    public ViewHolder setCheckable(int viewId, boolean checked) {
        Checkable checkable = getView(viewId);
        checkable.setChecked(checked);
        return this;
    }

    public ViewHolder setOnClickListener(int viewId, View.OnClickListener onClickListener) {
        View view = getView(viewId);
        view.setOnClickListener(onClickListener);
        return this;
    }

    public ViewHolder setOnTouchListener(int viewId, View.OnTouchListener onTouchListener) {
        View view = getView(viewId);
        view.setOnTouchListener(onTouchListener);
        return this;
    }

    public ViewHolder setOnLongClickListener(int viewId, View.OnLongClickListener onLongClickListener) {
        View view = getView(viewId);
        view.setOnLongClickListener(onLongClickListener);
        return this;
    }
}
