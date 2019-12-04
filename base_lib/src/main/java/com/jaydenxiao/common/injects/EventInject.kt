package com.jaydenxiao.common.injects

import org.greenrobot.eventbus.EventBus
import org.jetbrains.annotations.NotNull

/**
 * Created by 13198 on 2018/9/27.
 *
 * @desc eventBus组件
 */

/**
 * @desc 订阅eventbus事件
 */
fun <T> T.register() {
    if (!EventBus.getDefault().isRegistered(this)) {
        EventBus.getDefault().register(this)
    }
}

/**
 * @desc 移除eventbus订阅事件
 */
fun <T> T.unregister() {
    if (EventBus.getDefault().isRegistered(this)) {
        EventBus.getDefault().unregister(this)
    }
}

/**
 * @desc 发送消息给订阅者
 */
fun <T> T.sendEvent(@NotNull obj: Any) {
    EventBus.getDefault().post(obj)
}
