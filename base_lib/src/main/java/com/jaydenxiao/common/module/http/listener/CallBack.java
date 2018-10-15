package com.jaydenxiao.common.module.http.listener;

/**
 * Created by 13198 on 2018/9/15.
 */

public interface CallBack<M> {

    void onSuccess(M m);
    void onFail(String errorCode,String errorInfo);
    void onError(String errorInfo);
}
