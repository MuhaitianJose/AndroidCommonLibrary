package com.muhaitain.commonlibraty.recycleview.widget;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.muhaitain.commonlibraty.recycleview.adapter.SwipeAdapterWrapper;
import com.muhaitain.commonlibraty.recycleview.touch.DefaultItemTouchHelper;
import com.muhaitain.commonlibraty.recycleview.touch.OnItemMoveListener;
import com.muhaitain.commonlibraty.recycleview.touch.OnItemMovementListener;
import com.muhaitain.commonlibraty.recycleview.touch.OnItemStateChangedListener;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muhaitian on 2018/1/2.
 */

public class SwipeMenuRecyclerView extends RecyclerView {
    private static final String TAG = SwipeMenuRecyclerView.class.getSimpleName();

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
        this.mSwipeItemClickListener = new ItemClick(this, itemClickListener);
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

    public Adapter getOriginAdapter() {
        if (mAdapterWrapper == null) return null;
        return mAdapterWrapper.getOriginAdapter();
    }

    @Override
    public void setAdapter(Adapter adapter) {

        if (mAdapterWrapper != null) {
            mAdapterWrapper.getOriginAdapter().unregisterAdapterDataObserver(mAdapterDataObserver);
        }

        if (adapter == null) {
            mAdapterWrapper = null;
        } else {
            adapter.registerAdapterDataObserver(mAdapterDataObserver);

            mAdapterWrapper = new SwipeAdapterWrapper(getContext(), adapter);
            mAdapterWrapper.setSwipeItemClickListener(mSwipeItemClickListener);
            mAdapterWrapper.setSwipeMenuCreator(mSwipeMenuCreator);
            mAdapterWrapper.setSwipeMenuItemClickListener(mSwipeMenuItemClickListener);

            if (mHeaderViewList.size() > 0) {
                for (View view : mHeaderViewList) {
                    mAdapterWrapper.addHeaderView(view);
                }
            }

            if (mFooterViewList.size() > 0) {
                for (View view : mFooterViewList) {
                    mAdapterWrapper.addFooterView(view);
                }
            }
        }

        super.setAdapter(mAdapterWrapper);
    }

    @Override
    protected void onDetachedFromWindow() {
        if (mAdapterWrapper != null) {
            mAdapterWrapper.getOriginAdapter().unregisterAdapterDataObserver(mAdapterDataObserver);
        }
        super.onDetachedFromWindow();
    }

    private AdapterDataObserver mAdapterDataObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            mAdapterWrapper.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            positionStart += getHeaderItemCount();
            mAdapterWrapper.notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            positionStart += getHeaderItemCount();
            mAdapterWrapper.notifyItemRangeChanged(positionStart, itemCount, payload);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            positionStart += getHeaderItemCount();
            mAdapterWrapper.notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            positionStart += getHeaderItemCount();
            mAdapterWrapper.notifyItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            fromPosition += getHeaderItemCount();
            toPosition += getHeaderItemCount();
            mAdapterWrapper.notifyItemMoved(fromPosition, toPosition);
        }
    };

    private List<View> mHeaderViewList = new ArrayList<>();
    private List<View> mFooterViewList = new ArrayList<>();

    public void addHeaderView(View view) {
        mHeaderViewList.add(view);
        if (mAdapterWrapper != null) {
            mAdapterWrapper.addHeaderViewAndNotify(view);
        }
    }

    public void removeHeaderView(View view) {
        mHeaderViewList.remove(view);
        if (mAdapterWrapper != null) {
            mAdapterWrapper.removeHeaderViewAndNotify(view);
        }
    }

    public void addFooterView(View view) {
        mFooterViewList.add(view);
        if (mAdapterWrapper != null) {
            mAdapterWrapper.addFooterViewAndNotify(view);
        }
    }

    public void removeFooterView(View view) {
        mFooterViewList.remove(view);
        if (mAdapterWrapper != null) {
            mAdapterWrapper.removeFooterViewAndNotify(view);
        }
    }

    public int getHeaderItemCount() {
        if (mAdapterWrapper == null) {
            return 0;
        }

        return mAdapterWrapper.getHeaderItemCount();
    }

    public int getFooterItemCount() {
        if (mAdapterWrapper == null) {
            return 0;
        }
        return mAdapterWrapper.getFooterItemCount();
    }

    public int getItemViewType(int position) {
        if (mAdapterWrapper == null) {
            return 0;
        }
        return mAdapterWrapper.getItemViewType(position);
    }

    public void smoothOpenLeftMenu(int position) {
        smoothOpenMenu(position, LEFT_DIRECTION, SwipeMenuLayout.DEFAULT_SCROLLER_DURATION);
    }

    public void smoothOpenLeftMenu(int position, int duration) {
        smoothOpenMenu(position, LEFT_DIRECTION, duration);
    }

    public void smoothOpenRightMenu(int position) {
        smoothOpenMenu(position, RIGHT_DIRECTION, SwipeMenuLayout.DEFAULT_SCROLLER_DURATION);
    }

    public void smoothOpenRightMenu(int position, int duration) {
        smoothOpenMenu(position, RIGHT_DIRECTION, duration);
    }

    public void smoothOpenMenu(int position, @DirectionMode int direction, int duration) {
        if (mOldSwipedLayout != null) {
            if (mOldSwipedLayout.isMenuOpen()) {
                mOldSwipedLayout.smoothCloseMenu();
            }
        }

        position += getHeaderItemCount();
        ViewHolder vh = findViewHolderForAdapterPosition(position);
        if (vh != null) {
            View view = getSwipeMenuView(vh.itemView);
            if (view instanceof SwipeMenuLayout) {
                mOldSwipedLayout = (SwipeMenuLayout) view;
                if (direction == RIGHT_DIRECTION) {
                    mOldTouchedPosition = position;
                    mOldSwipedLayout.smoothOpenRightMenu(duration);
                } else if (direction == LEFT_DIRECTION) {
                    mOldTouchedPosition = position;
                    mOldSwipedLayout.smoothOpenLeftMenu(duration);
                }
            }
        }
    }

    private View getSwipeMenuView(View itemView) {
        if (itemView instanceof SwipeMenuLayout) {
            return itemView;
        }
        List<View> unvisited = new ArrayList<>();
        unvisited.add(itemView);
        while (!unvisited.isEmpty()) {
            View child = unvisited.remove(0);
            if (!(child instanceof ViewGroup)) {
                continue;
            }
            if (child instanceof SwipeMenuLayout) {
                return child;
            }

            ViewGroup group = (ViewGroup) child;

            final int childCount = group.getChildCount();
            for (int i = 0; i < childCount; i++) {
                unvisited.add(group.getChildAt(i));
            }
        }

        return itemView;
    }

    public void smoothCloseMenu() {
        if (mOldSwipedLayout != null && mOldSwipedLayout.isMenuOpen()) {
            mOldSwipedLayout.smoothCloseMenu();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {

        boolean isIntercepted = super.onInterceptTouchEvent(e);
        if (allowSwipeDelete) {
            return isIntercepted;
        } else {
            if (e.getPointerCount() > 1) return true;
            int action = e.getAction();
            int x = (int) e.getX();
            int y = (int) e.getY();
            switch (action) {
                case MotionEvent.ACTION_DOWN: {
                    mDownX = x;
                    mDownY = y;
                    isIntercepted = false;
                    int touchingPosition = getChildAdapterPosition(findChildViewUnder(x, y));
                    if (touchingPosition != mOldTouchedPosition && mOldSwipedLayout != null && mOldSwipedLayout.isMenuOpen()) {
                        mOldSwipedLayout.smoothCloseMenu();
                        isIntercepted = true;
                    }

                    if (isIntercepted) {
                        mOldSwipedLayout = null;
                        mOldTouchedPosition = INVALID_POSITION;
                    } else {
                        ViewHolder vh = findViewHolderForAdapterPosition(touchingPosition);
                        if (vh != null) {
                            View itemView = getSwipeMenuView(vh.itemView);
                            if (itemView instanceof SwipeMenuLayout) {
                                mOldSwipedLayout = (SwipeMenuLayout) itemView;
                                mOldTouchedPosition = touchingPosition;
                            }
                        }
                    }
                    break;
                }
                case MotionEvent.ACTION_MOVE: {
                    isIntercepted = handleUnDown(x, y, isIntercepted);
                    if (mOldSwipedLayout == null) break;
                    ViewParent viewParent = getParent();
                    if (viewParent == null) break;
                    int disX = mDownX - x;
                    boolean showRightCloseLeft = disX > 0 && (mOldSwipedLayout.hasRightMenu() || mOldSwipedLayout.isLeftCompleteOpen());
                    boolean showLeftCloseRight = disX < 0 && (mOldSwipedLayout.hasLeftMenu() || mOldSwipedLayout.isRightCompleteOpen());
                    viewParent.requestDisallowInterceptTouchEvent(showLeftCloseRight || showRightCloseLeft);
                }
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL: {
                    isIntercepted = handleUnDown(x, y, isIntercepted);
                    break;
                }
            }
        }
        return isIntercepted;
    }

    private boolean handleUnDown(int x, int y, boolean defaultValue) {
        int disX = mDownX - x;
        int disY = mDownY - y;
        //此处添加这个操作，不知道为啥会重新赋值为0
        if (mScaleTouchSlop == 0) {
            mScaleTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        }
        // swipe
        if (Math.abs(disX) > mScaleTouchSlop && Math.abs(disX) > Math.abs(disY)) {
            return false;
        }
        //click
        if (Math.abs(disY) < mScaleTouchSlop && Math.abs(disX) < mScaleTouchSlop) {
            return false;
        }
        return defaultValue;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        int action = e.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                if (mOldSwipedLayout != null && mOldSwipedLayout.isMenuOpen()) {
                    mOldSwipedLayout.smoothCloseMenu();
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return super.onTouchEvent(e);
    }

    private int mScrollState = -1;

    private boolean isLoadMore = false;
    private boolean isAutoLoadMore = true;
    private boolean isLoadError = false;

    private boolean mDataEmpty = true;
    private boolean mHasMore = false;

    private LoadMoreView mLoadMoreView;
    private LoadMoreListener mLoadMoreListener;

    @Override
    public void onScreenStateChanged(int screenState) {
        this.mScrollState = screenState;
    }

    @Override
    public void onScrolled(int dx, int dy) {
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager != null && layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            int itemCount = layoutManager.getItemCount();
            if (itemCount <= 0) {
                return;
            }

            int lastVisiblePosition = linearLayoutManager.findLastVisibleItemPosition();
            if (itemCount == lastVisiblePosition + 1 &&
                    (mScrollState == SCROLL_STATE_DRAGGING || mScrollState == SCROLL_STATE_SETTLING)) {
                dispatchLoadMore();
            }
        } else if (layoutManager != null && layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;

            int itemCount = layoutManager.getItemCount();
            if (itemCount <= 0) {
                return;
            }
            int[] lastVisiblePositionArray = staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(null);
            int lastVisiblePosition = lastVisiblePositionArray[lastVisiblePositionArray.length - 1];
            if (itemCount == lastVisiblePosition + 1 &&
                    (mScrollState == SCROLL_STATE_DRAGGING || mScrollState == SCROLL_STATE_SETTLING)) {
                dispatchLoadMore();
            }
        }
        super.onScrolled(dx, dy);
    }

    private void dispatchLoadMore() {
        if (isLoadError) return;

        if (!isAutoLoadMore) {
            if (mLoadMoreView != null) {
                mLoadMoreView.onWaitToLoadMore(mLoadMoreListener);
            }
        } else {
            if (isLoadMore || mDataEmpty || !mHasMore) {
                return;
            }

            isLoadMore = true;
            if (mLoadMoreView != null) {
                mLoadMoreView.onLoading();
            }
            if (mLoadMoreListener != null) {
                mLoadMoreListener.onLoadMore();
            }
        }

    }

    public void useDefaultLoadMore() {
        DefaultLoadMoreView defaultLoadMoreView = new DefaultLoadMoreView(getContext());
        addHeaderView(defaultLoadMoreView);
        addFooterView(defaultLoadMoreView);
    }

    public void setLoadMoreView(LoadMoreView loadMoreView) {
        mLoadMoreView = loadMoreView;
    }

    public void setLoadMoreListener(LoadMoreListener loadMoreListener) {
        mLoadMoreListener = loadMoreListener;
    }

    public void setAutoLoadMore(boolean autoLoadMore) {
        isAutoLoadMore = autoLoadMore;
    }

    public final void loadMoreFinish(boolean dataEmpty, boolean hasMore) {
        isLoadMore = false;
        isLoadError = false;

        mDataEmpty = dataEmpty;
        mHasMore = hasMore;

        if (mLoadMoreView != null) {
            mLoadMoreView.onLoadFinish(dataEmpty, hasMore);
        }
    }

    public void loadMoreError(int errorCode, String errorMessage) {
        isLoadMore = false;
        isLoadError = true;
        if (mLoadMoreView != null) {
            mLoadMoreView.onLoadError(errorCode, errorMessage);
        }
    }

    public interface LoadMoreView {

        /**
         * Show progress.
         */
        void onLoading();

        /**
         * Load finish, handle result.
         */
        void onLoadFinish(boolean dataEmpty, boolean hasMore);

        /**
         * Non-auto-loading mode, you can to click on the item to load.
         */
        void onWaitToLoadMore(LoadMoreListener loadMoreListener);

        /**
         * Load error.
         */
        void onLoadError(int errorCode, String errorMessage);
    }

    public interface LoadMoreListener {

        /**
         * More data should be requested.
         */
        void onLoadMore();
    }
}
