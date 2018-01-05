package com.muhaitain.commonlibraty.recycleview.widget;

/**
 * Created by Muhaitian on 2018/1/2.
 */

public interface SwipeSwitch {
    boolean isMenuOpen();

    boolean isLeftMenuOpen();

    boolean isRightMenuOpen();

    boolean isCompleteOpen();

    boolean isLeftCompleteOpen();

    boolean isRightCompleteOpen();

    boolean isMenuOpenNotEqual();

    boolean isLeftMenuOpenNotEqual();

    boolean isRightMenuOpenNotEqual();

    void smoothOpenMenu();

    void smoothOpenLeftMenu();

    void smoothOpenRightMenu();

    void smoothOpenLeftMenu(int duration);

    void smoothOpenRightMenu(int duration);

    void smoothCloseMenu();

    void smoothCloseLeftMenu();

    void smoothCloseRightMenu();

    void smoothCloseMenu(int duration);
}
