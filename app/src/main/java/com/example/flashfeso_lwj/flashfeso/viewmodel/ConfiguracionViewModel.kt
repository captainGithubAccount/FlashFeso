package com.example.flashfeso_lwj.flashfeso.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.flashfeso_lwj.flashfeso.repository.ProSupportRepository

class ConfiguracionViewModel @ViewModelInject constructor(
    private val proSupportRepository: ProSupportRepository
): ViewModel() {
    val proSupportLiveData = proSupportRepository.getLiveData()
    fun queryProSupport(){
        proSupportRepository.query()
    }
}