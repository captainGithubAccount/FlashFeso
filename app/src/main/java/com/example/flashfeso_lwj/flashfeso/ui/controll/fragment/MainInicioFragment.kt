package com.example.flashfeso_lwj.flashfeso.ui.controll.fragment

import android.content.Intent
import android.os.Handler
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.flashfeso_lwj.R
import com.example.flashfeso_lwj.flashfeso.base.DataResult
import com.example.flashfeso_lwj.flashfeso.utils.InfoUtil
import com.example.flashfeso_lwj.base.utils.SimpleProgressDialogUtil
import com.example.flashfeso_lwj.databinding.FragmentMainInicioBinding
import com.example.flashfeso_lwj.flashfeso.entity.AuthUserInfoEntity
import com.example.flashfeso_lwj.flashfeso.entity.CurrDetailEntity
import com.example.flashfeso_lwj.flashfeso.ui.controll.activity.*
import com.example.flashfeso_lwj.flashfeso.utils.Constants
import com.example.flashfeso_lwj.flashfeso.utils.UrlConstants
import com.example.flashfeso_lwj.flashfeso.viewmodel.LoginViewModel
import com.example.flashfeso_lwj.flashfeso.viewmodel.MainInicioViewModel
import com.example.lwj_base.common.base.BaseConstants
import com.example.lwj_common.common.ui.controll.fragment.BaseRecyclerFragment
import com.example.lwj_common.common.ui.controll.tools.utils.DateUtil
import com.example.lwj_common.common.ui.controll.tools.utils.DoubleUtils
import com.example.lwj_common.common.ui.controll.tools.utils.NumberUtils
import com.example.lwj_common.common.ui.controll.tools.utils.NumberUtils.goToZeroString
import com.example.lwj_common.common.ui.controll.tools.utils.StringUtils
import dagger.hilt.android.AndroidEntryPoint
import java.util.HashMap
import javax.inject.Inject

@AndroidEntryPoint
class MainInicioFragment: BaseRecyclerFragment<FragmentMainInicioBinding>() {
    private var isFirstResume = true
    val mLoginViewModel: LoginViewModel by viewModels()
    private val mViewModels: MainInicioViewModel by viewModels()
    private val mHandler = Handler()


    @Inject
    @JvmField
    var mSimpleProgressDialogUtil: SimpleProgressDialogUtil? = null
    var mAuthUserInfoEntity: AuthUserInfoEntity? = null
    var mCurrDetailEntity: CurrDetailEntity? = null

    //    lateinit var mCurrDetailEntity: CurrDetailEntity
    private var isProgressAllFinish = 0
    private var isProgressError = false
    private var isNotGetData = true
    private var isProgressAllFinishWhenNotify = 0
    private var isProgressErrorWhenNotify = false

    companion object {
        //????????????????????????????????????, ????????????????????????????????????, ??????????????????????????????????????????
        fun getInstance(): MainInicioFragment = MainInicioFragment()
    }

    override fun onResume() {
        super.onResume()
        if(! isFirstResume) {
            getDataWhenLoginAtvNotify()
        } else {
            isFirstResume = false
        }
    }

    override fun onDetach() {
        mHandler.removeCallbacksAndMessages(null)
        super.onDetach()
    }


    override fun onDestroy() {
        mHandler.removeCallbacksAndMessages(null)
        super.onDestroy()
        mSimpleProgressDialogUtil = null
    }

    override fun afterInitView() {
        super.afterInitView()
    }

    override fun FragmentMainInicioBinding.initView() {
        binding.inclMainInicioProgress.tvTest.setOnClickListener {
            val intent = Intent(getFrgmActivity(), LoginActivity::class.java)
            startActivity(intent)
        }

        binding.inclMainIncioSecond.let {
            it.ivInicioItem.setImageResource(R.drawable.icon_home_help)
            it.tvInicioItemCenter.setText(R.string.ayuda)
        }
        getDataWhenFirstComeIn()
    }

    override fun afterBindView() {
        super.afterBindView()

        binding.inclMainInicioError.viewErrorUpdate.setOnClickListener(View.OnClickListener {
            if(isClickUseful()) {
                getDataWhenFirstComeIn()
            }
        })




        //??????
        //todo doing
        binding.tvInicioOlicita.setOnClickListener(View.OnClickListener {
            if(isClickUseful()) {
                if(InfoUtil.isLogin) {
                    if(InfoUtil.authAllin) { //todo ?????????????????????!
                        if(BaseConstants.ISLOG) Log.d("----", "authAllin???false?????????????????????")
                        if(mCurrDetailEntity != null) {
                            when(mCurrDetailEntity?.orderStatus) {
                                - 1 -> {
                                    val intent = Intent(activity, DetallesDeLosPrestamosActivity::class.java)
                                    //todo ddd currDetailsBean
                                    intent.putExtra("currDetailsBean", mCurrDetailEntity)
                                    startActivity(intent)
                                }
                                1 -> if (mCurrDetailEntity != null) {
                                    if (!StringUtils.isEmpty(mCurrDetailEntity?.rejectedReDate)) {
                                        val rejectedReDate: String =
                                            mCurrDetailEntity?.rejectedReDate!!
                                        try {
                                            val timeMillis: Long =
                                                DateUtil.getTimeMillis("$rejectedReDate 00:00:00")
                                            val currentTimeMillis = System.currentTimeMillis()
                                            if (currentTimeMillis >= timeMillis) {
                                                val intent4 = Intent(activity,
                                                    DetallesDeLosPrestamosActivity::class.java)
                                                intent4.putExtra("isAgain", true)
                                                startActivity(intent4)
                                            } else {
                                                val intent3 = Intent(activity,
                                                    FracasoDelPrestamoActivity::class.java)
                                                intent3.putExtra("currDetailsBean", mCurrDetailEntity)
                                                startActivity(intent3)
                                            }
                                        } catch (e: Exception) {
                                            e.printStackTrace()
                                        }
                                    } else {
                                        val intent4 = Intent(activity,
                                            DetallesDeLosPrestamosActivity::class.java)
                                        intent4.putExtra("isAgain", true)
                                        startActivity(intent4)
                                    }
                                }
                                0, 2 -> {
                                }
                                3, 5 -> {
                                    val intent2 = Intent(activity, ReembolsoActivity::class.java)
                                    intent2.putExtra("currDetailsBean", mCurrDetailEntity)
                                    startActivity(intent2)
                                }
                                4 -> {
                                    val intent5 = Intent(activity,
                                        DetallesDeLosPrestamosActivity::class.java)

                                    intent5.putExtra("isAgain", true)
                                    startActivity(intent5)
                                }


                            }
                        }

                        //todo ???????????? ??????172??????????????????115-169??????
                        //startActivity(AgergarCuentaBancariaActivity::class.java)
                    } else {
                        if(BaseConstants.ISLOG) Log.d("----", "authAllin???false?????????????????????")
                        val bankAuth: Boolean = InfoUtil.isBankAuth
                        val cardAuth: Boolean = InfoUtil.isCardAuth
                        val contactsAuth: Boolean = InfoUtil.isContactsAuth
                        val loanHisAuth: Boolean = InfoUtil.isLoanHisAuth
                        val employAuth: Boolean = InfoUtil.isEmployAuth
                        val addressAuth: Boolean = InfoUtil.isAddressAuth
                        if(! bankAuth && cardAuth) {
                            startActivity(AgergarCuentaBancariaActivity::class.java)
                        } else if(! cardAuth && contactsAuth) {
                            startActivity(InformacionDeIdetidadActivity::class.java)
                        } else if(! contactsAuth && loanHisAuth) {
                            startActivity(InformacionDeContactosActivity::class.java)
                        } else if(! loanHisAuth && employAuth) {
                            startActivity(HistorialCrediticioActivity::class.java)
                        } else if(! employAuth && addressAuth) {
                            startActivity(InfomationLaboralActivity::class.java)
                        } else if(! addressAuth) {
                            startActivity(InfomationBasicaActivity::class.java)
                        }
                    }
                } else {
                    startActivity(LoginActivity::class.java)
                }
            }
        })

        binding.inclMainInicioFirst.comoProtegemosLl.setOnClickListener {
            if(isClickUseful()) {
                val intent = Intent(activity, LoginPrivacyDetailActivity::class.java)
                intent.putExtra(LoginPrivacyDetailActivity.HEADER_TITLE_TEXT, requireActivity().resources.getString(R.string.seguridad_de_los_datos))
                intent.putExtra(LoginPrivacyDetailActivity.WEBSITE_URL, UrlConstants.SEGURIDAD_DE_LOS_DATOS_URL)
                startActivity(intent)
            }
        }

        //todo doing
        binding.inclMainIncioSecond.comoProtegemosLl.setOnClickListener {
            if(isClickUseful()){
                startActivity(PreguntasFrecuentesActivity::class.java)
            }
        }




    }


    override fun observe() {

        mLoginViewModel.notifyUpdateLoginLiveData.observe(this, Observer {
            mSimpleProgressDialogUtil?.showHUD(getFrgmActivity(), false)
            if(! isNotGetData && InfoUtil.isLogin) { //??????????????????????????????
                queryWhenLoginAndNotify()
            } else if(! isNotGetData && ! InfoUtil.isLogin) { //?????????????????????????????????
                mViewModels.queryWhenNotLoginAndNotify()
            } else { //?????????????????????????????????
                mSimpleProgressDialogUtil?.closeHUD()
            }
        })

        mLoginViewModel.notifyInicioBeanLiveData.observe(this, Observer {
            mSimpleProgressDialogUtil?.showHUD(getFrgmActivity(), false)
            if(! isNotGetData && InfoUtil.isLogin) { //??????????????????????????????
                queryWhenLoginAndNotify()
            } else if(! isNotGetData && ! InfoUtil.isLogin) { //?????????????????????????????????
                mViewModels.queryWhenNotLoginAndNotify()
            } else { //?????????????????????????????????
                mSimpleProgressDialogUtil?.closeHUD()
            }
        })


        //?????????????????????CurrDetail??????
        mViewModels.currDetailWhenLoginLiveData.observe(this, Observer {
            it.whenSuccess {
                it?.run {mCurrDetailEntity = this}
                isProgressAllFinish ++
                setDataWhenLogin()

            }
            it.whenError {
                whenError()
                isProgressAllFinish ++
                isProgressError = true
            }
            it.whenClear {
                Toast.makeText(activity, (it as DataResult.Clear).clearMessage, Toast.LENGTH_SHORT).show()
                InfoUtil.clear()
                getDataWhenFirstComeIn()
            }
        })

        //?????????????????????AuthUserInfo??????
        mViewModels.authUserInfoWhenLoginLiveData.observe(this, Observer {
            it.whenError {
                whenError()
                isProgressAllFinish ++
                isProgressError = true
            }
            it.whenSuccess {
                it?.run {mAuthUserInfoEntity = this}
                isProgressAllFinish ++
                setDataWhenLogin()
            }
        })


        //????????????????????????AuthUserInfo??????
        mViewModels.authUserInfoWhenNotLoginLiveData.observe(this, Observer {
            it.whenError {
                whenError()
            }
            it.whenSuccess {
                isNotGetData = false
                binding.inclMainInicioProgress.llProgress.visibility = View.GONE
                binding.inclMainInicioError.llError.visibility = View.GONE

                it?.run {mAuthUserInfoEntity = this}

                it?.run {
                    InfoUtil.authAllin = authAllin
                    InfoUtil.isAddressAuth = isAddressAuth
                    InfoUtil.isEmployAuth = isEmployAuth
                    InfoUtil.isLoanHisAuth = isLoanHisAuth
                    InfoUtil.isContactsAuth = isContactsAuth
                    InfoUtil.isCardAuth = isCardAuth
                    InfoUtil.isBankAuth = isBankAuth

                    if(! StringUtils.isEmpty(days)) {
                        binding.tvMainInicioDays.text = days
                    } else {
                        binding.tvMainInicioDays.text = Constants.EMPTY_STRING
                    }

                    //todo neet to study
                    binding.tvMainInicioDays.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30f)

                    if(! StringUtils.isEmpty(quota)) {
                        binding.tvMainInicioQuota.text = NumberUtils.goToZeroString(DoubleUtils.divToString(quota, "100", 2))
                    } else {
                        binding.tvMainInicioQuota.setText(Constants.EMPTY_STRING)
                    }
                }



                binding.tvInicioDaysTitle.text = resources.getString(R.string.plazo_de_pr??stamo)
                binding.tvInicioDaysUnit.text = resources.getString(R.string.d??as)
                binding.tvInicioDaysUnit.visibility = View.VISIBLE

                binding.tvInicioOlicita.isEnabled = true
                binding.tvInicioOlicita.setTextColor(resources.getColor(R.color.color_main_background))
                binding.tvInicioOlicita.text = resources.getString(R.string.isolicita)

            }
        })


        //??????loginAtv???????????????????????????????????????autherInfo??????
        mViewModels.authUserInfoWhenLoginAndNotifyLiveData.observe(this, Observer {
            it.whenError {
                isProgressAllFinishWhenNotify ++
                isProgressErrorWhenNotify = true


                if(isProgressAllFinish == 2 && ! isProgressError && mAuthUserInfoEntity != null && mCurrDetailEntity != null) {
                    mSimpleProgressDialogUtil?.closeHUD()


                    InfoUtil.authAllin = mAuthUserInfoEntity !!.authAllin

                    InfoUtil.isAddressAuth = mAuthUserInfoEntity !!.isAddressAuth
                    InfoUtil.isEmployAuth = mAuthUserInfoEntity !!.isEmployAuth
                    InfoUtil.isLoanHisAuth = mAuthUserInfoEntity !!.isLoanHisAuth
                    InfoUtil.isContactsAuth = mAuthUserInfoEntity !!.isContactsAuth
                    InfoUtil.isCardAuth = mAuthUserInfoEntity !!.isCardAuth
                    InfoUtil.isBankAuth = mAuthUserInfoEntity !!.isBankAuth
                    setViewWhenAllLoginState()


                } else if(isProgressAllFinish == 2) {
                    mHandler.removeCallbacksAndMessages(null)
                    mHandler.postDelayed({getDataWhenLoginAtvNotify()}, 5000) //5???

                }
            }
            it.whenSuccess {
                it?.run {mAuthUserInfoEntity = this}
                isProgressAllFinishWhenNotify ++
                if(isProgressAllFinishWhenNotify == 2 && ! isProgressError && mAuthUserInfoEntity != null && mCurrDetailEntity != null) {
                    mSimpleProgressDialogUtil?.closeHUD()


                    InfoUtil.authAllin = mAuthUserInfoEntity !!.authAllin

                    InfoUtil.isAddressAuth = mAuthUserInfoEntity !!.isAddressAuth
                    InfoUtil.isEmployAuth = mAuthUserInfoEntity !!.isEmployAuth
                    InfoUtil.isLoanHisAuth = mAuthUserInfoEntity !!.isLoanHisAuth
                    InfoUtil.isContactsAuth = mAuthUserInfoEntity !!.isContactsAuth
                    InfoUtil.isCardAuth = mAuthUserInfoEntity !!.isCardAuth
                    InfoUtil.isBankAuth = mAuthUserInfoEntity !!.isBankAuth
                    setViewWhenAllLoginState()


                } else if(isProgressAllFinishWhenNotify == 2) {
                    mHandler.removeCallbacksAndMessages(null)
                    mHandler.postDelayed({getDataWhenLoginAtvNotify()}, 5000) //5???

                }
            }
        })


        //??????loginAtv???????????????????????????????????????currDetail??????
        mViewModels.currDetailWhenLoginAndNotifyLiveData.observe(this, Observer {
            it.whenSuccess {
                it?.run {mCurrDetailEntity = this}
                isProgressAllFinishWhenNotify ++
                if(isProgressAllFinishWhenNotify == 2 && ! isProgressError && mAuthUserInfoEntity != null && mCurrDetailEntity != null) {
                    mSimpleProgressDialogUtil?.closeHUD()


                    InfoUtil.authAllin = mAuthUserInfoEntity !!.authAllin

                    InfoUtil.isAddressAuth = mAuthUserInfoEntity !!.isAddressAuth
                    InfoUtil.isEmployAuth = mAuthUserInfoEntity !!.isEmployAuth
                    InfoUtil.isLoanHisAuth = mAuthUserInfoEntity !!.isLoanHisAuth
                    InfoUtil.isContactsAuth = mAuthUserInfoEntity !!.isContactsAuth
                    InfoUtil.isCardAuth = mAuthUserInfoEntity !!.isCardAuth
                    InfoUtil.isBankAuth = mAuthUserInfoEntity !!.isBankAuth
                    setViewWhenAllLoginState()


                } else if(isProgressAllFinishWhenNotify == 2) {
                    mHandler.removeCallbacksAndMessages(null)
                    mHandler.postDelayed({getDataWhenLoginAtvNotify()}, 5000) //5???

                }
            }
            it.whenError {
                isProgressAllFinishWhenNotify ++
                isProgressErrorWhenNotify = true
                if(isProgressAllFinish == 2 && ! isProgressError && mAuthUserInfoEntity != null && mCurrDetailEntity != null) {
                    mSimpleProgressDialogUtil?.closeHUD()


                    InfoUtil.authAllin = mAuthUserInfoEntity !!.authAllin

                    InfoUtil.isAddressAuth = mAuthUserInfoEntity !!.isAddressAuth
                    InfoUtil.isEmployAuth = mAuthUserInfoEntity !!.isEmployAuth
                    InfoUtil.isLoanHisAuth = mAuthUserInfoEntity !!.isLoanHisAuth
                    InfoUtil.isContactsAuth = mAuthUserInfoEntity !!.isContactsAuth
                    InfoUtil.isCardAuth = mAuthUserInfoEntity !!.isCardAuth
                    InfoUtil.isBankAuth = mAuthUserInfoEntity !!.isBankAuth
                    setViewWhenAllLoginState()


                } else if(isProgressAllFinish == 2) {
                    mHandler.removeCallbacksAndMessages(null)
                    mHandler.postDelayed({getDataWhenLoginAtvNotify()}, 5000) //5???

                }
            }
            it.whenClear {
                Toast.makeText(activity, (it as DataResult.Clear).clearMessage, Toast.LENGTH_SHORT).show()
                InfoUtil.clear()
                getDataWhenLoginAtvNotify()
            }
        })

        //??????loginAtv???????????????????????????  ???????????????autherInfo??????
        mViewModels.authUserInfoWhenNotLoginAndNotifyLiveData.observe(this, Observer {
            it.whenSuccess {
                mSimpleProgressDialogUtil?.closeHUD()

                it?.run {mAuthUserInfoEntity = this}
                InfoUtil.authAllin = mAuthUserInfoEntity !!.authAllin
                InfoUtil.isAddressAuth = mAuthUserInfoEntity !!.isAddressAuth
                InfoUtil.isEmployAuth = mAuthUserInfoEntity !!.isEmployAuth
                InfoUtil.isLoanHisAuth = mAuthUserInfoEntity !!.isLoanHisAuth
                InfoUtil.isContactsAuth = mAuthUserInfoEntity !!.isContactsAuth
                InfoUtil.isCardAuth = mAuthUserInfoEntity !!.isCardAuth
                InfoUtil.isBankAuth = mAuthUserInfoEntity !!.isBankAuth


                if(! StringUtils.isEmpty(mAuthUserInfoEntity?.days)) {
                    binding.tvMainInicioDays.setText(mAuthUserInfoEntity?.days)
                } else {
                    binding.tvMainInicioDays.setText(Constants.EMPTY_STRING)
                }
                binding.tvMainInicioDays.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30f)
                if(! StringUtils.isEmpty(mAuthUserInfoEntity?.quota)) {
                    binding.tvMainInicioQuota.setText(goToZeroString(DoubleUtils.divToString(mAuthUserInfoEntity?.quota, "100", 2)))
                } else {
                    binding.tvMainInicioQuota.setText(Constants.EMPTY_STRING)
                }
                binding.tvInicioDaysTitle.setText(resources.getString(R.string.plazo_de_pr??stamo))
                binding.tvInicioDaysUnit.setText(resources.getString(R.string.d??as))
                binding.tvInicioDaysUnit.setVisibility(View.VISIBLE)
                binding.tvInicioOlicita.setEnabled(true)
                binding.tvInicioOlicita.setTextColor(resources.getColor(R.color.color_main_background))
                binding.tvInicioOlicita.setText(resources.getString(R.string.isolicita))

            }
            it.whenError {
                mHandler.removeCallbacksAndMessages(null)
                mHandler.postDelayed({
                    mViewModels.queryWhenNotLoginAndNotify()
                }, 5000) //5???

            }
        })
    } //???getDataHide???????????????????????????


    //???getData???????????????????????????
    private fun setDataWhenLogin() {
        if(isProgressAllFinish == 2 && ! isProgressError && mAuthUserInfoEntity != null && mCurrDetailEntity != null) {
            binding.inclMainInicioProgress.llProgress.visibility = View.GONE
            binding.inclMainInicioError.llError.visibility = View.GONE
            isNotGetData = false
            InfoUtil.authAllin = mAuthUserInfoEntity !!.authAllin

            InfoUtil.isAddressAuth = mAuthUserInfoEntity !!.isAddressAuth
            InfoUtil.isEmployAuth = mAuthUserInfoEntity !!.isEmployAuth
            InfoUtil.isLoanHisAuth = mAuthUserInfoEntity !!.isLoanHisAuth
            InfoUtil.isContactsAuth = mAuthUserInfoEntity !!.isContactsAuth
            InfoUtil.isCardAuth = mAuthUserInfoEntity !!.isCardAuth
            InfoUtil.isBankAuth = mAuthUserInfoEntity !!.isBankAuth

            setViewWhenAllLoginState()
        } else if(isProgressAllFinish == 2) {
            binding.inclMainInicioProgress.llProgress.visibility = View.GONE
            binding.inclMainInicioError.llError.visibility = View.VISIBLE
            binding.inclMainInicioError.viewErrorUpdate.visibility = View.VISIBLE
        }
    }

    //????????????UI??????
    private fun setViewWhenAllLoginState() {
        when(mCurrDetailEntity?.orderStatus) {
            - 1, 1/*??????*/, 4/*??????*/ -> {
                if(! StringUtils.isEmpty(mAuthUserInfoEntity?.days)) {
                    binding.tvMainInicioDays.setText(mAuthUserInfoEntity?.days)
                } else {
                    binding.tvMainInicioDays.setText(Constants.EMPTY_STRING)
                }
                binding.tvMainInicioDays.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30f)
                if(! StringUtils.isEmpty(mAuthUserInfoEntity?.quota)) {
                    binding.tvMainInicioQuota.setText(goToZeroString(DoubleUtils.divToString(mAuthUserInfoEntity?.quota, "100", 2)))
                } else {
                    binding.tvMainInicioQuota.setText(Constants.EMPTY_STRING)
                }
                binding.tvInicioDaysTitle.setText(resources.getString(R.string.plazo_de_pr??stamo))
                binding.tvInicioDaysUnit.setText(resources.getString(R.string.d??as))
                binding.tvInicioDaysUnit.setVisibility(View.VISIBLE)
                binding.tvInicioOlicita.setEnabled(true)
                binding.tvInicioOlicita.setTextColor(resources.getColor(R.color.color_main_background))
                binding.tvInicioOlicita.setText(resources.getString(R.string.isolicita))
            }
            0/*?????????*/, 2/*?????????*/ -> {
                binding.tvMainInicioQuota.setText(goToZeroString(DoubleUtils.divToString(mCurrDetailEntity?.loanAmount, "100", 2)))
                if(! StringUtils.isEmpty(mCurrDetailEntity?.repayDate)) {
                    binding.tvMainInicioDays.setText(mCurrDetailEntity?.repayDate)
                } else {
                    binding.tvMainInicioDays.setText(Constants.EMPTY_STRING)
                }
                binding.tvMainInicioDays.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
                binding.tvInicioDaysTitle.setText(resources.getString(R.string.fecha_de_reembolso))
                binding.tvInicioDaysUnit.setVisibility(View.GONE)
                binding.tvInicioOlicita.setEnabled(false)
                binding.tvInicioOlicita.setTextColor(resources.getColor(R.color.color_main_background))
                binding.tvInicioOlicita.setText(resources.getString(R.string.examen_de_los_prestamos))

                //??????handler??????????????????????????????
                mHandler.removeCallbacksAndMessages(null)
                mHandler.postDelayed(Runnable {queryWhenLoginAndNotify()}, 30000) //30???
            }
            3/*?????????*/, 5/*??????*/ -> {

                binding.tvMainInicioQuota.setText(goToZeroString(DoubleUtils.divToString(mCurrDetailEntity?.loanAmount, "100", 2)))
                if(! StringUtils.isEmpty(mCurrDetailEntity?.repayDate)) {
                    binding.tvMainInicioDays.setText(mCurrDetailEntity?.repayDate)
                } else {
                    binding.tvMainInicioDays.setText(Constants.EMPTY_STRING)
                }
                binding.tvMainInicioDays.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
                binding.tvInicioDaysTitle.setText(resources.getString(R.string.fecha_de_reembolso))
                binding.tvInicioDaysUnit.setVisibility(View.GONE)
                binding.tvInicioOlicita.setEnabled(true)
                binding.tvInicioOlicita.setTextColor(resources.getColor(R.color.color_main_background))
                binding.tvInicioOlicita.setText(resources.getString(R.string.reembolso_inmediato))
            }
        }
    }

    private fun getDataWhenLoginAtvNotify() {
        mSimpleProgressDialogUtil?.showHUD(getFrgmActivity(), false)
        if(! isNotGetData && InfoUtil.isLogin) { //??????????????????????????????
            queryWhenLoginAndNotify()
        } else if(! isNotGetData && ! InfoUtil.isLogin) { //?????????????????????????????????
            mViewModels.queryWhenNotLoginAndNotify()
        } else { //?????????????????????????????????
            mSimpleProgressDialogUtil?.closeHUD()
        }
    }

    private fun queryWhenLoginAndNotify() {
        isProgressAllFinishWhenNotify = 0
        isProgressErrorWhenNotify = false //????????????????????????????????????????????????
        mViewModels.queryAuthUserInfoWhenLoginAndNotify()
        val map = HashMap<String, Any>()
        map["type"] = 0
        mViewModels.queryCurrDetailWhenLoginAndNotify(map)
    }


    private fun getDataWhenFirstComeIn() {
        binding.inclMainInicioProgress.llProgress.visibility = View.VISIBLE
        binding.inclMainInicioError.llError.visibility = View.GONE
        if(InfoUtil.isLogin) {

            val map = HashMap<String, Any>()
            map["type"] = 0 //????????????????????????????????????????????????
            mViewModels.queryCurrDetailWhenLogin(map)
            mViewModels.queryAuthUserInfoWhenLogin()
        } else { //????????????????????????????????????????????????????????????
            mViewModels.queryAuthUserInfoWhenNotLogin()
        }

    }

    fun whenError() {
        binding.inclMainInicioProgress.llProgress.visibility = View.GONE
        binding.inclMainInicioError.llError.visibility = View.VISIBLE
        binding.inclMainInicioError.viewErrorUpdate.visibility = View.VISIBLE
    }
}