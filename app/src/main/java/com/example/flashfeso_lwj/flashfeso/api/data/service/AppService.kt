package com.example.flashfeso_lwj.flashfeso.api.data.service

import com.example.flashfeso_lwj.flashfeso.base.FlashFesoAppBaseResponse
import com.example.flashfeso_lwj.flashfeso.entity.*
import com.example.lwj_common.common.net.BaseArrayResponse
import com.example.lwj_common.common.net.BaseResponse
import com.example.lwj_common.common.net.CommonEmptyEntity
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AppService {
    @POST("mexico/generateOrder")
    suspend fun getGenerateOrderResponse(@Body map: HashMap<String, Any>): FlashFesoAppBaseResponse<GenerateOrderEntity>

    @POST("mexico/amount/choose")
    suspend fun getAmountChoose(@Body map: HashMap<String, Any>): FlashFesoAppBaseResponse<AmountChooseEntity>

    @POST("mexico/other/customerFeed")
    suspend fun getCustomerFeedResponse(@Body map: HashMap<String, Any>): BaseResponse<CommonEmptyEntity>

    @POST("mexico/other/customerFeedList")
    suspend fun getCustomerFeedListResponse(@Body map: HashMap<String, Any>):
            BaseResponse<BaseArrayResponse<CustomerFeedListDetailEntity>>

    @GET("mexico/other/comPS")
    suspend fun getComPSResponse(): BaseResponse<ArrayList<ComPsEntity>>

    @GET("mexico/other/proSupport")
    suspend fun getProSupportResponse(): BaseResponse<ProSupportEntity>

    @GET("mexico/orderHistoryData")
    suspend fun getOrderHistoryDataResponse(): BaseResponse<BaseArrayResponse<OrderHistoryDataEntity>>


}