package com.example.flashfeso_lwj.flashfeso.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.flashfeso_lwj.flashfeso.repository.InfomationAdressRepository

class InfomationAddressViewModel @ViewModelInject constructor(
    private val infomationAdressRepository: InfomationAdressRepository
): ViewModel() {

    val addressLiveData = infomationAdressRepository.getDataLiveData()
    fun query(map: HashMap<String, Any>){
        infomationAdressRepository.query(map)
    }
}