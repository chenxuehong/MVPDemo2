package com.jaydenxiao.common.ui.view.AutoFitview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by 13198 on 2018/1/8.
 * 高度适配的ImageView
 */

@SuppressLint("AppCompatCustomView")
public class AutoHeightImageView extends ImageView {

    // 图片四个角是否是圆角
    private boolean isRoundCorner = false;
    // 圆角矩形四个角圆角半径
    private float[] radiusArray = {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f};

    public AutoHeightImageView(Context context) {
        super(context, null);
    }

    public AutoHeightImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public AutoHeightImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置四个角的圆角半径
     */
    public void setRadius(float leftTop, float rightTop, float rightBottom, float leftBottom) {
        radiusArray[0] = leftTop;
        radiusArray[1] = leftTop;
        radiusArray[2] = rightTop;
        radiusArray[3] = rightTop;
        radiusArray[4] = rightBottom;
        radiusArray[5] = rightBottom;
        radiusArray[6] = leftBottom;
        radiusArray[7] = leftBottom;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Drawable drawable = getDrawable();
        if (drawable != null) {
            int width = drawable.getMinimumWidth();
            int height = drawable.getMinimumHeight();
            float scale = (float) height / width;

            int widthMeasure = MeasureSpec.getSize(widthMeasureSpec);
            int heightMeasure = (int) (widthMeasure * scale);

            heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightMeasure, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        requestLayout();
        if (isRoundCorner) {
            setRadius(20, 20, 20, 20);
            Path path = new Path();
            path.addRoundRect(new RectF(0, 0, getWidth(), getHeight()),
                    radiusArray, Path.Direction.CW);
            canvas.clipPath(path);
        }
        super.onDraw(canvas);
    }
}