package com.example.lwj_common.common.net

data class BaseArrayResponse<out T>(
    val total: Int,
    val totalPage: Int,
    val dataList: List<T>?,
    val list: List<T>?
)