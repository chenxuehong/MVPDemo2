package com.jaydenxiao.common.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import com.jaydenxiao.common.R;
import com.jaydenxiao.common.commonutils.GlideImageLoader.GlideImageLoader;
import com.jaydenxiao.common.ui.view.viewpager.AutoFitViewPager;
import com.jaydenxiao.common.ui.view.AutoFitview.AutoHeightImageView;

import java.lang.reflect.Field;

/**
 * Created by 13198 on 2018/7/23.
 */

/*#################### 使用实例 ##############################*/
//    @Override
//    protected void initView() {
//
//             banner.setIndicatorColor(Color.RED, Color.GRAY)
//                .setIndicatorType(Banner.TYPE_CICLE_RECT, Banner.TYPE_CICLE)
//                .setIndicatorSize(15, 6, 6, 6)
//                .setIndicatorOffset(5)
//                .setAdViewPager(60, 1200, 3000)
//                .setData(R.drawable.b, R.drawable.b, R.drawable.b)
//                .addOnItemClickListener(new Banner.OnItemClickListener() {
//                    @Override
//                    public void onItemClicked(ViewGroup parent, int position) {
//                        Toast.makeText(getContext(), "" + position, Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        if (banner != null) {
//            banner.startLoopViewPager();
//        }
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        if (banner != null) {
//            banner.stopLoopViewPager();
//        }
//    }
//
//    @Override
//    public void onDestroyView() {
//        if (banner != null)
//            banner.destoryLoopViewPager();
//        super.onDestroyView();
//    }


/**
 * 对轮播广告封装
 */
public class Banner extends RelativeLayout {


    private final String VIEWPAGER_PAGERNAME = "android.support.v4.view.ViewPager";
    private Context context;

    private boolean isLoop = false;//轮播开关
    /*=========================== viewpager轮播图相关属性 =====================================*/
    // 轮播图容器
    private AutoFitViewPager autoFitViewPager;
    // 轮播图容器适配器
    private BannerAdapter bannerAdapter;
    // 轮播图片数据
    private Object[] data;
    // 轮播的延迟时间
    private long delayMillis;
    // 使用默认的轮播速度
    public static final int VP_SWITCH_DEFAULT_SPEED = -1;
    // viewpager展示的内容距离两端的距离
    private int pageMargin;
    // 当前轮播图位置索引
    private int currentPosition;
    // 图片四个角是否是圆角
    private boolean isRoundCorner;
    /*================================ viewpager轮播图相关属性 ====================================*/


    /*===================================指示器相关属性===========================================*/
    // 指示器容器
    private LinearLayout llIndicators;
    // 指示器宽高
    private int selectedIndicatorWidth = 12;
    private int selectedIndicatorHeight = 12;
    private int unSelectedIndicatorWidth = 12;
    private int unSelectedIndicatorHeight = 12;

    // 指示器之间的间距
    private int indicatorOffset = 10;

    // 指示器View的类型
    public static final int TYPE_CICLE = 101;
    public static final int TYPE_RECT = 102;
    public static final int TYPE_OVAL = 103;
    public static final int TYPE_CICLE_RECT = 104;

    private int selectedIndicatorType = TYPE_CICLE;
    private int unSelectedIndicatorType;

    private int indicatorSelectedColor = Color.RED;
    private int indicatorUnSelectedColor = Color.GRAY;
    /*==================================指示器相关属性===========================================*/

    // 轮播发送器
    private Handler handler = new Handler();
    // 轮播任务
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // 要做的事
            currentPosition++;
            handler.removeCallbacks(this);
            if (autoFitViewPager.getChildCount() > 0) {
                autoFitViewPager.setCurrentItem(currentPosition, true);
                handler.postDelayed(this, delayMillis);
            }
        }
    };

    public Banner(Context context) {
        this(context, null);
    }

    public Banner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Banner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.banner_item_layout, this);
        autoFitViewPager = (AutoFitViewPager) findViewById(R.id.banner_item_layout_autofit_viewpager);
        llIndicators = (LinearLayout) findViewById(R.id.ll_indicators);
        bannerAdapter = new BannerAdapter();
        autoFitViewPager.setAdapter(bannerAdapter);
        autoFitViewPager.setOffscreenPageLimit(2);
        autoFitViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
                autoFitViewPager.setCurrentItem(currentPosition, true);
                //  把之前的小圆点设置背景为暗红，当前小圆点设置为红色
                switchIndiacor(currentPosition % data.length);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * ViewPager的适配器
     */
    class BannerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return data != null ? data.length * 1000 : 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            // 校准角标
            int realPosition = position % data.length;

//            Log.i("tag", "realPosition" + realPosition);
            // 根据角标取对应图片并显示出来
            Object obj = data[realPosition];
            AutoHeightImageView imageView = new AutoHeightImageView(context);
            imageView.setRadius(40, 40, 40, 40);

            if (!(obj instanceof String)) {

                try {
                    throw new Exception("图片地址必须是String类型");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            String imgUrl = (String) obj;
            GlideImageLoader.display(context, imageView, imgUrl);

            final int finalPosition = realPosition;
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClicked(autoFitViewPager, finalPosition);
                    }
                }
            });
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:

                stopLoopViewPager();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:

                startLoopViewPager();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 设置指示器的选中和未选中颜色
     *
     * @param indicatorSelectedColor
     * @param indicatorUnSelectedColor
     * @return
     */
    public Banner setIndicatorColor(int indicatorSelectedColor, int indicatorUnSelectedColor) {
        this.indicatorSelectedColor = indicatorSelectedColor;
        this.indicatorUnSelectedColor = indicatorUnSelectedColor;
        return this;
    }

    /**
     * 设置指示器的类型
     *
     * @param selectedIndicatorType
     * @param unSelectedIndicatorType
     * @return
     */
    public Banner setIndicatorType(int selectedIndicatorType, int unSelectedIndicatorType) {

        this.selectedIndicatorType = selectedIndicatorType;
        this.unSelectedIndicatorType = unSelectedIndicatorType;
        return this;
    }

    /**
     * 指示器的选中和未选中时的宽高
     *
     * @param selectedIndicatorWidth
     * @param selectedIndicatorHeight
     * @param unSelectedIndicatorWidth
     * @param unSelectedIndicatorHeight
     * @return
     */
    public Banner setIndicatorSize(int selectedIndicatorWidth, int selectedIndicatorHeight, int unSelectedIndicatorWidth, int unSelectedIndicatorHeight) {

        this.selectedIndicatorWidth = selectedIndicatorWidth;
        this.selectedIndicatorHeight = selectedIndicatorHeight;
        this.unSelectedIndicatorWidth = unSelectedIndicatorWidth;
        this.unSelectedIndicatorHeight = unSelectedIndicatorHeight;
        return this;
    }

    /**
     * 指示器的间距
     *
     * @param indicatorOffset
     * @return
     */
    public Banner setIndicatorOffset(int indicatorOffset) {
        if (indicatorOffset < 0) {

            throw new IllegalArgumentException("indicatorOffset must be more than zero");
        }
        this.indicatorOffset = indicatorOffset;
        return this;
    }

    /**
     * 设置viewPager的参数
     *
     * @param pageMargin
     * @param vpSwitchSpeed
     * @param delayMillis
     * @return
     */
    public Banner setAdViewPager(int pageMargin, int vpSwitchSpeed, int delayMillis) {
        this.pageMargin = pageMargin;
        if (vpSwitchSpeed != VP_SWITCH_DEFAULT_SPEED) {

            fixedViewPagerSpeed(vpSwitchSpeed);
        }
        this.delayMillis = delayMillis;
        return this;
    }

    /**
     * 修改viewpager滑动的速度
     *
     * @param duration
     * @return
     */
    public void fixedViewPagerSpeed(int duration) {

        if (autoFitViewPager != null) {

            try {
                Class<?> clazz = Class.forName(VIEWPAGER_PAGERNAME);
                Field f = clazz.getDeclaredField("mScroller");
                FixedSpeedScroller fixedSpeedScroller = new FixedSpeedScroller(context, new LinearOutSlowInInterpolator());
                fixedSpeedScroller.setmDuration(duration);
                f.setAccessible(true);
                f.set(autoFitViewPager, fixedSpeedScroller);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 设置轮播图地址
     *
     * @param data 可以是网络请求的url，也可以是本地的drawable
     */
    public Banner setData(Object... data) {

        this.data = data;
        // 第一次显示第二页的图片
        currentPosition = bannerAdapter.getCount() / 2;
        initIndicators();
        refreshAdapter();
        initVP();
        return this;
    }

    private void switchIndiacor(int index) {

        if (llIndicators != null) {
            int childCount = llIndicators.getChildCount();
            for (int i = 0, j = childCount; i < j; i++) {

                View view = llIndicators.getChildAt(i);
                if (index == i) {

                    updateSelectedIndicatorByIndicatorType(selectedIndicatorType, view);
                } else {

                    updateIndicatorByIndicatorType(unSelectedIndicatorType, view);
                }
            }
        }
    }

    /**
     * 根据指示器类型更新未选中的指示器视图
     *
     * @param unSelectedIndicatorType
     * @param view
     */
    private void updateIndicatorByIndicatorType(int unSelectedIndicatorType, View view) {

        CommonRoundOvalView commonRoundOvalView = ((CommonRoundOvalView) view.findViewById(R.id.tab_indicator_commRectView))
                .setSize(unSelectedIndicatorWidth, unSelectedIndicatorHeight)
                .setDotColor(indicatorUnSelectedColor);
        switch (unSelectedIndicatorType) {
            case TYPE_CICLE:
                commonRoundOvalView.setViewType(CommonRoundOvalView.VIEW_TYPE_CIRCLE);
                break;
            case TYPE_RECT:
                commonRoundOvalView.setViewType(CommonRoundOvalView.VIEW_TYPE_RECT);
            case TYPE_OVAL:
                commonRoundOvalView.setViewType(CommonRoundOvalView.VIEW_TYPE_OVAL);
            case TYPE_CICLE_RECT:
                commonRoundOvalView.setViewType(CommonRoundOvalView.VIEW_TYPE_ROUND_RECT);
                break;
        }
        commonRoundOvalView.update();
    }

    /**
     * 根据指示器类型更新选中的指示器视图
     *
     * @param selectedIndicatorType
     * @param view
     */
    private void updateSelectedIndicatorByIndicatorType(int selectedIndicatorType, View view) {

        CommonRoundOvalView commonRoundOvalView = ((CommonRoundOvalView) view.findViewById(R.id.tab_indicator_commRectView))
                .setSize(selectedIndicatorWidth, selectedIndicatorHeight)
                .setDotColor(indicatorSelectedColor);
        switch (selectedIndicatorType) {
            case TYPE_CICLE:
                commonRoundOvalView.setViewType(CommonRoundOvalView.VIEW_TYPE_CIRCLE);
                break;
            case TYPE_RECT:
                commonRoundOvalView.setViewType(CommonRoundOvalView.VIEW_TYPE_RECT);
            case TYPE_OVAL:
                commonRoundOvalView.setViewType(CommonRoundOvalView.VIEW_TYPE_OVAL);
            case TYPE_CICLE_RECT:
                commonRoundOvalView.setViewType(CommonRoundOvalView.VIEW_TYPE_ROUND_RECT);
                break;
        }
        commonRoundOvalView.update();
    }

    /**
     * 初始化指示器
     */
    private void initIndicators() {
        clearIndicators();
        if (llIndicators != null) {
            if (data != null && data.length > 0) {
                for (int i = 0, j = data.length; i < j; i++) {

                    View view = View.inflate(context, R.layout.tab_indicator_layout, null);
                    CommonRoundOvalView commonRectView = view.findViewById(R.id.tab_indicator_commRectView);
                    int realPosition = currentPosition % data.length;

                    if (realPosition == i) {
                        updateSelectedIndicatorByIndicatorType(selectedIndicatorType, commonRectView);
                    } else {
                        updateIndicatorByIndicatorType(unSelectedIndicatorType, commonRectView);
                    }

                    LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    llParams.leftMargin = dp2px(indicatorOffset);
                    llIndicators.addView(view, llParams);
                }
            }
        }
    }

    private void clearIndicators() {
        if (llIndicators != null) {

            llIndicators.removeAllViews();
        }
    }

    private void initVP() {

        if (autoFitViewPager != null) {

            LayoutParams layoutParams = (LayoutParams) autoFitViewPager.getLayoutParams();
            layoutParams.leftMargin = dp2px(pageMargin);
            layoutParams.rightMargin = dp2px(pageMargin);
            autoFitViewPager.setLayoutParams(layoutParams);
            autoFitViewPager.setCurrentItem(currentPosition, true);
            if (pageMargin > 0)
                autoFitViewPager.setPageTransformer(false, new TransFormer());
        }
    }

    private void refreshAdapter() {
        if (bannerAdapter != null) {

            bannerAdapter.notifyDataSetChanged();
        }
    }

    private int dp2px(int dp) {
        if (context == null) {
            throw new IllegalArgumentException("context is null");
        }
        return (int) (context.getResources().getDisplayMetrics().density * dp + 0.5f);
    }

    public class TransFormer implements ViewPager.PageTransformer {
        public float MIN_ALPHA = 0.5f;
        public float MIN_SCALE = 0.8f;

        @Override
        public void transformPage(View page, float position) {
            if (position < -1 || position > 1) {
//                page.setAlpha(MIN_ALPHA);
                page.setScaleX(MIN_SCALE);
                page.setScaleY(MIN_SCALE);
//                Log.i("info", "缩放：position < -1 || position > 1");
            } else if (position <= 1) { // [-1,1]
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                if (position < 0) {
                    float scaleX = 1 + 0.2f * position;
//                    Log.i("info", "缩放：position < 0");
                    page.setScaleX(scaleX);
                    page.setScaleY(scaleX);
                } else {
                    float scaleX = 1 - 0.2f * position;
                    page.setScaleX(scaleX);
                    page.setScaleY(scaleX);
//                    Log.i("info", "缩放：position <= 1 >=0");
                }
                page.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA));
            }
        }
    }

    private OnItemClickListener onItemClickListener;

    public void addOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {

        void onItemClicked(ViewGroup parent, int position);
    }

    /**
     * 开始自动轮播
     */
    public void startLoopViewPager() {
        if (!isLoop && autoFitViewPager != null && handler != null) {
            handler.postDelayed(runnable, delayMillis);// 每两秒执行一次runnable.
            isLoop = true;
        }

    }

    /**
     * 停止自动轮播
     */
    public void stopLoopViewPager() {
        if (isLoop && autoFitViewPager != null && handler != null) {
            handler.removeCallbacks(runnable);
            isLoop = false;
        }
    }

    /**
     * 销毁自动轮播
     */
    public void destoryLoopViewPager() {

        if (isLoop && autoFitViewPager != null && handler != null) {
            handler.removeCallbacks(runnable);
            isLoop = false;
            autoFitViewPager = null;
            handler = null;
            if (llIndicators != null) {
                llIndicators.removeAllViews();
                llIndicators = null;
            }

            if (data != null) {
                data = null;
            }
        }

    }
}
