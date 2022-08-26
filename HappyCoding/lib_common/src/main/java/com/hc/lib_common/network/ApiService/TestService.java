package com.hc.lib_common.network.ApiService;

import com.hc.lib_common.network.dto.test.GetMyMatStoreBody;
import com.hc.lib_common.network.dto.test.MyResourceListBean;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author furuoxuan
 */
public interface TestService {

    /**
     * 获取我的素材列表
     *
     * @param email     用户id
     * @param pageindex 查询页数
     * @param pagesize  分页数量
     * @return MyResourceListBean
     */
    @FormUrlEncoded
    @POST("/epapi/api/MatStore/MyMatStore")
    Observable<MyResourceListBean> getMyMatStore(@Field("email") String email, @Field("pageindex") int pageindex,
            @Field("pagesize") int pagesize);

    /**
     * 获取我的素材列表
     *
     * @param body {@link GetMyMatStoreBody}
     * @return MyResourceListBean
     */
    @POST("/epapi/api/MatStore/MyMatStore")
    Observable<MyResourceListBean> getMyMatStore1(@Body GetMyMatStoreBody body);
}
