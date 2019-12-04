package com.jaydenxiao.common.module.http.core;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.jaydenxiao.common.BuildConfig;
import com.jaydenxiao.common.ui.LibApplication;
import com.jaydenxiao.common.constant.URLConstant;
import com.jaydenxiao.common.factory.CustomGsonConverterFactory;
import com.jaydenxiao.common.module.http.intercepter.FilterFastRequestInterceptor;
import com.jaydenxiao.common.module.http.intercepter.ForceCacheInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * Created by 13198 on 2018/3/28.
 *
 * @desc retrofit2.0 + okhttp + rxjava 网络框架的核心代码
 */

public class RetrofitCore {

    private static RetrofitCore instance;
    private Retrofit mRetrofit;
    private OkHttpClient.Builder builder;
    private static Context mContext;
    private RetrofitCore() {
        init();
    }

    public static RetrofitCore getInstance(Context context) {

        if (instance == null) {
            synchronized (RetrofitCore.class) {
                if (instance == null) {
                    mContext = context;
                    instance = new RetrofitCore();
                }
            }
        }
        return instance;
    }

    private void init() {
        initOkhttp();
        mRetrofit = createRetrofit();
    }

    private void initOkhttp() {
        // 初始化Okhttp
        builder = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS);

        // 设置拦截器
        if (BuildConfig.DEBUG) { // 判断是否为debug
            // 如果为 debug 模式，则添加日志拦截器
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(httpLoggingInterceptor);
        }

        builder.cache(new Cache(new File(mContext.getExternalCacheDir(),BuildConfig.PROJECT_NAME), 10 * 1024 * 1024));
        // 没有网络时，强制走缓存
        builder.addInterceptor(new ForceCacheInterceptor(LibApplication.Companion.getBaseApplication()));
        // 网络层拦截器，10秒内走缓存，10秒后走网络
        builder.addNetworkInterceptor(new FilterFastRequestInterceptor(10));
    }

    private Retrofit createRetrofit() {

        // 返回Retrofit
        return new Retrofit.Builder()
                .baseUrl(URLConstant.BASE_URL)
                .client(builder.build())
                .addConverterFactory(CustomGsonConverterFactory.create(buildGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    /**
     * 创建相应的服务接口实例化对象
     *
     * @param service
     * @param <T>
     * @return
     */
    public <T> T create(final Class<T> service) {

        checkServiceNotNull(service, "service is null");
        return mRetrofit.create(service);
    }

    private <T> void checkServiceNotNull(Class<T> object, String message) {

        if (object == null) {
            throw new NullPointerException(message);
        }
    }

    private Gson buildGson() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
    }
}
