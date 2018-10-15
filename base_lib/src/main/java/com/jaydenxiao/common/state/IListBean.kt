package com.jaydenxiao.common.state

interface IListBean<out T> {
    val list: List<T>
}