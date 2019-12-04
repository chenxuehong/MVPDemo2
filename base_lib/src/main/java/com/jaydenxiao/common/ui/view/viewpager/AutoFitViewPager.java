package com.jaydenxiao.common.ui.view.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Scroller;


/**
 * Created by 13198 on 2018/1/3.
 * 1.自适应高度的ViewPager
 * 2.setScrollble 可以设置是否可以手动滑动
 * 3.setParseLooperListener设置手动滑动时暂停自动轮播监听
 */
public class AutoFitViewPager extends ViewPager {

    private static final String TAG = "AutoFitViewPager";
    private boolean scrollble = true;
    private Context context;
    private Scroller mScroller; // 滑动控件

    public AutoFitViewPager(Context context) {
        this(context, null);
    }

    public AutoFitViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        mScroller = new Scroller(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = 0;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec,
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if (h > height)
                height = h;
        }

        if (height > 0) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
                    MeasureSpec.EXACTLY);
        }

//        Log.i("tag", "height===" + height);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public boolean isScrollble() {

        return scrollble;
    }

    /**
     * 设置viewPager是否可以手动滚动
     *
     * @param scrollble true 可以手动滚动，false不可以手动滚动.默认可以手动滚动
     */
    public void setScrollble(boolean scrollble) {
        this.scrollble = scrollble;
    }
}
