package com.jaydenxiao.common.base

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.os.Handler
import android.support.multidex.MultiDex

import com.squareup.leakcanary.LeakCanary

/**
 * APPLICATION
 */
open class LibApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        baseApplication = this
        singleHandle = Handler(mainLooper)
        if (AppConfig.DEBUG && LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
    }

    override fun onTerminate() {
        super.onTerminate()
    }

    /**
     * 分包
     *
     * @param base
     */
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    companion object {

        private var baseApplication: LibApplication? = null
        var singleHandle: Handler? = null
            private set

        val appContext: Context?
            get() = baseApplication

        val appResources: Resources
            get() = baseApplication!!.resources
    }

}
