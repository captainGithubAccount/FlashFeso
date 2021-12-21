package com.example.flashfeso_lwj.flashfeso.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
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
    fun queryLoginUserInfo(map: HashMap<String, Any>){
        loginUserInfoRepository.query(map)
    }


}