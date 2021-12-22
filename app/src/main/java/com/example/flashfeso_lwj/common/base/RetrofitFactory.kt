package com.example.flashfeso_lwj.common.base

import com.example.flashfeso_lwj.flashfeso.utils.UrlConstants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class RetrofitFactory {
    private var retrofit: Retrofit? = null

    //获取retrofit客户端
    fun getRetrofit(baseUrl: String = BASE_URL): Retrofit{
        return retrofit?:createRetrofitClient(baseUrl)
    }

    //配置retrofit
    private fun createRetrofitClient(baseUrl: String): Retrofit{
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(createOkhttpClient())
            .build()
    }

    //配置okhttp
    private fun createOkhttpClient(): OkHttpClient{
        val clientBuilder = OkHttpClient.Builder()
            //配置http的log拦截器
            .addInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            })
            //连接超时时间30 单位seconds(秒)
            .connectTimeout(30, TimeUnit.SECONDS)
            //读时间30 单位seconds(秒)
            .readTimeout(30, TimeUnit.SECONDS)
            //写时间30 单位seconds(秒)
            .writeTimeout(30, TimeUnit.SECONDS)
        return clientBuilder.build()
    }


}