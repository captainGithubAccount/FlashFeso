package com.example.flashfeso_lwj.flashfeso.api.data.service

import com.example.flashfeso_lwj.flashfeso.base.FlashFesoAppBaseResponse
import com.example.flashfeso_lwj.flashfeso.entity.AmountChooseEntity
import com.example.flashfeso_lwj.flashfeso.entity.GenerateOrderEntity
import com.example.flashfeso_lwj.flashfeso.entity.GenerateOrderResponse
import com.example.lwj_common.common.entity.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AppService {
    @POST("mexico/generateOrder")
    suspend fun getGenerateOrderResponse(
        @Body map: HashMap<String, Any>
    ): FlashFesoAppBaseResponse<GenerateOrderEntity>

    @POST("mexico/amount/choose")
    suspend fun getAmountChoose(
        @Body map: HashMap<String, Any>
    ): FlashFesoAppBaseResponse<AmountChooseEntity>


}