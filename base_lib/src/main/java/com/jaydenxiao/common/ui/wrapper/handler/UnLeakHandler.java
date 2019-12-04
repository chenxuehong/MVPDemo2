package com.jaydenxiao.common.ui.wrapper.handler;

import android.os.Handler;

import java.lang.ref.WeakReference;

/**
 * Created by 13198 on 2018/3/24.
 * 防止内存泄漏
 */

public class UnLeakHandler {

    public static class WeakReferenceHandler<T> extends Handler {

        //把Acitivity用WeakReference管理起来
        private final WeakReference<T> mActivity;

        public WeakReferenceHandler(T activity) {
            mActivity = new WeakReference<T>(activity);
        }
    }
}
