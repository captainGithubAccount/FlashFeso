package com.example.flashfeso_lwj.flashfeso.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.flashfeso_lwj.flashfeso.repository.PayChannelRepository
import com.example.flashfeso_lwj.flashfeso.repository.RepaymentOxxoRepository
import com.example.flashfeso_lwj.flashfeso.repository.RepaymentSTPRepository

class ReembolsoViewModel @ViewModelInject constructor(
    private val payChannelRepository: PayChannelRepository,
    private val repaymentOxxoRepository: RepaymentOxxoRepository,
    private val repaymentSTPRepository: RepaymentSTPRepository
) : ViewModel() {

    val payChannelLiveData = payChannelRepository.getLiveData()
    fun queryPayChannel(){
        payChannelRepository.query()
    }

    val repaymentOxxoLiveData = repaymentOxxoRepository.getLiveData()
    fun queryRepaymentOxxo(map: HashMap<String, Any>){
        repaymentOxxoRepository.query(map)
    }

    val repaymentSTPLiveData = repaymentSTPRepository.getLiveData()
    fun queryRepaymentSTP(map: HashMap<String, Any>){
        repaymentSTPRepository.query(map)
    }
}