package com.sihaiwanlian.baselib.mvp

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import com.jaydenxiao.common.IListView
import com.jaydenxiao.common.ITopPresenter
import com.jaydenxiao.common.R
import com.scwang.smartrefresh.layout.SmartRefreshLayout
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