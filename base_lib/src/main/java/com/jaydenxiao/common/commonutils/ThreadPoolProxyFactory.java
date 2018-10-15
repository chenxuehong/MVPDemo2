package com.jaydenxiao.common.commonutils;

/*
 * 怎么使用线程池？
 * 1、使用普通线程池执行普通任务的实例
 *      ThreadPoolProxyFactory .getNormalThreadPoolProxy().execute(Runnable);
 * 2、使用下载线程池执行下载任务的实例
 *      ThreadPoolProxyFactory .getDownLoadThreadPoolProxy().execute(Runnable);
 */

/**
 * Created by 13198 on 2018/6/23.
 * 使用动态代理方式创建建一个ThreadPoolProxyFactory ，里面提供两种方式获取线程池:
 * 普通线程池和下载的线程池
 */

public class ThreadPoolProxyFactory {
    static ThreadPoolProxy mNormalThreadPoolProxy;
    static ThreadPoolProxy mDownLoadThreadPoolProxy;

    /**
     * 得到普通线程池代理对象mNormalThreadPoolProxy
     */
    public static ThreadPoolProxy getNormalThreadPoolProxy() {
        if (mNormalThreadPoolProxy == null) {
            synchronized (ThreadPoolProxyFactory.class) {
                if (mNormalThreadPoolProxy == null) {
                    mNormalThreadPoolProxy = new ThreadPoolProxy(5, 5);
                }
            }
        }
        return mNormalThreadPoolProxy;
    }

    /**
     * 得到下载线程池代理对象mDownLoadThreadPoolProxy
     */
    public static ThreadPoolProxy getDownLoadThreadPoolProxy() {
        if (mDownLoadThreadPoolProxy == null) {
            synchronized (ThreadPoolProxyFactory.class) {
                if (mDownLoadThreadPoolProxy == null) {
                    mDownLoadThreadPoolProxy = new ThreadPoolProxy(3, 3);
                }
            }
        }
        return mDownLoadThreadPoolProxy;
    }

}
