package com.example.flashfeso_lwj

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.example.flashfeso_lwj.flashfeso.utils.SharedPreferenceUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun getSharedPreferences(@ApplicationContext context: Context): SharedPreferences = context.getSharedPreferences(context.getString(
            R.string.str_sharedpreference_key), Context.MODE_PRIVATE)


}