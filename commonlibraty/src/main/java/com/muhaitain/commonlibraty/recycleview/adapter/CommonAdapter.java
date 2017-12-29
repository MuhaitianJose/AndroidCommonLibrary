package com.muhaitain.commonlibraty.recycleview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.muhaitain.commonlibraty.recycleview.hodler.RecycleViewHolder;
import com.muhaitain.commonlibraty.recycleview.listener.RecycleViewListener;

import java.util.List;

/**
 * Created by Muhaitian on 2017/12/27.
 */

public class CommonAdapter<T> extends RecyclerView.Adapter<RecycleViewHolder> {

    protected List<T> datas;
    private int mItemLayoutRes;
    private RecycleViewListener<T> mRecycleViewListener;

    public CommonAdapter(List<T> datas, int mItemLayoutRes, RecycleViewListener mRecycleViewListener) {
        this.datas = datas;
        this.mItemLayoutRes = mItemLayoutRes;
        this.mRecycleViewListener = mRecycleViewListener;
    }

    @Override
    public RecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mItemLayoutRes, parent, false);
        RecycleViewHolder holder = new RecycleViewHolder(parent.getContext(), view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecycleViewHolder holder, int position) {
        final T data = datas.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRecycleViewListener != null) {
                    mRecycleViewListener.onClickItem(data, v);
                }
            }
        });

        if (mRecycleViewListener != null) {
            mRecycleViewListener.onConvertView(data, holder.viewHolder, datas, position);
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
