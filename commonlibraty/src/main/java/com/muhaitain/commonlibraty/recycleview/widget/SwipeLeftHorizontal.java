package com.muhaitain.commonlibraty.recycleview.widget;

import android.util.Log;
import android.view.View;
import android.widget.OverScroller;

/**
 * Created by Muhaitian on 2018/1/2.
 */

public class SwipeLeftHorizontal extends SwipeHorizontal {
    private static final String TAG = SwipeLeftHorizontal.class.getSimpleName();

    public SwipeLeftHorizontal(View menuView) {
        super(SwipeMenuRecyclerView.LEFT_DIRECTION, menuView);
    }

    @Override
    public boolean isMenuOpen(int scrollX) {
        Log.d(TAG, "isMenuOpen: getMenuView().getWidth()="+getMenuView().getWidth());
        Log.d(TAG, "isMenuOpen: scrollX="+scrollX);
        int i = -getMenuView().getWidth() * getDirection();
        return scrollX <= i && i != 0;
    }

    @Override
    public boolean isMenuOpenNotEqual(int scrollX) {
        return scrollX < -getMenuView().getWidth() * getDirection();
    }

    @Override
    public void autoOpenMenu(OverScroller scroller, int scrollX, int duration) {
        scroller.startScroll(Math.abs(scrollX), 0, getMenuView().getWidth() - Math.abs(scrollX), 0, duration);
    }

    @Override
    public void autoCloseMenu(OverScroller scroller, int scrollX, int duration) {
        Log.d(TAG, "autoCloseMenu: scrollX=" + scrollX);
        scroller.startScroll(-Math.abs(scrollX), 0, Math.abs(scrollX), 0, duration);
    }

    @Override
    public Checker checkXY(int x, int y) {
        mChecker.x = x;
        mChecker.y = y;
        mChecker.shouldResetSwipe = false;
        if (mChecker.x == 0) {
            mChecker.shouldResetSwipe = true;
        }

        if (mChecker.x >= 0) {
            mChecker.x = 0;
        }

        if (mChecker.x <= -getMenuView().getWidth()) {
            mChecker.x = -getMenuView().getWidth();
        }
        return mChecker;
    }

    @Override
    public boolean isClickOnContentView(int contentViewWidth, float x) {
        return x > getMenuView().getWidth();
    }
}
