package com.jaydenxiao.common.ui.inter

import android.view.View
import com.jaydenxiao.common.modle.state.IListBean
import com.jaydenxiao.common.modle.state.IStateBean

/**
 *
 * @author  XQ Yang
 * @date 5/6/2018  11:54 AM
 */

interface IStateView {
    var bindView: View?

    @ViewState
    var curState: Int
    var errorMsg: String
    var emptyMsg: String
    fun showSuccess()
    fun showError(msg: String? = null)
    fun showEmpty(msg: String? = null)
    fun showLoading()

    @ViewState
    var onStateChanged: ((Int) -> Unit)?

    var onRetry: (() -> Unit)?

    fun <T, DATA : IListBean<T>> getState(bean: IStateBean<T, DATA>?): Int
    fun <T, DATA : IListBean<T>> setData(bean: IStateBean<T, DATA>?)
}

//false表示一个整型是非标记性质的类型，不能通过|和&来连接
//@IntDef(value = *[STATE_SUCCESS.toLong(), STATE_EMPTY.toLong(), STATE_ERROR.toLong(), STATE_LOADING.toLong()], flag = false)
@Retention(AnnotationRetention.SOURCE)
annotation class ViewState

const val STATE_SUCCESS = 0
const val STATE_EMPTY = 1
const val STATE_ERROR = 2
const val STATE_LOADING = 3
