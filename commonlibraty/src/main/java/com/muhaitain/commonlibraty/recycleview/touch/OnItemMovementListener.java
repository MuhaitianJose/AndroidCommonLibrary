package com.muhaitain.commonlibraty.recycleview.touch;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by Muhaitian on 2017/12/29.
 */

public interface OnItemMovementListener {
    int INVALID = 0;

    int LEFT = ItemTouchHelper.LEFT;

    int UP = ItemTouchHelper.UP;

    int RIGHT = ItemTouchHelper.RIGHT;

    int DOWN = ItemTouchHelper.DOWN;

    /**
     * 是否可以拖放删除Item
     * @param recyclerView
     * @param viewHolder
     * @return use {@link #LEFT}, {@link #UP}, {@link #RIGHT}, {@link #DOWN}.
     */
    int onDragFlags(RecyclerView recyclerView,RecyclerView.ViewHolder viewHolder);

    /**
     * 是否可以滑动删除
     * @param recyclerView
     * @param targetViewHolder
     * @return use {@link #LEFT}, {@link #UP}, {@link #RIGHT}, {@link #DOWN}.
     */
    int onSwipeFlags(RecyclerView recyclerView,RecyclerView.ViewHolder targetViewHolder);

}
