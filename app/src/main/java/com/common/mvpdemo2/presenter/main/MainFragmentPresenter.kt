package com.common.mvpdemo2.presenter.main

import com.common.mvpdemo2.contract.IMainContact
import com.common.mvpdemo2.model.main.MainModel
import com.common.mvpdemo2.ui.LiveFragment
import com.jaydenxiao.common.presenter.BasePresenterKt
import com.jaydenxiao.common.module.http.mListSubscribe

class MainFragmentPresenter(liveFragment: LiveFragment) : BasePresenterKt<IMainContact.View, IMainContact.Model>(), IMainContact.Presenter {

    override fun createModel(): IMainContact.Model {
        return MainModel()
    }

    open var mliveFragment: LiveFragment? = liveFragment
    override fun loadJokeModelData(isRefresh: Boolean, page: Int, pageSize: Int) {

        getModel()?.getJokeModelData(page, pageSize)?.mListSubscribe(getView(), getModel(), isRefresh, {
            mliveFragment?.afterLoadJokeModelData(it)
        }, { i: Int, s: String ->
        })
//        mModel?.getJokeModelData()?.mSubscribe2(getView(), mModel, {
//            //            jokeModelData = it
//            mMainActivity?.afterLoadJokeModelData(it)
//        }, { i: Int, s: String ->
//
//        })
    }
}
