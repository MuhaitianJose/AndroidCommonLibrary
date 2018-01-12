package com.muhaitain.swiperecycletest.adapter

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Created by Muhaitian on 2018/1/10.
 */
abstract class BaseAdapter<VH : RecyclerView.ViewHolder>() : RecyclerView.Adapter<VH>() {
    /** 集合类型-数组
     * 1.有序可重复-Array索引从0开始
     * 2.无序不重复-set
     * 3.无序可重复-Map,但值有唯一的键
     * 4.List<E>接口继承于Collection<E>接口，元素以线性方式存储，集合中可以存放重复对象。
     * 5.MutableList<E>接口继承于List<E>，MutableCollection&ltE>，是对只读集合的扩展，增加了了对集合的添加及删除元素的操
     * */
    lateinit var mInflater: LayoutInflater

    constructor(context: Context) : this() {
        mInflater = LayoutInflater.from(context)
    }

    fun getInflater(): LayoutInflater {
        return mInflater
    }

    abstract fun notifyDataSetChanged(dataList: Array<String>)

}