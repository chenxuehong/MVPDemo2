package com.exmple.corelib.http.observer

import com.google.gson.JsonParseException
import com.jaydenxiao.common.injects.loge
import com.jaydenxiao.common.injects.showToastBottom
import com.jaydenxiao.common.modle.net.BaseModle
import com.jaydenxiao.common.modle.state.CodeStatus
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.net.ConnectException
import java.net.SocketTimeoutException

/**
 * @FileName: com.mou.demo.http.observer.BaseObserver.java
 * @author: mouxuefei
 * @date: 2017-12-21 16:40
 * @version V1.0 配置了baseModel,状态码统一处理的observer
 * @desc
 */
abstract class BaseModelObserver<T> : Observer<BaseModle<T>> {
    abstract fun onBegin(d: Disposable)
    abstract fun onError()
    abstract fun onHandleSuccess(t: T)
    override fun onSubscribe(d: Disposable) {
        onBegin(d)
    }

    override fun onNext(value: BaseModle<T>) {
        if (value.error_code == CodeStatus.SUCCESS) {
            onHandleSuccess(value.result)
        } else {
            onFail(value)
            onHandleFail(value.error_code, value.reason!!)
        }
    }

    abstract fun onFail(value: BaseModle<T>)

    override fun onError(e: Throwable) {
        showErrorToast(e)
        onError()
    }

    abstract override fun onComplete()

    private fun onHandleFail(code: Int, msg: String) {
        if (!msg.isEmpty()) {
            showToastBottom(msg)
        } else {
            showToastBottom("未知错误")
        }
//        val message: String
//        if (code == CodeStatus.REQUEST_TIME_OUT || code == CodeStatus.REQUEST_TIME_ERROR) {
//            message = "请求超时"
//            showToastBottom(message)
//        }else if (value.error_code.toInt() == CodeStatus.LOGIN_OUT) {//重新登录
//        showToastBottom("登录过期，请重新登录")
//    }else if (code == CodeStatus.SERVER_ERROR) {
//            message = "服务器异常"
//            showToastBottom(message)
//        } else if (code == CodeStatus.PARAMETER_MISSING) {
//            message = "参数缺失"
//            showToastBottom(message)
//        } else if (code == CodeStatus.LOGIN_STATUS_NO_ACCOUNT) {
//            message = "您的手机号码还未注册"
//            showToastBottom(message)
//        } else if (code == CodeStatus.LOGIN_STATUS_ACCOUNT_OR_PASSWORD_ERROR) {
//            message = "账号或密码错误"
//            showToastBottom(message)
//        } else if (code == CodeStatus.VERIFICATION_CODE_ERROR || code == CodeStatus.VERIFICATION_CODE_OUT_TIME) {
//            message = "验证码错误"
//            showToastBottom(message)
//        } else if (code == CodeStatus.ACCOUNT_EXSIT) {
//            message = "您的手机号码已注册"
//            showToastBottom(message)
//        } else if (code == CodeStatus.PIN_ERROR) {
//            message = "PIN码错误"
//
//        }else{
//            codeError(code)
//        }
    }


    private fun showErrorToast(e: Throwable) {
        loge("exception=${e.toString()}")
        if (e is SocketTimeoutException || e is ConnectException) {
            showToastBottom("连接失败,请检查网络状况!")
        } else if (e is JsonParseException) {
            showToastBottom("数据解析失败")
        } else {
            showToastBottom("请求失败")
        }
    }
}