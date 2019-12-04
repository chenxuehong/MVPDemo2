package com.jaydenxiao.common.injects

import android.support.annotation.StringRes
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.jaydenxiao.common.R
import com.jaydenxiao.common.ui.LibApplication
import org.jetbrains.annotations.NotNull

/**
 * Created by 13198 on 2018/9/27.
 * Toast
 */


fun showToastBottom(@NotNull msg: String) {
    val sToast = Toast.makeText(LibApplication.baseApplication, msg, Toast.LENGTH_SHORT)
    sToast.show()
}

fun showToastBottom(@StringRes msgResId: Int) {
    val sToast = Toast.makeText(LibApplication.baseApplication, LibApplication.baseApplication?.getString(msgResId), Toast.LENGTH_SHORT)
    sToast.show()
}

fun showToastCenter(@NotNull msg: String) {
    val sToast = Toast.makeText(LibApplication.baseApplication, msg, Toast.LENGTH_SHORT)
    sToast.setGravity(Gravity.CENTER, 0, 0)
    sToast.setText(msg)
    sToast.show()
}

fun showToastCenter(@StringRes msgResId: Int) {
    val sToast = Toast.makeText(LibApplication.baseApplication, LibApplication.baseApplication?.getString(msgResId), Toast.LENGTH_SHORT)
    sToast.setGravity(Gravity.CENTER, 0, 0)
    sToast.setText(LibApplication.baseApplication?.getString(msgResId))
    sToast.show()
}


/**
 * 显示有image的toast
 *
 * @param tvStr
 * @param imageResource
 * @return
 */
fun showToastWithImg(tvStr: String, imageResource: Int) {
    val sToast = Toast.makeText(LibApplication.baseApplication, tvStr, Toast.LENGTH_SHORT)
    val view = LayoutInflater.from(LibApplication.baseApplication).inflate(R.layout.toast_custom, null)
    val tv = view.findViewById<View>(R.id.toast_custom_tv) as TextView
    tv.text = if (TextUtils.isEmpty(tvStr)) "" else tvStr
    val iv = view.findViewById<View>(R.id.toast_custom_iv) as ImageView
    if (imageResource > 0) {
        iv.visibility = View.VISIBLE
        iv.setImageResource(imageResource)
    } else {
        iv.visibility = View.GONE
    }
    sToast.view = view
    sToast.setGravity(Gravity.CENTER, 0, 0)
    sToast.show()
}
