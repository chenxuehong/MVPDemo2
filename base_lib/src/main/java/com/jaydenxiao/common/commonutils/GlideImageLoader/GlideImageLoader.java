package com.jaydenxiao.common.commonutils.GlideImageLoader;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jaydenxiao.common.R;

import java.io.File;

/**
 * Description : 图片加载工具类 使用glide框架封装
 */
public class GlideImageLoader {

    public static void display(Context context, ImageView imageView, String url, int placeholder, int error) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context)
                .load(url)
                .placeholder(placeholder)
                .error(error)
//                .crossFade()
                .into(imageView);
    }

    public static void display(Context context, ImageView imageView, int url, int placeholder, int error) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context)
                .load(url)
                .placeholder(placeholder)
                .error(error)
//                .crossFade()
                .into(imageView);
    }

    public static void display(Context context, ImageView imageView, File url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .centerCrop()
                .placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_empty_picture)
//                .crossFade()
                .into(imageView);
    }

    public static void display(Context context, ImageView imageView, String url, float scale) {

        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(url).asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)   // 缓存模式
                .placeholder(R.drawable.ic_image_loading)   // 占位图
                .error(R.drawable.ic_empty_picture) // 加载错误显示的图片
                .thumbnail(scale)    // 缩放原图
                .into(imageView);
    }

    public static void displaySmallPhoto(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(url).asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)   // 缓存模式
                .placeholder(R.drawable.ic_image_loading)   // 占位图
                .error(R.drawable.ic_empty_picture) // 加载错误显示的图片
                .thumbnail(0.5f)    // 缩放为原图的50%
                .into(imageView);
    }

    public static void displayBigPhoto(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(url).asBitmap()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_empty_picture)
                .into(imageView);
    }

    public static void display(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.toux2)
                .centerCrop().transform(new GlideRoundTransformUtil(context)).into(imageView);
    }

    public static void display(Context context, ImageView imageView, int resId) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }

        Glide.with(context).load(resId)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.toux2)
                .centerCrop().transform(new GlideRoundTransformUtil(context)).into(imageView);
    }

    public static void displayCilce(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.toux2)
                .centerCrop().transform(new GlideCircleTransfromUtil(context)).into(imageView);
    }

    public static void displayCilce(Context context, ImageView imageView, int resId) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }

        Glide.with(context).load(resId)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.toux2)
                .centerCrop().transform(new GlideCircleTransfromUtil(context)).into(imageView);
    }

    public static void displayCilce(Context context, ImageView imageView, Bitmap mBitmap) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }

        Glide.with(context).load(mBitmap)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.toux2)
//                .centerCrop()
                .transform(new GlideCircleTransfromUtil(context)).into(imageView);
    }

    public static void resumeRequest(Context context) {
        RequestManager requestManager = Glide.with(context);
        if (requestManager.isPaused()) {
            requestManager.resumeRequests();
        }
    }

    public static void pauseRequest(Context context) {
        RequestManager requestManager = Glide.with(context);
        if (!requestManager.isPaused()) {
            requestManager.pauseRequests();
        }
    }

}
