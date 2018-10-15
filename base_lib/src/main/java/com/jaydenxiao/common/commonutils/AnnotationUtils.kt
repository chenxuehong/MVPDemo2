package com.jaydenxiao.common.commonutils

import org.greenrobot.eventbus.EventBus

/**
 * Created by 13198 on 2018/3/19.
 * 三方注解工具的封装
 */

class AnnotationUtils private constructor() {

    /**
     * 注销EventBus
     *
     * @param object
     */
    fun unregisterEventBus(`object`: Any) {

        val aDefault = EventBus.getDefault()
        if (aDefault.isRegistered(`object`)) {

            aDefault.unregister(`object`)
        }
    }

    /**
     * 注册EventBus
     *
     * @param object
     */
    fun registerEventBus(`object`: Any) {

        val aDefault = EventBus.getDefault()
        if (!aDefault.isRegistered(`object`)) {

            aDefault.register(`object`)
        }
    }

    companion object {

        private var instance: AnnotationUtils? = null

        val default: AnnotationUtils
            get() {

                if (instance == null) {

                    synchronized(AnnotationUtils::class.java) {
                        if (instance == null) {

                            instance = AnnotationUtils()
                        }
                    }
                }

                return instance!!
            }
    }
}
