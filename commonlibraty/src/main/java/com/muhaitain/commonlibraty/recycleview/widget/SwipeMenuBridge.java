package com.muhaitain.commonlibraty.recycleview.widget;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Muhaitian on 2018/1/4.
 */

public class SwipeMenuBridge {
    @SwipeMenuRecyclerView.DirectionMode
    private final int mDirection;
    private final int mPosition;
    private final SwipeSwitch mSwipeSwitch;
    private final View mViewRoot;

    int mAdapterPosition;
    TextView mTextView;
    ImageView mImageView;

    public SwipeMenuBridge(int mDirection, int mPosition, SwipeSwitch mSwipeSwitch, View mViewRoot) {
        this.mDirection = mDirection;
        this.mPosition = mPosition;
        this.mSwipeSwitch = mSwipeSwitch;
        this.mViewRoot = mViewRoot;
    }

    public SwipeMenuBridge setBackgroundDrawable(@DrawableRes int resId) {
        return setBackgroundDrawable(ContextCompat.getDrawable(mViewRoot.getContext(), resId));
    }

    public SwipeMenuBridge setBackgroundDrawable(Drawable background) {
        ViewCompat.setBackground(mViewRoot, background);
        return this;
    }

    public SwipeMenuBridge setBackgroundColorResource(@ColorRes int color) {
        return setBackgroundColor(ContextCompat.getColor(mViewRoot.getContext(), color));
    }

    public SwipeMenuBridge setBackgroundColor(@ColorInt int color) {
        mViewRoot.setBackgroundColor(color);
        return this;
    }

    public SwipeMenuBridge setImage(int resId) {
        return setImage(ContextCompat.getDrawable(mViewRoot.getContext(), resId));
    }

    public SwipeMenuBridge setImage(Drawable icon) {
        if (mImageView != null) {
            mImageView.setImageDrawable(icon);
        }
        return this;
    }

    public SwipeMenuBridge setText(int resId) {
        return setText(mViewRoot.getContext().getString(resId));
    }

    public SwipeMenuBridge setText(String title) {
        if (mTextView != null) {
            mTextView.setText(title);
        }
        return this;
    }

    @SwipeMenuRecyclerView.DirectionMode
    public int getDirection() {
        return mDirection;
    }

    public int getPosition() {
        return mPosition;
    }

    public int getAdapterPosition() {
        return mAdapterPosition;
    }

    public void closeMenu() {
        mSwipeSwitch.smoothCloseMenu();
    }
}
