package com.common.mvpdemo2.ui

import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import android.widget.TextView
import com.common.mvpdemo2.R
import com.common.mvpdemo2.contract.IShoppingContact
import com.common.mvpdemo2.presenter.shopping.ShoppingFragmentPrestener
import com.jaydenxiao.common.ui.adapter.CommonAdapter
import com.jaydenxiao.common.ui.holder.ViewHolder
import com.jaydenxiao.common.ui.mvp.BaseMvpTitleFragment
import com.jaydenxiao.common.injects.vertical
import kotlinx.android.synthetic.main.fragment_shopping.*

class ShoppingFragment : BaseMvpTitleFragment<IShoppingContact.Presenter>(), IShoppingContact.View {

    var mList = mutableListOf<String>()
    override fun hasBackIcon(): Boolean = false

    override fun loadMoreFail(isRefresh: Boolean) {

    }

    override fun setCenterTitle(tvCenterTtle: TextView, icCenterIcon: ImageView) {
        tvCenterTtle.text = "商城"
    }

    override fun getChildView(): Int = R.layout.fragment_shopping

    override fun savedStanceState(savedInstanceState: Bundle?) {
    }

    override fun initData() {

        mList = mutableListOf("1", "2", "aaaa", "ffffffff", "2", "aaaa", "ffffffff", "2", "aaaa", "ffffffff")
        fragment_shopping_RefreshRecyclerView.apply {
            vertical()
            adapter = object : CommonAdapter<String>(getCtx(),R.layout.item_shopping,mList){
                override fun convert(holder: ViewHolder?, t: String?, position: Int) {
                    holder?.setText(R.id.item_shopping_tv_content,t)
                }
            }

            setOnRefreshListener {
                initData()
                Handler().postDelayed({

                    hideHeaderView(true)
                }, 2000)
            }
//            setOnLoadMoreListener {
//                mList.add("我是加载更多的数据!")
//
//                Handler().postDelayed({
//
//                    hideFooterView()
//                }, 2000)
//            }
        }
    }

    override var mPresenter: IShoppingContact.Presenter? = ShoppingFragmentPrestener()

}
