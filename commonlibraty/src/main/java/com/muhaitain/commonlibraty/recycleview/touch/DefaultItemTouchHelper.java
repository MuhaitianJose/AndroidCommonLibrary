package com.muhaitain.commonlibraty.recycleview.touch;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.CompatItemTouchHelper;

/**
 * Created by Muhaitian on 2017/12/29.
 */

public class DefaultItemTouchHelper extends CompatItemTouchHelper {

    private DefaultItemTouchHelperCallback mDefaultItemTouchHelperCallback;

    public DefaultItemTouchHelper() {
        this(new DefaultItemTouchHelperCallback());
    }

    /**
     * Creates an ItemTouchHelper that will work with the given Callback.
     * <p>
     * You can attach ItemTouchHelper to a RecyclerView via
     * {@link #attachToRecyclerView(RecyclerView)}. Upon attaching, it will add an item decoration,
     * an onItemTouchListener and a Child attach / detach listener to the RecyclerView.
     *
     * @param callback The Callback which controls the behavior of this touch helper.
     */
    public DefaultItemTouchHelper(DefaultItemTouchHelperCallback callback) {
        super(callback);
        mDefaultItemTouchHelperCallback = (DefaultItemTouchHelperCallback) getCallback();
    }

    public void setOnItemMoveListener(OnItemMoveListener onItemMoveListener) {
        mDefaultItemTouchHelperCallback.setOnItemMoveListener(onItemMoveListener);
    }

    public OnItemMoveListener getOnItemMoveListener() {
        return mDefaultItemTouchHelperCallback.getOnItemMoveListener();
    }

    public void setOnItemMovementListener(OnItemMovementListener onItemMovementListener) {
        mDefaultItemTouchHelperCallback.setOnItemMovementListener(onItemMovementListener);
    }

    public OnItemMovementListener getOnItemMovementListener() {
        return mDefaultItemTouchHelperCallback.getOnItemMovementListener();
    }

    public void setOnStateChangeListener(OnItemStateChangedListener onStateChangeListener) {
        mDefaultItemTouchHelperCallback.setOnItemStateChangedListener(onStateChangeListener);
    }

    public OnItemStateChangedListener getOnIemStateChangeListener() {
        return mDefaultItemTouchHelperCallback.getOnItemStateChangedListener();
    }

    public void setLongPressDragEnabled(boolean canDrag) {
        mDefaultItemTouchHelperCallback.setLongPressDragEnabled(canDrag);
    }

    public boolean isLongPressDragEnabled() {
        return mDefaultItemTouchHelperCallback.isLongPressDragEnabled();
    }

    public void setItemViewSwipeEnabled(boolean canSwipe) {
        mDefaultItemTouchHelperCallback.setItemViewSwipeEnabled(canSwipe);
    }

    public boolean isItemViewSwipeEnabled() {
        return mDefaultItemTouchHelperCallback.isItemViewSwipeEnabled();

    }


}
