package com.jaydenxiao.common.injects

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.view.View
import org.jetbrains.anko.backgroundDrawable

/**
 * Created by 13198 on 2018/9/23.
 */

fun Context.getAsColor(@ColorRes color: Int): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        resources.getColor(color, null)
    } else {
        resources.getColor(color)
    }
}

fun Context.getAsDrawable(@DrawableRes color: Int): Drawable? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        resources.getDrawable(color, null)
    } else {
        resources.getDrawable(color)
    }
}

fun View.setBackgroundBorder(strokeColor: String = "", fillColor: String = "", cornerRadius: Int = dp2px(10), strokeWidth: Int = dp2px(1), cornerRadii: FloatArray? = null) {

    var mStrokeColor = strokeColor
    var mFillColor = fillColor

    val gd = GradientDrawable()//创建drawable

    if (!mFillColor.isNullOrEmpty()) {
        if (!mFillColor.contains("#")) {
            mFillColor.insertStrAtHead("#")
        }
        gd.setColor(Color.parseColor(mFillColor))
    }
    if (cornerRadii != null) {
        gd.cornerRadii = cornerRadii
    } else {
        gd.cornerRadius = cornerRadius.toFloat()
    }

    if (!mStrokeColor.isNullOrEmpty()) {
        if (!strokeColor.contains("#")) {
            mStrokeColor = strokeColor.insertStrAtHead("#")
        }
        gd.setStroke(strokeWidth, Color.parseColor(mStrokeColor))
    }

    if (Build.VERSION.SDK_INT < 16) {
        backgroundDrawable = gd
    } else {
        background = gd
    }
}

val ORIENTATION_TOP_BOTTOM = GradientDrawable.Orientation.TOP_BOTTOM
val ORIENTATION_BOTTOM_TOP = GradientDrawable.Orientation.BOTTOM_TOP
val ORIENTATION_LEFT_RIGHT = GradientDrawable.Orientation.LEFT_RIGHT
val ORIENTATION_RIGHT_LEFT = GradientDrawable.Orientation.RIGHT_LEFT
val ORIENTATION_TR_BL = GradientDrawable.Orientation.TR_BL
val ORIENTATION_BR_TL = GradientDrawable.Orientation.BR_TL
val ORIENTATION_BL_TR = GradientDrawable.Orientation.BL_TR
val ORIENTATION_TL_BR = GradientDrawable.Orientation.TL_BR

fun View.setGradientBackgroundBorder(vararg colors: String, orientation: GradientDrawable.Orientation = ORIENTATION_TOP_BOTTOM, strokeColor: String = "", cornerRadius: Int = dp2px(10), strokeWidth: Int = dp2px(1), cornerRadii: FloatArray? = null) {

    if (colors == null || colors[0].isNullOrEmpty()) {
        return
    }

    var colorsList = IntArray(colors.size)

    for (i in 0..(colors.size - 1)) {

        colorsList[i] = Color.parseColor(if (!colors[i]?.contains("#")) {
            "#${colors[i]!!}"
        } else {
            colors[i]!!
        })
    }
    val gd = GradientDrawable(orientation, colorsList)

    if (cornerRadii != null) {
        gd.cornerRadii = cornerRadii
    } else {
        gd.cornerRadius = cornerRadius.toFloat()
    }
    if (strokeColor?.isNotEmpty()!!) {
        gd.setStroke(strokeWidth, Color.parseColor(if (!strokeColor.contains("#")) {
            strokeColor.insertStrAtHead("#")
        } else {
            strokeColor
        }))
    }

    if (Build.VERSION.SDK_INT < 16) {
        backgroundDrawable = gd
    } else {
        background = gd
    }
}

/***
 * @desc: 将“#*******”String格式的color字符串转成Int类型的color
 */
fun String.parseColor(): Int {
    return Color.parseColor(this)
}


