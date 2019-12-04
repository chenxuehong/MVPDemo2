package com.jaydenxiao.common.ui.view;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Created by 13198 on 2018/1/30.
 * 利用这个类来修正viewpager的滑动速度
 * 需要重写startScroll方法，忽略传过来的duration属性，采用自己设置的时间
 */

public class FixedSpeedScroller extends Scroller {

    private int mDuration = 1500;

    public FixedSpeedScroller(Context context) {
        super(context);
    }

    public FixedSpeedScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public FixedSpeedScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {

        // 忽略viewpager传过来的时间，设置自己的时间
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
//        super.startScroll(startX, startY, dx, dy);
        startScroll(startX, startY, dx, dy, mDuration);
    }

    public void setmDuration(int duration) {
        mDuration = duration;
    }

    public int getmDuration() {
        return mDuration;
    }
}
