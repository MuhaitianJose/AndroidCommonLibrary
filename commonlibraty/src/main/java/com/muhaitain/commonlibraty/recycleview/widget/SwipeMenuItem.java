package com.muhaitain.commonlibraty.recycleview.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StyleRes;
import android.support.v4.content.ContextCompat;

/**
 * Created by Muhaitian on 2018/1/3.
 */

public class SwipeMenuItem {
    private Context mContext;
    private Drawable background;
    private int icon;
    private String title;
    private ColorStateList titleColor;
    private int titleSize;
    private Typeface textTypeface;
    private int textAppearance;
    private int width = -2;
    private int height = -2;
    private int weight = 0;

    private String MenuDescribe;

    public SwipeMenuItem(Context context) {
        mContext = context;
    }

    public SwipeMenuItem setBackground(@DrawableRes int resid) {
        return setBackground(ContextCompat.getDrawable(mContext, resid));
    }

    public SwipeMenuItem setBackground(Drawable background) {
        this.background = background;
        return this;
    }

    public Drawable getBackground() {
        return background;
    }

    public Typeface getTextTypeface() {
        return textTypeface;
    }

    public int getIcon() {
        return icon;
    }

    public SwipeMenuItem setImage(int resId) {
        this.icon = resId;
        return this;
    }

//    public SwipeMenuItem setImage(Drawable icon) {
//        this.icon = icon;
//        return this;
//    }


    public SwipeMenuItem setText(int resId) {
        return setText(mContext.getResources().getString(resId));
    }

    public SwipeMenuItem setText(String text) {
        this.title = text;
        return this;
    }

    public String getTitle() {
        return title;
    }

    @SuppressLint("ResourceType")
    public SwipeMenuItem setTextColorResource(@ColorRes int titleColor) {
        return setTextColorResource(ContextCompat.getColor(mContext, titleColor));
    }

    public SwipeMenuItem setTextColor(@ColorInt int titleColor) {
        this.titleColor = ColorStateList.valueOf(titleColor);
        return this;
    }

    public ColorStateList getTitleColor() {
        return this.titleColor;
    }

    public SwipeMenuItem setTextAppearance(@StyleRes int textAppearance) {
        this.textAppearance = textAppearance;
        return this;
    }

    public int getTextAppearance() {
        return this.textAppearance;
    }

    public SwipeMenuItem setTextTypeface(Typeface textTypeface) {
        this.textTypeface = textTypeface;
        return this;
    }

    public SwipeMenuItem setWidth(int width) {
        this.width = width;
        return this;
    }

    public int getWidth() {
        return this.width;
    }

    public SwipeMenuItem setHeight(int height) {
        this.height = height;
        return this;
    }

    public int getHeight(){
        return this.height;
    }

    public SwipeMenuItem setWeight(int weight) {
        this.weight = weight;
        return this;
    }

    public int getWeight() {
        return this.weight;
    }

    public int getTitleSize() {
        return titleSize;
    }

    public void setTitleSize(int titleSize) {
        this.titleSize = titleSize;
    }

    public String getMenuDescribe() {
        return MenuDescribe;
    }

    public SwipeMenuItem setMenuDescribe(String menuDescribe) {
        MenuDescribe = menuDescribe;
        return this;
    }
}

