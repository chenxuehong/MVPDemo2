package com.jaydenxiao.common.ui.view.recyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 13198 on 2018/8/10.
 *
 * @desc 解决ScrollView嵌套RecyclerView导致RecyclerView不显示
 */

public class RecyclerViewFixed extends RecyclerView {
    public RecyclerViewFixed(Context context) {
        super(context);
    }

    public RecyclerViewFixed(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerViewFixed(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int mExpandedSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, mExpandedSpec);
    }
}
