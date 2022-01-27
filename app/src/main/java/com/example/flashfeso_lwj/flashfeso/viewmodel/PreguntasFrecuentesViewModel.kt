package com.example.flashfeso_lwj.flashfeso.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.flashfeso_lwj.flashfeso.repository.ComPsRepository

class PreguntasFrecuentesViewModel @ViewModelInject constructor(
    private val comPsRepository: ComPsRepository
): ViewModel() {

    val comPsLiveData = comPsRepository.getLiveData()
    fun queryComPs(){
        comPsRepository.query()
    }

}