package com.jaydenxiao.common.commonutils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by 13198 on 2018/3/18.
 * android 系统功能控制器
 * 1.打电话
 */

public class SystemServiceUtil {

    /**
     * 打电话
     *
     * @param context
     * @param tel     电话号码
     */
    public static void callTelephone(Context context, String tel) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + tel));
        context.startActivity(intent);
    }
}
