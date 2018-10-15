package com.jaydenxiao.common.commonutils;

import android.widget.ProgressBar;

/**
 * Created by 13198 on 2018/1/21.
 * 操作progressbar的工具
 */

public class PbUtil {

    /**
     * 设置最大值
     *
     * @param mPb progressbar
     * @param max 最大值
     */
    private static void setMax(ProgressBar mPb, int max) {

        if (mPb == null) {
            throw new IllegalArgumentException("has a argument is null");
        }

        mPb.setMax(max);
    }

    /**
     * 获取progressbar的百分比值
     *
     * @param currentValue 当前值
     * @param totalValue   总值
     * @return 返回值在0~100
     */
    public static int getCurrentPercentVaule(long currentValue, long totalValue) {
        if (totalValue <= 100) {

            return (int) currentValue;
        }

        int currentPercent = (int) (currentValue * 100 / totalValue);
        if (currentPercent < 1) {
            currentPercent = 1;
        }
        return currentPercent;
    }

    /**
     * 当前进度
     *
     * @param mPb          progressbar
     * @param currentValue long
     * @param totalValue   long
     */
    public static void setProgress(ProgressBar mPb, long currentValue, long totalValue) {
        int currentExperPercent = getCurrentPercentVaule(currentValue, totalValue);
        setMax(mPb, 100);
        setCurrentProgress(mPb, currentExperPercent);
    }

    /**
     * 设置当前进度
     *
     * @param mPb   progressbar
     * @param value 当前值百分比
     */
    private static void setCurrentProgress(ProgressBar mPb, int value) {

        if (mPb == null) {
            throw new IllegalArgumentException("has a argument is null");
        }

        mPb.setProgress(value);
    }
}
