package com.jaydenxiao.common.mvp

import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.jaydenxiao.common.IListView
import com.jaydenxiao.common.ITopPresenter
import com.jaydenxiao.common.R
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.layout_list.*

/**
 * Created by 13198 on 2018/9/19.
 */

abstract class BaseMvpListActivity<P : ITopPresenter> : BaseMvpActivity<P>(), IListView<P> {
    override fun getChildView(): Int = R.layout.layout_list
    override val mRecyclerView: RecyclerView by lazy { list_rv }
    override val mRefreshLayout: SmartRefreshLayout by lazy { refreshLayout }

    override fun initView() {
        //设置列表背景色
        mRecyclerView?.setBackgroundColor(ContextCompat.getColor(this, setRecyclerViewBgColor))
        setEnableRefresh(false)
        setEnableLoadMore(false)
    }

    /**
     * 设置下拉刷新是否可用
     */
    private fun setEnableRefresh(isEnable: Boolean) {
        mRefreshLayout?.setEnableRefresh(isEnable)
    }

    /**
     * 设置上拉加载是否可用
     */
    private fun setEnableLoadMore(isEnable: Boolean) {
        mRefreshLayout?.setEnableLoadMore(isEnable)
    }

    open val setRecyclerViewBgColor = R.color.white
    open var setRefreshEnable = false

    fun RecyclerView.vertical() {
        layoutManager = LinearLayoutManager(mRecyclerView?.context, LinearLayoutManager.VERTICAL, false)
    }

    fun RecyclerView.horizontal() {
        layoutManager = LinearLayoutManager(mRecyclerView?.context, LinearLayoutManager.HORIZONTAL, false)
    }

    fun RecyclerView.vertical(spanCount: Int) {
        layoutManager = GridLayoutManager(mRecyclerView?.context, spanCount, GridLayoutManager.VERTICAL, false)
    }

    fun RecyclerView.horizontal(spanCount: Int) {
        layoutManager = GridLayoutManager(mRecyclerView?.context, spanCount, GridLayoutManager.HORIZONTAL, false)
    }

    fun setOnLoadMore(listener: OnLoadMoreListener) {
        setEnableLoadMore(true)
        // 加载更多
        if (listener != null) {
            mRefreshLayout?.setOnLoadMoreListener {
                listener.onLoadMore(refreshLayout)
                mRefreshLayout?.finishLoadMore(300)
            }
        }
    }

    fun setOnRefresh(listener: OnRefreshListener) {
        setEnableRefresh(true)
        // 刷新
        if (listener != null) {
            mRefreshLayout?.setOnRefreshListener {
                listener.onRefresh(refreshLayout)
                mRefreshLayout?.finishRefresh(300)
            }
        }
    }

}
