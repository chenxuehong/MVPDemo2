package com.sihaiwanlian.baselib.mvp

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
 * @FileName: {NAME}.java
 * @author: villa_mou
 * @date: {MONTH}-{HOUR}:03
 * @version V1.0 <描述当前版本功能>
 * @desc
 */
abstract class BaseMvpTitleListActivity<P : ITopPresenter> : BaseMvpTitleActivity<P>(), IListView<P> {
    override fun childView() = R.layout.layout_list
//        override val mStateView: IStateView by lazy { list_sv }
    override val mRecyclerView: RecyclerView by lazy { list_rv }
    override val mRefreshLayout: SmartRefreshLayout by lazy { refreshLayout }
    override fun initView() {
        super.initView()
        //设置背景色
        list_rv.setBackgroundColor(ContextCompat.getColor(this, setRecyclerViewBgColor))
        setEnableRefresh(false)
        setEnableLoadMore(false)
    }


    /**
     * 重试
     */
    abstract fun onRetry()

    open val setRecyclerViewBgColor = R.color.white

    fun RecyclerView.vertical() {
        layoutManager = LinearLayoutManager(mRecyclerView.context, LinearLayoutManager.VERTICAL, false)
    }

    fun RecyclerView.horizontal() {
        layoutManager = LinearLayoutManager(mRecyclerView.context, LinearLayoutManager.HORIZONTAL, false)
    }

    fun RecyclerView.vertical(spanCount: Int) {
        layoutManager = GridLayoutManager(mRecyclerView.context, spanCount, GridLayoutManager.VERTICAL, false)
    }

    fun RecyclerView.horizontal(spanCount: Int) {
        layoutManager = GridLayoutManager(mRecyclerView.context, spanCount, GridLayoutManager.HORIZONTAL, false)
    }

    /**
     * 设置下拉刷新是否可用
     */
    private fun setEnableRefresh(isEnable: Boolean) {
        refreshLayout.setEnableRefresh(isEnable)
    }

    /**
     * 设置上拉加载是否可用
     */
    private fun setEnableLoadMore(isEnable: Boolean) {
        refreshLayout.setEnableLoadMore(isEnable)
    }

    fun setOnLoadMore(listener: OnLoadMoreListener) {
        setEnableLoadMore(true)
        // 加载更多
        if (listener != null) {
            refreshLayout.setOnLoadMoreListener {
                listener.onLoadMore(refreshLayout)
                refreshLayout.finishLoadMore(300)
            }
        }
    }

    fun setOnRefresh(listener: OnRefreshListener) {
        setEnableRefresh(true)
        // 刷新
        if (listener != null) {
            refreshLayout.setOnRefreshListener {
                listener.onRefresh(refreshLayout)
                refreshLayout.finishRefresh(300)
            }
        }
    }
}