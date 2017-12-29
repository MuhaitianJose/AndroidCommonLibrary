package com.muhaitain.commonlibraty.recycleview.touch;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by Muhaitian on 2017/12/29.
 */

public class DefaultItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private OnItemMoveListener onItemMoveListener;
    private OnItemMovementListener onItemMovementListener;
    private OnItemStateChangedListener onItemStateChangedListener;

    private boolean isItemViewSwipeWnabled;
    private boolean isLongPressDragEnabled;

    public DefaultItemTouchHelperCallback(){}

    public boolean isItemViewSwipeWnabled() {
        return isItemViewSwipeWnabled;
    }

    public void setItemViewSwipeWnabled(boolean itemViewSwipeWnabled) {
        isItemViewSwipeWnabled = itemViewSwipeWnabled;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return isLongPressDragEnabled;
    }

    public void setLongPressDragEnable(boolean longPressDragEnable) {
        isLongPressDragEnabled = longPressDragEnable;
    }

    public OnItemMoveListener getOnItemMoveListener() {
        return onItemMoveListener;
    }

    public void setOnItemMoveListener(OnItemMoveListener onItemMoveListener) {
        this.onItemMoveListener = onItemMoveListener;
    }

    public OnItemMovementListener getOnItemMovementListener() {
        return onItemMovementListener;
    }

    public void setOnItemMovementListener(OnItemMovementListener onItemMovementListener) {
        this.onItemMovementListener = onItemMovementListener;
    }

    public OnItemStateChangedListener getOnItemStateChangedListener() {
        return onItemStateChangedListener;
    }

    public void setOnItemStateChangedListener(OnItemStateChangedListener onItemStateChangedListener) {
        this.onItemStateChangedListener = onItemStateChangedListener;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

        if(onItemMovementListener!=null){

        }else {
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if(layoutManager instanceof GridLayoutManager){

            }else if(layoutManager instanceof LinearLayoutManager){

            }
        }

        return 0;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }
}
