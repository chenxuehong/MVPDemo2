package com.common.mvpdemo2.service;


import com.common.mvpdemo2.model.JokeModel;
import com.common.mvpdemo2.model.PersonalCenterModel;
import com.jaydenxiao.common.modle.net.BaseModle;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by 13198 on 2018/9/15.
 */

public interface ApiService {
    /**
     * //拼接一个URL然后进行网络请求
     * //这里我们拼接的URL就是 完整URL:https://api.douban.com/v2/book/search?q=xxx&tag=&start=0&count=1;
     * //每个URL前面都是以http://www.jianshu.com/开头,我们把这个不变的部分，也叫做baseUrl提出来，放到另一个地方,在下面我们会提到
     */

//    //将返回值call改为observable
//    @GET("book/search")
//    Observable<BaseModle<Book>> getSearchBook(@Query("q") String name,
//                                              @Query("tag") String tag,
//                                              @Query("start") int start,
//                                              @Query("count") int count
//    );

    /**
     * 查询最新的段子数据
     *
     * @return
     */
//    接口完整地址text.from?key=ae240f7fba620fc370b803566654949e
//     http://japi.juhe.cn/joke/content/text.from?key=ae240f7fba620fc370b803566654949e
    @FormUrlEncoded
    @POST("text.from?key=ae240f7fba620fc370b803566654949e")
    Observable<BaseModle<JokeModel>> getCurrentJokeDataByPost(
            @Field("page") int page,
            @Field("pagesize") int pagesize
    );

    // 首页数据
    @POST("V1/Home/PersonalCenterModel")
    Observable<BaseModle<PersonalCenterModel>> getPersonalCenterModel();
}
