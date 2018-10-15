package com.jaydenxiao.common.commonutils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by 13198 on 2018/3/23.
 * App清单文件信息获取工具
 */

public class AppInfoUtil {

    /**
     * 获取app包名信息
     *
     * @param context 上下文
     * @return
     */
    public static PackageInfo getPackageInfo(Context context) {
        PackageInfo packageInfo = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
            return packageInfo;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return packageInfo;
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {

        return getPackageInfo(context).versionCode;
    }

    /**
     * 获取版本名称
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }
}
