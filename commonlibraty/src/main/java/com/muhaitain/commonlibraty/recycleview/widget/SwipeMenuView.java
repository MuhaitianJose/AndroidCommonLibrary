package com.muhaitain.commonlibraty.recycleview.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Muhaitian on 2018/1/4.
 */

public class SwipeMenuView extends LinearLayout implements View.OnClickListener {

    private static final String TAG = SwipeMenuView.class.getSimpleName();
    private RecyclerView.ViewHolder mAdatterViewHolder;
    private SwipeSwitch mSwipeSwitch;
    private SwipeMenuItemClickListener mItemClickListener;

    @SwipeMenuRecyclerView.DirectionMode
    private int mDirection;

    public SwipeMenuView(Context context) {
        super(context);
    }

    public SwipeMenuView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SwipeMenuView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void createMenu(SwipeMenu swipeMenu, SwipeSwitch swipeSwitch,
                           SwipeMenuItemClickListener swipeMenuItemClickListener,
                           @SwipeMenuRecyclerView.DirectionMode int direction) {
        removeAllViews();
        this.mSwipeSwitch = swipeSwitch;
        this.mItemClickListener = swipeMenuItemClickListener;
        this.mDirection = direction;
        List<SwipeMenuItem> items = swipeMenu.getSwipeMenuItems();
        for (int i = 0; i < items.size(); i++) {
            SwipeMenuItem item = items.get(i);

            LayoutParams params = new LayoutParams(item.getWidth(), item.getHeight());
            params.weight = item.getWeight();

            LinearLayout parent = new LinearLayout(getContext());
            parent.setId(i);
            parent.setGravity(Gravity.CENTER);
            parent.setOrientation(VERTICAL);
            parent.setLayoutParams(params);

            ViewCompat.setBackground(parent, item.getBackground());
            parent.setOnClickListener(this);
            addView(parent);

            SwipeMenuBridge menuBridge = new SwipeMenuBridge(mDirection, i, mSwipeSwitch, parent);
            parent.setTag(menuBridge);
            menuBridge.setMenuDescribe(item.getMenuDescribe());
            if (item.getIcon() != 0) {
                ImageView iv = createIcon(item);
                menuBridge.mImageView = iv;
                parent.addView(iv);
            }

            if (!TextUtils.isEmpty(item.getTitle())) {
                TextView tv = createTitle(item);
                menuBridge.mTextView = tv;

                parent.addView(tv);
            }

        }
    }

    public void bindViewHolder(RecyclerView.ViewHolder adapterViewHolder) {
        this.mAdatterViewHolder = adapterViewHolder;
    }

    private ImageView createIcon(SwipeMenuItem item) {
        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(item.getIcon());
        return imageView;
    }

    private TextView createTitle(SwipeMenuItem item) {
        TextView textView = new TextView(getContext());
        textView.setText(item.getTitle());
        textView.setGravity(Gravity.CENTER);
        int textSize = item.getTitleSize();
        if (textSize > 0) {
            textView.setTextSize(textSize);
        }

        ColorStateList textColor = item.getTitleColor();
        if (textColor != null) {
            textView.setTextColor(textColor);
        }
        int textAppearance = item.getTextAppearance();
        if (textAppearance != 0) {
            TextViewCompat.setTextAppearance(textView, textAppearance);
        }
        Typeface typeface = item.getTextTypeface();
        if (typeface != null) {
            textView.setTypeface(typeface);
        }

        return textView;
    }

    @Override
    public void onClick(View v) {
        if (mItemClickListener != null && mSwipeSwitch.isMenuOpen()) {
            SwipeMenuBridge menuBridge = (SwipeMenuBridge) v.getTag();
            menuBridge.mAdapterPosition = mAdatterViewHolder.getAdapterPosition();

            mItemClickListener.onItemClick(menuBridge);
        }
    }
}
