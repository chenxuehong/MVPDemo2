package com.jaydenxiao.common.ui.view.scrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ScrollView;

/**
 * Created by 13198 on 2018/8/10.
 *
 * @desc 滑动拦截的ScrollView
 * 主要为了解决ScrollView嵌套RecyclerView的冲突（RecyclerView滑动卡顿）
 */

public class ScrollInterceptScrollView extends ScrollView {
    private final int mTouchSlop;
    private int downX;
    private int downY;

    public ScrollInterceptScrollView(Context context) {
        this(context, null);
    }

    public ScrollInterceptScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollInterceptScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) ev.getRawX();
                downY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) ev.getRawY();
                // 判断是否滑动，若滑动就拦截事件
                if (Math.abs(moveY - downY) > mTouchSlop) {
                    return true;
                }
                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
