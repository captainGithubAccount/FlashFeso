package com.example.flashfeso_lwj.flashfeso.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import com.example.flashfeso_lwj.flashfeso.repository.LoginUserInfoRepository
import com.example.flashfeso_lwj.flashfeso.repository.LoginYzmRepository
import javax.inject.Inject

class LoginViewModel @ViewModelInject constructor(
    private val loginYzmRepository: LoginYzmRepository,
    private val loginUserInfoRepository: LoginUserInfoRepository
): ViewModel() {

    val loginYzmLiveData = loginYzmRepository.getDataLiveData()
    fun queryLoginYzm(map: HashMap<String, Any>){
        loginYzmRepository.query(map)
    }

    val loginUserLiveData = loginUserInfoRepository.getDataLiveData()
    fun queryLoginUserInfo(map: HashMap<String, Any?>){
        loginUserInfoRepository.query(map)
    }

    val notifyLiveData = MutableLiveData<String>()
    fun queryNotifyLiveData(){//UpdateLoginBean
        notifyLiveData.postValue("LoginAtv用来通知触发观察者的observe方法")
    }


    val notifyLiveData2 = MutableLiveData<String>()
    fun queryNotify2LiveData(){//InicioBeanUpdate
        notifyLiveData2.postValue("认证一界面用来通知触发观察者的observe方法,但又只想通知部分监听到")
    }



}