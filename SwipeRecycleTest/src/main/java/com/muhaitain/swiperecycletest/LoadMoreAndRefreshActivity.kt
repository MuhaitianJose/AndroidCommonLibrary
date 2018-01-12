package com.muhaitain.swiperecycletest

import android.os.Bundle
import com.muhaitain.swiperecycletest.base.BaseActivity

/**
 * Created by Muhaitian on 2018/1/10.
 */
class LoadMoreAndRefreshActivity:BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSwipeMenuRecyclerView.adapter = mAdapter
        mAdapter.notifyDataSetChanged(mDataList)
    }
}