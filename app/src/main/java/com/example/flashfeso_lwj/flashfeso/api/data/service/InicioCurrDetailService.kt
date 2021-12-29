package com.example.flashfeso_lwj.flashfeso.api.data.service

import com.example.flashfeso_lwj.flashfeso.entity.CurrDetailResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface InicioCurrDetailService {

    @POST("mexico/currDetails")
    suspend fun getCurrDetails(
        @Body map: HashMap<String, Any>
    ): CurrDetailResponse
}