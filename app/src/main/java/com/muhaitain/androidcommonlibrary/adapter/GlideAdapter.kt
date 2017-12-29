package com.muhaitain.androidcommonlibrary.adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.muhaitain.androidcommonlibrary.MainActivity
import com.muhaitain.androidcommonlibrary.R
import com.muhaitain.commonlibraty.glide.glideloader.ImageLoader
import jp.wasabeef.glide.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.activity_recycle_item.view.*

/**
 * Created by Muhaitian on 2017/12/25.
 */
class GlideAdapter(val modules: Array<MainActivity.AdapterModule>) : RecyclerView.Adapter<GlideAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder!!.onBind(modules[position], position)
    }

    override fun getItemCount(): Int {
        return modules.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): GlideAdapter.ViewHolder {
        var view = LayoutInflater.from(parent!!.context).inflate(R.layout.activity_recycle_item, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun onBind(module: MainActivity.AdapterModule, position: Int) {
            view.tv_module_name.text = module.name
            if (position == 0) {
                ImageLoader.with(view.context).showByResource(R.drawable.ic_launcher, view.img_module_img)
            } else if (position == 1) {
                ImageLoader.with(view.context).setTransformation(CropCircleTransformation()).showByResource(R.drawable.ic_launcher, view.img_module_img)
            } else if (position == 2) {
                Log.d("dsdsdsdsd",position.toString()+"")
                ImageLoader.with(view.context).reSize(100,100).showByResource(R.drawable.ic_launcher, view.img_module_img)
            }

        }
    }

}