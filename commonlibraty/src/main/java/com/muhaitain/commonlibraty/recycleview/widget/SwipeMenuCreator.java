package com.muhaitain.commonlibraty.recycleview.widget;

/**
 * Created by Muhaitian on 2018/1/3.
 */

public interface SwipeMenuCreator {
    /**
     * 创建左右菜单
     * @param swipeLeftMenu
     * @param swipeRightMenu
     * @param viewType
     */
    void onCreateMenu(SwipeMenu swipeLeftMenu,SwipeMenu swipeRightMenu,int viewType);
}
