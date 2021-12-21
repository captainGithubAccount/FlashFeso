package com.example.flashfeso_lwj.flashfeso.api.data.service

import com.example.flashfeso_lwj.flashfeso.entity.YzmEntity
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginYzmService {

    /**
     * 发送验证码
     */
    @POST("mexico/sendSms")
    suspend fun getVerificationCode(
        @Body map: HashMap<String, Any>
    ): /*@JvmSuppressWildcards*/ YzmEntity
}