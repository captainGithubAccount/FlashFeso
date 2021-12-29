package com.example.flashfeso_lwj.flashfeso.ui.controll.fragment

import android.content.Intent
import android.os.Handler
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.flashfeso_lwj.R
import com.example.flashfeso_lwj.base.entity.DataResult
import com.example.flashfeso_lwj.base.ui.controll.fragment.BaseDbFragment
import com.example.flashfeso_lwj.base.utils.InfoUtil
import com.example.flashfeso_lwj.base.utils.SimpleProgressDialogUtil
import com.example.flashfeso_lwj.databinding.FragmentMainInicioBinding
import com.example.flashfeso_lwj.flashfeso.entity.AuthUserInfoEntity
import com.example.flashfeso_lwj.flashfeso.entity.CurrDetailEntity
import com.example.flashfeso_lwj.flashfeso.ui.controll.activity.InformacionBasicaActivity
import com.example.flashfeso_lwj.flashfeso.ui.controll.activity.LoginActivity
import com.example.flashfeso_lwj.flashfeso.ui.controll.activity.LoginPrivacyDetailActivity
import com.example.flashfeso_lwj.flashfeso.utils.Constants
import com.example.flashfeso_lwj.flashfeso.utils.UrlConstants
import com.example.flashfeso_lwj.flashfeso.viewmodel.MainInicioViewModel
import com.example.lwj_common.common.utils.DateUtil
import com.example.lwj_common.common.utils.DoubleUtils
import com.example.lwj_common.common.utils.NumberUtils
import com.example.lwj_common.common.utils.NumberUtils.goToZeroString
import com.example.lwj_common.common.utils.StringUtils
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception
import java.util.HashMap
import javax.inject.Inject

@AndroidEntryPoint
class MainInicioFragment : BaseDbFragment<FragmentMainInicioBinding>(){
    @Inject
    lateinit var mInfoUtil: InfoUtil

    private val mViewModels: MainInicioViewModel by viewModels()

    private val mHandler = Handler()

    @Inject
    @JvmField
    var mSimpleProgressDialogUtil: SimpleProgressDialogUtil? = null

     var mAuthUserInfoEntity: AuthUserInfoEntity? = null
     var mCurrDetailEntity: CurrDetailEntity? = null
//    lateinit var mCurrDetailEntity: CurrDetailEntity
    private var isProgressAll = 0
    private var isProgressError = false
    private var isCreate = true

    private var isProgressAllHide = 0
    private var isProgressErrorHide = false

    companion object{
        //注意该方法获取的不是单例, 该项目中只调用一次该方法, 所以效果也是只有一个对象创建
        fun getInstance(): MainInicioFragment = MainInicioFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        mSimpleProgressDialogUtil = null
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
        getData()
    }

    override fun afterBindView() {
        super.afterBindView()

        binding.inclMainInicioError.viewErrorUpdate.setOnClickListener(View.OnClickListener {
            if(isClickUseful()){
                getData()
            }
        })


        //借款
        //todo doing
        binding.tvInicioOlicita.setOnClickListener(View.OnClickListener {
            if(isClickUseful()){
                if (mInfoUtil.isLogin) {
                    if (mInfoUtil.authAllin) {
                        /*if (mCurrDetailEntity != null) {
                            when (mCurrDetailEntity?.orderStatus) {
                                -1 -> {
                                    val intent = Intent(activity,
                                        DetallesDeLosPrestamosActivity::class.java)
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
                        }*/
                    } else {
                        val bankAuth: Boolean = mInfoUtil.isBankAuth
                        val cardAuth: Boolean = mInfoUtil.isCardAuth
                        val contactsAuth: Boolean = mInfoUtil.isContactsAuth
                        val loanHisAuth: Boolean = mInfoUtil.isLoanHisAuth
                        val employAuth: Boolean = mInfoUtil.isEmployAuth
                        val addressAuth: Boolean = mInfoUtil.isAddressAuth
                        /*if (!bankAuth && cardAuth) {
                            startActivity(AgergarCuentaBancariaActivity::class.java)
                        } else if (!cardAuth && contactsAuth) {
                            startActivity(InformacionDeIdetidadActivity::class.java)
                        } else if (!contactsAuth && loanHisAuth) {
                            startActivity(InformacionDeContactosActivity::class.java)
                        } else if (!loanHisAuth && employAuth) {
                            startActivity(HistorialCrediticioActivity::class.java)
                        } else if (!employAuth && addressAuth) {
                            startActivity(InformacionLaboralActivity::class.java)
                        } else*/ if (!addressAuth) {
                            startActivity(InformacionBasicaActivity::class.java)
                        }
                    }
                } else {
                    startActivity(LoginActivity::class.java)
                }
            }
        })

        binding.inclMainInicioFirst.comoProtegemosLl.setOnClickListener {
            if(isClickUseful()){
                val intent = Intent(activity, LoginPrivacyDetailActivity::class.java)
                intent.putExtra(LoginPrivacyDetailActivity.HEADER_TITLE_TEXT,
                    requireActivity().resources.getString(R.string.seguridad_de_los_datos))
                intent.putExtra(LoginPrivacyDetailActivity.WEBSITE_URL, UrlConstants.SEGURIDAD_DE_LOS_DATOS_URL)
                startActivity(intent)
            }
        }

        //todo doing
        /*binding.inclMainIncioSecond.comoProtegemosLl.setOnClickListener {
            if(isClickUseful()){
                startActivity(PreguntasFrecuentesActivity::class.java)
            }
        }*/




    }



    override fun observe() {
        //CurrDetail接口
        mViewModels.inicioCurrDetailLiveData.observe(this, Observer {
            it.whenSuccess {
                it?.run{mCurrDetailEntity = this}
                isProgressAll++
                setData()

            }
            it.whenError {
                whenError()
                isProgressAll++
                isProgressError = true
            }
            it.whenClear {
                Toast.makeText(activity, (it as DataResult.Clear).clearMessage, Toast.LENGTH_SHORT).show()
                mInfoUtil.clear()
                getData()
            }
        })

        //AuthUserInfo接口
        mViewModels.inicioAuthUserInfoLiveData.observe(this, Observer {
            it.whenError {
                whenError()
                isProgressAll++
                isProgressError = true
            }
            it.whenSuccess {
                it?.run{mAuthUserInfoEntity = this}
                isProgressAll++
                setData()
            }
        })

        //(AuthUserInfoSingle)    AuthUserInfo接口
        mViewModels.inicioAuthUserInfoSingleLiveData.observe(this, Observer {
            it.whenError {
                whenError()
            }
            it.whenSuccess {
                isCreate = false
                binding.inclMainInicioProgress.llProgress.visibility = View.GONE
                binding.inclMainInicioError.llError.visibility = View.GONE

                it?.run{mAuthUserInfoEntity = this}

                it?.run{
                    mInfoUtil.authAllin = authAllin
                    mInfoUtil.isAddressAuth = isAddressAuth
                    mInfoUtil.isEmployAuth = isEmployAuth
                    mInfoUtil.isLoanHisAuth = isLoanHisAuth
                    mInfoUtil.isContactsAuth = isContactsAuth
                    mInfoUtil.isCardAuth = isCardAuth
                    mInfoUtil.isBankAuth = isBankAuth

                    if (!StringUtils.isEmpty(days)) {
                        binding.tvMainInicioDays.text = days
                    } else {
                        binding.tvMainInicioDays.text = Constants.EMPTY_STRING
                    }

                    //todo neet to study
                    binding.tvMainInicioDays.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30f)

                    if (!StringUtils.isEmpty(quota)) {
                        binding.tvMainInicioQuota.text = NumberUtils.goToZeroString(DoubleUtils.divTOString(quota,
                            "100",
                            2))
                    } else {
                        binding.tvMainInicioQuota.setText(Constants.EMPTY_STRING)
                    }
                }



                binding.tvInicioDaysTitle.text = resources.getString(R.string.plazo_de_préstamo)
                binding.tvInicioDaysUnit.text = resources.getString(R.string.días)
                binding.tvInicioDaysUnit.visibility = View.VISIBLE

                binding.tvInicioOlicita.isEnabled = true
                binding.tvInicioOlicita.setTextColor(resources.getColor(R.color.color_main_background))
                binding.tvInicioOlicita.text = resources.getString(R.string.isolicita)

            }
        })

        //AuthUserInfoHide
        mViewModels.inicioAuthUserInfoHideLiveData.observe(this, Observer {
            it.whenError {
                isProgressAllHide++
                isProgressErrorHide = true
                setDataHide()
            }
            it.whenSuccess {
                it?.run{mAuthUserInfoEntity = this}
                isProgressAllHide++
                setDataHide()
            }
        })

        //CurrDetailHide
        mViewModels.inicioCurrDetailHideLiveData.observe(this, Observer {
            it.whenSuccess {
                it?.run{mCurrDetailEntity = this}
                isProgressAllHide++
                setDataHide()
            }
            it.whenError {
                isProgressAllHide++
                isProgressErrorHide = true
                setDataHide()
            }
            it.whenClear {
                Toast.makeText(activity, (it as DataResult.Clear).clearMessage, Toast.LENGTH_SHORT).show()
                mInfoUtil.clear()
                getHide()
            }
        })

        //AuthInfoSingleHide
        mViewModels.inicioAuthUserInfoSingleHideLiveData.observe(this, Observer {
            it.whenSuccess {
                    mSimpleProgressDialogUtil?.closeHUD()
                    it?.run{mAuthUserInfoEntity = this}
                    mInfoUtil.authAllin = mAuthUserInfoEntity!!.authAllin
                    mInfoUtil.isAddressAuth = mAuthUserInfoEntity!!.isAddressAuth
                    mInfoUtil.isEmployAuth = mAuthUserInfoEntity!!.isEmployAuth
                    mInfoUtil.isLoanHisAuth = mAuthUserInfoEntity!!.isLoanHisAuth
                    mInfoUtil.isContactsAuth = mAuthUserInfoEntity!!.isContactsAuth
                    mInfoUtil.isCardAuth = mAuthUserInfoEntity!!.isCardAuth
                    mInfoUtil.isBankAuth = mAuthUserInfoEntity!!.isBankAuth


                    if (!StringUtils.isEmpty(mAuthUserInfoEntity?.days)) {
                        binding.tvMainInicioDays.setText(mAuthUserInfoEntity?.days)
                    } else {
                        binding.tvMainInicioDays.setText(Constants.EMPTY_STRING)
                    }
                    binding.tvMainInicioDays.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30f)
                    if (!StringUtils.isEmpty(mAuthUserInfoEntity?.quota)) {
                        binding.tvMainInicioQuota.setText(goToZeroString(DoubleUtils.divTOString(mAuthUserInfoEntity?.quota, "100", 2)))
                    } else {
                        binding.tvMainInicioQuota.setText(Constants.EMPTY_STRING)
                    }
                    binding.tvInicioDaysTitle.setText(resources.getString(R.string.plazo_de_préstamo))
                    binding.tvInicioDaysUnit.setText(resources.getString(R.string.días))
                    binding.tvInicioDaysUnit.setVisibility(View.VISIBLE)
                    binding.tvInicioOlicita.setEnabled(true)
                    binding.tvInicioOlicita.setTextColor(resources.getColor(R.color.color_main_background))
                    binding.tvInicioOlicita.setText(resources.getString(R.string.isolicita))

            }
            it.whenError {
                mHandler.removeCallbacksAndMessages(null)
                mHandler.postDelayed({
                    mViewModels.queryInicioAuthUserInfoSingleHide()
                                     }, 5000) //5秒

            }
        })

    }
    //将getDataHide获取的数据全局存储
    private fun setDataHide(){
        if (isProgressAll == 2 && !isProgressError && mAuthUserInfoEntity != null && mCurrDetailEntity != null) {
            mSimpleProgressDialogUtil?.closeHUD()


            mInfoUtil.authAllin = mAuthUserInfoEntity!!.authAllin

            mInfoUtil.isAddressAuth = mAuthUserInfoEntity!!.isAddressAuth
            mInfoUtil.isEmployAuth = mAuthUserInfoEntity!!.isEmployAuth
            mInfoUtil.isLoanHisAuth = mAuthUserInfoEntity!!.isLoanHisAuth
            mInfoUtil.isContactsAuth = mAuthUserInfoEntity!!.isContactsAuth
            mInfoUtil.isCardAuth = mAuthUserInfoEntity!!.isCardAuth
            mInfoUtil.isBankAuth = mAuthUserInfoEntity!!.isBankAuth
            setView()


        } else if (isProgressAll == 2) {
            mHandler.removeCallbacksAndMessages(null)
            mHandler.postDelayed({ getHide() }, 5000) //5秒

        }
    }

    //将getData获取的数据全局存储
    private fun setData() {
        if (isProgressAll == 2 && !isProgressError && mAuthUserInfoEntity != null && mCurrDetailEntity != null) {
            binding.inclMainInicioProgress.llProgress.visibility = View.GONE
            binding.inclMainInicioError.llError.visibility = View.GONE
            isCreate = false
            mInfoUtil.authAllin = mAuthUserInfoEntity!!.authAllin

            mInfoUtil.isAddressAuth = mAuthUserInfoEntity!!.isAddressAuth
            mInfoUtil.isEmployAuth = mAuthUserInfoEntity!!.isEmployAuth
            mInfoUtil.isLoanHisAuth = mAuthUserInfoEntity!!.isLoanHisAuth
            mInfoUtil.isContactsAuth = mAuthUserInfoEntity!!.isContactsAuth
            mInfoUtil.isCardAuth = mAuthUserInfoEntity!!.isCardAuth
            mInfoUtil.isBankAuth = mAuthUserInfoEntity!!.isBankAuth

            setView()
        } else if (isProgressAll == 2) {
            binding.inclMainInicioProgress.llProgress.visibility = View.GONE
            binding.inclMainInicioError.llError.visibility = View.VISIBLE
            binding.inclMainInicioError.viewErrorUpdate.visibility = View.VISIBLE
        }
    }

    //登录下的UI展示
    private fun setView() {
        when (mCurrDetailEntity?.orderStatus) {
            -1, 1/*拒单*/, 4/*结单*/ -> {
                if (!StringUtils.isEmpty(mAuthUserInfoEntity?.days)) {
                    binding.tvMainInicioDays.setText(mAuthUserInfoEntity?.days)
                } else {
                    binding.tvMainInicioDays.setText(Constants.EMPTY_STRING)
                }
                binding.tvMainInicioDays.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30f)
                if (!StringUtils.isEmpty(mAuthUserInfoEntity?.quota)) {
                    binding.tvMainInicioQuota.setText(goToZeroString(DoubleUtils.divTOString(mAuthUserInfoEntity?.quota,
                        "100",
                        2)))
                } else {
                    binding.tvMainInicioQuota.setText(Constants.EMPTY_STRING)
                }
                binding.tvInicioDaysTitle.setText(resources.getString(R.string.plazo_de_préstamo))
                binding.tvInicioDaysUnit.setText(resources.getString(R.string.días))
                binding.tvInicioDaysUnit.setVisibility(View.VISIBLE)
                binding.tvInicioOlicita.setEnabled(true)
                binding.tvInicioOlicita.setTextColor(resources.getColor(R.color.color_main_background))
                binding.tvInicioOlicita.setText(resources.getString(R.string.isolicita))
            }
            0/*待审核*/, 2/*待放款*/ -> {
                binding.tvMainInicioQuota.setText(goToZeroString(DoubleUtils.divTOString(mCurrDetailEntity?.loanAmount,
                    "100",
                    2)))
                if (!StringUtils.isEmpty(mCurrDetailEntity?.repayDate)) {
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

                //删除handler所有的消息和回调函数
                mHandler.removeCallbacksAndMessages(null)
                mHandler.postDelayed(Runnable { getDataHide() }, 30000) //30秒
            }
            3/*待还款*/, 5/*逾期*/ -> {

                binding.tvMainInicioQuota.setText(goToZeroString(DoubleUtils.divTOString(mCurrDetailEntity?.loanAmount,
                    "100",
                    2)))
                if (!StringUtils.isEmpty(mCurrDetailEntity?.repayDate)) {
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

    private fun getHide(){
        mSimpleProgressDialogUtil?.showHUD(getFrgmActivity(), false)
        if (!isCreate && mInfoUtil.isLogin) {
            getDataHide()
        } else if (!isCreate && !mInfoUtil.isLogin) {
            mViewModels.queryInicioAuthUserInfoSingleHide()
        } else {
            mSimpleProgressDialogUtil?.closeHUD()
        }
    }

    private fun getDataHide() {
        isProgressAllHide = 0
        isProgressErrorHide = false

        //登录的时候获取订单状态和可贷金额
        mViewModels.queryInicioAuthUserInfoHide()

        val map = HashMap<String, Any>()
        map["type"] = 0
        mViewModels.queryInicioCurrDetailHide(map)
    }



    private fun getData() {
        binding.inclMainInicioProgress.llProgress.visibility = View.VISIBLE
        binding.inclMainInicioError.llError.visibility = View.GONE
        if(mInfoUtil.isLogin){

            val map = HashMap<String, Any>()
            map["type"] = 0
            //登录的时候获取订单状态和可贷金额
            mViewModels.queryInicioCurrDetail(map)
            mViewModels.queryInicioAuthUserInfo()
        }else{
            //未登录只获取，认证状态和可贷款金额及天数
            mViewModels.queryInicioAuthUserInfoSingle()
        }

    }

    fun whenError(){
        binding.inclMainInicioProgress.llProgress.visibility = View.GONE
        binding.inclMainInicioError.llError.visibility = View.VISIBLE
        binding.inclMainInicioError.viewErrorUpdate.visibility = View.VISIBLE

    }





}