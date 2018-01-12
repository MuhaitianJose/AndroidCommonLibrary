package com.muhaitain.commonlibraty.recycleview.adapter;

import android.content.Context;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.muhaitain.commonlibraty.R;
import com.muhaitain.commonlibraty.recycleview.widget.SwipeItemClickListener;
import com.muhaitain.commonlibraty.recycleview.widget.SwipeMenu;
import com.muhaitain.commonlibraty.recycleview.widget.SwipeMenuCreator;
import com.muhaitain.commonlibraty.recycleview.widget.SwipeMenuItemClickListener;
import com.muhaitain.commonlibraty.recycleview.widget.SwipeMenuLayout;
import com.muhaitain.commonlibraty.recycleview.widget.SwipeMenuRecyclerView;
import com.muhaitain.commonlibraty.recycleview.widget.SwipeMenuView;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Muhaitian on 2018/1/4.
 */

public class SwipeAdapterWrapper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int BASE_ITEM_TYPE_HEADER = 10000;
    private static final int BASE_ITEM_TYPE_FOOTER = 20000;
    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    private SparseArrayCompat<View> mFootViews = new SparseArrayCompat<>();

    private RecyclerView.Adapter mAdapter;
    private LayoutInflater mInflater;

    private SwipeMenuCreator mSwipeMenuCreator;
    private SwipeMenuItemClickListener mSwipeMenuItemClickListener;
    private SwipeItemClickListener mSwipeItemClickListener;

    public SwipeAdapterWrapper(Context context,RecyclerView.Adapter mAdapter) {
        this.mAdapter = mAdapter;
        this.mInflater = LayoutInflater.from(context);
    }

    public RecyclerView.Adapter getOriginAdapter() {
        return mAdapter;
    }

    public void setSwipeMenuCreator(SwipeMenuCreator swipeMenuCreator) {
        this.mSwipeMenuCreator = swipeMenuCreator;
    }

    public void setSwipeMenuItemClickListener(SwipeMenuItemClickListener menuItemClickListener) {
        this.mSwipeMenuItemClickListener = menuItemClickListener;
    }

    public void setSwipeItemClickListener(SwipeItemClickListener swipeItemClickListener) {
        this.mSwipeItemClickListener = swipeItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderViews.get(viewType) != null) {
            return new ViewHolder(mHeaderViews.get(viewType));
        } else if (mFootViews.get(viewType) != null) {
            return new ViewHolder(mFootViews.get(viewType));
        }

        final RecyclerView.ViewHolder viewHolder = mAdapter.onCreateViewHolder(parent, viewType);

        if (mSwipeItemClickListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSwipeItemClickListener.onItemClick(v, viewHolder.getAdapterPosition());
                }
            });
        }

        if (mSwipeMenuCreator == null) {
            return viewHolder;
        }

        final SwipeMenuLayout swipeMenuLayout = (SwipeMenuLayout) mInflater.inflate(R.layout.recycler_swipe_view_item, parent, false);
        SwipeMenu swipeLeftMenu = new SwipeMenu(swipeMenuLayout, viewType);
        SwipeMenu swipeRightMenu = new SwipeMenu(swipeMenuLayout, viewType);

        mSwipeMenuCreator.onCreateMenu(swipeLeftMenu, swipeRightMenu, viewType);
        int leftCount = swipeLeftMenu.getSwipeMenuItems().size();
        if (leftCount > 0) {
            SwipeMenuView swipeLeftMenuView = swipeMenuLayout.findViewById(R.id.swipe_left);
            swipeLeftMenuView.setOrientation(swipeLeftMenu.getOrientation());
            swipeLeftMenuView.createMenu(swipeLeftMenu, swipeMenuLayout, mSwipeMenuItemClickListener, SwipeMenuRecyclerView.LEFT_DIRECTION);
        }
        int rightCount = swipeRightMenu.getSwipeMenuItems().size();
        if (rightCount > 0) {
            SwipeMenuView swipeMenuRightView = swipeMenuLayout.findViewById(R.id.swipe_right);
            swipeMenuRightView.setOrientation(swipeRightMenu.getOrientation());
            swipeMenuRightView.createMenu(swipeRightMenu, swipeMenuLayout, mSwipeMenuItemClickListener, SwipeMenuRecyclerView.RIGHT_DIRECTION);
        }

        ViewGroup viewGroup = swipeMenuLayout.findViewById(R.id.swipe_content);
        viewGroup.addView(viewHolder.itemView);
        try {
            Field itemView = getSupperClass(viewHolder.getClass()).getDeclaredField("itemView");
            if (!itemView.isAccessible()) {
                itemView.setAccessible(true);
            }
            itemView.set(viewHolder, swipeMenuLayout);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return viewHolder;
    }

    private Class<?> getSupperClass(Class<?> aClass) {
        Class<?> supperClass = aClass.getSuperclass();
        if (supperClass != null && !supperClass.equals(Object.class)) {
            return getSupperClass(supperClass);
        }

        return aClass;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {

        if (isHeaderView(position) || isFooterView(position)) {
            return;
        }
        View itemView = holder.itemView;
        if (itemView instanceof SwipeMenuLayout) {
            SwipeMenuLayout swipeMenuLayout = (SwipeMenuLayout) itemView;
            int childCount = swipeMenuLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childView = swipeMenuLayout.getChildAt(i);
                if (childView instanceof SwipeMenuView) {
                    ((SwipeMenuView) childView).bindViewHolder(holder);
                }
            }
        }
        mAdapter.onBindViewHolder(holder, position - getHeaderItemCount(), payloads);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        mAdapter.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        int position = holder.getAdapterPosition();

        if (isHeaderView(position) || isFooterView(position)) {
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        } else {
            mAdapter.onViewAttachedToWindow(holder);
        }
    }

    @Override
    public int getItemCount() {
        return getHeaderItemCount() + getContentItemCount() + getFooterItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderView(position)) {
            return mHeaderViews.keyAt(position);
        } else if (isFooterView(position)) {
            return mFootViews.keyAt(position - getHeaderItemCount() - getContentItemCount());
        }
        return mAdapter.getItemViewType(position - getHeaderItemCount());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public boolean isHeaderView(int position) {
        return position >= 0 && position < getHeaderItemCount();
    }

    public boolean isFooterView(int position) {
        return position >= getHeaderItemCount() + getContentItemCount();
    }

    private int getContentItemCount() {
        return mAdapter.getItemCount();
    }

    public int getHeaderItemCount() {
        return mHeaderViews.size();
    }

    public int getFooterItemCount() {
        return mFootViews.size();
    }

    public void addHeaderView(View view) {
        mHeaderViews.put(getHeaderItemCount() + BASE_ITEM_TYPE_HEADER, view);
    }

    public void addHeaderViewAndNotify(View view) {
        mHeaderViews.put(getHeaderItemCount() + BASE_ITEM_TYPE_HEADER, view);
        notifyItemInserted(getHeaderItemCount() - 1);
    }

    public void removeHeaderViewAndNotify(View view) {
        int headerIndex = mHeaderViews.indexOfValue(view);
        mHeaderViews.removeAt(headerIndex);
        notifyItemRemoved(headerIndex);
    }

    public void addFooterView(View view) {
        mFootViews.put(getFooterItemCount() + BASE_ITEM_TYPE_FOOTER, view);
    }

    public void addFooterViewAndNotify(View view) {
        mFootViews.put(getFooterItemCount() + BASE_ITEM_TYPE_FOOTER, view);
        notifyItemInserted(getHeaderItemCount() + getContentItemCount() + getFooterItemCount());
    }

    public void removeFooterViewAndNotify(View view){
        int footerIndex = mFootViews.indexOfValue(view);
        mFootViews.removeAt(footerIndex);
        notifyItemRemoved(getHeaderItemCount()+getContentItemCount()+footerIndex);
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        mAdapter.setHasStableIds(hasStableIds);
    }

    @Override
    public long getItemId(int position) {
        if (!isHeaderView(position) || !isFooterView(position)) {
            return mAdapter.getItemId(position);
        }
        return super.getItemId(position);
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        int position = holder.getAdapterPosition();
        if (!isHeaderView(position) || !isFooterView(position)) {
            mAdapter.onViewRecycled(holder);
        }
    }

    @Override
    public boolean onFailedToRecycleView(RecyclerView.ViewHolder holder) {
        int position = holder.getAdapterPosition();
        if (!isHeaderView(position) || !isFooterView(position)) {
            return mAdapter.onFailedToRecycleView(holder);
        }
        return false;
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        int position = holder.getAdapterPosition();
        if (!isHeaderView(position) || isFooterView(position)) {
            mAdapter.onViewDetachedFromWindow(holder);
        }
    }

    @Override
    public void registerAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        super.registerAdapterDataObserver(observer);
    }

    @Override
    public void unregisterAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        super.unregisterAdapterDataObserver(observer);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        mAdapter.onDetachedFromRecyclerView(recyclerView);
    }
}
