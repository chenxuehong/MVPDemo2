package com.jaydenxiao.common.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.jaydenxiao.common.R
import com.jaydenxiao.common.commonutils.LoadingDialog
import com.jaydenxiao.common.commonutils.ToastUitl
import com.jaydenxiao.common.register
import com.jaydenxiao.common.unregister

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
    protected abstract fun lazyLoad()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layout = getContentView()
        val rootView = inflater.inflate(layout, container, false)
        this.mContext = context
        ButterKnife.bind(rootView,activity)
        if (useEventBus()) {
            register(this)
        }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        savedStanceState(savedInstanceState)
        initData()
    }

    abstract fun initView(view: View?)
    abstract fun savedStanceState(savedInstanceState: Bundle?)
    abstract fun initData()

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            lazyLoadDataIfPrepared()
        }
    }

    private fun lazyLoadDataIfPrepared() {
        if (userVisibleHint && isViewPrepare && !hasLoadData) {
            lazyLoad()
            hasLoadData = true
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
    fun showNetErrorTip() {
        ToastUitl.showToastWithImg(getText(R.string.net_error).toString(), R.drawable.ic_wifi_off)
    }

    private fun showNetErrorTip(error: String) {
        ToastUitl.showToastWithImg(error, R.drawable.ic_wifi_off)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        ButterKnife.unbind(this)
        if (useEventBus()) {
            unregister(this)
        }
    }
}

