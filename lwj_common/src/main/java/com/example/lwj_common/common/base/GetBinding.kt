package com.example.lwj_base.common.base

import android.view.LayoutInflater
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * @author lwj
 * */
@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
interface GetBinding<T> {
    fun getViewBindingByReflex(layoutInflater: LayoutInflater): T? {
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