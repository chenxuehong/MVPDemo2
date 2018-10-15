package com.jaydenxiao.common.modle.net;

/**
 * des:封装服务器返回数据
 * Created by xsf
 * on 2016.09.9:47
 */
public class BaseModle<T> {
    public int error_code;
    public String reason;
//    public Boolean IsSuccess = false;
    public T result;

}
