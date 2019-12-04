package com.jaydenxiao.common.ui.view.bottomBarLayout;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.jaydenxiao.common.R;
import com.jaydenxiao.common.ui.view.CommonRoundOvalView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 13198 on 2018/7/13.
 * <p>
 */
/*

    使用例子：

    private BottomBarLayoutWithVP bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
       bottomBar = findViewById(R.id.bottom_bar);
        NoScrollViewPager viewPager = findViewById(R.id.fl_container);
        bottomBar.with(this)
                .setViewPager(viewPager)
                .setTextColor(Color.RED, Color.BLACK)
                .setTextSize(14)
                .setSmoothScroll(true)
                .addItemView("直播", R.drawable.zhibo_l, R.drawable.zhibo, LiveFragment.class)
                .addItemView("商城", R.drawable.shangcheng_l, R.drawable.shangcheng, ShoppingFragment.class)
                .addItemView("我的", R.drawable.wo_l, R.drawable.wo, MineFragment.class)
                .addStateListAnimatorForScale(1.0f, 0.88f, 50)
                .setUnreadMsgCountAtIndex(1,2)
                .build();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bottomBar != null) {
            bottomBar.clearCache();
        }
    }
 */
public class BottomBarLayoutWithVP extends LinearLayout {
    private Context context;
    private String STATE_ITEM = "state_item";
    private String STATE_INSTANCE = "state_instance";

    // 当前选中页面索引
    private int currentCheckedIndex;

    private ViewPager viewPager;

    // 底部文本颜色
    private int selectedTextColor = Color.BLACK;
    private int unselectedTextColor = Color.RED;
    private int textSize = 14;
    // 平滑切换
    private boolean smoothScroll;
    // 底部导航栏背景颜色
    private int bottomLayoutColor = Color.WHITE;
    // 缩放前后的百分比集合
    private float[] scaleArr;
    // 动画执行周期
    private int duration;
    List<Fragment> fragmentList = new ArrayList<>();
    List<String> titles = new ArrayList<>();
    List<Integer> selectedImgIdList = new ArrayList<>();
    List<Integer> unselectedImgIdList = new ArrayList<>();
    Map<Integer, Integer> msgCountMapByIndex = new HashMap<>();

    public BottomBarLayoutWithVP(Context context) {
        this(context, null);
    }

    public BottomBarLayoutWithVP(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomBarLayoutWithVP(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
    }

    public BottomBarLayoutWithVP with(Context context) {
        this.context = context;
        return this;
    }

    /**
     * 设置viewPager
     *
     * @param viewPager
     * @return
     */
    public BottomBarLayoutWithVP setViewPager(ViewPager viewPager) {

        this.viewPager = viewPager;
        return this;
    }

    /**
     * 设置底部导航栏背景色
     *
     * @param bottomLayoutColor
     */
    public BottomBarLayoutWithVP setBottomLayoutBackgroundColor(int bottomLayoutColor) {

        this.bottomLayoutColor = bottomLayoutColor;
        return this;
    }

    /**
     * 设置选中和未选中时文本颜色
     *
     * @param selectedTextColor
     * @param unselectedTextColor
     */
    public BottomBarLayoutWithVP setTextColor(int selectedTextColor, int unselectedTextColor) {

        this.selectedTextColor = selectedTextColor;
        this.unselectedTextColor = unselectedTextColor;
        return this;
    }

    /**
     * 设置文本大小
     *
     * @param textSize
     * @return
     */
    public BottomBarLayoutWithVP setTextSize(int textSize) {

        this.textSize = textSize;
        return this;
    }

    /**
     * 添加底部item
     *
     * @param title
     * @param selectedImgId
     * @param unselectedImgId
     * @param fragmentClass
     * @return
     */
    public BottomBarLayoutWithVP addItemView(String title, int selectedImgId, int unselectedImgId, Class<? extends Fragment> fragmentClass) {

        addFragmentByFragmentClass(fragmentClass);
        addItemTitle(title);
        addSelectedImgId(selectedImgId);
        addUnSelectedImgId(unselectedImgId);
        return this;
    }

    /**
     * 根据fragment地class字节码创建一个fragment对象
     *
     * @param fragmentClass
     * @return
     */
    private void addFragmentByFragmentClass(Class<? extends Fragment> fragmentClass) {
        Fragment fragment = createFragment(fragmentClass);
        if (fragmentList != null && fragment != null && !fragmentList.contains(fragment)) {

            fragmentList.add(fragment);
        }
    }

    /**
     * 调用fragmentClass.newInstance()方法创建Fragment
     *
     * @param fragmentClass
     * @return
     */
    private Fragment createFragment(Class<? extends Fragment> fragmentClass) {

        try {
            Fragment fragment = fragmentClass.newInstance();
            return fragment;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 添加item标题
     *
     * @param title
     */
    private void addItemTitle(String title) {

        if (titles != null && title != null && !titles.contains(title)) {

            titles.add(title);
        }
    }

    /**
     * 添加未选中图片的resid
     *
     * @param selectedImgId
     */
    private void addSelectedImgId(int selectedImgId) {

        if (selectedImgIdList != null && selectedImgId != -1 && !selectedImgIdList.contains(selectedImgId)) {

            selectedImgIdList.add(selectedImgId);
        }
    }

    /**
     * 添加选中图片的resid
     *
     * @param unselectedImgId
     */
    private void addUnSelectedImgId(int unselectedImgId) {

        if (unselectedImgIdList != null && unselectedImgId != -1 && !unselectedImgIdList.contains(unselectedImgId)) {
            unselectedImgIdList.add(unselectedImgId);
        }
    }

    /**
     * viewpager是否平滑切换
     *
     * @param smoothScroll
     * @return
     */
    public BottomBarLayoutWithVP setSmoothScroll(boolean smoothScroll) {

        this.smoothScroll = smoothScroll;
        return this;
    }


    /**
     * 给底部导航栏添加动画选择器
     *
     * @param startScale
     * @param endScale
     * @param duration   动画执行时间
     * @return
     */
    public BottomBarLayoutWithVP addStateListAnimatorForScale(float startScale, float endScale, int duration) {

        this.scaleArr = new float[]{startScale, endScale};
        this.duration = duration;
        return this;
    }

    /**
     * 设置初始化状态
     */
    public void build() {

        currentCheckedIndex = 0;
        if (getChildCount() > 0)
            removeAllViews();

        letBottomSwitchFellowVP();
        initBottomLayout();
        initTopFrgaments();
    }

    private void letBottomSwitchFellowVP() {
        if (viewPager != null) {
            // 监听fragment的切换
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {

                    // 让底部状态随着viewpager更新
                    currentCheckedIndex = position;
                    changeBottomItemView();
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
    }

    private float getDensity() {
        return context.getResources().getDisplayMetrics().density;
    }


    private void initBottomLayout() {
        if (context != null && titles != null) {

            if (bottomLayoutColor != -1) {

                setBackgroundColor(bottomLayoutColor);
            }
            int lenth = titles.size();
            setWeightSum(lenth);
            // 将itemView添加到LinearLayout上
            for (int i = 0; i < lenth; i++) {

                View itemBottomView = View.inflate(context, R.layout.layout_item_bottom, null);
                LayoutParams llParams = new LayoutParams(0, LayoutParams.WRAP_CONTENT);
                llParams.weight = 1;

                updateItemMsgView(i, itemBottomView);
                setItemBottom(i, itemBottomView);
                addView(itemBottomView, llParams);
            }
        }
    }

    /**
     * 更新底部未读消息提示view
     *
     * @param i
     * @param itemBottomView
     */
    private void updateItemMsgView(int i, View itemBottomView) {
        CommonRoundOvalView commonRoundOvalView = itemBottomView.findViewById(R.id.item_bottom_cycle_point);
        if (msgCountMapByIndex.containsKey(i)) {

            commonRoundOvalView.setVisibility(View.VISIBLE);
            Integer integer = msgCountMapByIndex.get(i);
            int currentMsgViewType = integer > 10 ? CommonRoundOvalView.VIEW_TYPE_OVAL : CommonRoundOvalView.VIEW_TYPE_CIRCLE;

            int widthDp = 15 + (integer > 10 ? 5 : 0);
            int heightDp = 15;
            String msgCount = integer == 0 ? "" : String.valueOf(integer);
            commonRoundOvalView.setViewType(currentMsgViewType)
                    .setSize(widthDp, heightDp)
                    .setDotColor(Color.RED)
                    .setText(msgCount)
                    .setTextColor(Color.WHITE)
                    .setTextSize(12)
                    .setStrokeWidth(2)
                    .update();
        } else {
            commonRoundOvalView.setVisibility(View.GONE);
        }
    }

    /**
     * 设置底部itemBottom
     *
     * @param i
     * @param itemBottomView
     */
    private void setItemBottom(int i, View itemBottomView) {
        ImageView iv = (ImageView) itemBottomView.findViewById(R.id.item_bottom_iv_icon);
        TextView tv = (TextView) itemBottomView.findViewById(R.id.item_bottom_tv_title);

        if (i == 0) {

            iv.setImageResource(selectedImgIdList.get(0));
            tv.setTextColor(selectedTextColor);
        } else {
            tv.setTextColor(unselectedTextColor);
            iv.setImageResource(unselectedImgIdList.get(i));
        }

        if ("".equals(titles.get(i))) {
            tv.setVisibility(View.GONE);
        }

        tv.setText(titles.get(i));
        tv.setTextSize(textSize);
        setOnTouchListenerForBottomItemView(i, itemBottomView);
    }

    /**
     * 设置对应位置未读消息数量
     *
     * @param msgIndex
     * @param msgCount
     * @return
     */
    public BottomBarLayoutWithVP setUnreadMsgCountAtIndex(int msgIndex, int msgCount) {

        msgCountMapByIndex.put(msgIndex, msgCount);
        return this;
    }

    private void setOnTouchListenerForBottomItemView(final int i, final View itemBottomView) {

        // 注意必须设置clickable为true，否则触摸事件的up手指抬起事件不执行。
        itemBottomView.setClickable(true);
        itemBottomView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                currentCheckedIndex = i;
                changeBottomItemView();
                setViewPagerScrollToCurrentIndex();
            }
        });
        itemBottomView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        if (scaleArr != null && scaleArr.length == 2) {

                            startAnimor(scaleArr[0], scaleArr[1], itemBottomView);
                        }
                        break;
                    case MotionEvent.ACTION_UP:

                        if (scaleArr != null && scaleArr.length == 2) {

                            startAnimor(scaleArr[1], scaleArr[0], itemBottomView);
                        }
                        break;
                }
                return false;
            }
        });
    }

    private void startAnimor(float startScaleVaule, float endScaleVaule, final View itemBottomView) {
        ValueAnimator animator = ValueAnimator.ofFloat(startScaleVaule, endScaleVaule);
        animator.setDuration(duration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float scaleValue = (float) animation.getAnimatedValue();

                itemBottomView.setScaleX(scaleValue);
                itemBottomView.setScaleY(scaleValue);
            }
        });
        animator.start();
    }

    private void initTopFrgaments() {

        if (context instanceof FragmentActivity) {

            viewPager.setAdapter(new FragmentStatePagerAdapter(((FragmentActivity) context).getSupportFragmentManager()) {
                @Override
                public int getCount() {
                    return fragmentList != null ? fragmentList.size() : 0;
                }

                @Override
                public Fragment getItem(int position) {

                    if (fragmentList != null) {
                        Fragment fragment = fragmentList.get(position);
                        if (fragment != null) {
                            return fragment;
                        }
                    }
                    return null;
                }
            });

            setViewPagerScrollToCurrentIndex();
        }

    }

    private void setViewPagerScrollToCurrentIndex() {
        if (viewPager != null)
            viewPager.setCurrentItem(currentCheckedIndex, smoothScroll);
    }

    /**
     * 设置被点击的view选中,其他view未被选中
     */
    private void changeBottomItemView() {

        int childCount = getChildCount();

        for (int i = 0, j = childCount; i < j; i++) {

            View childView = getChildAt(i);
            if (i == currentCheckedIndex) {

                setViewChecked(childView, selectedImgIdList.get(i));
            } else {
                setViewUnChecked(childView, unselectedImgIdList.get(i));
            }
        }
    }

    private void setViewUnChecked(View childView, Integer unselectedImgId) {

        if (childView instanceof ViewGroup) {
            int childCount = ((ViewGroup) childView).getChildCount();
            for (int i = 0; i < childCount; i++) {

                View newChildView = ((ViewGroup) childView).getChildAt(i);
                if (newChildView instanceof ImageView) {

                    ((ImageView) newChildView).setImageResource(unselectedImgId);
                } else if (newChildView instanceof TextView) {

                    ((TextView) newChildView).setTextColor(unselectedTextColor);
                }
            }
        }
    }

    private void setViewChecked(View childView, Integer selectedImgId) {

        if (childView instanceof ViewGroup) {
            int childCount = ((ViewGroup) childView).getChildCount();
            for (int i = 0; i < childCount; i++) {

                View newChildView = ((ViewGroup) childView).getChildAt(i);
                if (newChildView instanceof ImageView) {

                    ((ImageView) newChildView).setImageResource(selectedImgId);
                } else if (newChildView instanceof TextView) {

                    ((TextView) newChildView).setTextColor(selectedTextColor);
                }
            }
        }
    }

    /**
     * 当view被销毁后，保存数据
     *
     * @return
     */
    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {

        Bundle bundle = new Bundle();
        bundle.putParcelable(STATE_INSTANCE, super.onSaveInstanceState());
        bundle.putInt(STATE_ITEM, currentCheckedIndex);
        return bundle;
    }

    /**
     * 用于恢复数据使用
     *
     * @param state
     */
    @Override
    protected void onRestoreInstanceState(Parcelable state) {

        if (state instanceof Bundle) {

            Bundle bundle = (Bundle) state;
            int currentItem = bundle.getInt(STATE_ITEM);

            currentCheckedIndex = currentItem;
            changeBottomItemView();
            setViewPagerScrollToCurrentIndex();
            super.onRestoreInstanceState(bundle.getParcelable(STATE_INSTANCE));
        } else {
            super.onRestoreInstanceState(state);
        }
    }

    /**
     * 清除集合缓存
     */
    public void clearCache() {

        if (fragmentList != null) {
            fragmentList.clear();
            fragmentList = null;
        }
        if (titles != null) {
            titles.clear();
            titles = null;
        }
        if (selectedImgIdList != null) {
            selectedImgIdList.clear();
            selectedImgIdList = null;
        }
        if (unselectedImgIdList != null) {
            unselectedImgIdList.clear();
            unselectedImgIdList = null;
        }

        if (msgCountMapByIndex != null) {
            msgCountMapByIndex.clear();
            msgCountMapByIndex = null;
        }
    }
}
