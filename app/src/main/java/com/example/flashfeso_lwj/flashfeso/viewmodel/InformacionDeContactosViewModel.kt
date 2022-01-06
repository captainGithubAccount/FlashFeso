package com.example.flashfeso_lwj.flashfeso.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.flashfeso_lwj.flashfeso.repository.InformacionDeContactosRepository

class InformacionDeContactosViewModel @ViewModelInject constructor(
    private val informacionDeContactosRepository: InformacionDeContactosRepository
): ViewModel() {
    val liveData = informacionDeContactosRepository.getDataLiveData()
    fun query(map: HashMap<String, Any>) {
        informacionDeContactosRepository.query(map)
    }

}