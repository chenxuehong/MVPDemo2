package com.jaydenxiao.common.module.http

import com.google.gson.JsonParseException
import com.jaydenxiao.common.*
import com.jaydenxiao.common.base.LibApplication
import com.jaydenxiao.common.commonutils.NetWorkUtils
import com.jaydenxiao.common.modle.net.BaseModle
import com.jaydenxiao.common.module.http.constant.CodeStatus
import com.jaydenxiao.common.module.http.core.RetrofitCore
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.net.ConnectException
import java.net.SocketTimeoutException

/**
 * Created by 13198 on 2018/9/15.
 */

fun <T> load(service: Class<T>): T {

    return RetrofitCore.getInstance().create(service)
}

fun <T> Observable<BaseModle<T>>.mSubscribe(
        iBaseView: ITopView? = null,
        iModel: IModel? = null,
        onSuccess: (T) -> Unit
) {

    subscribeOn(Schedulers.io())   //  指定被观察者的操作在io线程中完成
            .observeOn(AndroidSchedulers.mainThread()) //  指定观察者的操作在main线程中完成
            .subscribe(object : Observer<BaseModle<T>> {
                override fun onComplete() {
                    iBaseView?.dismissLoading()
                }

                override fun onSubscribe(d: Disposable) {
                    iModel?.addDisposable(d)
                    iBaseView?.showLoading("正在加载中...")
                    if (!NetWorkUtils.isNetConnected(LibApplication.appContext)) {
                        iBaseView?.showNetErrorTip()
                        onComplete()
                    }
                }

                override fun onNext(value: BaseModle<T>) {
                    if (value.error_code.toInt() == CodeStatus.SUCCESS) {
                        onSuccess.invoke(value?.result!!)
                    } else if (value.error_code.toInt() == CodeStatus.LOGIN_OUT) {//重新登录
                        showToastBottom("登录过期，请重新登录")
                    } else {
                        if (!value.reason.isNullOrEmpty()) {
                            value.reason?.let { showToastBottom(it) }
                        } else {
                            iBaseView?.showServerErrorTip()
                        }
                    }
                }

                override fun onError(e: Throwable) {
                    iBaseView?.dismissLoading()
                    var errorInfo= ""
                    if (e is SocketTimeoutException || e is ConnectException) {
                        errorInfo = "连接失败,请检查网络状况!"
                    } else if (e is JsonParseException) {
                        errorInfo = "数据解析失败"
                    } else {
                        errorInfo = "请求失败"
                    }
                    iBaseView?.showServerErrorTip()
                    showToastBottom(errorInfo)
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
            .subscribe(object : Observer<BaseModle<T>> {
                override fun onComplete() {
                    iBaseView?.dismissLoading()
                }

                override fun onSubscribe(d: Disposable) {
                    iModel?.addDisposable(d)
                    iBaseView?.showLoading("正在加载中...")
                    if (!NetWorkUtils.isNetConnected(LibApplication.appContext)) {
                        iBaseView?.showNetErrorTip()
                        onComplete()
                    }
                }

                override fun onNext(value: BaseModle<T>) {
                    if (value.error_code.toInt() == CodeStatus.SUCCESS) {
                        onSuccess.invoke(value.result)
                    } else if (value.error_code.toInt() == CodeStatus.LOGIN_OUT) {//重新登录
                        showToastBottom("登录过期，请重新登录")
                    } else {
                        onFail.invoke(value.error_code.toInt(), value.reason)
                    }
                }

                override fun onError(e: Throwable) {
                    iBaseView?.dismissLoading()
                    var errorInfo= ""
                    if (e is SocketTimeoutException || e is ConnectException) {
                        errorInfo = "连接失败,请检查网络状况!"
                    } else if (e is JsonParseException) {
                        errorInfo = "数据解析失败"
                    } else {
                        errorInfo = "请求失败"
                    }
                    iBaseView?.showServerErrorTip()
                    showToastBottom(errorInfo)
                }

            })
}

fun <T,P : ITopPresenter> Observable<BaseModle<T>>.mSubscribeList(
        iBaseView: IListView<P>? = null,
        iModel: IModel? = null,
        isRefresh: Boolean,
        onSuccess: (T) -> Unit,
        onFail: (code: Int, msg: String) -> Unit
) {

    subscribeOn(Schedulers.io())   //  指定被观察者的操作在io线程中完成
            .observeOn(AndroidSchedulers.mainThread()) //  指定观察者的操作在main线程中完成
            .subscribe(object : Observer<BaseModle<T>> {
                override fun onComplete() {
                    iBaseView?.dismissLoading()
                }

                override fun onSubscribe(d: Disposable) {
                    iModel?.addDisposable(d)
                    iBaseView?.showLoading("正在加载中...")
                    if (!NetWorkUtils.isNetConnected(LibApplication.appContext)) {
                        iBaseView?.showNetErrorTip()
                        onComplete()
                        if (!isRefresh) {
                            iBaseView?.loadMoreFail(isRefresh)
                        }
                    }
                }

                override fun onNext(value: BaseModle<T>) {
                    if (value.error_code.toInt() == CodeStatus.SUCCESS) {
                        onSuccess.invoke(value.result)
                    } else if (value.error_code.toInt() == CodeStatus.LOGIN_OUT) {//重新登录
                        showToastBottom("登录过期，请重新登录")
                    } else {
                        onFail.invoke(value.error_code.toInt(), value.reason)
                    }
                }

                override fun onError(e: Throwable) {
                    iBaseView?.dismissLoading()
                    var errorInfo= ""
                    if (e is SocketTimeoutException || e is ConnectException) {
                        errorInfo = "连接失败,请检查网络状况!"
                    } else if (e is JsonParseException) {
                        errorInfo = "数据解析失败"
                    } else {
                        errorInfo = "请求失败"
                    }
                    iBaseView?.showServerErrorTip()
                    if (!isRefresh) {
                        iBaseView?.loadMoreFail(isRefresh)
                    }
                }

            })
}

//fun <T : BaseModle<T>, P : ITopPresenter> Observable<T>.mSubscribe(
//        iBaseView: IView<P>? = null
//        , iModel: IModel? = null
//        , msg: String = ""
//        , onSuccess: (T) -> Unit) {
//    this.compose(SchedulerUtils.ioToMain())
//            .subscribe(object : Observer<T> {
//                override fun onComplete() {
//                    iBaseView?.dismissLoading()
//                }
//
//                override fun onSubscribe(d: Disposable) {
//                    iModel?.addDisposable(d)
//                    iBaseView?.showLoading(if (msg.isEmpty()) "请求中..." else msg)
//                    if (!NetWorkUtils.isNetConnected(LibApplication.appContext)) {
//                        showToastBottom("连接失败,请检查网络状况!")
//                        onComplete()
//                    }
//                }
//
//                override fun onNext(t: T) {
//                    if (t.code == CodeStatus.SUCCESS) {
//                        onSuccess.invoke(t)
//                    } else if (t.code == CodeStatus.LOGIN_OUT) {//重新登录
//                val currentActivity = ActivityUtils.currentActivity()
//                UserManager.getInstance().clear()
////                EMClient.getInstance().logout(true)
//                showToastBottom("登录过期，请重新登录")
//                val intent = Intent(currentActivity, LoginActivity::class.java)
//                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                currentActivity?.startActivity(intent)
//                    } else {
//                        if (!t.msg.isNullOrEmpty()) {
//                            t.msg?.let { showToastBottom(it) }
//                        } else {
//                            showToastBottom("请求失败")
//                        }
//                    }
//                }
//
//                override fun onError(e: Throwable) {
//                    iBaseView?.dismissLoading()
//                    if (e is SocketTimeoutException || e is ConnectException) {
//                        showToastBottom("连接失败,请检查网络状况!")
//                    } else if (e is JsonParseException) {
//                        showToastBottom("数据解析失败")
//                    } else {
//                        showToastBottom("请求失败")
//                    }
//                }
//            })
//}

//fun <T : BaseBean, P : ITopPresenter> Observable<T>.listSubcribe(
//        iBaseView: IListView<P>? = null
//        , iModel: IModel? = null
//        , isRefresh: Boolean
//        , isLoadMore: Boolean
//        , onSuccess: (T) -> Unit) {
//    this.compose(SchedulerUtils.ioToMain())
//            .subscribe(object : Observer<T> {
//                override fun onComplete() {}
//                override fun onSubscribe(d: Disposable) {
//                    iModel?.addDisposable(d)
//                    if (!isRefresh && !isLoadMore) {
//                        iBaseView?.mStateView?.showLoading()
//                    }
//                }
//
//                override fun onNext(t: T) {
//
//                    if (t.code == CodeStatus.SUCCESS) {
//                        iBaseView?.mStateView?.showSuccess()
//                        onSuccess.invoke(t)
//                    } else if (t.code == CodeStatus.LOGIN_OUT) {//重新登录
////                    UserManager.getInstance().clear()
////                    showToastBottom("登录过期，请重新登录")
////                    EMClient.getInstance().logout(true)
////                    val intent = Intent(currentActivity, LoginActivity::class.java)
////                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
////                    currentActivity.startActivity(intent)
//                    } else {
//                        iBaseView?.mStateView?.showError()
//                    }
//                }
//
//                override fun onError(e: Throwable) {
//                    if (!isLoadMore) {
//                        iBaseView?.mStateView?.showError()
//                    } else {
//                        iBaseView?.loadMoreFail(isRefresh)
//                    }
//                }
//            })
//}