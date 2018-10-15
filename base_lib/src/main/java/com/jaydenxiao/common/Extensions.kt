package com.jaydenxiao.common

import android.content.Context
import android.support.annotation.StringRes
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.jaydenxiao.common.base.LibApplication
import org.greenrobot.eventbus.EventBus
import org.jetbrains.annotations.NotNull


fun showToastBottom(@NotNull msg: String) {
    val sToast = Toast.makeText(LibApplication.appContext, msg, Toast.LENGTH_SHORT)
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
    val sToast = Toast.makeText(LibApplication.appContext, tvStr, Toast.LENGTH_SHORT)
    val view = LayoutInflater.from(LibApplication.appContext).inflate(R.layout.toast_custom, null)
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

fun showToastBottom(@StringRes msgResId: Int) {
    val sToast = Toast.makeText(LibApplication.appContext, LibApplication.appContext?.resources?.getString(msgResId), Toast.LENGTH_SHORT)
    sToast.show()
}

fun showToastCenter(@NotNull msg: String) {
    val sToast = Toast.makeText(LibApplication.appContext, msg, Toast.LENGTH_SHORT)
    sToast.setGravity(Gravity.CENTER, 0, 0)
    sToast.setText(msg)
    sToast.show()
}

fun showToastCenter(@StringRes msgResId: Int) {
    val sToast = Toast.makeText(LibApplication.appContext, LibApplication.appContext?.resources?.getString(msgResId), Toast.LENGTH_SHORT)
    sToast.setGravity(Gravity.CENTER, 0, 0)
    sToast.setText(LibApplication.appContext?.resources?.getString(msgResId))
    sToast.show()
    sToast.show()
}

fun register(@NotNull obj: Any) {
    if (!EventBus.getDefault().isRegistered(obj)) {
        EventBus.getDefault().register(obj)
    }
}

fun unregister(@NotNull obj: Any) {
    if (EventBus.getDefault().isRegistered(obj)) {
        EventBus.getDefault().unregister(obj)
    }
}

fun sendEvent(@NotNull obj: Any) {
    EventBus.getDefault().post(obj)
}

fun TextView.stringTrim(): String {
    return this.text.toString().trim()
}


/**
 * dp转px
 */
fun View.dp2px(dipValue: Float): Float {
    return (dipValue * this.resources.displayMetrics.density + 0.5f)
}

/**
 * px转dp
 */
fun View.px2dp(pxValue: Float): Float {
    return (pxValue / this.resources.displayMetrics.density + 0.5f)
}

/**
 * sp转px
 */
fun Context.sp2px(spValue: Float): Float {
    return (spValue * this.resources.displayMetrics.scaledDensity + 0.5f)
}

fun Int.inflate(): View {
    return View.inflate(LibApplication.appContext,this,null)
}
