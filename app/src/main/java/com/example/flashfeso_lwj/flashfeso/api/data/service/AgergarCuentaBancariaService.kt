package com.example.flashfeso_lwj.flashfeso.api.data.service

import com.example.flashfeso_lwj.flashfeso.entity.AllBankResponse
import com.example.flashfeso_lwj.flashfeso.entity.AuthBankInfoResponse
import com.example.flashfeso_lwj.flashfeso.entity.RiskInfoResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AgergarCuentaBancariaService {
    @POST("mexico/riskInfo")
    suspend fun getRiskInfoResponse(
        @Body map: HashMap<String, Any>
    ): RiskInfoResponse


    @POST("mexico/authBankInfo")
    suspend fun getAuthBankInfoResponse(@Body map: HashMap<String, Any>): AuthBankInfoResponse

    @GET("mexico/other/allBank")
    suspend fun getAllBank(): AllBankResponse



}