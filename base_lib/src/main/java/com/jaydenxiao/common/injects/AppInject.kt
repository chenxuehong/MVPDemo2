package com.jaydenxiao.common.injects

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.os.Handler
import com.jaydenxiao.common.BuildConfig
import com.jaydenxiao.common.commonutils.AppManager
import com.jaydenxiao.common.ui.activity.BaseActivity
import com.jaydenxiao.common.ui.LibApplication
import com.jaydenxiao.common.ui.LibApplication.Companion.appResources
import com.jaydenxiao.common.ui.LibApplication.Companion.baseApplication
import com.jaydenxiao.common.ui.LibApplication.Companion.singleHandle
//import com.squareup.leakcanary.LeakCanary

/**
 * Created by 13198 on 2018/9/20.
 */
fun LibApplication.init() {
    initDebug()
    initActivity()
    baseApplication = this
    appResources = baseApplication!!.resources
    singleHandle = Handler(mainLooper)
}

fun LibApplication.initActivity() {
    registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
        override fun onActivityPaused(activity: Activity?) {

        }

        override fun onActivityResumed(activity: Activity?) {
        }

        override fun onActivityStarted(activity: Activity?) {
        }

        override fun onActivityDestroyed(activity: Activity?) {
            if (activity is BaseActivity) {
//                BaseApplication.currentActivity = activity
                AppManager.getInstance().removeActivity(activity)
            }
        }

        override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
        }

        override fun onActivityStopped(activity: Activity?) {
        }

        override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
            if (activity is BaseActivity) {
//                BaseApplication.currentActivity = null
                AppManager.getInstance().addActivity(activity)
            }
        }

    })
}

private fun LibApplication.initDebug() {
//    if (!BuildConfig.DEBUG || LeakCanary.isInAnalyzerProcess(this)) {
//
//        return
//    }
//    LeakCanary.install(this)
}
