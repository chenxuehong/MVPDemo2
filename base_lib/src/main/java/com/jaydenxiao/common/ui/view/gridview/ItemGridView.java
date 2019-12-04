package com.jaydenxiao.common.ui.view.gridview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * Created by 13198 on 2018/3/24.
 *
 * @desc GridView高度自适应
 */

public class ItemGridView extends GridView {
    /*
      一、int expandSpec = MeasureSpec.makeMeasureSpec(
              　　　　　　　　Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
          其实就是根据提供的大小值和模式创建一个测量值(格式)；
          一个MeasureSpec由大小和模式组成。
          它有三种模式：
          UNSPECIFIED(未指定)，父元素不对子元素施加任何束缚，子元素可以得到任意想要的大小；
          EXACTLY(完全)，父元素决定自元素的确切大小，子元素将被限定在给定的边界里而忽略它本身大小；
          AT_MOST(至多)，子元素至多达到指定大小的值。
          这里我们的MeasureSpec.AT_MOST代表高度自适应，也就是GridView能多大就有多大。而”size” 就是提供一个可测量的最大值。我们取Integer的最大值并使用位运算右移两位，是因为：
          MeasureSpec是一个32位的int值，其中高2位为测量的模式，低30位为测量的大小。在计算中使用位运算的原因是为了提高并优化效率。
          在布局中使用自定义高度的GridView发现图片能正确的显示出来了。好吧，第一个坑解决了。


    二、接下来想在ListView中做Item的点击事件，给ListView设置了OnItemClickListener监听，并重写了OnItemClick方法后，
       不知道为什么Item就是点击不了，好像失灵了一样。不过最后在某度的帮助下还是解决了：
          在自己定义的item的布局最外层的layout加入标签 ：
        android:descendantFocusability="blocksDescendants"
        item就可以点击了。如下：
        <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
            android:orientation="vertical"
            android:descendantFocusability="blocksDescendants"
            android:layout_width="match_parent"
            android:layout_height="match_parent">
               ...

        </LinearLayout>

        总结了一下：项目中用到的listview不仅仅是显示简单的文字，有时是自己定义listview，用自己的Adapter去继承BaseAdapter，
        在adapter中按照需求进行编写。而点击每一个item的时候没有反应，无法获取的焦点的原因多半是由于在你自己定义的Item中存在诸如ImageButton，
        Button等子控件(也可以说ImageButton或者Button的子类控件)，此时这些子控件会将焦点获取到，所以常常当点击item时变化的是子控件，item本身的点击没有响应。
        这时候就可以使用descendantFocusability来解决了。
        　　
        其属性值如下：
        beforeDescendants：viewgroup会优先其子类控件而获取到焦点
        afterDescendants：viewgroup只有当其子类控件不需要获取焦点时才获取焦点
        blocksDescendants：viewgroup会覆盖子类控件而直接获得焦点

    三、接下来问题又来了，嵌套了GridView之后，发现无图的空白区域里面没有触点击事件。
        既不能触发Girdview的onItemClick,也不触发Listview的onItemClick怎么回事呢？
        后来发现，原来可以自己去监听GridView的无效区域触发事件的。
     */
    private OnTouchInvalidPositionListener onTouchInvalidPositionListener;

    public ItemGridView(Context context) {
        super(context);
    }

    public ItemGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ItemGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //先创建一个监听接口，一旦点击了无效区域，便实现onTouchInvalidPosition方法，
        // 返回true or false来确认是否消费了这个事件
        if (onTouchInvalidPositionListener != null) {
            if (!isEnabled()) {
                return isClickable() || isLongClickable();
            }
            int motionPosition = pointToPosition((int) ev.getX(), (int) ev.getY());
            if (ev.getAction() == MotionEvent.ACTION_UP && motionPosition == INVALID_POSITION) {
                super.onTouchEvent(ev);
                return onTouchInvalidPositionListener.onTouchInvalidPosition(motionPosition);
            }
        }
        return super.onTouchEvent(ev);
    }

    public void setOnTouchInvalidPositionListener(
            OnTouchInvalidPositionListener onTouchInvalidPositionListener) {
        this.onTouchInvalidPositionListener = onTouchInvalidPositionListener;
    }

    public interface OnTouchInvalidPositionListener {
        boolean onTouchInvalidPosition(int motionEvent);
    }

}
