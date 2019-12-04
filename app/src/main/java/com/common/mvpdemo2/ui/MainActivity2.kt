package com.common.mvpdemo2.ui

import android.graphics.Color
import com.common.mvpdemo2.R
import com.jaydenxiao.common.ui.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity2 : BaseActivity() {

    override fun getContentView(): Int = R.layout.activity_main

    override fun hasFinishTransitionAnim(): Boolean {
        return false
    }

    override fun hasEnterTransitionAnim(): Boolean {
        return false
    }

    override fun initData() {
    }

    override fun initView() {

        activity_main_bottomBarLayout.with(this)
                .setViewPager(activity_main_noScrollViewPager)
                .setTextColor(Color.RED, Color.BLACK)
                .setTextSize(14)
                .setSmoothScroll(true)
                .addItemView("直播", R.mipmap.zhibo_l, R.mipmap.zhibo, LiveFragment::class.java)
                .addItemView("商城", R.mipmap.shangcheng_l, R.mipmap.shangcheng, ShoppingFragment::class.java)
                .addItemView("我的", R.mipmap.wo_l, R.mipmap.wo, MineFragment::class.java)
                .addStateListAnimatorForScale(1.0f, 0.88f, 50)
                .setUnreadMsgCountAtIndex(1, 2)
                .build()

    }

}
