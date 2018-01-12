package com.muhaitain.commonlibraty.recycleview.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.muhaitain.commonlibraty.R;
import com.yanzhenjie.loading.LoadingView;

/**
 * Created by Muhaitian on 2018/1/8.
 */

public class DefaultLoadMoreView extends LinearLayout implements SwipeMenuRecyclerView.LoadMoreView, View.OnClickListener {

    private LoadingView mLoadingView;
    private TextView mTvMessage;

    private SwipeMenuRecyclerView.LoadMoreListener mLoadMoreListener;

    public DefaultLoadMoreView(Context context) {
        this(context, null);
    }

    public DefaultLoadMoreView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setGravity(Gravity.CENTER);
        setVisibility(GONE);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int minHeight = (int) (displayMetrics.density * 60 + 0.5);
        setMinimumHeight(minHeight);

        inflate(getContext(), R.layout.recycler_swipe_view_load_more, this);

        mLoadingView = (LoadingView) findViewById(R.id.loading_view);
        mTvMessage = (TextView) findViewById(R.id.tv_load_more_message);

        int color1 = ContextCompat.getColor(getContext(), R.color.recycler_swipe_color_loading_color1);
        int color2 = ContextCompat.getColor(getContext(), R.color.recycler_swipe_color_loading_color2);
        int color3 = ContextCompat.getColor(getContext(), R.color.recycler_swipe_color_loading_color3);

        mLoadingView.setCircleColors(color1, color2, color3);

        setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (mLoadMoreListener != null) {
            mLoadMoreListener.onLoadMore();
        }
    }

    @Override
    public void onLoading() {
        setVisibility(VISIBLE);
        mLoadingView.setVisibility(VISIBLE);
        mTvMessage.setVisibility(VISIBLE);
        mTvMessage.setText(R.string.activity_base_content_tips);
    }

    @Override
    public void onLoadFinish(boolean dataEmpty, boolean hasMore) {
        if (hasMore) {
            setVisibility(INVISIBLE);
        } else {
            mLoadingView.setVisibility(GONE);
            mTvMessage.setVisibility(VISIBLE);
            mTvMessage.setText(R.string.recycler_swipe_more_not);
        }
    }

    @Override
    public void onWaitToLoadMore(SwipeMenuRecyclerView.LoadMoreListener loadMoreListener) {
        mLoadMoreListener = loadMoreListener;

        setVisibility(VISIBLE);
        mLoadingView.setVisibility(GONE);
        mTvMessage.setVisibility(VISIBLE);
        mTvMessage.setText(R.string.recycler_swipe_click_load_more);
    }

    @Override
    public void onLoadError(int errorCode, String errorMessage) {
        setVisibility(VISIBLE);
        mLoadingView.setVisibility(GONE);
        mTvMessage.setVisibility(VISIBLE);

        mTvMessage.setText(TextUtils.isEmpty(errorMessage) ? getContext().getString(R.string.recycler_swipe_load_error) : errorMessage);
    }
}
