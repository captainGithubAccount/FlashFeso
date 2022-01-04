package com.example.flashfeso_lwj.flashfeso.api.data.service

import com.example.flashfeso_lwj.flashfeso.entity.AuthLoanHistoryResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface HistorialCrediticioService {

    @POST("mexico/authLoanHistory")
    suspend fun getAuthLoanHistoryResponse(@Body map: HashMap<String, Any>): AuthLoanHistoryResponse
}