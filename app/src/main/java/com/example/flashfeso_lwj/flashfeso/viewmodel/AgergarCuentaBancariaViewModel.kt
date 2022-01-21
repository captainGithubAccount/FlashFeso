package com.example.flashfeso_lwj.flashfeso.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.flashfeso_lwj.flashfeso.repository.AllBankRepository
import com.example.flashfeso_lwj.flashfeso.repository.AuthBankInfoRepository
import com.example.flashfeso_lwj.flashfeso.repository.RiskInfoRepository

class AgergarCuentaBancariaViewModel @ViewModelInject constructor(
    private val riskInfoRep: RiskInfoRepository,
    private val authBankInfoRep: AuthBankInfoRepository,
    private val allBankRep : AllBankRepository
): ViewModel() {

    val riskInfoLiveData = riskInfoRep.getDataLiveData()
    fun queryRiskInfo(map: HashMap<String, Any>){
        riskInfoRep.query(map)
    }


    val authBankInfoLiveData = authBankInfoRep.getDataLiveData()
    fun queryAuthBankInfo(map: HashMap<String, Any>) {
        authBankInfoRep.query(map)
    }

    val allBankLiveData = allBankRep.getDataLiveData()
    fun queryAllBank() {
        allBankRep.query()
    }
}

