package com.jaydenxiao.common.ui.view.recyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.jaydenxiao.common.commonutils.GlideImageLoader.GlideImageLoader;


/**
 * Created by 13198 on 2018/8/8.
 * 加载图片流畅度优化：停止滑动时加载图片的优化
 */

public class LoadIconRecyclerViewWhenUnScrolling extends RecyclerView {

    private boolean sIsScrolling = true;

    public LoadIconRecyclerViewWhenUnScrolling(Context context) {
        this(context, null);
    }

    public LoadIconRecyclerViewWhenUnScrolling(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadIconRecyclerViewWhenUnScrolling(final Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == SCROLL_STATE_IDLE) {
                    GlideImageLoader.resumeRequest(context);
                    sIsScrolling = false;
                } else {

                    if (sIsScrolling)
                        GlideImageLoader.pauseRequest(context);
                    sIsScrolling = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });
    }

}
