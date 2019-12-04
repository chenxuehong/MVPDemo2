package com.sihaiwanlian.baselib.mvp

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.jaydenxiao.common.ITopPresenter
import com.jaydenxiao.common.IView
import com.jaydenxiao.common.R
import com.jaydenxiao.common.ui.activity.BaseActivity
import com.jaydenxiao.common.ui.view.linearlayout.LoadingPager
import kotlinx.android.synthetic.main.title_action_bar.*


/**
 * 所有标题的activity的父类
 * 在这里主要统一处理标题
 * Created by mou on 2016/7/13.
 */
abstract class BaseMvpTitleActivity<P : ITopPresenter> : BaseActivity(), IView<P> {
    private var rightMenuTexts: String? = null
    private var rightMenuIcons: Int? = null
    private var titleTv: TextView? = null
    var mLoadingPager: LoadingPager? = null

    override fun getContentView(): Int = R.layout.activtiy_base_title

    override fun getCtx(): Context? = this

    @LayoutRes
    protected abstract fun childView(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        inited()
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        val container = this.findViewById<FrameLayout>(R.id.base_container)
        mLoadingPager = this.findViewById<LoadingPager>(R.id.activity_base_title_loadingPager)
        container.addView(layoutInflater.inflate(childView(), null))
        mLoadingPager?.setOnReloadListener {
            onRetry()
        }
        if (hasBackIcon()) {
            title_back.visibility = View.VISIBLE
        }
        setTitleBarBackground(title_bar_fl_bg)
        setCenterTitle(title_name,titlebar_iv_icon)
        setViewLine(title_bar_view_line)
        setLeftTitle(tv_title_back,title_left)
        setRightTitle(title_share,check_img)
    }

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
    abstract fun onRetry()
    private fun setLoadingState(loadStatus: LoadingPager.LoadStatus) {
        mLoadingPager?.setLoadingTip(loadStatus)
    }

    override fun showLoading(msg: String) {

        setLoadingState(LoadingPager.LoadStatus.loading)
        mLoadingPager?.setTips(msg)
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

    open fun hasBackIcon() = true

}
