package com.jaydenxiao.common.injects

import android.content.Context
import android.view.View

/**
 * Created by 13198 on 2018/9/23.
 * @desc 屏幕适配相关操作
 */


/**
 * dp转px
 */
fun View.dp2px(dipValue: Int): Int {
    return ((dipValue * this.resources.displayMetrics.density + 0.5f).toInt())
}

/**
 * px转dp
 */
fun View.px2dp(pxValue: Int): Int {
    return ((pxValue / this.resources.displayMetrics.density + 0.5f).toInt())
}

/**
 * @desc sp转px
 */
fun View.sp2px(spValue: Int): Int {
    return ((spValue * this.resources.displayMetrics.scaledDensity + 0.5f).toInt())
}

/**
 * @desc 屏幕宽度
 */
var Context.width: Int
    set(value) = Unit
    get() {
        return resources.displayMetrics.widthPixels
    }

/**
 * @desc 屏幕高度
 */
var Context.height: Int
    set(value) = Unit
    get() {
        return resources.displayMetrics.heightPixels
    }