package com.jaydenxiao.common.presenter

import com.jaydenxiao.common.IModel
import com.jaydenxiao.common.ITopView
import java.lang.ref.WeakReference

open class BasePresenterKt<V : ITopView, M : IModel> {
    var vWeakReference: WeakReference<V>? = null
    var mModel: M? = null
}