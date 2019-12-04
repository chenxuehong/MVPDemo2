package com.jaydenxiao.common.injects

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.scwang.smartrefresh.layout.SmartRefreshLayout

/**
 * recyclerView
 */
fun RecyclerView.vertical() {
    layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
}

fun RecyclerView.horizontal() {
    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
}

fun RecyclerView.vertical(spanCount: Int) {
    layoutManager = GridLayoutManager(context, spanCount, GridLayoutManager.VERTICAL, false)
}

fun RecyclerView.horizontal(spanCount: Int) {
    layoutManager = GridLayoutManager(context, spanCount, GridLayoutManager.HORIZONTAL, false)
}

fun SmartRefreshLayout.setOnLoadMore(onLoadMoreFinish: () -> Unit) {
    setEnableLoadMore(true)
    // 加载更多
    if (onLoadMoreFinish != null) {
        setOnLoadMoreListener {
            onLoadMoreFinish.invoke()
            finishLoadMore(300)
        }
    }
}

fun SmartRefreshLayout.setOnRefresh(onRefreshFinish: () -> Unit) {
    setEnableRefresh(true)
    // 刷新
    if (onRefreshFinish != null) {
        setOnRefreshListener {
            onRefreshFinish.invoke()
            finishRefresh(300)
        }
    }
}



