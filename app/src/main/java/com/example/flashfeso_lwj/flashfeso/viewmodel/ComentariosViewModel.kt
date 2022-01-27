package com.example.flashfeso_lwj.flashfeso.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.flashfeso_lwj.flashfeso.repository.CustomerFeedListRepository
import com.example.flashfeso_lwj.flashfeso.repository.CustomerFeedRepository

class ComentariosViewModel @ViewModelInject constructor(
    private val customerFeedRepository: CustomerFeedRepository,
    private val customerFeedListRepository: CustomerFeedListRepository
): ViewModel() {

    val customerFeedLiveData = customerFeedRepository.getLiveData()
    fun queryCustomerFeed(map: HashMap<String, Any>){
        customerFeedRepository.query(map)
    }

    val customerFeedListLiveData = customerFeedListRepository.getLiveData()
    fun queryCustomerListFeed(map: HashMap<String, Any>){
        customerFeedListRepository.query(map)
    }


}