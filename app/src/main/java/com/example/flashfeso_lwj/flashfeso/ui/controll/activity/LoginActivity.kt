package com.example.flashfeso_lwj.flashfeso.ui.controll.activity

import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.flashfeso_lwj.App
import com.example.flashfeso_lwj.R
import com.example.flashfeso_lwj.common.entity.DataResult
import com.example.flashfeso_lwj.common.ui.controll.activity.BasePageStyleActivity
import com.example.flashfeso_lwj.common.utils.SimpleProgressDialogUtil
import com.example.flashfeso_lwj.databinding.ActivityLoginBinding
import com.example.flashfeso_lwj.flashfeso.utils.StringUtils
import com.example.flashfeso_lwj.flashfeso.viewmodel.LoginViewModel
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
                if(App.ISDEBUG)Log.d("TAG:yzm error mes", "ERROR: ${(it as DataResult.Error).errorMessage}")
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
        binding.inclLoginEnterTelephone.let { ll ->
            ll.etLoginPhoneNumber.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {


                }

                override fun afterTextChanged(p0: Editable?) {
                    mEnterPhoneNumber = p0.toString()
                    if (!StringUtils.isEmpty(mEnterPhoneNumber) && mEnterPhoneNumber.length == 10) {
                        if (StringUtils.isPhone(mEnterPhoneNumber)) {
                            binding.inclLoginEnterTelephone.llLoginEnterTelephone.visibility =
                                View.GONE
                            binding.inclLoginVerificationCode.llLoginVerificationCode.visibility =
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


        binding.inclLoginVerificationCode.let { ll ->
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
                    if(StringUtils.isEmpty(verificationCode) && verificationCode.length == 6){
                        if(mIsAgreePrivacy){
                            //todo ---
                            //userLogin()
                        }else{
                            Toast.makeText(this@LoginActivity, resources.getString(R.string.read_privacidad), Toast.LENGTH_LONG).show()
                        }
                    }
                }

            })

            ll.etLoginVerificationCode

            ll.tvLoginPrivacyDetail.setOnClickListener {
                if(isClickUseful()){
                    //跳转协议详情Atv界面
                    //val intent = Intent(this, LoginPrivacyDetailActivity::class.java)
                    /*startActivity(intent.apply{

                    })*/
                }
            }

            ll.ivLoginPrivacyImg.setOnClickListener {
                if(mIsAgreePrivacy){
                    mIsAgreePrivacy = false
                    (it as ImageView).setImageResource(R.drawable.icon_disagree)
                }else{
                    mIsAgreePrivacy = true
                    (it as ImageView).setImageResource(R.drawable.icon_disagree)
                    val verificationCode = binding.inclLoginVerificationCode.etLoginVerificationCode.text.toString().trim()
                    if(StringUtils.isEmpty(verificationCode) && verificationCode.length == 6){

                    //todo ---
                    //userLogin()
                    }
                }
            }



        }




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