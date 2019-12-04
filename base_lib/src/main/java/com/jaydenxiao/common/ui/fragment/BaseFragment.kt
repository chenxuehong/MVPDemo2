package com.jaydenxiao.common.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jaydenxiao.common.R
import com.jaydenxiao.common.commonutils.LoadingDialog
import com.jaydenxiao.common.commonutils.ToastUitl
import com.jaydenxiao.common.injects.logi
import com.jaydenxiao.common.injects.register
import com.jaydenxiao.common.injects.unregister

/**
 * des:基类fragment
 * Created by xsf
 * on 2016.07.12:38
 */

/***************使用例子 */
//1.mvp模式

abstract class BaseFragment : Fragment() {

    open fun useEventBus(): Boolean = false

    open var mContext: Context? = null
    @LayoutRes
    protected abstract fun getContentView(): Int

    private var isViewPrepare = false
    private var hasLoadData = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layout = getContentView()
        val rootView = inflater.inflate(layout, container, false)
        this.mContext = context
        if (useEventBus()) {
            register()
        }

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView(view)
        if (userVisibleHint) {

            initData()
            hasLoadData = true
        }
        isViewPrepare = false
        savedStanceState(savedInstanceState)
    }

    abstract fun initView(view: View?)
    abstract fun savedStanceState(savedInstanceState: Bundle?)
    abstract fun initData()

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        lazyLoad()
    }

    private fun lazyLoad() {
        if (userVisibleHint && isViewPrepare && !hasLoadData) {
           initData()
        }
    }

    /**
     * 通过Class跳转界面
     */
    fun startActivityForResult(cls: Class<*>, requestCode: Int) {
        startActivityForResult(cls, null, requestCode)
    }

    /**
     * 含有Bundle通过Class跳转界面
     */
    fun startActivityForResult(cls: Class<*>, bundle: Bundle?,
                               requestCode: Int) {
        val intent = Intent()
        intent.setClass(activity, cls)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivityForResult(intent, requestCode)
    }

    /**
     * 含有Bundle通过Class跳转界面
     */
    @JvmOverloads
    fun startActivity(cls: Class<*>, bundle: Bundle? = null) {
        val intent = Intent()
        intent.setClass(activity, cls)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
    }

    /**
     * 开启加载进度条
     */
    private fun startProgressDialog() {
        LoadingDialog.showDialogForLoading(activity)
    }

    /**
     * 开启加载进度条
     *
     * @param msg
     */
    private fun startProgressDialog(msg: String) {
        LoadingDialog.showDialogForLoading(activity, msg, true)
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

    private fun showNetErrorTip(error: String) {
        ToastUitl.showToastWithImg(error, R.drawable.ic_wifi_off)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        hasLoadData = false
        isViewPrepare = false
        if (useEventBus()) {
            unregister()
        }
    }
}

