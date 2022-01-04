package com.example.flashfeso_lwj.flashfeso.api.data.service

import com.example.flashfeso_lwj.flashfeso.entity.AuthAddressResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface InfomationAuthAddressService {

    @POST("/mexico/authAddress")
    suspend fun getAuthAddressResponse(
        @Body map: HashMap<String, Any>
    ): AuthAddressResponse
}