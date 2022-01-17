package com.example.lwj_common.common.ui.controll.tools.utils


import com.alibaba.fastjson.JSON
import org.json.JSONException
import java.util.ArrayList

object JsonUtil {
    fun <T> parseObject(json: String?, klass: Class<T>?): T? {
        if (StringUtils.isEmpty(json)) {
            return null
        }
        try {
            return JSON.parseObject(json, klass)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return null
    }

    fun <T> parseArray(json: String?, klass: Class<T>?): List<T> {
        if (StringUtils.isEmpty(json)) {
            return ArrayList()
        }
        try {
            return JSON.parseArray(json, klass)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return ArrayList()
    }
}