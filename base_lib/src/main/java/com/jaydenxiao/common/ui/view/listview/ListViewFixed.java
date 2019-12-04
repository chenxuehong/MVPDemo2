package com.jaydenxiao.common.ui.view.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by 13198 on 2018/8/10.
 *
 * @desc 解决ScrollView嵌套ListView的冲突
 */
//=====================================  使用说明   ===============================================
//  1、scrollView嵌套ListView导致ListView只能显示1行
//     重写onMesaure方法，配置：
//     @Override
//     protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//         int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
//         super.onMeasure(widthMeasureSpec, mExpandSpec);
//       }
//
//  2、scrollView嵌套ListView导致ListView抢占焦点，而ListView时ScrollView中第二个ChildView，scrollView没有滑动到顶部。
//  两种解决方案：
//  （1）第一种在布局中配置
//     给ScrollView的顶部ChildView布局中设置获取焦点
//          android:focusable="true"
//         android:focusableInTouchMode="true"
//  （2）第二种在代码中设置
//      mScrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//             @Override public void onGlobalLayout() {
//                 if (mScrollView != null && mScrollView.getScrollY() != 0) {
//                     mScrollView.scrollTo(0, 0);
//                        Log.i("onGlobalLayout", "scrollTo 被执行了！");
//                        mScrollView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                  }
//              }
//       });
//


public class ListViewFixed extends ListView {
    public ListViewFixed(Context context) {
        super(context);
    }

    public ListViewFixed(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewFixed(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, mExpandSpec);
    }
}
