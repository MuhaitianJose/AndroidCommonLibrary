package com.muhaitain.commonlibraty.base.examples01;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Muhaitian on 2017/12/21.
 */

public abstract class AbstractBaseFragment<T extends BasePresenter> extends AbstractSuperFragment<T> implements INetBaseView {

    private Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int viewId = getResLayout();
        View rootView = inflater.inflate(viewId, container, false);
        mUnbinder = ButterKnife.bind(rootView);
        initView();
        initData();
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
            mUnbinder = null;
        }
    }

    @LayoutRes
    protected abstract int getResLayout();

    protected abstract void initView();

    protected abstract void initData();

    @Override
    public void onNetNotConnected() {

    }

    @Override
    public void onRequestError(int code, String tips) {

    }

    @Override
    public void onRequestSuccess() {

    }

    @Override
    public void onRequestEmpty() {

    }
}
