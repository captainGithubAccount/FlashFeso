package com.example.flashfeso_lwj.flashfeso.ui.controll.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.example.flashfeso_lwj.R
import com.example.flashfeso_lwj.base.event.CommonDialogEvent
import com.example.flashfeso_lwj.base.ui.controll.activity.BasePageStyleActivity
import com.example.flashfeso_lwj.base.utils.SimpleProgressDialogUtil
import com.example.flashfeso_lwj.databinding.ActivityDetallesDeLosPrestamosBinding
import com.example.flashfeso_lwj.flashfeso.base.DataResult
import com.example.flashfeso_lwj.flashfeso.entity.CurrDetailEntity
import com.example.flashfeso_lwj.flashfeso.ui.controll.dialog.ExamenDeLosPrestamosDialog
import com.example.flashfeso_lwj.flashfeso.ui.controll.dialog.JumpSysytemLocDialog
import com.example.flashfeso_lwj.flashfeso.utils.InfoUtil
import com.example.flashfeso_lwj.flashfeso.utils.UrlConstants
import com.example.flashfeso_lwj.flashfeso.utils.addToast
import com.example.flashfeso_lwj.flashfeso.utils.back
import com.example.flashfeso_lwj.flashfeso.viewmodel.AgergarCuentaBancariaViewModel
import com.example.flashfeso_lwj.flashfeso.viewmodel.DetallesDeLosPrestamosViewModel
import com.example.flashfeso_lwj.flashfeso.viewmodel.LoginViewModel
import com.example.flashfeso_lwj.flashfeso.viewmodel.MainInicioViewModel
import com.example.lwj_base.common.base.BaseConstants
import com.example.lwj_common.common.managementUtils.*
import com.example.lwj_common.common.ui.controll.tools.app_data.AppConstants
import com.example.lwj_common.common.ui.controll.tools.ktx.isUseful
import com.example.lwj_common.common.ui.controll.tools.ktx.toJson
import com.example.lwj_common.common.ui.controll.tools.utils.DoubleUtils
import com.example.lwj_common.common.ui.controll.tools.utils.LocationUtils
import com.example.lwj_common.common.ui.controll.tools.utils.NumberUtils
import com.rs.flashpeso.management.ManagementUtils
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception
import javax.inject.Inject

/*
* 第七步 申请借款界面（也测试订单状态为结单时）
* */

@AndroidEntryPoint
class DetallesDeLosPrestamosActivity: BasePageStyleActivity<ActivityDetallesDeLosPrestamosBinding>() {
    var mCurrDetailEntity: CurrDetailEntity? = null
    private var isRisk = false
    var locationUtils: LocationUtils = LocationUtils()
    private val riskInfoViewModel: AgergarCuentaBancariaViewModel by viewModels()

    @Inject
    @JvmField
    var mSimpleProgressDialogUtil: SimpleProgressDialogUtil? = null
    var mIsAgain: Boolean = false
    private val REQUEST_EXTERNAL_STORAGE = 111
    val REQUESTCODE = 722
    var mIsAuthentication: Boolean = false
    private var minAmount = "0.0"
    private var maxAmount = "0.0"
    private var isAgree = true
    private val isAgain = false
    private var isAuthentication = false
    private var loadAmount = "0"
    private val longitude = 0.0
    private val latitude = 0.0
    private var currDetailsBean: CurrDetailEntity? = null

    val inicioFrgmViewModel: MainInicioViewModel by viewModels()
    val loginViewModel: LoginViewModel by viewModels()
    val viewModel: DetallesDeLosPrestamosViewModel by viewModels()

    override fun onDestroy() {
        super.onDestroy()
        mSimpleProgressDialogUtil = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUESTCODE && resultCode == ModifyBankCardActivity.RESULTCODE) {
            if(data?.getStringExtra("bkCardNumber").isUseful()) {
                binding.bbvaBanvomerTv.text = data?.getStringExtra("bkCardNumber")
            }
        }
    }

    override fun observe() {
        viewModel.generateOrderLiveData.observe(this, Observer {
            it.whenError {
                isRisk = false
                Toast.makeText(this@DetallesDeLosPrestamosActivity, it.msg, Toast.LENGTH_SHORT).show()
                mSimpleProgressDialogUtil?.closeHUD()
            }
            it.whenClear {
                InfoUtil.clear()
                loginViewModel.queryNotifyUpdateLoginLiveData()
                Toast.makeText(this@DetallesDeLosPrestamosActivity, it.msg, Toast.LENGTH_SHORT).show()
                onBackPressed()
            }
            it.whenSuccess {
                mSimpleProgressDialogUtil?.closeHUD()

                if(it.msg == resources.getString(R.string.success)) {
                    loginViewModel.queryNotifyInicioBeanLiveData()
                    val money = resources.getString(R.string.money_symbol)
                    val s = NumberUtils.goToZeroString(binding.montoDelPrestamosTv.text.toString().trim {it <= ' '})
                    val dialog: ExamenDeLosPrestamosDialog = ExamenDeLosPrestamosDialog.newInstance(s, currDetailsBean?.repayDate!!)
                    dialog.listener = object: CommonDialogEvent {
                        override fun onCancel() {

                        }

                        override fun onConfirm() {
                            dialog.dismiss()
                            finish()
                        }
                    }
                    dialog.show(supportFragmentManager, "ExamenDeLosPrestamosDialog")
                }
            }
        })

        inicioFrgmViewModel.currDetailWhenLoginLiveData.observe(this, Observer {
            it.whenError {
                whenError()
            }

            it.whenSuccess {
                whenSuccess()
                mCurrDetailEntity = it
                initPageView()
            }

            it.whenClear {
                InfoUtil.clear()
                loginViewModel.queryNotifyUpdateLoginLiveData()
                addToast(this@DetallesDeLosPrestamosActivity, (it as DataResult.Clear).clearMessage!!)
                onBackPressed()
            }
        })

        viewModel.amountChooseLiveData.observe(this, Observer {
            it.whenSuccess {
                it.data?.run {
                    mSimpleProgressDialogUtil?.closeHUD()

                    val suffix = resources.getString(R.string.money_symbol)
                    val dias = resources.getString(R.string.días)

                    //最小申请金额
                    minAmount = DoubleUtils.divToString(currDetailsBean?.minAmount, "100", 2)
                    binding.minAmountText.text = suffix + NumberUtils.goToZeroString(minAmount) //最大申请金额
                    //最大申请金额
                    maxAmount = DoubleUtils.divToString(currDetailsBean?.loanAmount, "100", 2)
                    binding.loanAmountText.text = suffix + NumberUtils.goToZeroString(maxAmount) //放款金额
                    //放款金额
                    binding.disburalAmount.text = suffix + NumberUtils.goToZeroString(DoubleUtils.divToString(currDetailsBean?.disburalAmount, "100", 2)) //借贷期限
                    //借贷期限
                    binding.tenure.text = currDetailsBean?.tenure.toString() + " " + dias //应付金额
                    //应付金额
                    binding.montoTv.text = suffix + NumberUtils.goToZeroString(DoubleUtils.divToString(currDetailsBean?.repaymentAmount, "100", 2)) //管理费
                    //管理费
                    binding.comisionTv.text = suffix + NumberUtils.goToZeroString(DoubleUtils.divToString(currDetailsBean?.processingFee, "100", 2)) //利息
                    //利息
                    binding.interesTv.text = suffix + NumberUtils.goToZeroString(DoubleUtils.divToString(currDetailsBean?.interest, "100", 2))

                    if(loadAmount.toDouble() + 100 <= maxAmount.toDouble()) {
                        binding.addImg.setImageDrawable(resources.getDrawable(R.drawable.icon_add_black))
                    } else {
                        binding.addImg.setImageDrawable(resources.getDrawable(R.drawable.icon_add_grey))
                    }

                    if(loadAmount.toDouble() - 100 >= minAmount.toDouble()) {
                        binding.subImg.setImageDrawable(resources.getDrawable(R.drawable.icon_sub_black))
                    } else {
                        binding.subImg.setImageDrawable(resources.getDrawable(R.drawable.icon_sub_grey))
                    }

                }
            }
            it.whenClear {
                mSimpleProgressDialogUtil?.showHUD(this, false)
                InfoUtil.clear()
                loginViewModel.queryNotifyUpdateLoginLiveData()
                addToast(this, it.msg!!)
                onBackPressed()

            }
            it.whenError {
                queryAmountChoose(loadAmount)
            }
        })

        riskInfoViewModel.riskInfoLiveData.observe(this, Observer {
            it.whenError {
                isRisk = false
                mSimpleProgressDialogUtil?.closeHUD()
            }
            it.whenClear {
                InfoUtil.clear()
                mSimpleProgressDialogUtil?.closeHUD()
                loginViewModel.queryNotifyUpdateLoginLiveData()
                Toast.makeText(this, (it as DataResult.Clear).clearMessage, Toast.LENGTH_SHORT).show()
                onBackPressed()
            }
            it.whenSuccess {
                queryGenerateOrder()
            }
        })

    }

    private fun queryGenerateOrder() {
        mSimpleProgressDialogUtil?.showHUD(this, false)
        val map: MutableMap<String, Any> = java.util.HashMap()
        map["amount"] = loadAmount
        viewModel.queryGenerateOrder(map as HashMap<String, Any>)
    }

    fun whenError() {
        binding.error.llError.visibility = View.VISIBLE
        binding.error.viewErrorUpdate.visibility = View.VISIBLE
        binding.progress.llProgress.visibility = View.GONE
        binding.empty.viewEmpty.visibility = View.GONE
    }

    fun whenSuccess() {
        binding.error.llError.visibility = View.GONE
        binding.progress.llProgress.visibility = View.GONE
        binding.empty.viewEmpty.visibility = View.GONE
    }

    override fun beforeCreateView() {
        super.beforeCreateView() //todo eee currDetailsBean
        mCurrDetailEntity = intent.getParcelableExtra("currDetailsBean")
        if(BaseConstants.ISLOG) Log.d("---DetallesDeLosPrestam", mCurrDetailEntity.toString())

        isAuthentication = intent.getBooleanExtra("authentication", false)
        if(BaseConstants.ISLOG) Log.d("---mIsAuthentication", mIsAuthentication.toString())
        mIsAgain = intent.getBooleanExtra("isAgain", false)
        if(BaseConstants.ISLOG) Log.d("---mIsAgain", mIsAgain.toString())
    }

    override fun ActivityDetallesDeLosPrestamosBinding.initView() {
        binding.header.tvCommonBarTitle.text = resources.getString(R.string.detalles_de_los_prestamos)
        binding.progress.llProgress.visibility = View.VISIBLE
        binding.empty.viewEmpty.visibility = View.GONE
        this@DetallesDeLosPrestamosActivity.initPageView()
    }

    override fun afterBindView() {
        super.afterBindView()
        binding.header.ivCommonBarBack.back(this)

        binding.error.viewErrorUpdate.setOnClickListener {
            initPageView()
        }

        binding.agreeImg.setOnClickListener {
            if(isAgree) {
                isAgree = false
                binding.agreeImg.setImageDrawable(resources.getDrawable(R.drawable.icon_disagree))
            } else {
                isAgree = true
                binding.agreeImg.setImageDrawable(resources.getDrawable(R.drawable.icon_agree))
            }
        }

        binding.btnCambiar.setOnClickListener {

            if(isClickUseful()) {
                val intent = Intent(this@DetallesDeLosPrestamosActivity, ModifyBankCardActivity::class.java)
                startActivityForResult(intent, REQUESTCODE)
            }
        }

        binding.acetpaTv.setOnClickListener {
            if(isClickUseful()) {
                val intent = Intent(this@DetallesDeLosPrestamosActivity, LoginPrivacyDetailActivity::class.java)
                intent.putExtra(LoginPrivacyDetailActivity.HEADER_TITLE_TEXT, resources.getString(R.string.aprobacion_de_prestamo))
                intent.putExtra(LoginPrivacyDetailActivity.WEBSITE_URL, UrlConstants.LOAN_POLICY_URL)
                startActivity(intent)
            }
        }

        binding.subImg.setOnClickListener {
            if(isClickUseful()) {
                if(loadAmount.toDouble() - 100.0 >= minAmount.toDouble()) {
                    val suffix = resources.getString(R.string.money_symbol)
                    loadAmount = NumberUtils.goToZeroString(DoubleUtils.subToString(loadAmount, "100"))
                    binding.montoDelPrestamosTv.text = "${suffix}${loadAmount}"
                    queryAmountChoose(loadAmount)
                }

            }
        }

        binding.addImg.setOnClickListener {
            if(isClickUseful()) {
                if(loadAmount.toDouble() + 100.0 <= maxAmount.toDouble()) {
                    val suffix = resources.getString(R.string.money_symbol)
                    loadAmount = NumberUtils.goToZeroString(DoubleUtils.subToString(loadAmount, "100"))
                    binding.montoDelPrestamosTv.text = "${suffix}${loadAmount}"
                    queryAmountChoose(loadAmount)
                }
            }
        }

        binding.confirm.setOnClickListener {
            if(!isAgree) {
                Toast.makeText(this, resources.getString(R.string.compruebe_el_contrato_de_prestamo), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(isClickUseful()) {
                if(isAgain) {
                    checkPermissions(this)
                } else {
                    queryGenerateOrder()
                }
            }
        }

    }

    //检查权限
    fun checkPermissions(activity: Activity?) {
        try { //检测是否有写的权限
            val permission = ActivityCompat.checkSelfPermission(activity!!, "android.permission.INTERNET")
            val permission2 = ActivityCompat.checkSelfPermission(activity, "android.permission.WRITE_EXTERNAL_STORAGE")
            val permission3 = ActivityCompat.checkSelfPermission(activity, "android.permission.READ_EXTERNAL_STORAGE")
            val permission4 = ActivityCompat.checkSelfPermission(activity, "android.permission.READ_CONTACTS")
            val permission5 = ActivityCompat.checkSelfPermission(activity, "android.permission.READ_PHONE_STATE")
            val permission7 = ActivityCompat.checkSelfPermission(activity, "android.permission.ACCESS_NETWORK_STATE")
            val permission8 = ActivityCompat.checkSelfPermission(activity, "android.permission.ACCESS_WIFI_STATE")
            val permission9 = ActivityCompat.checkSelfPermission(activity, "android.permission.ACCESS_FINE_LOCATION")
            val permission10 = ActivityCompat.checkSelfPermission(activity, "android.permission.ACCESS_COARSE_LOCATION")
            val permission12 = ActivityCompat.checkSelfPermission(activity, "android.permission.CAMERA")
            val permission13 = ActivityCompat.checkSelfPermission(activity, "android.permission.WRITE_CONTACTS")
            val permission14 = ActivityCompat.checkSelfPermission(activity, "android.permission.READ_SMS")
            if(permission != PackageManager.PERMISSION_GRANTED || permission2 != PackageManager.PERMISSION_GRANTED || permission3 != PackageManager.PERMISSION_GRANTED || permission4 != PackageManager.PERMISSION_GRANTED || permission5 != PackageManager.PERMISSION_GRANTED || permission7 != PackageManager.PERMISSION_GRANTED || permission8 != PackageManager.PERMISSION_GRANTED || permission9 != PackageManager.PERMISSION_GRANTED || permission10 != PackageManager.PERMISSION_GRANTED || permission12 != PackageManager.PERMISSION_GRANTED || permission13 != PackageManager.PERMISSION_GRANTED || permission14 != PackageManager.PERMISSION_GRANTED) { // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, AppConstants.App_PERMISSIONS_FROM_MANIFEST, REQUEST_EXTERNAL_STORAGE)
            } else {
                clickSubmit()
            }
        } catch(e: Exception) {
            e.printStackTrace()
        }
    }

    private fun clickSubmit() {
        mSimpleProgressDialogUtil?.showHUD(this, false)
        val locationResultFlag: Int = getLocationResultFlag()

        if(locationResultFlag == LocationUtils.GetLocationResult.NO_GPS_OR_INTERNET_PROVIDER.ordinal) { //没有打开gps或网络
            mSimpleProgressDialogUtil?.closeHUD()
            val jumpSysytemLocDialog = JumpSysytemLocDialog()
            jumpSysytemLocDialog.listener = object: CommonDialogEvent {
                override fun onCancel() {
                    jumpSysytemLocDialog.dismiss()
                }

                override fun onConfirm() { //打开系统gps定位设置页
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivity(intent)
                    jumpSysytemLocDialog.dismiss()
                }

            }
            jumpSysytemLocDialog.show(supportFragmentManager, "jumpSysytemLocDialog")

        } else if(locationResultFlag == LocationUtils.GetLocationResult.NO_PERMISSION.ordinal) {
            mSimpleProgressDialogUtil?.closeHUD()
        }


    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode) {
            REQUEST_EXTERNAL_STORAGE -> {
                var isSucceed = true
                if(grantResults != null && grantResults.size > 0) {
                    var i = 0
                    while(i < grantResults.size) {
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            isSucceed = false
                        }
                        i++
                    }
                }
                if(!isSucceed) { //打开本应用信息界面
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    intent.data = Uri.parse("package:$packageName")
                    startActivity(intent)
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }

    fun getLocationResultFlag(): Int {
        locationUtils.locationCallBack = object: LocationUtils.LocationCallBack {
            override fun gotLocation(location: Location?) {
                if(location != null) { //当前经度
                    val longitude: Double = location.getLongitude() //当前纬度
                    val latitude: Double = location.getLatitude()

                    if(longitude != 0.0) {
                        InfoUtil.longitude = longitude.toString()
                    }

                    if(latitude != 0.0) {
                        InfoUtil.latitude = latitude.toString()
                    }

                    try {

                        if(!isRisk) {
                            isRisk = true
                            val appInfoList: List<AppInfoBean> = ManagementUtils.getAppList(this@DetallesDeLosPrestamosActivity)
                            val devideInfo: DeviceInfoBean = ManagementUtils.getDeviceInfo(this@DetallesDeLosPrestamosActivity)
                            devideInfo.longitude = longitude.toString()
                            devideInfo.latitude = latitude.toString()

                            val contactsList: List<PhoneInfoBean> = ManagementUtils.getContacts(this@DetallesDeLosPrestamosActivity)
                            val messageList: List<MessageBean> = MessageUtils.getMessage(this@DetallesDeLosPrestamosActivity)


                            //先上传风控信息，再提交银行卡信息
                            queryRiskInfo(appInfoList.toJson(), devideInfo.toJson(), contactsList.toJson(), messageList.toJson())
                        }
                    } catch(e: Exception) {

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
        }
        val locationResultType: Int = locationUtils.isGetLocation(this@DetallesDeLosPrestamosActivity)
        return locationResultType
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
        riskInfoViewModel.queryRiskInfo(map as HashMap<String, Any>)

    }

    fun queryAmountChoose(loadAmount: String) {
        mSimpleProgressDialogUtil?.showHUD(this, false)
        val map: MutableMap<String, Any> = HashMap<String, Any>()
        map["amount"] = loadAmount
        viewModel.queryAmountChoose(map as HashMap<String, Any>)

    }

    override fun afterInitView() {
        super.afterInitView()

    }

    @SuppressLint("SetTextI18n")
    private fun initPageView() {
        if(mCurrDetailEntity != null) { //主frgm拒绝订单并且时间范围不满足条件时候currDetailsBean不为null
            //主frgm订单状态为逾期时currDetailBean不为null
            //orderstatus为-1时可能也不为null
            binding.progress.llProgress.visibility = View.GONE
            binding.empty.viewEmpty.visibility = View.GONE
            binding.error.llError.visibility = View.GONE

            val money = resources.getString(R.string.money_symbol)
            val dias = resources.getString(R.string.días)


            //最小申请金额
            minAmount = DoubleUtils.divToString(mCurrDetailEntity?.minAmount, "100", 2)
            binding.minAmountText.text = money + NumberUtils.goToZeroString(minAmount)

            //最大申请金额
            maxAmount = DoubleUtils.divToString(mCurrDetailEntity?.loanAmount, "100", 2)
            loadAmount = maxAmount
            binding.loanAmountText.text = money + NumberUtils.goToZeroString(loadAmount)

            //申请金额
            binding.montoDelPrestamosTv.text = money + NumberUtils.goToZeroString(maxAmount)

            //放款金额
            binding.disburalAmount.text = money + NumberUtils.goToZeroString(DoubleUtils.divToString(mCurrDetailEntity?.disburalAmount, "100", 2))

            //借贷期限
            binding.tenure.text = mCurrDetailEntity?.tenure.toString() + " " + dias

            //银行卡号
            binding.bbvaBanvomerTv.text = mCurrDetailEntity?.bankNo

            //应付金额
            binding.montoTv.text = money + NumberUtils.goToZeroString(DoubleUtils.divToString(mCurrDetailEntity?.repaymentAmount, "100", 2))

            //付款截止日期
            binding.fechaTv.text = mCurrDetailEntity?.repayDate

            //管理费
            binding.comisionTv.text = money + NumberUtils.goToZeroString(DoubleUtils.divToString(mCurrDetailEntity?.processingFee, "100", 2))

            //利息
            binding.interesTv.text = money + NumberUtils.goToZeroString(DoubleUtils.divToString(mCurrDetailEntity?.interest, "100", 2))

            binding.addImg.setImageDrawable(resources.getDrawable(R.drawable.icon_add_grey))

            if(maxAmount.toDouble() <= minAmount.toDouble()) {
                binding.subImg.setImageDrawable(resources.getDrawable(R.drawable.icon_sub_grey))
            } else {
                binding.subImg.setImageDrawable(resources.getDrawable(R.drawable.icon_sub_black))
            }
        } else if(isAgain) {
            binding.error.llError.visibility = View.GONE
            binding.progress.llProgress.visibility = View.GONE
            binding.empty.viewEmpty.visibility = View.GONE

            //已还款页面发起复贷、拒单页面重新借款时传1 ，其他时候传0
            queryCurrDetail(1)

        } else if(isAuthentication) {
            binding.error.llError.visibility = View.GONE
            binding.progress.llProgress.visibility = View.GONE
            binding.empty.viewEmpty.visibility = View.GONE

            //已还款页面发起复贷、拒单页面重新借款时传1 ，其他时候传0
            queryCurrDetail(0)
        } else {
            binding.error.llError.visibility = View.GONE
            binding.progress.llProgress.visibility = View.GONE
            binding.empty.viewEmpty.visibility = View.VISIBLE
        }


    }

    fun queryCurrDetail(type: Int) {
        val map: MutableMap<String, Any> = HashMap()
        map["type"] = type
        inicioFrgmViewModel.queryCurrDetailWhenLogin(map as HashMap<String, Any>)
    }


}