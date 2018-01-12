package com.muhaitain.swiperecycletest.adapter

import android.content.Context
import android.view.ViewGroup

import com.muhaitain.commonlibraty.recycleview.hodler.RecycleViewHolder
import com.muhaitain.swiperecycletest.R


/**
 * Created by Muhaitian on 2018/1/10.
 */
class CommonAdapter(var context:Context):BaseAdapter<RecycleViewHolder>(context) {
    lateinit var dataList: Array<String>
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecycleViewHolder {
        return RecycleViewHolder(context,getInflater().inflate(R.layout.recycleview_main_item,parent,false))
    }

    override fun onBindViewHolder(holder: RecycleViewHolder?, position: Int) {
        holder!!.viewHolder.setText(R.id.tv_title,dataList.get(position))
    }

    override fun notifyDataSetChanged(dataList: Array<String>) {
        this.dataList = dataList
        super.notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}