package com.muhaitain.swiperecycletest


import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.muhaitain.swiperecycletest.base.BaseActivity
import java.io.File

/**
 * Created by Muhaitian on 2018/1/11.
 */
/**
 *
 * 扩展函数放在kotlin文件中，不能放在任何类中不然无法调用
 *
 */

fun AppCompatActivity.LOGD(TAG: String, message: String) {
    Log.d(TAG, message)
}

 fun AppCompatActivity.LOGE(TAG: String, message: String) {
    Log.e(TAG, message)
}

 fun AppCompatActivity.LOGI(TAG: String, message: String) {
    Log.i(TAG, message)
}

fun AppCompatActivity.LOGW(TAG: String, message: String) {
    Log.w(TAG, message)
}
