package com.jaydenxiao.common.ui.activity

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.jaydenxiao.common.R
import com.jaydenxiao.common.commonutils.LoadingDialog
import com.jaydenxiao.common.commonutils.StatusBarUtil
import com.jaydenxiao.common.commonutils.ToastUitl
import com.jaydenxiao.common.injects.register
import com.jaydenxiao.common.injects.unregister

/**
 * Created by 13198 on 2018/9/1.
 */

abstract class BaseActivity : AppCompatActivity() {

    private var isConfigChange: Boolean = false
    abstract fun getContentView(): Int
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initWindow()
        setContentView(getContentView())
        if (useEventBus()) {
            register()
        }
        initView()
        initData()
    }

    open fun useEventBus(): Boolean = false

    abstract fun initData()

    abstract fun initView()

    protected fun initWindow() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT//强制竖屏
        setStatusBar()

    }

    protected open fun setStatusBar() {
        StatusBarUtil.setStatusBarTranslucent(this, false)
    }

    override fun finish() {
        super.finish()
        if (hasFinishTransitionAnim()) {
            overridePendingTransitionExit()
        }
    }

    open fun hasFinishTransitionAnim(): Boolean {
        return true
    }

    open fun hasEnterTransitionAnim(): Boolean {
        return true
    }

    private fun overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left)
    }

    private fun overridePendingTransitionExit() {
        overridePendingTransition(R.anim.in_from_left, R.anim.out_from_right)
    }

    /**
     * 通过Class跳转界面
     */
    fun startActivityForResult(cls: Class<*>, requestCode: Int) {
        startActivityForResult(cls, null, requestCode)
        if (hasEnterTransitionAnim()) {
            overridePendingTransitionEnter()
        }
    }

    /**
     * 含有Bundle通过Class跳转界面
     */
    fun startActivityForResult(cls: Class<*>, bundle: Bundle?,
                               requestCode: Int) {
        val intent = Intent()
        intent.setClass(this, cls)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivityForResult(intent, requestCode)
        if (hasEnterTransitionAnim()) {
            overridePendingTransitionEnter()
        }
    }

    /**
     * 含有Bundle通过Class跳转界面
     */
    fun startActivity(cls: Class<*>, bundle: Bundle? = null) {
        val intent = Intent()
        intent.setClass(this, cls)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
        if (hasEnterTransitionAnim()) {
            overridePendingTransitionEnter()
        }
    }

    /**
     * 开启加载进度条
     */
    private fun startProgressDialog() {
        LoadingDialog.showDialogForLoading(this)
    }

    /**
     * 开启加载进度条
     *
     * @param msg
     */
    private fun startProgressDialog(msg: String) {
        LoadingDialog.showDialogForLoading(this, msg, true)
    }

    /**
     * 停止加载进度条
     */
    fun stopProgressDialog() {
        LoadingDialog.cancelDialogForLoading()
    }

    /**
     * 网络访问错误提醒
     */
    open fun showNetErrorTip() {
        ToastUitl.showToastWithImg(getText(R.string.net_error).toString(), R.drawable.ic_wifi_off)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        isConfigChange = true
    }

    override fun onResume() {
        super.onResume()
        if (!isConfigChange) {
            // 友盟统计 调用onResume方法

        }
    }

    override fun onPause() {
        super.onPause()
        if (!isConfigChange) {
            // 友盟统计 调用onPause方法

        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_UP) {
            val v = currentFocus
            //如果不是落在EditText区域，则需要关闭输入法
            if (hideKeyboard(v, ev)) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
     */
    private fun hideKeyboard(view: View?, event: MotionEvent): Boolean {
        if (view != null && view is EditText) {

            val location = intArrayOf(0, 0)
            view.getLocationInWindow(location)

            //获取现在拥有焦点的控件view的位置，即EditText
            val left = location[0]
            val top = location[1]
            val bottom = top + view.height
            val right = left + view.width
            //判断我们手指点击的区域是否落在EditText上面，如果不是，则返回true，否则返回false
            val isInEt = (event.x > left && event.x < right && event.y > top
                    && event.y < bottom)
            return !isInEt
        }
        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        if (useEventBus()) {
            unregister()
        }
    }

    fun isNightMode(isNight: Boolean) {

    }
}

