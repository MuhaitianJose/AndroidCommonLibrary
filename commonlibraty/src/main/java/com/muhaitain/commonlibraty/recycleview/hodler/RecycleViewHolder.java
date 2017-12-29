package com.muhaitain.commonlibraty.recycleview.hodler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Muhaitian on 2017/12/27.
 */

public class RecycleViewHolder extends RecyclerView.ViewHolder{
    public ViewHolder viewHolder;
    public RecycleViewHolder(Context context,View itemView) {
        super(itemView);
        viewHolder = ViewHolder.createViewHolder(context,itemView);
    }
}
