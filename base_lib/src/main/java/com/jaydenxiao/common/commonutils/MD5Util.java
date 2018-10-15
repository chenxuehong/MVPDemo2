package com.jaydenxiao.common.commonutils;

import java.security.MessageDigest;

/**
 * Created by Administrator on 2017/12/3.
 */

public class MD5Util {

    public static String MD5(String str) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //将字符串转换成字符串数组
        char[] pswdArray = str.toCharArray();
        byte[] pswdByte = new byte[pswdArray.length];
        //将字符转换成字节
        for (int i = 0; i < pswdArray.length; i++) {
            pswdByte[i] = (byte) pswdArray[i];
        }
        byte[] digest = md5.digest(pswdByte);
        //将得到的字节数组转换成十六进制数
        StringBuffer buff = new StringBuffer();
        for (int i = 0; i < digest.length; i++) {
            int num = ((int) digest[i]) & 0xff;
            //如果不足16，加0填充
            if (num < 16)
                buff.append("0");
            buff.append(Integer.toHexString(num));
        }
        return buff.toString();
    }

}
