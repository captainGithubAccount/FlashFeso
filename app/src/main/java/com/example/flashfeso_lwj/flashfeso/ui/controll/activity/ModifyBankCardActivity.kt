package com.example.flashfeso_lwj.flashfeso.ui.controll.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.text.InputFilter
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.flashfeso_lwj.R
import com.example.flashfeso_lwj.base.event.CommonDialogEvent
import com.example.flashfeso_lwj.base.ui.controll.activity.BasePageStyleActivity
import com.example.flashfeso_lwj.base.utils.SimpleProgressDialogUtil
import com.example.flashfeso_lwj.databinding.ActivityModifyBankCardBinding
import com.example.flashfeso_lwj.flashfeso.base.DataResult
import com.example.flashfeso_lwj.flashfeso.entity.AllBankEntity
import com.example.flashfeso_lwj.flashfeso.event.InfomationSelectItemOnClickListener
import com.example.flashfeso_lwj.flashfeso.ui.controll.dialog.InfomationSelectDialog
import com.example.flashfeso_lwj.flashfeso.ui.controll.dialog.JumpSysytemLocDialog
import com.example.flashfeso_lwj.flashfeso.utils.APP_PERMISSIONS
import com.example.flashfeso_lwj.flashfeso.utils.InfoUtil
import com.example.flashfeso_lwj.flashfeso.utils.addToast
import com.example.flashfeso_lwj.flashfeso.utils.back
import com.example.flashfeso_lwj.flashfeso.viewmodel.AgergarCuentaBancariaViewModel
import com.example.flashfeso_lwj.flashfeso.viewmodel.LoginViewModel
import com.example.lwj_common.common.managementUtils.*
import com.example.lwj_common.common.ui.controll.tools.ktx.deleteBlank
import com.example.lwj_common.common.ui.controll.tools.ktx.isUseful
import com.example.lwj_common.common.ui.controll.tools.ktx.toBankCardEditText
import com.example.lwj_common.common.ui.controll.tools.ktx.toJson
import com.example.lwj_common.common.ui.controll.tools.utils.LocationUtils
import com.rs.flashpeso.management.ManagementUtils
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception
import javax.inject.Inject

@AndroidEntryPoint
class ModifyBankCardActivity: BasePageStyleActivity<ActivityModifyBankCardBinding>(), InfomationSelectItemOnClickListener {
    private var tipoDeCuentaPosition = 1
    var tipoDeCuentaDialog: InfomationSelectDialog? = null
    var selectBankDialog: InfomationSelectDialog? = null
    var allBank: List<AllBankEntity>? = null
    val agergarCuentaBancariaViewModel: AgergarCuentaBancariaViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()
    lateinit var bkCardNumber: String
    lateinit var bkName: String
    private val REQUEST_EXTERNAL_STORAGE = 222
    var bankNo: Int = 0
    var locationUtils: LocationUtils = LocationUtils()
    private var isRisk = false

    companion object{

        val RESULTCODE = 1
    }

    @Inject
    @JvmField
    var mSimpleProgressDialogUtil: SimpleProgressDialogUtil? = null
    override fun observe() {
        agergarCuentaBancariaViewModel.authBankInfoLiveData.observe(this, Observer {
            it.whenError {
                mSimpleProgressDialogUtil?.closeHUD()
            }
            it.whenSuccessResponse {
                if((it as DataResult.Success).successMessagle == resources.getString(R.string.success)){
                    Toast.makeText(this@ModifyBankCardActivity, it.successMessagle, Toast.LENGTH_SHORT)
                        .show()
                    val intent = Intent()
                    intent.putExtra("bkCardNumber", bkCardNumber)
                    setResult(RESULTCODE, intent)
                    onBackPressed()
                }
            }
            it.whenClear {
                InfoUtil.clear()
                loginViewModel.queryNotifyUpdateLoginLiveData()
                Toast.makeText(this@ModifyBankCardActivity, (it as DataResult.Clear).clearMessage, Toast
                    .LENGTH_SHORT).show()
                onBackPressed()
            }
        })

        agergarCuentaBancariaViewModel.allBankLiveData.observe(this, Observer {
            it.whenSuccess {
                binding.error.llError.visibility = View.GONE
                binding.progress.llProgress.visibility = View.GONE
                allBank = it
            }
            it.whenError {
                binding.error.llError.visibility = View.VISIBLE
                binding.progress.llProgress.visibility = View.GONE
                addToast(this, (it as DataResult.Error).errorMessage !!)
            }
            it.whenClear {
                InfoUtil.clear()
                loginViewModel.queryNotifyUpdateLoginLiveData()
                addToast(this, (it as DataResult.Clear).clearMessage !!)
                onBackPressed()
            }
        })

        agergarCuentaBancariaViewModel.riskInfoLiveData.observe(this, Observer {
            mSimpleProgressDialogUtil?.closeHUD()
            it.whenSuccessResponse {
                if((it as DataResult.Success).successMessagle == resources.getString(R.string.success)) {
                    queryAuthBankInfo(bkCardNumber, bkName, bankNo)
                }
            }
            it.whenError {
                isRisk = false
            }
            it.whenClear {
                InfoUtil.clear()
                loginViewModel.queryNotifyUpdateLoginLiveData()
                addToast(this, (it as DataResult.Clear).clearMessage !!)
                onBackPressed()
            }
        })


    }

    private fun queryAuthBankInfo(bkCardNumber: String, bkName: String, bankNo: Int) {
        val map: MutableMap<String, Any> = HashMap()
        map["bkType"] = tipoDeCuentaPosition
        map["bkCardNumber"] = bkCardNumber
        map["bkName"] = bkName
        map["bkCode"] = bankNo
        agergarCuentaBancariaViewModel.queryAuthBankInfo(map as HashMap<String, Any>)
    }

    override fun afterBindView() {
        super.afterBindView()
        binding.header.ivCommonBarBack.back(this)

        binding.error.viewErrorUpdate.setOnClickListener {
            if(isClickUseful()) {
                queryAllBank()
            }
        }

        binding.tipoDeCuentaLl.setOnClickListener {
            if(isClickUseful()) {
                hideKeyboardAll()
                val data: Array<String> = resources.getStringArray(R.array.array_card_type)
                tipoDeCuentaDialog = InfomationSelectDialog.newInstance().addSetting(AgergarCuentaBancariaActivity.Dialog.TIPO_DECUENT.ordinal, data.toList(), this)
                tipoDeCuentaDialog?.show(supportFragmentManager, "tipoDeCuentaDialog2")
            }
        }

        binding.bancoLl.setOnClickListener {
            if(isClickUseful() && ! allBank.isNullOrEmpty()) {
                hideKeyboardAll()
                val data = arrayListOf<String>()
                allBank?.forEachIndexed {_, allBankEntity ->
                    data.add(allBankEntity.name)
                }
                selectBankDialog = InfomationSelectDialog.newInstance().addSetting(AgergarCuentaBancariaActivity.Dialog.SELECT_BANK.ordinal, data, this)
                selectBankDialog !!.show(supportFragmentManager, "selectBankDialog")

            }
        }

        binding.confirm.setOnClickListener {
            hideKeyboardAll()
            val banco = binding.bancoTv.text.toString().trim()
            if(! banco.isUseful()) {
                //吐司上下文可能需要修改
                addToast(this, resources.getString(R.string.introduzca_el_banco))
                return@setOnClickListener
            }
            bkCardNumber = binding.tarjetaTv.text?.deleteBlank()!!
            if(tipoDeCuentaPosition == 0 && (! bkCardNumber.isUseful() || bkCardNumber.length != 18)) {
                addToast(this, getResources().getString(R.string.introduzca_el_numero_correcto_de_tarjeta_de_16_bits))
                return@setOnClickListener
            } else if(tipoDeCuentaPosition == 0 && (! bkCardNumber.isUseful() || bkCardNumber.length != 16)) {
                addToast(this, getResources().getString(R.string.introduzca_el_numero_correcto_de_tarjeta_de_16_bits))
                return@setOnClickListener
            }

            var bankNo = - 1
            allBank?.forEach {
                if(it.name == banco) {
                    bankNo = it.code
                }
            }

            if(isClickUseful() && bankNo > 0) {
                queryAuthBankInfo(bkCardNumber, banco, bankNo)
            }

        }
    }





    fun queryAllBank() {
        binding.error.llError.setVisibility(View.GONE)
        binding.progress.llProgress.setVisibility(View.VISIBLE)
        agergarCuentaBancariaViewModel.queryAllBank()
    }

    override fun afterInitView() {
        super.afterInitView()
        queryAllBank()
    }



    override fun ActivityModifyBankCardBinding.initView() {
        binding.header.tvCommonBarTitle.text = resources.getString(R.string.modificar_la_cuenta_bancaria)
        binding.tarjetaTv.toBankCardEditText()

    }

    fun hideKeyboardAll() {
        if(binding.tarjetaTv.hasFocus()) {
            val imm = binding.tarjetaTv.context.getSystemService(Context.INPUT_METHOD_SERVICE) as
                    InputMethodManager?
            imm?.hideSoftInputFromWindow(binding.tarjetaTv.windowToken, 0)
        }
    }

    override fun onDialogItemClick(list: List<String>, flag: Int) {
        when(flag) {
            AgergarCuentaBancariaActivity.Dialog.TIPO_DECUENT.ordinal -> {
                tipoDeCuentaDialog?.dismiss()
                binding.tipoDeCuentaTv.text = list[0]
                val position = list[1]
                when(position.toInt()) {
                    0 -> {
                        tipoDeCuentaPosition = 0
                        binding.tarjetaTt.setText(resources.getString(R.string.tarjeta_18_digitos))
                        binding.tarjetaTv.setFilters(arrayOf<InputFilter>(InputFilter.LengthFilter(22)))
                    }
                    else -> {
                        tipoDeCuentaPosition = 1
                        binding.tarjetaTt.setText(resources.getString(R.string.tarjeta_16_digitos))
                        binding.tarjetaTv.setFilters(arrayOf<InputFilter>(InputFilter.LengthFilter(19)))
                        val s1: String = binding.tarjetaTv.getText().toString()
                        //由于一开始选择的银行类型是et长度为22位的,导致选择et长度为19位的et时候, 此时原本输入的22位长度字符串需要截取为19位, 并将光标后移至最后一位
                        if(s1.length >= 19) {
                            binding.tarjetaTv.setText(s1.substring(0, 19))
                            binding.tarjetaTv.setSelection(19)
                        }
                    }
                }
            }
            AgergarCuentaBancariaActivity.Dialog.SELECT_BANK.ordinal -> {
                binding.bancoTv.text = list[0]
                selectBankDialog?.dismiss()
            }
        }
    }

}