package com.example.flashfeso_lwj.flashfeso.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.flashfeso_lwj.flashfeso.repository.LoginYzmRepository
import javax.inject.Inject

class LoginViewModel @ViewModelInject constructor(
    private val loginYzmRepository: LoginYzmRepository
): ViewModel() {

    val loginYzmLiveData = loginYzmRepository.getDataLiveData()
    fun query(map: HashMap<String, Any>){
        loginYzmRepository.query(map)
    }
}