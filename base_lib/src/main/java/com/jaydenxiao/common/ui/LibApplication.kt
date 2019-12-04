package com.jaydenxiao.common.ui

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.os.Handler
import android.support.multidex.MultiDex
import com.jaydenxiao.common.injects.init

/**
 * APPLICATION
 */
open class LibApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        init()
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
        var baseApplication: LibApplication? = null
        var appResources: Resources  ?= null
        var singleHandle: Handler? = null
    }

}
