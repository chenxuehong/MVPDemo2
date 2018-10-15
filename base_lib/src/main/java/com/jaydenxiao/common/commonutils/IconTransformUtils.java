package com.jaydenxiao.common.commonutils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by 13198 on 2018/3/22.
 * 转换imgaeView图片格式工具
 */

public class IconTransformUtils {

    /**
     * imgaeview转换成Bitmap
     *
     * @param context
     * @param resId
     * @return
     */
    public static Bitmap transForm2Bitmap(Context context, int resId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId, options);

        return bitmap;
    }
}
