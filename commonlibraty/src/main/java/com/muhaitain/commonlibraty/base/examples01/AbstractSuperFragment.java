package com.muhaitain.commonlibraty.base.examples01;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Muhaitian on 2017/12/21.
 */

public abstract class AbstractSuperFragment<T extends BasePresenter> extends Fragment implements IBaseView {


    private List<T> mBasePresenters;
    private Context mActivity;

    protected abstract List<T> creatPresenter();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        mBasePresenters = creatPresenter();
        if (mBasePresenters != null && mBasePresenters.size() > 0) {
            for (BasePresenter presenter : mBasePresenters) {
                presenter.attcachView(this);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBasePresenters != null && mBasePresenters.size() > 0) {
            for (BasePresenter presenter : mBasePresenters) {
                presenter.destoty();
            }
        }
        dismissLoading();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void showToast(String toast) {

    }

    @Override
    public void showToast(int strId) {

    }

    @Override
    public void showLoading(String tips) {

    }

    @Override
    public void dismissLoading() {

    }
}
