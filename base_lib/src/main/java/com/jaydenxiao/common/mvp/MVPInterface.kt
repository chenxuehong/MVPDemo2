package com.jaydenxiao.common

/**
 * Description :
 * @author  XQ Yang
 * @date 2018/7/5  11:45
 */
import android.app.Activity
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Context
import android.support.annotation.StringRes
import android.support.v7.widget.RecyclerView
import com.jaydenxiao.common.commonutils.MyLogger
import com.jaydenxiao.common.state.IStateView
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.jetbrains.annotations.NotNull
import java.lang.ref.WeakReference

interface ITopView : LifecycleOwner {
    fun getCtx(): Context?
    fun inited()
    fun finish(resultCode: Int = Activity.RESULT_CANCELED)
    fun showLoading(@NotNull msg: String)
    fun showLoading(@StringRes srtResId: Int)
    fun dismissLoading()
    fun showToast(@StringRes srtResId: Int)
    fun showToast(@NotNull message: String)
    fun showNetErrorTip(@NotNull error: String)
}

interface ITopPresenter : LifecycleObserver {
    fun attachView(view: ITopView)
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun detachView()
}


interface ITopModel {
    fun onDetach()
}

interface IView<P : ITopPresenter> : ITopView {
    var mPresenter: P?

    override fun inited() {
        mPresenter!!.attachView(this)
    }
}

interface IPresenter<M : IModel> : ITopPresenter {
    var vWeakReference: WeakReference<ITopView>?
    var mModel: M?

    fun getContext() = getView()?.getCtx()
    @Suppress("UNCHECKED_CAST")
    override fun attachView(view: ITopView) {
        vWeakReference = WeakReference(view)
        getView()?.lifecycle?.addObserver(this)
    }

    fun getView(): ITopView? {
        checkViewAttached()
        return vWeakReference?.get()
    }

    override fun detachView() {
        mModel?.onDetach()
        mModel = null
        checkViewAttached()
        vWeakReference!!.clear()
        vWeakReference = null
    }

    //判断是否初始化View
    private val isViewAttached: Boolean
        get() = vWeakReference != null && vWeakReference!!.get() != null

    private fun checkViewAttached() {
        if (!isViewAttached) throw MvpViewNotAttachedException()
    }

    private class MvpViewNotAttachedException internal constructor() : RuntimeException("Please call IPresenter.attachView(IBaseView) before" + " requesting data to the IPresenter")

}

interface IModel : ITopModel {
    var mDisposablePool: CompositeDisposable

    fun addDisposable(disposable: Disposable) {
        mDisposablePool.add(disposable)
    }

    override fun onDetach() {
        MyLogger.e("IModel onDetach ", "走了吗")
        if (!mDisposablePool.isDisposed) {
            mDisposablePool.clear()
        }
    }
}

interface IListView<P : ITopPresenter> : IView<P> {
    val mRecyclerView: RecyclerView?
    /*val mStateView: IStateView?*/
    val mRefreshLayout: SmartRefreshLayout?
    fun loadMoreFail(isRefresh: Boolean)
}