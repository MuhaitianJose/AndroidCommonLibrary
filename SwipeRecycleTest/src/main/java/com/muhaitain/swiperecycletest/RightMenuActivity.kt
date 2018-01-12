package com.muhaitain.swiperecycletest

import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import com.muhaitain.commonlibraty.recycleview.widget.*
import com.muhaitain.swiperecycletest.base.BaseActivity
import org.jetbrains.anko.toast

/**
 * Created by Muhaitian on 2018/1/10.
 */
class RightMenuActivity : BaseActivity() {
    val TAG: String = (RightMenuActivity::class.java).simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSwipeMenuRecyclerView.setSwipeMenuCreator(swipeMenuCreator)
        mSwipeMenuRecyclerView.setSwipeMenuItemClickListener(mSwipeMenuItemListener)
        mSwipeMenuRecyclerView.adapter = mAdapter

        mAdapter.notifyDataSetChanged(mDataList)
    }

    private val swipeMenuCreator = object : SwipeMenuCreator {
        override fun onCreateMenu(swipeLeftMenu: SwipeMenu, swipeRightMenu: SwipeMenu, viewType: Int) {
            val width = resources.getDimensionPixelSize(R.dimen.dimen_70)

            // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
            // 2. 指定具体的高，比如80;
            // 3. WRAP_CONTENT，自身高度，不推荐;
            val height = ViewGroup.LayoutParams.MATCH_PARENT

            // 添加左侧的，如果不添加，则左侧不会出现菜单。
//            run {
//                val addItem = SwipeMenuItem(this@RightMenuActivity)
//                        .setMenuDescribe("add")
//                        .setBackground(R.drawable.selector_green)
//                        .setImage(R.mipmap.ic_action_add)
//                        .setWidth(width)
//                        .setHeight(height)
//                swipeLeftMenu.addMenuItem(addItem) // 添加菜单到左侧。
//
//                val closeItem = SwipeMenuItem(this@RightMenuActivity)
//                        .setMenuDescribe("close")
//                        .setBackground(R.drawable.selector_red)
//                        .setImage(R.mipmap.ic_action_close)
//                        .setWidth(width)
//                        .setHeight(height)
//                swipeLeftMenu.addMenuItem(closeItem) // 添加菜单到左侧。
//            }

            // 添加右侧的，如果不添加，则右侧不会出现菜单。
            run {
                val deleteItem = SwipeMenuItem(this@RightMenuActivity)
                        .setMenuDescribe("delete")
                        .setBackground(R.drawable.selector_red)
                        .setImage(R.mipmap.ic_action_delete)
                        .setText("删除")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height)
                swipeRightMenu.addMenuItem(deleteItem)// 添加菜单到右侧。

                val addItem = SwipeMenuItem(this@RightMenuActivity).setMenuDescribe("add")
                        .setBackground(R.drawable.selector_green)
                        .setText("添加")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height)
                swipeRightMenu.addMenuItem(addItem) // 添加菜单到右侧。
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