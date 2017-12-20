package com.muhaitain.commonlibraty.okhttp.base;

/**
 * Created by Muhaitian on 2017/12/18.
 */

interface IExcutor<T> {
    void post();
    void get();
    void delete();
    void update();
}
