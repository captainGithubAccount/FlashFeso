package com.example.flashfeso_lwj.flashfeso.api.data.service

import com.example.flashfeso_lwj.flashfeso.entity.AuthUserInfoResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface InicioAuthUserInfoService {
    @GET("mexico/authUserInfo")
    suspend fun getAuthUserInfo(): AuthUserInfoResponse
}