package com.muhaitain.commonlibraty.recycleview.touch;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by Muhaitian on 2017/12/29.
 */

public interface OnItemStateChangedListener {
    /**
     * ItemTouchHelper处于空闲状态。在这个状态下，或者没有相关的运动事件
     * 用户或最新的动作事件尚未触发滑动或拖动。
     */
    int ACTION_STATE_IDLE = ItemTouchHelper.ACTION_STATE_IDLE;
    /**
     * A View is currently being swiped.
     */
    int ACTION_STATE_SWIPE = ItemTouchHelper.ACTION_STATE_SWIPE;

    /**
     * A View is currently being dragged.
     */
    int ACTION_STATE_DRAG = ItemTouchHelper.ACTION_STATE_DRAG;

    /**
     * 当ViewHolder由ItemTouchHelper进行修改或拖动时，会被调用。
     * @param viewHolder
     * @param actionState
     */
    void onSelectedChanged(RecyclerView.ViewHolder viewHolder,int actionState);
}
