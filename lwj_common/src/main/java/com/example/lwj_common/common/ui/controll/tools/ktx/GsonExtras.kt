package com.example.lwj_common.common.ui.controll.tools.ktx

import com.google.gson.Gson
import com.google.gson.JsonParser


private val gson by lazy { Gson() }

/**
 * 使用gson实现object(Bean)对象反序列化
 * */
fun <T> String.fromJson(clz: Class<T>): T {
    return gson.fromJson(this, clz)
}

/**
 * 使用gson实现Any[list, object, array...]序列化
 * */
fun <T> T.toJson(): String {
    return gson.toJson(this)
}

fun main(){
    val list = listOf("a", 3, "dwafa")
    print(list.toJson())
}

/*
    //gson官网demo:(https://github.com/google/gson/blob/master/extras/src/main/java/com/google/gson/extras/examples/rawcollections/RawCollectionsExample.java)
    //作用: 用来处理包含混合类型的 JSON 数组。例如： ['hello',5,{name:'GREETINGS',source:'guest'}]
    Gson gson = new Gson();
    Collection collection = new ArrayList();
    collection.add("hello");
    collection.add(5);
    collection.add(new Event("GREETINGS", "guest"));
    String json = gson.toJson(collection);
    System.out.println("Using Gson.toJson() on a raw collection: " + json);
    JsonArray array = JsonParser.parseString(json).getAsJsonArray();
    String message = gson.fromJson(array.get(0), String.class);
    int number = gson.fromJson(array.get(1), int.class);
    Event event = gson.fromJson(array.get(2), Event.class);
    System.out.printf("Using Gson.fromJson() to get: %s, %d, %s", message, number, event);
 */
/**
 * 使用gson实现任意类型的对象反序列化
 * */
fun <T> String.fromJsonArray(clz: Class<T>): List<T> {
    val array = JsonParser.parseString(this).asJsonArray
    return array.map { gson.fromJson(it, clz) }
}


