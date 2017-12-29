package com.muhaitain.androidcommonlibrary.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.muhaitain.androidcommonlibrary.MainActivity
import com.muhaitain.androidcommonlibrary.R
import com.muhaitain.commonlibraty.picasso.PicassoLoader
import kotlinx.android.synthetic.main.activity_recycle_item.view.*

/**
 * Created by Muhaitian on 2017/12/25.
 */

class PicassoAdapter(val imageView:ImageView,val datas: Array<MainActivity.AdapterModule>) : RecyclerView.Adapter<PicassoAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent!!.context).inflate(R.layout.activity_recycle_item, parent, false)
        return ViewHolder(view,imageView)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder!!.onBind(datas[position],position)
    }

    override fun getItemCount(): Int {
        return datas.size
    }


    class ViewHolder(val view: View,val imageView: ImageView) : RecyclerView.ViewHolder(view) {
        fun onBind(module: MainActivity.AdapterModule,position:Int) {
            view.tv_module_name.text = module.name
            if (position==0){
                PicassoLoader.loadImageSimaple(view.context,R.drawable.ic_launcher,view.img_module_img)
            }else if (position==1){
                PicassoLoader.loadImageResize(view.context,R.drawable.ic_launcher,200,200,view.img_module_img)
            }else if (position==2){
                PicassoLoader.loadImageByTransform(view.context,R.drawable.ic_launcher,view.img_module_img, PicassoLoader.CircleTransformation(imageView))
            }


        }
    }
}
