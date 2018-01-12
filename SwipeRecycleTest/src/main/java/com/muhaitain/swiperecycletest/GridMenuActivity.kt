package com.muhaitain.swiperecycletest

import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.muhaitain.commonlibraty.recycleview.divider.DividerGridItemDecoration
import com.muhaitain.commonlibraty.recycleview.widget.*
import com.muhaitain.swiperecycletest.base.BaseActivity
import org.jetbrains.anko.toast

/**
 * Created by Muhaitian on 2018/1/10.
 */
class GridMenuActivity : BaseActivity() {
    val TAG:String=(GridMenuActivity::class.java).simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSwipeMenuRecyclerView.setSwipeMenuCreator(swipeMenuCreator)
        mSwipeMenuRecyclerView.setSwipeMenuItemClickListener(mSwipeMenuItemListener)
        mSwipeMenuRecyclerView.adapter = mAdapter
        mAdapter.notifyDataSetChanged(mDataList)

    }

    override fun createLayoutManager(): RecyclerView.LayoutManager {
        return GridLayoutManager(this,2)
    }

    override fun createItemDecoration(): RecyclerView.ItemDecoration {
        return DividerGridItemDecoration(this)
    }
    private val swipeMenuCreator = object : SwipeMenuCreator {
        override fun onCreateMenu(swipeLeftMenu: SwipeMenu, swipeRightMenu: SwipeMenu, viewType: Int) {
            val width = resources.getDimensionPixelSize(R.dimen.dimen_70)


            val height = ViewGroup.LayoutParams.MATCH_PARENT


            run {
                val addItem = SwipeMenuItem(this@GridMenuActivity)
                        .setMenuDescribe("add")
                        .setBackground(R.drawable.selector_green)
                        .setImage(R.mipmap.ic_action_add)
                        .setWidth(width)
                        .setHeight(height)
                swipeLeftMenu.addMenuItem(addItem)

                val closeItem = SwipeMenuItem(this@GridMenuActivity)
                        .setMenuDescribe("close")
                        .setBackground(R.drawable.selector_red)
                        .setImage(R.mipmap.ic_action_close)
                        .setWidth(width)
                        .setHeight(height)
                swipeLeftMenu.addMenuItem(closeItem)
            }


            run {
                val deleteItem = SwipeMenuItem(this@GridMenuActivity)
                        .setMenuDescribe("delete")
                        .setBackground(R.drawable.selector_red)
                        .setImage(R.mipmap.ic_action_delete)
                        .setText("删除")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height)
                swipeRightMenu.addMenuItem(deleteItem)

                val addItem = SwipeMenuItem(this@GridMenuActivity).setMenuDescribe("add")
                        .setBackground(R.drawable.selector_green)
                        .setText("添加")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height)
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