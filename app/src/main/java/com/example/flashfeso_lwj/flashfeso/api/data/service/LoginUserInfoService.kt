package com.example.flashfeso_lwj.flashfeso.api.data.service

import com.example.flashfeso_lwj.flashfeso.entity.UserInfoEntity
import com.example.flashfeso_lwj.flashfeso.entity.UserInfoResponse
import com.example.flashfeso_lwj.flashfeso.entity.VersionResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginUserInfoService {

    /**
     * 登录
     */
    @POST("mexico/login")
    suspend fun getUserInfoDetail(
        @Body map: HashMap<String, Any>
    ): UserInfoResponse
}