package com.muhaitain.commonlibraty.recycleview.widget;

import android.content.Context;
import android.support.annotation.IntDef;
import android.widget.LinearLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muhaitian on 2018/1/3.
 */

public class SwipeMenu {

    @IntDef({HORIZONTAL, VERTICAL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface OrientationMode {
    }

    public static final int HORIZONTAL = LinearLayout.HORIZONTAL;
    public static final int VERTICAL = LinearLayout.VERTICAL;

    private SwipeMenuLayout mSwipeMenuLayout;

    private int mViewType;

    private int orientation = HORIZONTAL;

    private List<SwipeMenuItem> mSwipeMenuItems = new ArrayList<>(2);

    public SwipeMenu(SwipeMenuLayout mSwipeMenuLayout, int mViewType) {
        this.mSwipeMenuLayout = mSwipeMenuLayout;
        this.mViewType = mViewType;
    }

    public void setOpenPercent(float openPercent) {
        if (openPercent != mSwipeMenuLayout.getOpenPercent()) {
            openPercent = openPercent > 1 ? 1 : (openPercent < 0 ? 0 : openPercent);
            mSwipeMenuLayout.setOpenPercent(openPercent);
        }
    }
    public void setScrollerDuration(int scrollerDuration){
        this.mSwipeMenuLayout.setScrollerDuration(scrollerDuration);
    }

    public void setOrientation(@OrientationMode int orientation){
        if (orientation!=HORIZONTAL&&orientation!=VERTICAL){
            throw new IllegalArgumentException("Use SwipeMenu#HORIZONTAL or SwipeMenu#VERTICAL.");
        }
        this.orientation = orientation;
    }
    @OrientationMode
    public int getOrientation() {
        return orientation;
    }

    public void addMenuItem(SwipeMenuItem item){
        mSwipeMenuItems.add(item);
    }

    public void removeMenuItem(SwipeMenuItem item){
        mSwipeMenuItems.remove(item);
    }

    public List<SwipeMenuItem> getSwipeMenuItems() {
        return mSwipeMenuItems;
    }

    public SwipeMenuItem getMenuItem(int index){
        return mSwipeMenuItems.get(index);
    }

    public Context getContext(){
        return mSwipeMenuLayout.getContext();
    }

    public int getViewType(){
        return mViewType;
    }
}
