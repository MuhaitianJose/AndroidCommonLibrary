package com.muhaitain.commonlibraty.base.examples01;

import android.support.annotation.StringRes;

/**
 * Created by Muhaitian on 2017/12/20.
 */

public interface IBaseView {

    void showToast(String toast);

    void showToast(@StringRes int strId);

    void showLoading(String tips);

    void dismissLoading();
}
