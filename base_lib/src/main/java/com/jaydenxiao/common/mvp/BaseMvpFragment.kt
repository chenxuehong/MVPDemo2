package com.jaydenxiao.common.mvp

import android.os.Bundle
import com.jaydenxiao.common.ITopPresenter
import com.jaydenxiao.common.IView
import com.jaydenxiao.common.base.BaseFragment
import com.jaydenxiao.common.commonutils.LoadingDialog
import com.jaydenxiao.common.showToastBottom

/**
 * Created by 13198 on 2018/9/19.
 */

abstract class BaseMvpFragment<P : ITopPresenter> : BaseFragment(), IView<P> {

    override fun getCtx() = context

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        inited()
    }

    override fun showToast(message: String) {
        showToastBottom(message)
    }

    override fun showToast(srtResId: Int) {
        showToast(resources.getString(srtResId))
    }

    override fun showLoading(msg: String) {
        LoadingDialog.showDialogForLoading(activity, msg, true)
    }

    override fun showLoading(srtResId: Int) {
        LoadingDialog.showDialogForLoading(activity, resources.getString(srtResId), true)
    }

    override fun dismissLoading() {
        LoadingDialog.cancelDialogForLoading()
    }
}
