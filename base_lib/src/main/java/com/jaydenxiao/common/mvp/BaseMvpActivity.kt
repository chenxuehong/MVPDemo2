package com.jaydenxiao.common.mvp

import android.content.Context
import android.os.Bundle
import com.jaydenxiao.common.*
import com.jaydenxiao.common.base.BaseActivity
import com.jaydenxiao.common.commonutils.LoadingDialog

/**
 * Created by 13198 on 2018/9/19.
 * @desc BaseMvpActivity
 */

abstract class BaseMvpActivity<P : ITopPresenter> : BaseActivity(), IView<P> {

    override fun onCreate(savedInstanceState: Bundle?) {
        inited()
        super.onCreate(savedInstanceState)
    }

    override fun getCtx(): Context? {
        return this
    }

    override fun showLoading(msg: String) {

        LoadingDialog.showDialogForLoading(this, msg, true)
    }

    override fun finish(resultCode: Int) {
        finish()
    }

    override fun showLoading(srtResId: Int) {
        LoadingDialog.showDialogForLoading(this, resources.getString(srtResId), true)
    }

    override fun dismissLoading() {
        LoadingDialog.cancelDialogForLoading()
    }

    /**
     * 网络访问错误提醒
     */
    override fun showNetErrorTip(error: String) {
        showToastWithImg(error, R.drawable.ic_wifi_off)
    }

    override fun showToast(message: String) {
        showToastBottom(message)
    }

    override fun showToast(srtResId: Int) {
        showToast(resources.getString(srtResId))
    }
}
