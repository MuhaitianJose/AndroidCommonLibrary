package com.muhaitain.androidcommonlibrary

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import android.support.v7.widget.LinearLayoutManager
import com.muhaitain.androidcommonlibrary.adapter.GlideAdapter
import com.muhaitain.androidcommonlibrary.adapter.PicassoAdapter
import com.muhaitain.commonlibraty.recycleview.divider.DividerItemDecoration
import kotlinx.android.synthetic.main.activity_main.*;

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initGlideRecycleView()
        initPacissoRecycleView()
    }

    fun initGlideRecycleView() {
        var data = arrayOf<AdapterModule>(AdapterModule("muhaitian", 20)
                , AdapterModule("lizihai", 20)
                , AdapterModule("wangkang",18))
        rv_glide_loader.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_glide_loader.adapter = GlideAdapter(data)

        rv_glide_loader.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
    }

    fun initPacissoRecycleView() {
        var data = arrayOf<AdapterModule>(AdapterModule("Jose", 12)
                , AdapterModule("Jack", 12)
                , AdapterModule("Mike", 12))
        rv_picasso_loader.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        rv_picasso_loader.adapter = PicassoAdapter(imag_content,data)
    }

    data class AdapterModule(var name: String, var age: Int)


}
