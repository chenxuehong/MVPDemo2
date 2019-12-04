package com.jaydenxiao.common.module.http

import android.content.Context
import com.exmple.corelib.http.observer.BaseModelObserver
import com.jaydenxiao.common.*
import com.jaydenxiao.common.ui.LibApplication
import com.jaydenxiao.common.commonutils.NetWorkUtils
import com.jaydenxiao.common.modle.net.BaseModle
import com.jaydenxiao.common.module.http.core.RetrofitCore
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by 13198 on 2018/9/15.
 */

fun <T> load(cxt: Context, service: Class<T>): T {

    return RetrofitCore.getInstance(cxt).create(service)
}

fun <T> Observable<BaseModle<T>>.mSubscribe(
        iBaseView: ITopView? = null,
        iModel: IModel? = null,
        onSuccess: (T) -> Unit
) {

    subscribeOn(Schedulers.io())   //  指定被观察者的操作在io线程中完成
            .observeOn(AndroidSchedulers.mainThread()) //  指定观察者的操作在main线程中完成
            .subscribe(object : BaseModelObserver<T>() {
                override fun onFail(value: BaseModle<T>) {

                }

                override fun onComplete() {
                    iBaseView?.dismissLoading()
                }

                override fun onBegin(d: Disposable) {
                    iModel?.addDisposable(d)
                    iBaseView?.showLoading("正在加载中...")
                }

                override fun onError() {
                    if (!NetWorkUtils.isNetConnected(LibApplication.baseApplication)) {
                        iBaseView?.showNetErrorTip()
                    }
                }

                override fun onHandleSuccess(t: T) {
                    onSuccess.invoke(t)
                }
            })
}

fun <T> Observable<BaseModle<T>>.mSubscribe2(
        iBaseView: ITopView? = null,
        iModel: IModel? = null,
        onSuccess: (T) -> Unit,
        onFail: (code: Int, msg: String) -> Unit
) {

    subscribeOn(Schedulers.io())   //  指定被观察者的操作在io线程中完成
            .observeOn(AndroidSchedulers.mainThread()) //  指定观察者的操作在main线程中完成
            .subscribe(object : BaseModelObserver<T>() {
                override fun onFail(value: BaseModle<T>) {
                    onFail.invoke(value.error_code.toInt(), value.reason)
                }

                override fun onComplete() {
                    iBaseView?.dismissLoading()
                }

                override fun onBegin(d: Disposable) {
                    iModel?.addDisposable(d)
                    iBaseView?.showLoading("正在加载中...")
                }

                override fun onError() {
                    if (!NetWorkUtils.isNetConnected(LibApplication.baseApplication)) {
                        iBaseView?.showNetErrorTip()
                    }
                }

                override fun onHandleSuccess(t: T) {
                    onSuccess.invoke(t)
                }
            })
}

fun <T, P : ITopPresenter> Observable<BaseModle<T>>.mListSubscribe(
        iBaseView: IListView<P>? = null,
        iModel: IModel? = null,
        isRefresh: Boolean,
        onSuccess: (T) -> Unit,
        onFail: (code: Int, msg: String) -> Unit
) {

    subscribeOn(Schedulers.io())   //  指定被观察者的操作在io线程中完成
            .observeOn(AndroidSchedulers.mainThread()) //  指定观察者的操作在main线程中完成
            .subscribe(object : BaseModelObserver<T>() {
                override fun onFail(value: BaseModle<T>) {
                    onFail.invoke(value.error_code.toInt(), value.reason)
                }

                override fun onComplete() {
                    iBaseView?.dismissLoading()
                }

                override fun onBegin(d: Disposable) {
                    iModel?.addDisposable(d)
                    if (isRefresh) {
                        iBaseView?.showLoading("正在加载中...")
                    }
                }

                override fun onError() {
                    if (!NetWorkUtils.isNetConnected(LibApplication.baseApplication)) {
                        iBaseView?.showNetErrorTip()
                    }
                }

                override fun onHandleSuccess(t: T) {
                    onSuccess.invoke(t)
                }
            })
}

