package com.jaydenxiao.common.injects

import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatDialog
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import com.jaydenxiao.common.R
import com.jaydenxiao.common.ui.activity.BaseActivity

/**
 * @desc 创建普通对话框
 * @author cxh
 */
fun View.createDialog(w: Int = wrapContent, h: Int = wrapContent): AppCompatDialog {
    val dialog = AppCompatDialog(context, R.style.showPopupAnimation)
    dialog.setContentView(this)
//    dialog.show()
    val lp = dialog.window.attributes as android.view.WindowManager.LayoutParams
    lp.width = w
    lp.height = h
    dialog.window.attributes = lp
    return dialog
}

fun View.createAnimDialog(w: Int = wrapContent, h: Int = wrapContent, anim: Int = R.style.showPopupAnimation, call: (v: View) -> Unit): AppCompatDialog {
    val dialog = AppCompatDialog(context, R.style.CustomDialog)
    dialog.setContentView(this)
    dialog.window.setWindowAnimations(anim)
//    dialog.show()
    val lp = dialog.window.attributes as android.view.WindowManager.LayoutParams
    lp.width = w
    lp.height = h
    dialog.window.attributes = lp
    return dialog
}

/**
 * @author cxh
 * @desc：从底部弹出对话框
 */
fun View.showPopupWindow(h: Int = wrapContent, call: (v: View) -> Unit): PopupWindow {
    val rootview = (context as BaseActivity).findViewById<ViewGroup>(android.R.id.content)
    var heig = h
    val popwindow = PopupWindow(this, context.width, heig).apply {
        animationStyle = R.style.showPopupAnimationDailogStyle
        isFocusable = true
        setBackgroundDrawable(ColorDrawable(context.getAsColor(R.color.shadow)))
        setOnDismissListener {
            this@showPopupWindow.animation(0.6f, 1f, end = {
                this@showPopupWindow.setBackgroundResource(R.color.trans)
                if (this@showPopupWindow.parent != null) {
                    (this@showPopupWindow.parent as ViewGroup).setBackgroundResource(R.color.trans)
                }
            }) {
                val lp = (context as BaseActivity).window
                        .attributes
                lp.alpha = it
                (context as BaseActivity).window.attributes = lp
            }

        }
    }.apply { showAtLocation(rootview, Gravity.BOTTOM, 0, 0) }
    this@showPopupWindow.animation(1f, 0.6f) {
        val lp = (context as BaseActivity).window
                .attributes
        lp.alpha = it
        (context as BaseActivity).window.attributes = lp
    }
    return popwindow
}