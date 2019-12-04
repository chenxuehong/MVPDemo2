package com.jaydenxiao.common.ui.mvp

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import com.jaydenxiao.common.*
import com.jaydenxiao.common.ui.fragment.BaseFragment
import com.jaydenxiao.common.ui.view.linearlayout.LoadingPager

/**
 * Created by 13198 on 2018/9/19.
 */

abstract class BaseMvpFragment<P : ITopPresenter> : BaseFragment(), IView<P> {

    var mLoadingPager: LoadingPager? = null
    override fun getCtx() = context
    override fun getContentView(): Int = R.layout.activtiy_base
    abstract fun getChildView(): Int

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        inited()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initView(view: View?) {

        val container = view?.findViewById<FrameLayout>(R.id.activity_base_fl_container)
        //container?.removeAllViews()
        container?.addView(layoutInflater.inflate(getChildView(), null))
        mLoadingPager = view?.findViewById<LoadingPager>(R.id.activity_base_loadingPager)
        mLoadingPager?.setOnReloadListener {
            onRetry()
        }
    }

    /**
     * 重试
     */
    abstract fun onRetry()

    private fun setLoadingState(loadStatus: LoadingPager.LoadStatus) {
        mLoadingPager?.setLoadingTip(loadStatus)
    }

    override fun showLoading(msg: String) {

        mLoadingPager?.setTips(msg)
        setLoadingState(LoadingPager.LoadStatus.loading)
    }

    override fun finish(resultCode: Int) {
        finish()
    }

    override fun showLoading(srtResId: Int) {
        showLoading(resources.getString(srtResId))
    }

    override fun dismissLoading() {

        setLoadingState(LoadingPager.LoadStatus.finish)
    }

    /**
     * 网络访问错误提醒
     */
    override fun showNetErrorTip() {
        setLoadingState(LoadingPager.LoadStatus.error)
    }

    /**
     * 网络访问错误提醒
     */
    override fun showEmpty() {
        setLoadingState(LoadingPager.LoadStatus.empty)
    }

    /**
     * 网络访问错误提醒
     */
    override fun showServerErrorTip() {
        setLoadingState(LoadingPager.LoadStatus.sereverError)
    }

}
