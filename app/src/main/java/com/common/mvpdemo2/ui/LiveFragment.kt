package com.common.mvpdemo2.ui

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.common.mvpdemo2.R
import com.common.mvpdemo2.contract.IMainContact
import com.common.mvpdemo2.model.JokeModel
import com.common.mvpdemo2.presenter.main.MainFragmentPresenter
import com.jaydenxiao.common.ui.adapter.CommonAdapter
import com.jaydenxiao.common.ui.holder.ViewHolder
import com.jaydenxiao.common.ui.mvp.BaseMvpTitleFragment
import com.jaydenxiao.common.injects.setOnLoadMore
import com.jaydenxiao.common.injects.setOnRefresh
import com.jaydenxiao.common.injects.vertical
import kotlinx.android.synthetic.main.fragment_live.*

class LiveFragment : BaseMvpTitleFragment<IMainContact.Presenter>(), IMainContact.View {

    override fun loadMoreFail(isRefresh: Boolean) {

    }

    override var mPresenter: IMainContact.Presenter? = MainFragmentPresenter(this)
    var currentPage = 0
    var list = mutableListOf<JokeModel.Data>()

    override fun getChildView(): Int {
        return R.layout.fragment_live
    }

    override fun hasBackIcon(): Boolean = false

    override fun setCenterTitle(tvCenterTtle: TextView, icCenterIcon: ImageView) {
        tvCenterTtle.text = "直播"
    }

    override fun initData() {
        currentPage = 0
        mPresenter?.loadJokeModelData(true, currentPage, 8)
        fragment_live_refreshLayout?.setOnLoadMore({
            currentPage++
            mPresenter?.loadJokeModelData(false, currentPage, 8)
        })

        fragment_live_refreshLayout?.setOnRefresh({
            currentPage = 0
            mPresenter?.loadJokeModelData(true, currentPage, 8)
        })
    }

    override fun savedStanceState(savedInstanceState: Bundle?) {

    }

    override fun afterLoadJokeModelData(it: JokeModel) {
        if (currentPage == 0) {
            list.clear()
            list.addAll(it.data!!)
            fragment_live_list_rv?.apply {
                vertical()
                adapter = object : CommonAdapter<JokeModel.Data>(this@LiveFragment.mContext, R.layout.item_main_1, list) {
                    override fun convert(holder: ViewHolder?, t: JokeModel.Data?, position: Int) {

                        holder?.setText(R.id.item_main1_tv_content, t?.content)
                        holder?.setText(R.id.item_main1_tv_updateTime, t?.updatetime)
                    }
                }
            }
        } else {
            list.addAll(it.data!!)
            fragment_live_list_rv?.adapter?.notifyDataSetChanged()
        }

    }

}
