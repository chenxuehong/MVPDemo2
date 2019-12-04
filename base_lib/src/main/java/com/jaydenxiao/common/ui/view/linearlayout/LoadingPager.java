package com.jaydenxiao.common.ui.view.linearlayout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jaydenxiao.common.R;

/**
 * des:加载页面内嵌提示
 * Created by xsf
 * on 2016.07.17:22
 */
public class LoadingPager extends LinearLayout {

    private ImageView img_tip_logo;
    private ProgressBar progress;
    private TextView tv_tips;
    private Button bt_operate;
    private String errorMsg;
    private onReloadListener onReloadListener;
    private  Context context;

    public LoadingPager(Context context) {
        super(context);
        initView(context);
    }

    public LoadingPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public LoadingPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LoadingPager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    //分为服务器失败，网络加载失败、数据为空、加载中、完成四种状态
    public enum LoadStatus {
        sereverError, error, empty, loading, finish
    }

    private void initView(Context context) {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.dialog_loading_tip, this, true);
        img_tip_logo = (ImageView) findViewById(R.id.img_tip_logo);
        progress = (ProgressBar) findViewById(R.id.progress);
        tv_tips = (TextView) findViewById(R.id.tv_tips);
        bt_operate = (Button) findViewById(R.id.bt_operate);
        //重新尝试
        bt_operate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onReloadListener != null) {
                    onReloadListener.reload();
                }
            }
        });
        setVisibility(View.GONE);
    }

    public void setTips(String tips) {
        if (tv_tips != null) {
            tv_tips.setText(tips);
        }
    }

    /**
     * 根据状态显示不同的提示
     *
     * @param loadStatus
     */
    public void setLoadingTip(LoadStatus loadStatus) {
        switch (loadStatus) {
            case empty:
                setBackgroundColor(ContextCompat.getColor(context,R.color.white));
                setVisibility(View.VISIBLE);
                bt_operate.setVisibility(View.GONE);
                img_tip_logo.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                tv_tips.setText(getContext().getText(R.string.empty).toString());
                img_tip_logo.setImageResource(R.drawable.no_content_tip);
                break;
            case sereverError:
                setBackgroundColor(ContextCompat.getColor(context,R.color.white));
                setVisibility(View.VISIBLE);
                img_tip_logo.setVisibility(View.VISIBLE);
                bt_operate.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                if (TextUtils.isEmpty(errorMsg)) {
                    tv_tips.setText(getContext().getText(R.string.net_error).toString());
                } else {
                    tv_tips.setText(errorMsg);
                }
                img_tip_logo.setImageResource(R.drawable.ic_wrong);
                break;
            case error:
                setBackgroundColor(ContextCompat.getColor(context,R.color.white));
                setVisibility(View.VISIBLE);
                bt_operate.setVisibility(View.VISIBLE);
                img_tip_logo.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                if (TextUtils.isEmpty(errorMsg)) {
                    tv_tips.setText(getContext().getText(R.string.net_error).toString());
                } else {
                    tv_tips.setText(errorMsg);
                }
                img_tip_logo.setImageResource(R.drawable.ic_wifi_off);
                break;
            case loading:
                setBackgroundColor(ContextCompat.getColor(context,R.color.trans));
                setVisibility(View.VISIBLE);
                bt_operate.setVisibility(View.GONE);
                img_tip_logo.setVisibility(View.GONE);
                progress.setVisibility(View.VISIBLE);
                tv_tips.setText(getContext().getText(R.string.loading).toString());
                break;
            case finish:
                setBackgroundColor(ContextCompat.getColor(context,R.color.trans));
                setVisibility(View.GONE);
                break;
        }
    }


    public void setOnReloadListener(onReloadListener onReloadListener) {
        this.onReloadListener = onReloadListener;
    }

    /**
     * 重新尝试接口
     */
    public interface onReloadListener {
        void reload();
    }


}

