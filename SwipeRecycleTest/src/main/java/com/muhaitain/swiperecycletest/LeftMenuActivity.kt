package com.muhaitain.swiperecycletest

import android.os.Bundle
import android.view.ViewGroup
import com.muhaitain.commonlibraty.recycleview.widget.SwipeMenu
import com.muhaitain.commonlibraty.recycleview.widget.SwipeMenuCreator
import com.muhaitain.commonlibraty.recycleview.widget.SwipeMenuItem
import com.muhaitain.commonlibraty.recycleview.widget.SwipeMenuItemClickListener
import com.muhaitain.swiperecycletest.base.BaseActivity
import org.jetbrains.anko.toast

/**
 * Created by Muhaitian on 2018/1/10.
 */
class LeftMenuActivity : BaseActivity() {

    val TAG = (LeftMenuActivity::class.java).simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSwipeMenuRecyclerView.setSwipeMenuItemClickListener(mSwipeMenuItemListener)
        mSwipeMenuRecyclerView.setSwipeMenuCreator(swipeMenuCreator)
        mSwipeMenuRecyclerView.adapter = mAdapter
        mAdapter.notifyDataSetChanged(mDataList)


    }

    private val swipeMenuCreator = object : SwipeMenuCreator {
        override fun onCreateMenu(swipeLeftMenu: SwipeMenu, swipeRightMenu: SwipeMenu, viewType: Int) {
            val width = resources.getDimensionPixelSize(R.dimen.dimen_70)

            val height = ViewGroup.LayoutParams.MATCH_PARENT


            run {
                val addItem = SwipeMenuItem(this@LeftMenuActivity)
                        .setMenuDescribe("add")
                        .setBackground(R.drawable.selector_green)
                        .setImage(R.mipmap.ic_action_add)
                        .setWidth(width)
                        .setHeight(height)
                swipeLeftMenu.addMenuItem(addItem)

                val closeItem = SwipeMenuItem(this@LeftMenuActivity)
                        .setMenuDescribe("close")
                        .setBackground(R.drawable.selector_red)
                        .setImage(R.mipmap.ic_action_close)
                        .setWidth(width)
                        .setHeight(height)
                swipeLeftMenu.addMenuItem(closeItem)
            }


        }
    }

    private val mSwipeMenuItemListener = SwipeMenuItemClickListener { menuBridge ->

        when (menuBridge!!.menuDescribe) {
            "delete" -> {
                LOGD(TAG, "delete")
                toast("delete")
            }
            "add" -> {
                LOGD(TAG, "add")
                toast("add")
            }
            "close" -> {
                LOGD(TAG, "close")
                toast("close")
            }
            else -> {
                LOGD(TAG, "nothing")
                toast("nothing")
            }
        }
    }


}