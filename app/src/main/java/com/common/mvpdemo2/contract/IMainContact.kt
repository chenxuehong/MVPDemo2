package com.common.mvpdemo2.contract

import com.common.mvpdemo2.model.JokeModel
import com.jaydenxiao.common.IListView
import com.jaydenxiao.common.IModel
import com.jaydenxiao.common.IPresenter
import com.jaydenxiao.common.modle.net.BaseModle
import io.reactivex.Observable

interface IMainContact {

    interface View : IListView<Presenter> {
        fun afterLoadJokeModelData(it: JokeModel)
    }

    interface Presenter : IPresenter<View,Model> {
        fun loadJokeModelData(isRefresh: Boolean, page: Int, pageSize: Int)
    }

    interface Model : IModel {
        fun getJokeModelData(page: Int, pageSize: Int): Observable<BaseModle<JokeModel>>
    }
}
