package com.common.mvpdemo2.ui

import android.widget.ImageView
import android.widget.TextView
import com.common.mvpdemo2.R
import com.common.mvpdemo2.contract.IMainContact
import com.common.mvpdemo2.model.JokeModel
import com.common.mvpdemo2.presenter.main.MainPresenter
import com.jaydenxiao.common.ui.adapter.CommonAdapter
import com.jaydenxiao.common.ui.holder.ViewHolder
import com.jaydenxiao.common.injects.setOnLoadMore
import com.jaydenxiao.common.injects.setOnRefresh
import com.jaydenxiao.common.injects.vertical
import com.sihaiwanlian.baselib.mvp.BaseMvpTitleListActivity


class MainActivity : BaseMvpTitleListActivity<IMainContact.Presenter>(), IMainContact.View {

    override var mPresenter: IMainContact.Presenter? = MainPresenter(this)

    override fun hasFinishTransitionAnim(): Boolean {
        return false
    }

    override fun hasEnterTransitionAnim(): Boolean {
        return false
    }

    override fun hasBackIcon(): Boolean = false

    override fun setCenterTitle(tvCenterTtle: TextView, icCenterIcon: ImageView) {

        tvCenterTtle.text = "主页"
    }

    override fun onRetry() {
        initData()
    }

    override fun loadMoreFail(isRefresh: Boolean) {

    }

    var currentPage = 0
    var list = mutableListOf<JokeModel.Data>()

    override fun initData() {

        currentPage = 0
        mPresenter?.loadJokeModelData(true, currentPage, 8)
        mRefreshLayout?.setOnLoadMore({
            currentPage++
            mPresenter?.loadJokeModelData(false, currentPage, 8)
        })

        mRefreshLayout?.setOnRefresh({
            currentPage = 0
            mPresenter?.loadJokeModelData(true, currentPage, 8)
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
