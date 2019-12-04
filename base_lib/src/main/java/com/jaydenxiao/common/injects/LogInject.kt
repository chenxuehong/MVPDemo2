package com.jaydenxiao.common.injects

import com.jaydenxiao.common.BuildConfig
import com.jaydenxiao.common.commonutils.MyLogger

/**
 * Created by 13198 on 2018/9/27.
 * Log日志
 */

fun <T> T.logi(vararg str: Any?) {
    MyLogger.i(BuildConfig.DEBUG_TAG, getArgsStr(*str))
}

fun <T> T.loge(vararg str: Any?) {
    MyLogger.e(BuildConfig.DEBUG_TAG, getArgsStr(*str))
}

fun <T> T.logw(vararg str: Any?) {
    MyLogger.w(BuildConfig.DEBUG_TAG, getArgsStr(*str))
}

fun <T> T.logv(vararg str: Any?) {
    MyLogger.v(BuildConfig.DEBUG_TAG, getArgsStr(*str))
}

fun <T> T.logd(vararg str: Any?) {
    MyLogger.d(BuildConfig.DEBUG_TAG, getArgsStr(*str))
}