package com.common.mvpdemo2.presenter.main


import com.common.mvpdemo2.model.main.MainModel
import com.common.mvpdemo2.view.mian.ui.MainActivity
import com.jaydenxiao.common.IPresenter
import com.jaydenxiao.common.ITopView
import com.jaydenxiao.common.module.http.mSubscribe
import com.jaydenxiao.common.module.http.mSubscribe2
import java.lang.ref.WeakReference

class MainPresenter(mainActivity: MainActivity) : IPresenter<MainModel>, MainPresenterIml {
    override var vWeakReference: WeakReference<ITopView>? = null
    override var mModel: MainModel? = MainModel()
    open var mMainActivity: MainActivity? = mainActivity
    override fun loadJokeModelData(page: Int, pageSize: Int) {

        mModel?.getJokeModelData(page, pageSize)?.mSubscribe(getView(), mModel, {
            //            jokeModelData = it
            mMainActivity?.afterLoadJokeModelData(it)
        })
//        mModel?.getJokeModelData()?.mSubscribe2(getView(), mModel, {
//            //            jokeModelData = it
//            mMainActivity?.afterLoadJokeModelData(it)
//        }, { i: Int, s: String ->
//
//        })
    }
}
