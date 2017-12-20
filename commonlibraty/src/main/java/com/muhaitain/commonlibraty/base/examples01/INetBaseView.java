package com.muhaitain.commonlibraty.base.examples01;

import com.muhaitain.commonlibraty.base.examples01.IBaseView;

/**
 * Created by Muhaitian on 2017/12/20.
 */

public interface INetBaseView extends IBaseView {
    void onNetNotConnected();

    void onRequestError(int code, String tips);

    void onRequestSuccess();

    void onRequestEmpty();
}
