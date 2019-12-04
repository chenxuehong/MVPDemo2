package com.jaydenxiao.common.modle.state

interface IListBean<out T> {
    val list: List<T>
}