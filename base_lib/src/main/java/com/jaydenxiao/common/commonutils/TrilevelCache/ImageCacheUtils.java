package com.jaydenxiao.common.commonutils.TrilevelCache;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.jaydenxiao.common.BuildConfig;
import com.jaydenxiao.common.commonutils.MyLogger;

/**
 * Created by 13198 on 2018/7/29.
 * 图片的三级缓存工具
 */

public class ImageCacheUtils {

    private MemoryCacheUtils memoryCacheUtils;
    private LocalCacheUtils localCacheUtils;
    private NetCacheUtils netCacheUtils;

    private static final String TAG = ImageCacheUtils.class.getSimpleName();
    private static ImageCacheUtils intance;
    private Bitmap bitmap;

    public ImageCacheUtils(Context context) {
        memoryCacheUtils = new MemoryCacheUtils();
        localCacheUtils = LocalCacheUtils.get(context);
        netCacheUtils = new NetCacheUtils(localCacheUtils, memoryCacheUtils);
    }

    public static ImageCacheUtils with(Context context) {

        if (intance == null) {
            synchronized (ImageCacheUtils.class) {
                if (intance == null) {
                    intance = new ImageCacheUtils(context);
                }
            }
        }
        return intance;
    }

    public void load(ImageView imageView, String imgUrl) {

        bitmap = memoryCacheUtils.getBitmapFromMemory(imgUrl);
        if (bitmap != null) {

            imageView.setImageBitmap(bitmap);
            MyLogger.e(TAG,"从内存中加载图片...");
            return;
        }

        bitmap = localCacheUtils.getBitmapFromLocalCache(imgUrl);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            MyLogger.e(TAG,"从本地中加载图片并且存入到缓存中...");
            memoryCacheUtils.setBitmap2Memory(imgUrl, bitmap);
            return;
        }
        MyLogger.e(BuildConfig.DEBUG_TAG,"从网络中加载图片...");
        netCacheUtils.getBitmapFromNet(imageView, imgUrl);
    }
}
