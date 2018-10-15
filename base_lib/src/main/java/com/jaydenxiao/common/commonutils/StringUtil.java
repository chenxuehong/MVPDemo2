package com.jaydenxiao.common.commonutils;

import android.text.TextUtils;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/12/2.
 * 1.将其他数据类型转成string
 * 2.防止string出现空造成崩溃
 */

public class StringUtil {

    public static String getString(String data) {

        if (TextUtils.isEmpty(data)) {
            data = "";
        }
        return data;
    }

    public static String getString(int data) {

        return String.valueOf(data);
    }

    public static String getString(long data) {

        return String.valueOf(data);
    }

    public static String getString(double data) {

        return String.valueOf(data);
    }

    public static String getString(float data) {

        return String.valueOf(data);
    }

    public static String getString(char data) {

        return String.valueOf(data);
    }

    /**
     * 返回*万的数据
     *
     * @param number 数量 可以设置粉丝数量
     * @return
     */
    public static String toNumberWanUnit(long number) {

        if (number < 10000) {
            return String.valueOf(number);
        } else {

            return number * 1f / 10000 + "万";
        }
    }

    /**
     * 返回*万的数据
     *
     * @param number 数量 可以设置粉丝数量
     * @return
     */
    public static String toNumberWanUnit(String number) {

        Long numberl = Long.valueOf(number);
        if (numberl < 10000) {
            return String.valueOf(number);
        } else {

            return numberl * 1f / 10000 + "万";
        }
    }

    /**
     * 格式化字符串，每三位用逗号隔开
     *
     * @param str
     * @return
     */
    public static String addComma(String str) {
        str = new StringBuilder(str).reverse().toString();     //先将字符串颠倒顺序
        if (str.equals("0")) {
            return str;
        }
        String str2 = "";
        for (int i = 0; i < str.length(); i++) {
            if (i * 3 + 3 > str.length()) {
                str2 += str.substring(i * 3, str.length());
                break;
            }
            str2 += str.substring(i * 3, i * 3 + 3) + ",";
        }
        if (str2.endsWith(",")) {
            str2 = str2.substring(0, str2.length() - 1);
        }
        //最后再将顺序反转过来
        String temp = new StringBuilder(str2).reverse().toString();
        //将最后的,去掉
        return temp.substring(0, temp.lastIndexOf(",")) + temp.substring(temp.lastIndexOf(",") + 1, temp.length());
    }


    public static String formatDoublePointTwo(double money) {
        //保持小数点后两位
        DecimalFormat formater = new DecimalFormat();
        formater.setMaximumFractionDigits(2);
        formater.setGroupingSize(0);
        formater.setRoundingMode(RoundingMode.FLOOR);
        return formater.format(money);
    }
}
