package com.common.mvpdemo2.model.main

import com.common.mvpdemo2.contract.IMainContact
import com.common.mvpdemo2.model.JokeModel
import com.common.mvpdemo2.model.PersonalCenterModel
import com.common.mvpdemo2.service.ApiService
import com.jaydenxiao.common.IModel
import com.jaydenxiao.common.modle.net.BaseModle
import com.jaydenxiao.common.module.http.load
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

class MainModel : IMainContact.Model {

    override var mDisposablePool: CompositeDisposable = CompositeDisposable()
    override fun getJokeModelData(page:Int,pageSize:Int): Observable<BaseModle<JokeModel>> {
        return load(ApiService::class.java).getCurrentJokeDataByPost(page, pageSize)
    }
}
