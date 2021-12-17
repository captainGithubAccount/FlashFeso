package com.example.flashfeso_lwj.flashfeso.entity

import com.example.flashfeso_lwj.common.base.StateData

/*
* create time: 12.17
* autor: lwj
* create reason: 这样就不需要多次编写重复代码了
* 其他实现: 也可以写一个抽象类, 抽象类里面包含code和data两个字段和getStateData方法, 看个人喜好, 我更喜欢这种, 因为实现接口的时候需要传入泛型更好的
* 提醒我该response的data是什么类型
* */
interface GetStateData<T>{

    val code: Int
    val data: T?

    fun getStateData(code: Int = 200): StateData<T?> {
        return if(code == 200 && data != null){
            StateData.Success(data)
        }else if(code != 200){
            StateData.Error(errorMessage = "返回码为${code}")
        }else{
            StateData.Error(errorMessage = "返回的数据为null")
        }

    }
}