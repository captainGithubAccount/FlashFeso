package com.example.flashfeso_lwj

import com.example.flashfeso_lwj.base.base.RetrofitFactory
import com.example.flashfeso_lwj.flashfeso.utils.InfoUtil
import com.example.flashfeso_lwj.flashfeso.api.data.service.*
import com.example.flashfeso_lwj.flashfeso.utils.UrlConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import javax.inject.Singleton
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Retrofit
import javax.inject.Inject

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideRetrofitClient(retrofitFactory: RetrofitFactory): Retrofit = retrofitFactory.getRetrofit(UrlConstants.BASE_URL,
        object: Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val requestBuilder = chain.request().newBuilder()

                if (InfoUtil.isLogin) {
                    val userId: String = InfoUtil.getUserId()!!
                    val token: String = InfoUtil.getToken()!!
                    requestBuilder
                        .addHeader("userId", userId)
                        .addHeader("userToken", token)
                }
                val request = requestBuilder.build()
                return chain.proceed(request)
            }

        }
    )

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

    @Singleton
    @Provides
    fun provideInicioCurrDetailService(retrofit: Retrofit): InicioCurrDetailService = retrofit.create(InicioCurrDetailService::class.java)

    @Singleton
    @Provides
    fun provideInicioAuthUserInfoService(retrofit: Retrofit): InicioAuthUserInfoService = retrofit.create(InicioAuthUserInfoService::class.java)

    @Singleton
    @Provides
    fun provideAuthAddressService(retrofit: Retrofit): InfomationAuthAddressService = retrofit.create(InfomationAuthAddressService::class.java)

    @Singleton
    @Provides
    fun provideInfomationLaboralAuthWorkService(retrofit: Retrofit): InfomationLaboralAuthWorkService = retrofit.create(InfomationLaboralAuthWorkService::class.java)


    @Singleton
    @Provides
    fun provideHistorialCrediticioService(retrofit: Retrofit): HistorialCrediticioService = retrofit.create(HistorialCrediticioService::class.java)

    @Singleton
    @Provides
    fun provideInformacionDeldetidadMobiRecordService(retrofit: Retrofit): InformacionDeldetidadMobiRecordService = retrofit.create(InformacionDeldetidadMobiRecordService::class.java)
}