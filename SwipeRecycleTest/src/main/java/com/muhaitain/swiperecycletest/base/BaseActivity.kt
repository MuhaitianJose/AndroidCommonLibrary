package com.muhaitain.swiperecycletest.base


import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity


import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar

import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.muhaitain.commonlibraty.recycleview.divider.DividerItemDecoration
import com.muhaitain.commonlibraty.recycleview.hodler.RecycleViewHolder
import com.muhaitain.commonlibraty.recycleview.widget.SwipeItemClickListener
import com.muhaitain.commonlibraty.recycleview.widget.SwipeMenuRecyclerView
import com.muhaitain.swiperecycletest.LOGD

import com.muhaitain.swiperecycletest.R
import com.muhaitain.swiperecycletest.adapter.BaseAdapter
import com.muhaitain.swiperecycletest.adapter.CommonAdapter
import kotlinx.android.synthetic.main.activity_scroll.*
import kotlinx.android.synthetic.main.toolbar_scroll.*


/**
 * Created by Muhaitian on 2018/1/9.
 */
abstract class BaseActivity : AppCompatActivity(), SwipeItemClickListener {

    protected lateinit var mToolbar:Toolbar
    protected lateinit var mActionBar:ActionBar
    protected lateinit var mSwipeMenuRecyclerView:SwipeMenuRecyclerView
    protected lateinit var layoutManager:RecyclerView.LayoutManager
    protected lateinit var mItemDecoration: RecyclerView.ItemDecoration
    protected  lateinit var mAdapter:BaseAdapter<RecycleViewHolder>
    protected lateinit var mDataList:Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentView())
        initBaseData()

    }

    fun initBaseData(){

        setSupportActionBar(toolbar)
        mSwipeMenuRecyclerView = recycler_view
        mActionBar = supportActionBar as ActionBar
        layoutManager = createLayoutManager()
        mItemDecoration = createItemDecoration()
        mActionBar.setDisplayHomeAsUpEnabled(true)
        mDataList = createDataList()
        mAdapter = createAdapter()
        mSwipeMenuRecyclerView.layoutManager = layoutManager
        mSwipeMenuRecyclerView.setSwipeItemClickListener(this)
        mSwipeMenuRecyclerView.addItemDecoration(mItemDecoration)

    }

    open fun createItemDecoration(): RecyclerView.ItemDecoration {
        return DividerItemDecoration(this,LinearLayoutManager.VERTICAL)
    }

    override fun onItemClick(itemView: View?, position: Int) {
        Toast.makeText(this, "第" + position + "个", Toast.LENGTH_SHORT).show()
    }
    open fun getContentView():Int{
        return R.layout.activity_scroll
    }

    open fun createLayoutManager():RecyclerView.LayoutManager{
        return LinearLayoutManager(this)
    }

     open fun createDataList(): Array<String> {
      return Array(20,{i->("第"+i+"个")})

    }

    open fun createAdapter():BaseAdapter<RecycleViewHolder>{
        return CommonAdapter(this )
    }

    /***
     * Toolbar 返回键 图标点击监听，是R,id,home,结束Activity
     *
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        LOGD(TAG="onOptionsItemSelected" , message = ""+item.itemId.toString())
        if (item.itemId == 16908332) {
            finish()
        }
        return false
    }
}