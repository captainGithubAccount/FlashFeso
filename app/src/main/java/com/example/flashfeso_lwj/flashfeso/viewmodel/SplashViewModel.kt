package com.example.flashfeso_lwj.flashfeso.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.flashfeso_lwj.flashfeso.entity.VersionEntity
import com.example.flashfeso_lwj.flashfeso.repository.SplashRepository

class SplashViewModel @ViewModelInject constructor(
    private val splashRepository: SplashRepository
) : ViewModel() {

    val versionLiveData = splashRepository.getVersionLiveData()

    fun query(){
        splashRepository.query()
    }

    val versionErrorLiveData = splashRepository.getVersionErrorLiveData()

}