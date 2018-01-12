package com.muhaitain.commonlibraty.recycleview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.OverScroller;
import android.widget.TextView;

import com.muhaitain.commonlibraty.R;

/**
 * Created by Muhaitian on 2018/1/2.
 */

public class SwipeMenuLayout extends FrameLayout implements SwipeSwitch {

    private static final String TAG = SwipeMenuLayout.class.getSimpleName();

    public static final int DEFAULT_SCROLLER_DURATION = 200;

    private int mLeftViewId = 0;
    private int mContentViewId = 0;
    private int mRightViewId = 0;

    private float mOpenPercent = 0.5f;
    private int mScrollerDuration = DEFAULT_SCROLLER_DURATION;

    private int mScaledTouchSlop;
    private int mLastX;
    private int mLastY;
    private int mDownX;
    private int mDownY;
    private View mContentView;
    private SwipeLeftHorizontal mSwipeLeftHorizontal;
    private SwipeRightHorizontal mSwipeRightHorizontal;
    private SwipeHorizontal mSwipeCurrentHorizontal;
    private boolean shouldResetSwipe;
    private boolean mDragging;
    private boolean swipeEnable = true;
    private OverScroller mScroller;
    private VelocityTracker mVelocityTracker;
    private int mScaledMinimumFlingVelocity;
    private int mScaledMaximumFlingVelocity;


    public SwipeMenuLayout(@NonNull Context context) {
        this(context,null);
    }

    public SwipeMenuLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SwipeMenuLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.recycler_swipe_SwipeMenuLayout);
        mLeftViewId = typedArray.getResourceId(R.styleable.recycler_swipe_SwipeMenuLayout_leftViewId, mLeftViewId);
        mRightViewId = typedArray.getResourceId(R.styleable.recycler_swipe_SwipeMenuLayout_rightViewId, mRightViewId);
        mContentViewId = typedArray.getResourceId(R.styleable.recycler_swipe_SwipeMenuLayout_contentViewId, mContentViewId);
        typedArray.recycle();
        ViewConfiguration configuration = ViewConfiguration.get(getContext());
        mScaledTouchSlop = configuration.getScaledTouchSlop();
        mScaledMaximumFlingVelocity = configuration.getScaledMaximumFlingVelocity();
        mScaledMinimumFlingVelocity = configuration.getScaledMinimumFlingVelocity();
        mScroller = new OverScroller(getContext());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (mLeftViewId != 0 && mSwipeLeftHorizontal == null) {
            View view = findViewById(mLeftViewId);
            mSwipeLeftHorizontal = new SwipeLeftHorizontal(view);
        }

        if (mRightViewId != 0 && mSwipeRightHorizontal == null) {
            View view = findViewById(mRightViewId);
            mSwipeRightHorizontal = new SwipeRightHorizontal(view);
        }
        if (mContentViewId != 0 && mContentView == null) {
            mContentView = findViewById(mContentViewId);
        } else {
            TextView errorView = new TextView(getContext());
            errorView.setClickable(true);
            errorView.setGravity(Gravity.CENTER);
            errorView.setTextSize(16);
            errorView.setText("You may not have set the ContentView.");
            mContentView = errorView;
            addView(mContentView);
        }
    }

    public void setSwipeEnable(boolean swipeEnable) {
        this.swipeEnable = swipeEnable;
    }

    public boolean isSwipeEnable() {
        return swipeEnable;
    }

    public void setOpenPercent(float openPercent) {
        this.mOpenPercent = openPercent;
    }

    public float getOpenPercent() {
        return mOpenPercent;
    }

    public void setScrollerDuration(int scrollerDuration) {
        this.mScrollerDuration = scrollerDuration;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        boolean isIntercepted = super.onInterceptTouchEvent(ev);
        int action = ev.getAction();
//        Log.d(TAG, "onInterceptTouchEvent: isIntercepted="+isIntercepted);
//        Log.d(TAG, "onInterceptTouchEvent: action="+action);
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                mDownX = mLastX = (int) ev.getX();
                mDownY = (int) ev.getY();
                return false;
            }
            case MotionEvent.ACTION_MOVE: {
                int disX = (int) (ev.getX() - mDownX);
                int disY = (int) (ev.getY() - mDownY);
                boolean result = Math.abs(disX) > mScaledTouchSlop && Math.abs(disX) > Math.abs(disY);

                return result;
            }

            case MotionEvent.ACTION_UP: {
                boolean isClick = mSwipeCurrentHorizontal != null
                        && mSwipeCurrentHorizontal.isClickOnContentView(getWidth(), ev.getX());
                if (isMenuOpen() && isClick) {
                    smoothCloseMenu();
                    return true;
                }
                return false;
            }

            case MotionEvent.ACTION_CANCEL: {
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                return false;
            }
        }
        return isIntercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        Log.d(TAG, "onTouchEvent: ");
        if (mVelocityTracker == null) mVelocityTracker = VelocityTracker.obtain();
        mVelocityTracker.addMovement(event);
        int dx;
        int dy;
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                mLastX = (int) event.getX();
                mLastY = (int) event.getY();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if (!isSwipeEnable()) break;
                int disX = (int) (mLastX - event.getX());
                int disY = (int) (mLastY - event.getY());
                if (!mDragging && Math.abs(disX) > mScaledTouchSlop && Math.abs(disX) > Math.abs(disY)) {
                    mDragging = true;
                }

                if (mDragging) {
                    if (mSwipeCurrentHorizontal == null || shouldResetSwipe) {
                        if (disX < 0) {
                            if (mSwipeLeftHorizontal != null) {
                                mSwipeCurrentHorizontal = mSwipeLeftHorizontal;
                            } else {
                                mSwipeCurrentHorizontal = mSwipeRightHorizontal;
                            }
                        } else {
                            if (mSwipeRightHorizontal != null) {
                                mSwipeCurrentHorizontal = mSwipeRightHorizontal;
                            } else {
                                mSwipeCurrentHorizontal = mSwipeLeftHorizontal;
                            }
                        }
                    }
                    scrollBy(disX, 0);
                    mLastX = (int) event.getX();
                    mLastY = (int) event.getY();
                    shouldResetSwipe = false;
                }
                break;
            }

            case MotionEvent.ACTION_UP: {
                dx = (int) (mDownX - event.getX());
                dy = (int) (mDownY - event.getY());
                mDragging = false;
                mVelocityTracker.computeCurrentVelocity(1000, mScaledMaximumFlingVelocity);
                int velocityX = (int) mVelocityTracker.getXVelocity();
                int velocity = Math.abs(velocityX);
                if (velocity > mScaledMinimumFlingVelocity) {
                    if (mSwipeCurrentHorizontal != null) {
                        int duration = getSwipeDuration(event, velocity);
                        if (mSwipeCurrentHorizontal instanceof SwipeRightHorizontal) {
                            if (velocityX < 0) {
                                smoothOpenMenu(duration);
                            } else {
                                smoothCloseMenu(duration);
                            }
                        } else {
                            if (velocityX > 0) {
                                smoothOpenMenu(duration);
                            } else {
                                smoothCloseMenu(duration);
                            }
                        }
                        ViewCompat.postInvalidateOnAnimation(this);
                    }
                } else {
                    judgeOpenClose(dx, dy);
                }

                mVelocityTracker.clear();
                mVelocityTracker.recycle();
                mVelocityTracker = null;
                if (Math.abs(mDownX - event.getX()) > mScaledTouchSlop
                        || Math.abs(mDownY - event.getY()) > mScaledTouchSlop
                        || isLeftMenuOpen()
                        || isRightMenuOpen()) {
                    event.setAction(MotionEvent.ACTION_CANCEL);
                    super.onTouchEvent(event);
                    return true;
                }
                break;
            }
            case MotionEvent.ACTION_CANCEL: {
                mDragging = false;
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                } else {
                    dx = (int) (mDownX - event.getX());
                    dy = (int) (mDownY - event.getY());
                    judgeOpenClose(dx, dy);
                }
                break;
            }
        }
        return super.onTouchEvent(event);
    }

    private int getSwipeDuration(MotionEvent ev, int velocity) {
        int sx = getScrollX();
        int dx = (int) (ev.getX() - sx);
        final int width = mSwipeCurrentHorizontal.getMenuWidth();
        final int halfWidth = width / 2;
        final float distanceRatio = Math.min(1f, 1.0f * Math.abs(dx) / width);
        final float distance = halfWidth + halfWidth * distanceInfluenceForSnapDuration(distanceRatio);
        int duration;
        if (velocity > 0) {
            duration = 4 * Math.round(1000 * Math.abs(distance / velocity));
        } else {
            final float pageDelta = Math.abs(dx) / width;
            duration = (int) ((pageDelta + 1) * 100);
        }
        duration = Math.min(duration, mScrollerDuration);
        return duration;
    }

    float distanceInfluenceForSnapDuration(float fl) {
        fl -= 0.5f;
        fl *= 0.3f * Math.PI / 2.0f;
        return (float) Math.sin(fl);
    }

    private void judgeOpenClose(int dx, int dy) {
        if (mSwipeCurrentHorizontal != null) {
            if (Math.abs(getScrollX()) >= (mSwipeCurrentHorizontal.getMenuView().getWidth() * mOpenPercent)) {
                if (Math.abs(dx) > mScaledTouchSlop || Math.abs(dy) > mScaledTouchSlop) {
                    if (isMenuOpenNotEqual()) {
                        smoothCloseMenu();
                    } else {
                        smoothOpenMenu();
                    }
                } else {
                    if (isMenuOpen()) {
                        smoothCloseMenu();
                    } else {
                        smoothOpenMenu();
                    }
                }
            } else {
                smoothCloseMenu();
            }
        }

    }

    @Override
    public void scrollTo(int x, int y) {
        if (mSwipeCurrentHorizontal == null) {
            super.scrollTo(x, y);
        } else {
            SwipeHorizontal.Checker checker = mSwipeCurrentHorizontal.checkXY(x, y);
            shouldResetSwipe = checker.shouldResetSwipe;
            if (checker.x != getScrollX()) {
                super.scrollTo(checker.x, checker.y);
            }
        }
    }

    @Override
    public void computeScroll() {

        if (mScroller.computeScrollOffset() && mSwipeCurrentHorizontal != null) {
            if (mSwipeCurrentHorizontal instanceof SwipeRightHorizontal) {
                scrollTo(Math.abs(mScroller.getCurrX()), 0);
                invalidate();
            } else {
                scrollTo(-Math.abs(mScroller.getCurrX()), 0);
                invalidate();
            }
        }
    }

    public boolean hasLeftMenu() {
        return mSwipeLeftHorizontal != null && mSwipeLeftHorizontal.canSwipe();
    }

    public boolean hasRightMenu() {
        return mSwipeRightHorizontal != null && mSwipeRightHorizontal.canSwipe();
    }

    @Override
    public boolean isMenuOpen() {
        return isLeftMenuOpen() || isRightMenuOpen();
    }

    @Override
    public boolean isLeftMenuOpen() {
        return mSwipeLeftHorizontal != null && mSwipeLeftHorizontal.isMenuOpen(getScrollX());
    }

    @Override
    public boolean isRightMenuOpen() {
        return mSwipeRightHorizontal != null && mSwipeRightHorizontal.isMenuOpen(getScrollX());
    }

    @Override
    public boolean isCompleteOpen() {
        return isLeftCompleteOpen() && isRightMenuOpen();
    }

    @Override
    public boolean isLeftCompleteOpen() {
        return mSwipeLeftHorizontal != null && mSwipeLeftHorizontal.isCompleteClose(getScrollX());
    }

    @Override
    public boolean isRightCompleteOpen() {
        return mSwipeRightHorizontal != null && mSwipeRightHorizontal.isCompleteClose(getScrollX());
    }

    @Override
    public boolean isMenuOpenNotEqual() {
        return isLeftMenuOpenNotEqual() || isRightMenuOpenNotEqual();
    }

    @Override
    public boolean isLeftMenuOpenNotEqual() {
        return mSwipeLeftHorizontal != null && mSwipeLeftHorizontal.isMenuOpenNotEqual(getScrollX());
    }

    @Override
    public boolean isRightMenuOpenNotEqual() {
        return mSwipeRightHorizontal != null && mSwipeRightHorizontal.isMenuOpenNotEqual(getScrollX());
    }

    private void smoothOpenMenu(int duration) {
        if (mSwipeCurrentHorizontal != null) {
            mSwipeCurrentHorizontal.autoOpenMenu(mScroller, getScrollX(), duration);
            invalidate();
        }
    }

    @Override
    public void smoothOpenMenu() {
        smoothOpenMenu(mScrollerDuration);
    }

    @Override
    public void smoothOpenLeftMenu() {
        smoothOpenLeftMenu(mScrollerDuration);
    }

    @Override
    public void smoothOpenRightMenu() {
        smoothOpenRightMenu(mScrollerDuration);
    }

    @Override
    public void smoothOpenLeftMenu(int duration) {
        if (mSwipeLeftHorizontal != null) {
            mSwipeCurrentHorizontal = mSwipeLeftHorizontal;
            smoothOpenMenu(duration);
        }
    }

    @Override
    public void smoothOpenRightMenu(int duration) {
        if (mSwipeRightHorizontal != null) {
            mSwipeCurrentHorizontal = mSwipeRightHorizontal;
            smoothOpenMenu(duration);
        }
    }

    @Override
    public void smoothCloseMenu() {
        smoothCloseMenu(mScrollerDuration);
    }

    @Override
    public void smoothCloseLeftMenu() {
        if (mSwipeLeftHorizontal != null) {
            mSwipeCurrentHorizontal = mSwipeLeftHorizontal;
            smoothCloseMenu();
        }
    }

    @Override
    public void smoothCloseRightMenu() {
        if (mSwipeRightHorizontal != null) {
            mSwipeCurrentHorizontal = mSwipeRightHorizontal;
            smoothCloseMenu();
        }
    }

    /**
     *
     * @param duration
     */
    @Override
    public void smoothCloseMenu(int duration) {
        if (mSwipeCurrentHorizontal != null) {
            mSwipeCurrentHorizontal.autoCloseMenu(mScroller, getScrollX(), duration);
            invalidate();
        }
    }

    /**
     * 1.精确模式（MeasureSpec.EXACTLY）
     * 在这种模式下，尺寸的值是多少，那么这个组件的长或宽就是多少。
     * 2.最大模式（MeasureSpec.AT_MOST）
     * 这个也就是父组件，能够给出的最大的空间，当前组件的长或宽最大只能为这么大，当然也可以比这个小。
     * 3.未指定模式（MeasureSpec.UNSPECIFIED）
     * 这个就是说，当前组件，可以随便用空间，不受限制。
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int contentViewHeight = 0;
        if (mContentView != null) {
            measureChildWithMargins(mContentView, widthMeasureSpec, 0, heightMeasureSpec, 0);
            contentViewHeight = mContentView.getMeasuredHeight();
        }

        if (mSwipeLeftHorizontal != null) {
            View leftMenu = mSwipeLeftHorizontal.getMenuView();
            int menuViewHeight = contentViewHeight == 0 ? leftMenu.getMeasuredHeightAndState() : contentViewHeight;

            int menuWidthSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.AT_MOST);
            int menuHeightSpec = MeasureSpec.makeMeasureSpec(menuViewHeight, MeasureSpec.EXACTLY);
            leftMenu.measure(menuWidthSpec, menuHeightSpec);
        }

        if (mSwipeRightHorizontal != null) {
            View rightMenu = mSwipeRightHorizontal.getMenuView();
            int menuViewHeight = contentViewHeight == 0 ? rightMenu.getMeasuredHeightAndState() : contentViewHeight;

            int menuWidthSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.AT_MOST);
            int menuHeightSpec = MeasureSpec.makeMeasureSpec(menuViewHeight, MeasureSpec.EXACTLY);
            rightMenu.measure(menuWidthSpec, menuHeightSpec);
        }

        if (contentViewHeight > 0) {
            setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), contentViewHeight);
        }


    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int contentViewHeight;
        if (mContentView!=null){
            int contentViewWidth = mContentView.getMeasuredWidthAndState();
            contentViewHeight = mContentView.getMeasuredHeightAndState();
            LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
            int start = getPaddingStart();
            int top_ = getPaddingTop() +lp.topMargin;
            mContentView.layout(start,top_,start+contentViewWidth,top_+contentViewHeight);
        }

        if (mSwipeLeftHorizontal!=null){
            View leftMenu = mSwipeLeftHorizontal.getMenuView();
            int menuViewWidth = leftMenu.getMeasuredWidthAndState();
            int menuViewHeight = leftMenu.getMeasuredHeightAndState();
            LayoutParams lp = (LayoutParams) leftMenu.getLayoutParams();
            int top_ = getPaddingTop()+lp.topMargin;
            leftMenu.layout(-menuViewWidth,top_,0,top_+menuViewHeight);
        }

        if (mSwipeRightHorizontal!=null){
            View rightMenu = mSwipeRightHorizontal.getMenuView();
            int menuViewWidth = rightMenu.getMeasuredWidthAndState();
            int menuViewHeight = rightMenu.getMeasuredHeightAndState();
            LayoutParams lp = (LayoutParams) rightMenu.getLayoutParams();
            int top_ = getPaddingTop()+lp.topMargin;
            int contentViewWidth = mContentView.getMeasuredWidthAndState();
            rightMenu.layout(contentViewWidth,top_,contentViewWidth+menuViewWidth,top_+menuViewHeight);
        }
    }
}
