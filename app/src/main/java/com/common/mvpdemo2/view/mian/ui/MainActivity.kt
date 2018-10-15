package com.common.mvpdemo2.view.mian.ui

import com.common.mvpdemo2.R
import com.common.mvpdemo2.model.JokeModel
import com.common.mvpdemo2.presenter.main.MainPresenter
import com.jaydenxiao.common.base.CommonAdapter
import com.jaydenxiao.common.base.ViewHolder
import com.jaydenxiao.common.mvp.BaseMvpListActivity
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener


class MainActivity : BaseMvpListActivity<MainPresenter>(), MainActivityIml {

    override fun loadMoreFail(isRefresh: Boolean) {

    }

    var currentPage = 0
    var list = mutableListOf<JokeModel.Data>()


    override var mPresenter: MainPresenter? = MainPresenter(this)

    override fun initData() {

        currentPage = 0
        mPresenter?.loadJokeModelData(currentPage, 8)
    }

    override fun initView() {
        super.initView()
        setOnLoadMore(OnLoadMoreListener {
            currentPage++
            mPresenter?.loadJokeModelData(currentPage, 8)
        })

        setOnRefresh(OnRefreshListener {
            currentPage = 0
            mPresenter?.loadJokeModelData(currentPage, 8)
        })
    }

    override fun afterLoadJokeModelData(it: JokeModel) {

        if (currentPage == 0) {
            list.clear()
            list.addAll(it.data!!)
            mRecyclerView?.apply {
                vertical()
                adapter = object : CommonAdapter<JokeModel.Data>(this@MainActivity, R.layout.item_main_1, list) {
                    override fun convert(holder: ViewHolder?, t: JokeModel.Data?, position: Int) {

                        holder?.setText(R.id.item_main1_tv_content, t?.content)
                        holder?.setText(R.id.item_main1_tv_updateTime, t?.updatetime)
                    }
                }
            }
        } else {
            list.addAll(it.data!!)
            mRecyclerView?.adapter?.notifyDataSetChanged()
        }
    }
}
