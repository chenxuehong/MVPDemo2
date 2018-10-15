package com.jaydenxiao.common.base;


/**
 * ************************************************************************
 * **                              _oo0oo_                               **
 * **                             o8888888o                              **
 * **                             88" . "88                              **
 * **                             (| -_- |)                              **
 * **                             0\  =  /0                              **
 * **                           ___/'---'\___                            **
 * **                        .' \\\|     |// '.                          **
 * **                       / \\\|||  :  |||// \\                        **
 * **                      / _ ||||| -:- |||||- \\                       **
 * **                      | |  \\\\  -  /// |   |                       **
 * **                      | \_|  ''\---/''  |_/ |                       **
 * **                      \  .-\__  '-'  __/-.  /                       **
 * **                    ___'. .'  /--.--\  '. .'___                     **
 * **                 ."" '<  '.___\_<|>_/___.' >'  "".                  **
 * **                | | : '-  \'.;'\ _ /';.'/ - ' : | |                 **
 * **                \  \ '_.   \_ __\ /__ _/   .-' /  /                 **
 * **            ====='-.____'.___ \_____/___.-'____.-'=====             **
 * **                              '=---='                               **
 * ************************************************************************
 * **                        佛祖保佑      镇类之宝                         **
 * ************************************************************************
 */
public interface AppConfig {

    /**
     * The constant DEBUG_TAG.
     */
    String DEBUG_TAG = "logger";// LogCat的标记
    boolean DEBUG = true;// true 调试,false上线

    /* 自动更新配置*/
    // 支付宝支付
    // appId
    String APP_ALIPAY_ID = "2018080860981483";
    // 签约合作者身份ID
    String APP_PARTNER = "";
    // 签约卖家支付宝账号
    String APP_SELLER = "";


    //fire.im的token
    String API_FIRE_TOKEN = "a4f8aa03dc120fc81fcc96464fd03a4b";
    //fire.im的应用id
    String APP_FIRE_ID = "57e8ccd4ca87a851e4001199";

    //appid 微信分配的公众账号ID
    String APP_ID = "";

}