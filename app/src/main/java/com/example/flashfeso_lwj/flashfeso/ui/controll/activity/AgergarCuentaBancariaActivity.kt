package com.example.flashfeso_lwj.flashfeso.ui.controll.activity

import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.flashfeso_lwj.R
import com.example.flashfeso_lwj.base.entity.DataResult
import com.example.flashfeso_lwj.base.ui.controll.activity.BasePageStyleActivity
import com.example.flashfeso_lwj.databinding.ActivityAgergarCuentaBancariaBinding
import com.example.flashfeso_lwj.flashfeso.entity.AllBankEntity
import com.example.flashfeso_lwj.flashfeso.event.InfomationSelectItemOnClickListener
import com.example.flashfeso_lwj.flashfeso.ui.controll.dialog.InfomationSelectDialog
import com.example.flashfeso_lwj.flashfeso.utils.InfoUtil
import com.example.flashfeso_lwj.flashfeso.utils.addToast
import com.example.flashfeso_lwj.flashfeso.utils.back
import com.example.flashfeso_lwj.flashfeso.viewmodel.AgergarCuentaBancariaViewModel
import com.example.flashfeso_lwj.flashfeso.viewmodel.LoginViewModel
import com.example.lwj_common.common.ui.controll.tools.ktx.toBankCardEditText
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AgergarCuentaBancariaActivity : BasePageStyleActivity<ActivityAgergarCuentaBancariaBinding>(),
    InfomationSelectItemOnClickListener {
    var tipoDeCuentaDialog: InfomationSelectDialog? = null
    private var tipoDeCuentaPosition = 1

    var allBank: List<AllBankEntity>? = null
    private val mViewModel: AgergarCuentaBancariaViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()
    override fun observe() {
        mViewModel.allBankLiveData.observe(this, Observer {
            it.whenSuccess {
                binding.error.llError.visibility = View.GONE
                binding.progress.llProgress.visibility = View.GONE
                allBank = it
            }
            it.whenError {
                binding.error.llError.visibility = View.VISIBLE
                binding.progress.llProgress.visibility = View.GONE
                addToast(this, (it as DataResult.Error).errorMessage!!)
            }
            it.whenClear {
                InfoUtil.clear()
                loginViewModel.queryNotifyUpdateLoginLiveData()
                addToast(this, (it as DataResult.Clear).clearMessage!!)
                onBackPressed()
            }
        })
    }

    override fun afterBindView() {
        super.afterBindView()
        binding.header.ivCommonBarBack.back(this@AgergarCuentaBancariaActivity)

        binding.error.viewErrorUpdate.setOnClickListener {
            if (isClickUseful()){
                queryAllBank()
            }
        }

        binding.tipoDeCuentaLl.setOnClickListener {
            val data: Array<String> = resources.getStringArray(R.array.array_card_type)
            tipoDeCuentaDialog = InfomationSelectDialog.newInstance().addSetting(Dialog.TIPO_DECUENT.ordinal, data.toList(), this)
            tipoDeCuentaDialog?.show(supportFragmentManager, "tipoDeCuentaDialog")
        }
    }

    override fun ActivityAgergarCuentaBancariaBinding.initView() {
        binding.header.tvCommonBarTitle.text = resources.getString(R.string.agergar_cuenta_bancaria)
        binding.tarjetaTv.toBankCardEditText()
    }

    override fun afterInitView() {
        super.afterInitView()
        queryAllBank()
    }

    fun queryAllBank(){
        binding.error.llError.setVisibility(View.GONE)
        binding.progress.llProgress.setVisibility(View.VISIBLE)
        mViewModel.queryAllBank()
    }

    enum class Dialog{
        TIPO_DECUENT
    }

    override fun onDialogItemClick(list: List<String>, flag: Int) {
        when(flag){
            Dialog.TIPO_DECUENT.ordinal -> {
                binding.tipoDeCuentaTv.text = list[0]
                val position = list[1]
                when(position.toInt()){
                    0 -> {
                        tipoDeCuentaPosition = 0
                        binding.tarjetaTt.setText(resources.getString(R.string.tarjeta_18_digitos))
                        binding.tarjetaTv.setFilters(arrayOf<InputFilter>(LengthFilter(22)))
                    }
                    else -> {
                        binding.tarjetaTt.setText(resources.getString(R.string.tarjeta_16_digitos))
                        binding.tarjetaTv.setFilters(arrayOf<InputFilter>(LengthFilter(19)))
                        val s1: String = binding.tarjetaTv.getText().toString()
                        if (s1.length >= 19) {
                            binding.tarjetaTv.setText(s1.substring(0, 19))
                            binding.tarjetaTv.setSelection(19)
                        }
                    }
                }
            }
        }
    }


}