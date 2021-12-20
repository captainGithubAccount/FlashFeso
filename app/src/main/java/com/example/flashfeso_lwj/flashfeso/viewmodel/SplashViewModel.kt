package com.example.flashfeso_lwj.flashfeso.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.flashfeso_lwj.flashfeso.repository.SplashVersionRepository

class SplashViewModel @ViewModelInject constructor(
    private val splashRepository: SplashVersionRepository
) : ViewModel() {

    //不带状态的数据实现方式
    val versionLiveData = splashRepository.getVersionLiveData()

    fun query(){
        splashRepository.query()
    }

    val versionErrorLiveData = splashRepository.getVersionErrorLiveData()



    //带有状态的数据实现方式
    val dataLiveData = splashRepository.getDataLiveData()

    fun query2(){
        splashRepository.query2()
    }

}