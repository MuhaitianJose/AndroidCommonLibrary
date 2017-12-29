package com.muhaitain.commonlibraty.recycleview.touch;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Muhaitian on 2017/12/29.
 */

public interface OnItemMoveListener {
    /**
     * 当拖拽时回调
     *
     * @param srcHodler    源Item
     * @param targetHodler 目标Item
     * @return
     */
    boolean onItemMove(RecyclerView.ViewHolder srcHodler, RecyclerView.ViewHolder targetHodler);

    /**
     * 删除回调
     *
     * @param srcHodler
     */
    void onItemDismiss(RecyclerView.ViewHolder srcHodler);
}
