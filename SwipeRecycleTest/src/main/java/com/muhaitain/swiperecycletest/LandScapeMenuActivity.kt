package com.muhaitain.swiperecycletest

import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import com.muhaitain.commonlibraty.recycleview.widget.*
import com.muhaitain.swiperecycletest.base.BaseActivity
import org.jetbrains.anko.toast

/**
 * Created by Muhaitian on 2018/1/10.
 */
class LandScapeMenuActivity:BaseActivity(){

    val TAG:String=(LandScapeMenuActivity::class.java).simpleName
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


            swipeLeftMenu.orientation = SwipeMenu.VERTICAL
            swipeRightMenu.orientation = SwipeMenu.VERTICAL

            run {
                val addItem = SwipeMenuItem(this@LandScapeMenuActivity)
                        .setMenuDescribe("add")
                        .setBackground(R.drawable.selector_green)
                        .setImage(R.mipmap.ic_action_add)
                        .setWidth(width)
                        .setHeight(0)
                        .setWeight(1)
                swipeLeftMenu.addMenuItem(addItem)

                val closeItem = SwipeMenuItem(this@LandScapeMenuActivity)
                        .setMenuDescribe("close")
                        .setBackground(R.drawable.selector_red)
                        .setImage(R.mipmap.ic_action_close)
                        .setWidth(width)
                        .setHeight(0)
                        .setWeight(1)
                swipeLeftMenu.addMenuItem(closeItem)
            }


            run {
                val deleteItem = SwipeMenuItem(this@LandScapeMenuActivity)
                        .setMenuDescribe("delete")
                        .setBackground(R.drawable.selector_red)
                        .setImage(R.mipmap.ic_action_delete)
                        .setText("删除")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(0)
                        .setWeight(1)
                swipeRightMenu.addMenuItem(deleteItem)

                val addItem = SwipeMenuItem(this@LandScapeMenuActivity)
                        .setMenuDescribe("add")
                        .setBackground(R.drawable.selector_green)
                        .setText("添加")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(0)
                        .setWeight(1)
                swipeRightMenu.addMenuItem(addItem)
            }
        }
    }

    private val mSwipeMenuItemListener = object : SwipeMenuItemClickListener {
        override fun onItemClick(menuBridge: SwipeMenuBridge?) {
            LOGD(TAG, "menuBridge!!.menuDescribe==" + menuBridge!!.menuDescribe)
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
}
