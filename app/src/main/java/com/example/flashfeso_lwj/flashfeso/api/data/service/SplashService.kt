package com.example.flashfeso_lwj.flashfeso.api.data.service

import com.example.flashfeso_lwj.flashfeso.entity.VersionEntity
import com.example.flashfeso_lwj.flashfeso.entity.VersionResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SplashService {

    /**
     * 获取APP最新版本
     */
    @GET("mexico/other/version")
    suspend fun getVersionLatest(): VersionResponse

}