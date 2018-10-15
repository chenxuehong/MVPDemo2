package com.jaydenxiao.common.commonutils;

import java.text.DecimalFormat;

/**
 * des:金钱
 * Created by xsf
 * on 2016.06.11:48
 */
public class MoneyUtil {

    /**
     * 金钱额度
     * 保留小数点后两位数
     *
     * @param d
     * @return
     */
    public static String MoneyFomatWithTwoPoint(double d) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(d);
    }

    /**
     * 金钱额度
     * 保留小数点后两位数
     *
     * @param d
     * @return
     */
    public static String MoneyFomatWithTwoPoint(String d) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(Double.valueOf(d));
    }
}
