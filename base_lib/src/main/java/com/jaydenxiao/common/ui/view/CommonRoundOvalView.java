package com.jaydenxiao.common.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 13198 on 2018/7/22.
 * 通用的view
 * 1.带数字的圆点
 * 2.不带数字的圆点
 * 3.带数字的实心方块
 * 4.不带数字的实心方块
 */

public class CommonRoundOvalView extends View {

    private Paint paint;
    private int dotColor = Color.RED;
    private int textColor = Color.WHITE;

    private String text;

    private Context context;
    private float textSize;
    private Rect bounds;
    private float strokeWidth;

    public static final int VIEW_TYPE_CIRCLE = 100;
    public static final int VIEW_TYPE_RECT = 101;
    public static final int VIEW_TYPE_OVAL = 102;
    public static final int VIEW_TYPE_ROUND_RECT = 103;

    private int view_type;
    private int width = 20;
    private int height = 20;
    // 圆形
    private RectF oval;
    // 圆角矩形四个角圆角半径
    private float[] radiusArray = {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f};

    private int measuredWidth;
    private int measuredHeight;

    public CommonRoundOvalView(Context context) {
        this(context, null);
    }

    public CommonRoundOvalView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonRoundOvalView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {

        // 初始化画笔
        paint = new Paint();
        paint.setAntiAlias(true);

        // 文本大小
        textSize = getDensity() * 16;
        // 文本粗细
        strokeWidth = 2;
        // 用来存储文本宽高信息
        bounds = new Rect();

        // 椭圆oval
        oval = new RectF();

        view_type = VIEW_TYPE_CIRCLE;
    }

    private float getDensity() {
        return context.getResources().getDisplayMetrics().density;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        measuredWidth = getMeasuredWidth();
        measuredHeight = getMeasuredHeight();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(dotColor);
        paint.setStyle(Paint.Style.FILL);

        switch (view_type) {
            case VIEW_TYPE_CIRCLE:  // 圆点
                canvas.drawCircle(measuredWidth / 2f, measuredHeight / 2f, measuredWidth / 2f, paint);
                break;
            case VIEW_TYPE_RECT:    //方形
                canvas.drawRect(0, 0, measuredWidth, measuredHeight, paint);
                break;
            case VIEW_TYPE_OVAL:    //椭圆
                oval.set(0, 0, measuredWidth, measuredHeight);
                canvas.drawOval(oval, paint);
                break;
            case VIEW_TYPE_ROUND_RECT:    //圆角矩形
//                roundRect.set(0, 0, measuredWidth, measuredHeight);
                setRadius(5, 5, 5, 5);
                Path path = new Path();
                path.addRoundRect(new RectF(0, 0, measuredWidth, measuredHeight),
                        radiusArray, Path.Direction.CW);
                canvas.clipPath(path);
                canvas.drawRect(0, 0, measuredWidth, measuredHeight, paint);
//                canvas.drawRect(roundRect, measuredWidth / 2f, measuredHeight / 2f, paint);
                break;
        }

        // 绘制文本
        if (!isEmptyText()) {

            paint.setColor(textColor);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(strokeWidth);
            paint.setStyle(Paint.Style.FILL);
            paint.setTextSize(textSize);
            paint.getTextBounds(text, 0, text.length(), bounds);
            canvas.drawText(text, 0, text.length(), measuredWidth / 2f - bounds.width() / 2f, measuredHeight / 2f + bounds.height() / 2f, paint);
        }

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
    }

    private boolean isEmptyText() {
        return text == null || text.length() == 0;
    }

    /**
     * 设置指示器颜色
     *
     * @param dotColor
     * @return
     */
    public CommonRoundOvalView setDotColor(int dotColor) {
        this.dotColor = dotColor;
        return this;
    }

    /**
     * 设置文本颜色
     *
     * @param textColor
     * @return
     */
    public CommonRoundOvalView setTextColor(int textColor) {
        this.textColor = textColor;
        return this;
    }

    /**
     * 设置文本
     *
     * @param text
     * @return
     */
    public CommonRoundOvalView setText(String text) {
        this.text = text;
        return this;
    }

    /**
     * 设置文本大小
     *
     * @param textSizeDp
     * @return
     */
    public CommonRoundOvalView setTextSize(float textSizeDp) {
        this.textSize = getDensity() * textSizeDp;
        return this;
    }

    /**
     * 设置文本粗细
     *
     * @param strokeWidth
     * @return
     */
    public CommonRoundOvalView setStrokeWidth(float strokeWidth) {
        this.strokeWidth = getDensity() * strokeWidth;
        return this;
    }

    /**
     * 设置宽高的dp
     *
     * @param widthDp
     * @param heightDp
     * @return
     */
    public CommonRoundOvalView setSize(int widthDp, int heightDp) {

        this.width = (int) (getDensity() * widthDp + 0.5f);
        this.height = (int) (getDensity() * heightDp + 0.5f);
        return this;
    }

    /**
     * 设置view的类型：矩形，圆形view
     *
     * @param viewType
     * @return
     */
    public CommonRoundOvalView setViewType(int viewType) {

        this.view_type = viewType;
        return this;
    }

    public CommonRoundOvalView update() {

        reSetSize();
        invalidate();
        return this;
    }

    private void reSetSize() {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (layoutParams != null) {
            layoutParams.width = width;
            layoutParams.height = height;
            setLayoutParams(layoutParams);
        }
    }
}
