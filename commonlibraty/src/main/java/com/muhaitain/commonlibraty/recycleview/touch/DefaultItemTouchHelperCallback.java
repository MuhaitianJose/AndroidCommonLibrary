package com.muhaitain.commonlibraty.recycleview.touch;

import android.graphics.Canvas;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by Muhaitian on 2017/12/29.
 */

public class DefaultItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private OnItemMoveListener mOnItemMoveListener;
    private OnItemMovementListener mOnItemMovementListener;
    private OnItemStateChangedListener mOnItemStateChangedListener;

    private boolean isItemViewSwipeEnabled;
    private boolean isLongPressDragEnabled;

    public DefaultItemTouchHelperCallback() {
    }
    @Override
    public boolean isItemViewSwipeEnabled() {
        return isItemViewSwipeEnabled;
    }

    public void setItemViewSwipeEnabled(boolean itemViewSwipeWnabled) {
        isItemViewSwipeEnabled = itemViewSwipeWnabled;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return isLongPressDragEnabled;
    }

    public void setLongPressDragEnabled(boolean longPressDragEnable) {
        isLongPressDragEnabled = longPressDragEnable;
    }

    public OnItemMoveListener getOnItemMoveListener() {
        return mOnItemMoveListener;
    }

    public void setOnItemMoveListener(OnItemMoveListener onItemMoveListener) {
        mOnItemMoveListener = onItemMoveListener;
    }

    public OnItemMovementListener getOnItemMovementListener() {
        return mOnItemMovementListener;
    }

    public void setOnItemMovementListener(OnItemMovementListener onItemMovementListener) {
        mOnItemMovementListener = onItemMovementListener;
    }

    public OnItemStateChangedListener getOnItemStateChangedListener() {
        return mOnItemStateChangedListener;
    }

    public void setOnItemStateChangedListener(OnItemStateChangedListener onItemStateChangedListener) {
        mOnItemStateChangedListener = onItemStateChangedListener;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

        if (mOnItemMovementListener != null) {
            int dragFlags = mOnItemMovementListener.onDragFlags(recyclerView, viewHolder);
            int swipeFlags = mOnItemMovementListener.onSwipeFlags(recyclerView, viewHolder);
            return makeMovementFlags(dragFlags, swipeFlags);
        } else {
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof GridLayoutManager) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                if (linearLayoutManager.getOrientation() == LinearLayoutManager.HORIZONTAL) {
                    int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                    int swipeFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                    return makeMovementFlags(dragFlags, swipeFlags);
                } else {
                    int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                    int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                }
            } else if (layoutManager instanceof LinearLayoutManager) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                if (linearLayoutManager.getOrientation() == LinearLayoutManager.HORIZONTAL) {
                    int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                    int swipeFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                    return makeMovementFlags(dragFlags, swipeFlags);
                } else {
                    int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                    int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                    return makeMovementFlags(dragFlags, swipeFlags);
                }
            }
        }

        return makeMovementFlags(0, 0);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        //判断当前是否是swipe方式：侧滑。
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            float alpha;
            if (layoutManager instanceof LinearLayoutManager) {
                //1.ItemView--ViewHolder; 2.侧滑条目的透明度程度关联谁？dX(delta增量，范围：当前条目-width~width)。
                int orientation = ((LinearLayoutManager) layoutManager).getOrientation();
                if (orientation == LinearLayoutManager.HORIZONTAL) {
                    alpha = 1 - Math.abs(dY) / viewHolder.itemView.getHeight();
                } else {
                    alpha = 1 - Math.abs(dX) / viewHolder.itemView.getWidth();
                }
                viewHolder.itemView.setAlpha(alpha);
            }
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        if (mOnItemMoveListener != null) {
            //回调刷新数据以及界面
            return mOnItemMoveListener.onItemMove(viewHolder, target);
        }
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        //回调刷新数据以及界面
        if (mOnItemMoveListener != null) {
            mOnItemMoveListener.onItemDismiss(viewHolder);
        }
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        if (mOnItemStateChangedListener!=null&&actionState!=ItemTouchHelper.ACTION_STATE_IDLE){
            mOnItemStateChangedListener.onSelectedChanged(viewHolder,actionState);
        }
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        if (mOnItemStateChangedListener!=null){
            mOnItemStateChangedListener.onSelectedChanged(viewHolder,OnItemStateChangedListener.ACTION_STATE_IDLE);
        }
    }
}
