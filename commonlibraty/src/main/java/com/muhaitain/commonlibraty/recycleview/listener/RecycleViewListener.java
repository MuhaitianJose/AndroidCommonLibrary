package com.muhaitain.commonlibraty.recycleview.listener;

import android.view.View;

import com.muhaitain.commonlibraty.recycleview.hodler.ViewHolder;

import java.util.List;

/**
 * Created by Muhaitian on 2017/12/27.
 */

public interface RecycleViewListener<T> {
    void onClickItem(T data,View item);
    void onConvertView(T data, ViewHolder viewHolder, List<T> datas,int position);
}
