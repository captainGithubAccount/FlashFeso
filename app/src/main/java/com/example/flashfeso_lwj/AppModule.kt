package com.example.flashfeso_lwj

import android.content.Context
import android.content.SharedPreferences
import com.example.flashfeso_lwj.flashfeso.api.data.service.LoginYzmService
import com.example.flashfeso_lwj.flashfeso.api.data.service.SplashVersionService
import com.example.flashfeso_lwj.common.base.RetrofitFactory
import com.example.flashfeso_lwj.flashfeso.api.data.service.LoginUserInfoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun getSharedPreferences(@ApplicationContext context: Context): SharedPreferences = context.getSharedPreferences(context.getString(
            R.string.str_sharedpreference_key), Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideRetrofitClient(retrofitFactory: RetrofitFactory): Retrofit = retrofitFactory.getRetrofit()

    @Singleton
    @Provides
    fun provideRetrofitFactory(): RetrofitFactory = RetrofitFactory()

    @Singleton
    @Provides
    fun provideSplashService(retrofit: Retrofit): SplashVersionService = retrofit.create(SplashVersionService::class.java)

    @Singleton
    @Provides
    fun provideLoginYzmService(retrofit: Retrofit): LoginYzmService = retrofit.create(LoginYzmService::class.java)

    @Singleton
    @Provides
    fun provideLoginUserInfoService(retrofit: Retrofit): LoginUserInfoService = retrofit.create(LoginUserInfoService::class.java)







}