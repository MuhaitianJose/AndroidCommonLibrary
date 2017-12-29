package com.muhaitain.commonlibraty.base.examples01;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.ViewStub;

import com.muhaitain.commonlibraty.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Muhaitian on 2017/12/21.
 */

public abstract class AbstractBaseActivity<T extends BasePresenter> extends AbstractSuperActivity<T> {

    ViewStub vsBaseTitle;
    ViewStub vsBaseContent;
    ViewStub vsBaseNetwork;
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        initViewSub();
        mUnbinder = ButterKnife.bind(this);
    }

    private void initViewSub() {
        vsBaseTitle = (ViewStub) findViewById(R.id.vs_base_title);
        vsBaseContent = (ViewStub) findViewById(R.id.vs_base_content);
        vsBaseNetwork = (ViewStub) findViewById(R.id.vs_base_network);

        int titleId = getTitleLayout();
        int contentId = getContentLayout();
        int netWorkId = getNetLayout();

        if (titleId <= 0) {
            titleId = R.layout.activity_base_title;
        }
        if (contentId <= 0) {
            contentId = R.layout.activity_base_content;
        }
        if (netWorkId <= 0) {
            netWorkId = R.layout.activity_base_network;
        }
        if (isNeededTitleLayout()) {
            vsBaseTitle.setLayoutResource(titleId);
        }

        vsBaseContent.setLayoutResource(contentId);
        vsBaseNetwork.setLayoutResource(netWorkId);

    }

    protected abstract boolean isNeededTitleLayout();

    protected abstract void initView();

    protected abstract void initActionBar();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
            mUnbinder = null;
        }
    }

    @LayoutRes
    protected abstract int getTitleLayout();

    @LayoutRes
    protected abstract int getContentLayout();

    @LayoutRes
    protected abstract int getNetLayout();

}
