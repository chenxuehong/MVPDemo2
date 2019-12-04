package com.jaydenxiao.common.injects

import android.content.Context
import android.graphics.Paint
import android.graphics.Typeface
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.jaydenxiao.common.R
import com.jaydenxiao.common.commonutils.AppManager
import com.jaydenxiao.common.commonutils.GlideImageLoader.GlideImageLoader
import com.jaydenxiao.common.ui.view.address.CityPicker
import kotlinx.android.synthetic.main.select_address.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.lang.Exception
import java.lang.reflect.Field
import java.util.*

/**
 * @author cxh
 * @desc View的相关操作
 * Created by 13198 on 2018/9/23.
 */

val matchParent: Int = android.view.ViewGroup.LayoutParams.MATCH_PARENT
val wrapContent: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT

/**
 * @desc 填充布局转成view
 */
fun Context.inflate(call: () -> Int): View {
    return View.inflate(this, call.invoke(), null)
}

/**
 * #desc 展示图片
 */
fun <P, V : View> V.loadImage(path: P, radius: Int = 0) {

    if (radius > 0) {
        GlideImageLoader.displayCilce(AppManager.getInstance().currentActivity(), this as ImageView, path)
    } else if (radius < 0) {
        GlideImageLoader.displayRound(AppManager.getInstance().currentActivity(), this as ImageView, path)
    } else {
        GlideImageLoader.display(AppManager.getInstance().currentActivity(), this as ImageView, path)
    }
}

/**
 * @desc 弹出地址选择器对话框
 */
fun Context.showAddressSelect(call: CityPicker.OnSelectingListener) {
    inflate { R.layout.select_address }.apply {
        val dialog = showPopupWindow(dp2px(400)) { }
        confirm.onClick {
            call.selected(true, city_select_dialog.getprovince_name(), city_select_dialog.getcity_name(), city_select_dialog.getcouny_name(), city_select_dialog.getcity_code())
            dialog.dismiss()
        }
        cancel.onClick { dialog.dismiss() }
    }
}

/*==================================== TextView =================================================*/
/**
 * @desc 在头部添加人民币金额符号
 */
fun TextView.rmb() {
    text = "¥$text"
}

/**
 * @desc 在头部添加"#"
 */
fun TextView.oct() {
    text = "#$text"
}

/**
 * @desc 设置textview粗体bold
 */
fun TextView.blod() {
    typeface = Typeface.defaultFromStyle(Typeface.BOLD)
}

/**
 * @desc 中划线
 */
fun TextView.strikeLine() {
    paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
}

/**
 * @desc 加下划线
 */
fun TextView.underLine() {
    paint.flags = Paint.UNDERLINE_TEXT_FLAG
}

/**
 * @desc 去除字符串两端空格
 */
fun TextView.stringTrim(): String {
    return this.text.toString().trim()
}

/*==================================== EditText =================================================*/
/**
 * @desc 给editText添加文本内容
 */
fun EditText.addText(str: String) {
    text.insert(selectionStart, str)
}

/**
 * @desc 删除EditText指定的文本内容
 */
fun EditText.rmText(size: Int) {
    if (text.isEmpty() || selectionStart == 0) {
        return
    }
    text.delete(selectionStart - size, selectionStart)
}

/**
 * @desc 文本改变监听器
 */
fun EditText.onInputChangeListener(call: (text: String) -> Unit) {
    if (mOnTextWatcher?.get(this) is ArrayList<*>) {
        (mOnTextWatcher?.get(this) as ArrayList<*>).clear()
    }
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            call(text.toString())
        }
    })
}

val mOnTextWatcher by lazy {
    var field: Field? = null
    try {
        field = TextView::class.java.getDeclaredField("mListeners")
        field?.isAccessible = true
    } catch (e: Exception) {
    }
    return@lazy field
}