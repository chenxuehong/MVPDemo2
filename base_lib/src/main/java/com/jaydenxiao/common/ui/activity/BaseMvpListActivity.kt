package com.jaydenxiao.common.ui.mvp

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import com.jaydenxiao.common.IListView
import com.jaydenxiao.common.ITopPresenter
import com.jaydenxiao.common.R
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.layout_list.*

/**
 * Created by 13198 on 2018/9/19.
 */

abstract class BaseMvpListActivity<P : ITopPresenter> : BaseMvpActivity<P>(), IListView<P> {
    override fun getChildView(): Int = R.layout.layout_list
    val mRecyclerView: RecyclerView by lazy { list_rv }
    val mRefreshLayout: SmartRefreshLayout by lazy { refreshLayout }

    override fun initView() {
        super.initView()
        //设置列表背景色
        mRecyclerView?.setBackgroundColor(ContextCompat.getColor(this, setRecyclerViewBgColor))
        mRefreshLayout?.setEnableRefresh(false)
        mRefreshLayout?.setEnableLoadMore(false)
    }

    open val setRecyclerViewBgColor = R.color.white

}
