package com.example.flashfeso_lwj.base.base

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject



class RetrofitFactory() {
    private var retrofit: Retrofit? = null

    //获取retrofit客户端
    fun getRetrofit(baseUrl: String, interceptor: Interceptor? = null): Retrofit{
        return retrofit?:createRetrofitClient(baseUrl, interceptor)
    }

    //配置retrofit
    private fun createRetrofitClient(baseUrl: String, interceptor: Interceptor? = null): Retrofit{
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(createOkhttpClient(interceptor))
            .build()
    }

    //配置okhttp
    private fun createOkhttpClient(interceptor: Interceptor?): OkHttpClient{
        val clientBuilder = OkHttpClient.Builder()
            //配置http的log拦截器
            .addInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }).apply {
                if(interceptor != null){
                    this.addInterceptor(interceptor)
                }
            }
            //连接超时时间30 单位seconds(秒)
            .connectTimeout(30, TimeUnit.SECONDS)
            //读时间30 单位seconds(秒)
            .readTimeout(30, TimeUnit.SECONDS)
            //写时间30 单位seconds(秒)
            .writeTimeout(30, TimeUnit.SECONDS)

        return clientBuilder.build()
    }


}