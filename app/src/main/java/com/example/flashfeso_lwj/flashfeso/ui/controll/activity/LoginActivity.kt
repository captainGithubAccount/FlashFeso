package com.example.flashfeso_lwj.flashfeso.ui.controll.activity

import android.content.Intent
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.adjust.sdk.Adjust
import com.example.flashfeso_lwj.App
import com.example.flashfeso_lwj.R
import com.example.flashfeso_lwj.base.entity.DataResult
import com.example.flashfeso_lwj.base.ui.controll.activity.BasePageStyleActivity
import com.example.flashfeso_lwj.flashfeso.utils.InfoUtil
import com.example.flashfeso_lwj.base.utils.SimpleProgressDialogUtil
import com.example.flashfeso_lwj.databinding.ActivityLoginBinding
import com.example.flashfeso_lwj.flashfeso.utils.Constants
import com.example.flashfeso_lwj.flashfeso.utils.UrlConstants
import com.example.flashfeso_lwj.flashfeso.viewmodel.LoginViewModel
import com.example.lwj_common.common.utils.StringUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : BasePageStyleActivity<ActivityLoginBinding>() {
    val mLoginViewModel: LoginViewModel by viewModels()

    @Inject
    @JvmField
    var mSimpleProgressDialogUtil: SimpleProgressDialogUtil? = null
    private var mIsAgreePrivacy = true
    private var mIsYzmLayoutVisible = false
    private lateinit var mEnterPhoneNumber: String



    override fun observe() {
        mLoginViewModel.loginUserLiveData.observe(this, Observer {
            it.whenSuccessResponse {  dr ->
                val dataResult = dr as DataResult.Success
                mSimpleProgressDialogUtil?.closeHUD()
                if (!StringUtils.isEmpty(dataResult.data?.phone)) {
                    InfoUtil.setAccount(dataResult.data?.phone)
                } else {
                    InfoUtil.setAccount("")
                }

                if (!StringUtils.isEmpty(dataResult.data?.token)) {
                    InfoUtil.setToken(dataResult.data?.token)
                } else {
                    InfoUtil.setToken("")
                }

                if (!StringUtils.isEmpty(dataResult.data?.userId.toString())) {
                    InfoUtil.setUserId(dataResult.data?.userId.toString())
                } else {
                    InfoUtil.setUserId("")
                }
                //登录成功通知其他需要登录的地方更新数据
                mLoginViewModel.queryLiveData()

                finish()
                Toast.makeText(this@LoginActivity, dataResult.successMessagle, Toast.LENGTH_SHORT).show()
            }

            it.whenError {
                binding.inclLoginVerificationCode.llLoginUpdateTime.visibility = View.GONE
                binding.inclLoginVerificationCode.tvLoginYzmSend.visibility = View.VISIBLE
                mSimpleProgressDialogUtil?.closeHUD()
                //Toast.makeText(App.context, "Error: ${it}", Toast.LENGTH_LONG).show()
            }
        })


        mLoginViewModel.loginYzmLiveData.observe(this@LoginActivity, Observer {
            it.whenSuccess {
                mSimpleProgressDialogUtil?.closeHUD()
                Toast.makeText(App.context, "SUCCESS: ${it}", Toast.LENGTH_LONG).show()

                //倒计时实现
                object : CountDownTimer(5 * 1000L, 1000) {
                    override fun onFinish() {
                        binding.inclLoginVerificationCode.llLoginUpdateTime.visibility =
                            View.GONE
                        binding.inclLoginVerificationCode.tvLoginYzmSend.visibility =
                            View.VISIBLE
                    }

                    override fun onTick(p0: Long) {
                        binding.inclLoginVerificationCode.llLoginUpdateTime.visibility =
                            View.VISIBLE
                        binding.inclLoginVerificationCode.tvLoginYzmSend.visibility = View.GONE
                        binding.inclLoginVerificationCode.tvUpdateTime.setText(String.format(resources.getString(R.string.login_verification_code_time), p0.div(1000)))
                    }
                }.start()
                binding.inclLoginVerificationCode.etLoginVerificationCode.requestFocus()
            }
            it.whenError {
                binding.inclLoginVerificationCode.llLoginUpdateTime.visibility = View.GONE
                binding.inclLoginVerificationCode.tvLoginYzmSend.visibility = View.VISIBLE
                if(Constants.ISLOG)Log.d("TAG:yzm error mes", "ERROR: ${(it as DataResult.Error).errorMessage}")
                Toast.makeText(App.context, "ERROR: ${(it as DataResult.Error).errorMessage}", Toast.LENGTH_LONG).show()
                mSimpleProgressDialogUtil?.closeHUD()
            }
        })
    }


    override fun onDestroy() {
        super.onDestroy()
        //原项目工具类有内存泄漏风险, 但是目前这种方式通过installIn(application)中实现单例, 仍然会有内存泄漏, 需要置null解决内存泄漏
        mSimpleProgressDialogUtil = null

    }

    override fun onBackPressed() {
        if(mIsYzmLayoutVisible){
            mIsYzmLayoutVisible = false
            binding.inclLoginEnterTelephone.llLoginEnterTelephone.visibility = View.VISIBLE
            binding.inclLoginEnterTelephone.llLoginEnterTelephone.visibility = View.GONE
        }else{
            super.onBackPressed()
        }
    }

    override fun ActivityLoginBinding.initView() {
        inclLoginEnterTelephone.let { ll ->
            ll.etLoginPhoneNumber.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {


                }

                override fun afterTextChanged(p0: Editable?) {
                    mEnterPhoneNumber = p0.toString()
                    if (!StringUtils.isEmpty(mEnterPhoneNumber) && mEnterPhoneNumber.length == 10) {
                        if (StringUtils.isPhone(mEnterPhoneNumber)) {
                            inclLoginEnterTelephone.llLoginEnterTelephone.visibility =
                                View.GONE
                            inclLoginVerificationCode.llLoginVerificationCode.visibility =
                                View.VISIBLE
                            mIsYzmLayoutVisible = true
                            sendMs()

                        } else {
                            Toast.makeText(App.context,
                                getResources().getString(R.string.enter_phone_number),
                                Toast.LENGTH_LONG).show()
                        }
                    }
                }

            })
        }



        inclLoginVerificationCode.let { ll ->
            ll.tvLoginYzmSend.setOnClickListener {
                /*mFirstClick = mSecoundClick
                mSecoundClick = System.currentTimeMillis()

                if(mSecoundClick - mFirstClick > Constants.DOUBLE_CLICK_TIME){
                    sendMs()
                }*/
                if (isClickUseful()) {
                    sendMs()
                }
            }

            ll.etLoginVerificationCode.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    val verificationCode = p0.toString().trim()
                    if(!StringUtils.isEmpty(verificationCode) && verificationCode.length == 6){
                        if(mIsAgreePrivacy){
                            //todo ---
                            userLogin(verificationCode)
                        }else{
                            Toast.makeText(this@LoginActivity, resources.getString(R.string.read_privacidad), Toast.LENGTH_LONG).show()
                        }
                    }
                }

            })

            //ll.etLoginVerificationCode

            ll.tvLoginPrivacyDetail.setOnClickListener {
                if(isClickUseful()){
                    //跳转协议详情Atv界面
                    val intent = Intent(this@LoginActivity, LoginPrivacyDetailActivity::class.java)
                    intent.putExtra(LoginPrivacyDetailActivity.HEADER_TITLE_TEXT,
                        resources.getString(R.string.seguridad_de_los_datos))
                    intent.putExtra(LoginPrivacyDetailActivity.WEBSITE_URL,
                        UrlConstants.SEGURIDAD_DE_LOS_DATOS_URL)
                    startActivity(intent)
                }
            }

            ll.ivLoginPrivacyImg.setOnClickListener {
                if(mIsAgreePrivacy){
                    mIsAgreePrivacy = false
                    (it as ImageView).setImageResource(R.drawable.icon_disagree)
                }else{
                    mIsAgreePrivacy = true
                    (it as ImageView).setImageResource(R.drawable.icon_agree)
                    val verificationCode = inclLoginVerificationCode.etLoginVerificationCode.text.toString().trim()
                    if(!StringUtils.isEmpty(verificationCode) && verificationCode.length == 6){

                    userLogin(ll.etLoginVerificationCode.text.toString().trim())
                    }
                }
            }
        }
    }


    private fun userLogin(verificationCode: String){
        mSimpleProgressDialogUtil?.showHUD(this, false)
        var trackerName = "Organic"
        var adid = ""

        //获取请求参数, 从Adjust获取
        val attribution = Adjust.getAttribution()
        if (attribution != null && !StringUtils.isEmpty(attribution.trackerName)) {
            trackerName = attribution.trackerName
        }
        if (attribution != null && !StringUtils.isEmpty(attribution.adid)) {
            adid = attribution.adid
        }
        val map = HashMap<String, Any?>()
        map["me_phoneNumber"] = mEnterPhoneNumber
        map["me_smsCode"] = verificationCode
        map["me_regisChannel"] = trackerName
        map["adid"] = adid
        map["gps_adid"] = InfoUtil.gpsAdid
        if(Constants.ISLOG)Log.d("--登录接口请求参数", map.toString())
        mLoginViewModel.queryLoginUserInfo(map)
    }



    private fun sendMs() {
        binding.inclLoginVerificationCode.llLoginUpdateTime.visibility = View.VISIBLE
        binding.inclLoginVerificationCode.tvLoginYzmSend.isClickable = false
        binding.inclLoginVerificationCode.tvLoginYzmSend.visibility = View.GONE

        mSimpleProgressDialogUtil?.showHUD(this, false)

        val map = HashMap<String, Any>()
        map.put("me_phoneNumber", mEnterPhoneNumber)
        map.put("me_productName", "FlashPeso")

        mLoginViewModel.queryLoginYzm(map)

    }



}