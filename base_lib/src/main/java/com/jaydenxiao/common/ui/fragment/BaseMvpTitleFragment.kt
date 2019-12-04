package com.jaydenxiao.common.ui.mvp

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.jaydenxiao.common.ITopPresenter
import com.jaydenxiao.common.IView
import com.jaydenxiao.common.R
import com.jaydenxiao.common.ui.fragment.BaseFragment
import com.jaydenxiao.common.ui.view.linearlayout.LoadingPager
import kotlinx.android.synthetic.main.title_action_bar.*


/**
 * Created by 13198 on 2018/9/19.
 */

abstract class BaseMvpTitleFragment<P : ITopPresenter> : BaseFragment(), IView<P> {

    private var rightMenuTexts: String? = null
    private var rightMenuIcons: Int? = null
    private var titleTv: TextView? = null
    var mLoadingPager: LoadingPager? = null

    override fun getCtx() = context
    override fun getContentView(): Int = R.layout.activtiy_base_title
    abstract fun getChildView(): Int

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        inited()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initView(view: View?) {

        val container = view?.findViewById<FrameLayout>(R.id.base_container)
        container?.addView(layoutInflater.inflate(getChildView(), null))
        mLoadingPager = view?.findViewById<LoadingPager>(R.id.activity_base_title_loadingPager)
        mLoadingPager?.setOnReloadListener {
            onRetry()
        }
        if (hasBackIcon()) {
            title_back.visibility = View.VISIBLE
        }
        setTitleBarBackground(title_bar_fl_bg)
        setCenterTitle(title_name, titlebar_iv_icon)
        setViewLine(title_bar_view_line)
        setLeftTitle(tv_title_back, title_left)
        setRightTitle(title_share, check_img)
    }

    open fun hasBackIcon() = true

    open fun setTitleBarBackground(titleBarFlBg: FrameLayout?) {

    }

    /**
     * 设置标题栏中间的图标
     */
    open fun setCenterTitle(tvCenterTtle: TextView, icCenterIcon: ImageView) {

    }

    /**
     * 对标题栏底部分割线进行处理
     */
    open fun setViewLine(viewLine: View?) {

    }

    /**
     * 设置titlebar的左边标题和图标
     */
    open fun setLeftTitle(title_back: ImageView, leftTitle: TextView) {

    }

    /**
     * 设置titlebar的右边标题和图标
     */
    open fun setRightTitle(rightTitle: TextView, rightIcon: ImageView) {

    }

    /**
     * 重试
     */
    open fun onRetry(){
        initData()
    }

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
