package com.example.flashfeso_lwj.flashfeso.api.data.service

import com.example.flashfeso_lwj.flashfeso.entity.VersionResponse
import retrofit2.http.GET

interface SplashVersionService {
//    将函数使用suspend声明retrofit将自动在执行的时候开辟一个ui的协程

    /**
     * 获取APP最新版本
     */
    @GET("mexico/other/version")
    suspend fun getVersionLatest(): VersionResponse

}