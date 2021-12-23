package com.example.flashfeso_lwj

import android.view.LayoutInflater
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

interface GetBinding<T> {
    fun getAtvOrFrgmBindingByReflex(layoutInflater: LayoutInflater): T? {
        val genericSuperclass: Type = javaClass.genericSuperclass
        if (genericSuperclass is ParameterizedType) {
            val type = genericSuperclass .actualTypeArguments[0] as Class<T>
            val inflateMethod = type.getMethod("inflate", LayoutInflater::class.java)
            return inflateMethod.invoke(null, layoutInflater) as T
        } else {
            return null
        }
    }
}