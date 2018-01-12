package com.muhaitain.swiperecycletest

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.muhaitain.swiperecycletest.base.BaseActivity

class MainActivity : BaseActivity() {

    var TAG: String = (MainActivity::class.java).simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mSwipeMenuRecyclerView.adapter = mAdapter
        mAdapter.notifyDataSetChanged(mDataList)

    }


    override fun onItemClick(itemView: View?, position: Int) {
        when (position) {
            0 -> {
                startActivity(Intent(this, LeftMenuActivity::class.java))

            }
            1 -> {
                startActivity(Intent(this, RightMenuActivity::class.java))
            }
            2 -> {
                startActivity(Intent(this, LeftAndRightMenuActivity::class.java))
            }
            3 -> {
                startActivity(Intent(this, LandScapeMenuActivity::class.java))
            }
            4 -> {
                startActivity(Intent(this, LoadMoreAndRefreshActivity::class.java))
            }
            5 -> {
                startActivity(Intent(this, GridMenuActivity::class.java))
            }
            else -> {
                Log.d(TAG, "do not Action")
            }
        }
    }

    override fun createDataList(): Array<String> {

        return this.resources.getStringArray(R.array.main_item)
    }
}
