package com.muhaitain.commonlibraty.recycleview.widget;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewConfiguration;

import com.muhaitain.commonlibraty.recycleview.touch.DefaultItemTouchHelper;
import com.muhaitain.commonlibraty.recycleview.touch.OnItemMoveListener;
import com.muhaitain.commonlibraty.recycleview.touch.OnItemMovementListener;
import com.muhaitain.commonlibraty.recycleview.touch.OnItemStateChangedListener;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Muhaitian on 2018/1/2.
 */

public class SwipeMenuRecyclerView extends RecyclerView {

    public static final int LEFT_DIRECTION = 1;
    public static final int RIGHT_DIRECTION = -1;

    @IntDef({LEFT_DIRECTION, RIGHT_DIRECTION})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DirectionMode {
    }

    /**
     * Invalid position
     */
    private static final int INVALID_POSITION = -1;

    protected int mScaleTouchSlop;
    protected SwipeMenuLayout mOldSwipedLayout;
    protected int mOldTouchedPosition = INVALID_POSITION;

    private int mDownX;
    private int mDownY;

    private boolean allowSwipeDelete = false;

    private DefaultItemTouchHelper mDefaultItemTouchHelper;

    private SwipeMenuCreator mSwipeMenuCreator;
    private SwipeMenuItemClickListener mSwipeMenuItemClickListener;
    private SwipeItemClickListener mSwipeItemClickListener;

    private SwipeAdapterWrapper mAdapterWrapper;

    public SwipeMenuRecyclerView(Context context) {
        super(context);
    }

    public SwipeMenuRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SwipeMenuRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //getScaledTouchSlop是一个距离，表示滑动的时候，手的移动要大于这个距离才开始移动控件
        mScaleTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    private void initializeItemTouchHelper() {
        if (mDefaultItemTouchHelper == null) {
            mDefaultItemTouchHelper = new DefaultItemTouchHelper();
            mDefaultItemTouchHelper.attachToRecyclerView(this);
        }
    }

    public void setOnItemMoveListener(OnItemMoveListener onItemMoveListener) {
        initializeItemTouchHelper();
        this.mDefaultItemTouchHelper.setOnItemMoveListener(onItemMoveListener);
    }

    public void setOnItemMovementListener(OnItemMovementListener onItemMovementListener) {
        initializeItemTouchHelper();
        this.mDefaultItemTouchHelper.setOnItemMovementListener(onItemMovementListener);
    }

    public void setOnItemStateChangedListener(OnItemStateChangedListener onItemStateChangedListener) {
        initializeItemTouchHelper();
        this.mDefaultItemTouchHelper.setOnStateChangeListener(onItemStateChangedListener);
    }

    /**
     * drag true,otherwise is can't
     *
     * @param canDrag
     */
    public void setLongPressDragEnabled(boolean canDrag) {
        initializeItemTouchHelper();
        this.mDefaultItemTouchHelper.setLongPressDragEnabled(canDrag);
    }

    public boolean isLongPressDragEnabled() {
        initializeItemTouchHelper();
        return mDefaultItemTouchHelper.isLongPressDragEnabled();
    }

    /**
     * swipe true,otherwise is can't
     *
     * @param canSwipe
     */
    public void setItemViewSwipeEnabled(boolean canSwipe) {
        initializeItemTouchHelper();
        this.mDefaultItemTouchHelper.setItemViewSwipeEnabled(canSwipe);
    }

    public boolean isItemViewSwipeEnabled() {
        initializeItemTouchHelper();
        return this.isItemViewSwipeEnabled();
    }

    private void checkAdapterExist(String message) {
        if (mAdapterWrapper != null) {
            throw new IllegalStateException(message);
        }
    }

    public void setSwipeItemClickListener(SwipeItemClickListener itemClickListener) {
        if (itemClickListener == null) {
            return;
        }
        checkAdapterExist("Cannot set item click listener, setAdapter has already been called.");
        this.mSwipeItemClickListener = new ItemClick()
    }

    private class ItemClick implements SwipeItemClickListener {
        private SwipeMenuRecyclerView mRecyclerView;
        private SwipeItemClickListener mCallBack;

        public ItemClick(SwipeMenuRecyclerView recyclerView, SwipeItemClickListener itemClickListener) {
            mRecyclerView = recyclerView;
            mCallBack = itemClickListener;
        }

        @Override
        public void onItemClick(View itemView, int position) {
            position = position - mRecyclerView.getHeaderItemCount();
            if (position >= 0) {
                mCallBack.onItemClick(itemView, position);
            }
        }
    }

    public void setSwipeMenuCreator(SwipeMenuCreator mSwipeMenuCreator) {
        if (mSwipeMenuCreator == null) {
            return;
        }
        checkAdapterExist("cannot set menu creater, setAdapter has already been called");
        this.mSwipeMenuCreator = mSwipeMenuCreator;
    }

    public void setSwipeMenuItemClickListener(SwipeMenuItemClickListener menuItemClickListener) {
        if (menuItemClickListener == null) {
            return;
        }
        checkAdapterExist("Cannot set menu item click listener, set Adapter has already been called");
        this.mSwipeMenuItemClickListener = new MenuItemClick(this, menuItemClickListener);
    }

    private class MenuItemClick implements SwipeMenuItemClickListener {

        private SwipeMenuRecyclerView mRecycleView;
        private SwipeMenuItemClickListener mCallback;

        public MenuItemClick(SwipeMenuRecyclerView mRecycleView, SwipeMenuItemClickListener mCallback) {
            this.mRecycleView = mRecycleView;
            this.mCallback = mCallback;
        }

        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            int position = menuBridge.getAdapterPosition();
            position = position - mRecycleView.getHeaderItemCount();
            if (position >= 0) {
                menuBridge.mAdapterPosition = position;
                mCallback.onItemClick(menuBridge);
            }
        }
    }

    @Override
    public void setLayoutManager(LayoutManager layoutManager) {
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            final GridLayoutManager.SpanSizeLookup spanSizeLookupHolder = gridLayoutManager.getSpanSizeLookup();

            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (mAdapterWrapper.isHeaderView(position) || mAdapterWrapper.isFooterView(position)) {
                        return gridLayoutManager.getSpanCount();
                    }
                    if (spanSizeLookupHolder != null)
                        return spanSizeLookupHolder.getSpanSize(position - mAdapterWrapper.getHeaderItemCount());
                    return 1;
                }
            });
        }
        super.setLayoutManager(layoutManager);
    }

    public Adapter getOriginAdapter(){
        if (mAdapterWrapper==null) return null;
        return mAdapterWrapper.getOriginAdapter();
    }

    @Override
    public void setAdapter(Adapter adapter) {

        if (mAdapterWrapper !=null){
            mAdapterWrapper.getOriginAdapter().unregisterAdapterDataObserver(mAdapterDataObserver);
        }

        if (adapter==null){
            mAdapterWrapper=null;
        }else {
            adapter.registerAdapterDataObserver(mAdapterDataObserver);
        }

        super.setAdapter(adapter);
    }

    private AdapterDataObserver mAdapterDataObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            mAdapterWrapper.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {

        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            super.onItemRangeChanged(positionStart, itemCount, payload);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
        }
    };
}
