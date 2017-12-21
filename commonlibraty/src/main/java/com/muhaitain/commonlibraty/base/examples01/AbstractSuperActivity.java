package com.muhaitain.commonlibraty.base.examples01;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

/**
 * Created by Muhaitian on 2017/12/20.
 */

public abstract class AbstractSuperActivity<T extends BasePresenter> extends AppCompatActivity implements IBaseView {
    List<T> mBasePresenters;

    protected abstract List<T> createPresenters();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBasePresenters = createPresenters();
        if (mBasePresenters != null && mBasePresenters.size() > 0) {
            for (BasePresenter presenter : mBasePresenters) {
                presenter.attcachView(this);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBasePresenters != null && mBasePresenters.size() > 0) {
            for (BasePresenter presenter : mBasePresenters) {
                presenter.destoty();
            }
        }
        dismissLoading();
    }

    /**
     * 处理网络请求出现的错误，并进行提示
     * @param toast
     */
    @Override
    public void showToast(String toast) {

    }

    @Override
    public void showToast(int strId) {

    }

    /**
     * 网络请求的加载动画显示与隐藏
     * @param tips
     */
    @Override
    public void showLoading(String tips) {

    }

    @Override
    public void dismissLoading() {

    }
}
