package com.muhaitain.commonlibraty.okhttp.bean;

import java.io.Serializable;

/**
 * Created by Muhaitian on 2017/12/19.
 */

public class ServerTips implements Serializable {
    public int error_code;
    public String error_msg;

    public ServerTips() {
    }

    public ServerTips(int code, String tip) {
        this.error_code = code;
        this.error_msg = tip;
    }
}
