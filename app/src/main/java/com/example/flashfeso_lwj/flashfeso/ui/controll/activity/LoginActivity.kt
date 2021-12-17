package com.example.flashfeso_lwj.flashfeso.ui.controll.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.example.flashfeso_lwj.App
import com.example.flashfeso_lwj.R
import com.example.flashfeso_lwj.databinding.ActivityLoginBinding
import com.example.flashfeso_lwj.flashfeso.base.BaseDbActivity
import com.example.flashfeso_lwj.flashfeso.utils.Constants
import com.example.flashfeso_lwj.flashfeso.utils.StringUtils

class LoginActivity : BaseDbActivity<ActivityLoginBinding>(){
    private var mIsYzmVisible = false

    private var mFirstClick = 0L
    private var mSecoundClick = 0L



    override fun observe() {

    }

    override fun ActivityLoginBinding.initView() {

        binding.inclLoginEnterTelephone.let{ ll ->
            ll.etLoginPhoneNumber.addTextChangedListener(object: TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {


                }

                override fun afterTextChanged(p0: Editable?) {
                    val enterPhoneNumber = p0.toString()
                    if(!StringUtils.isEmpty(enterPhoneNumber) && enterPhoneNumber.length == 10){
                        if(StringUtils.isPhone(enterPhoneNumber)){
                            binding.inclLoginEnterTelephone.llLoginEnterTelephone.visibility = View.GONE
                            binding.inclLoginVerificationCode.llLoginVerificationCode.visibility = View.GONE
                            mIsYzmVisible = true
                            sendMs()

                        }else{
                            Toast.makeText(App.context, getResources().getString(R.string.enter_phone_number), Toast.LENGTH_LONG).show()
                        }
                    }
                }

            })
        }


        binding.inclLoginVerificationCode.let{ ll ->
            ll.yzmSend.setOnClickListener {
                mFirstClick = mSecoundClick
                mSecoundClick = System.currentTimeMillis()

                if(mSecoundClick - mFirstClick > Constants.DOUBLE_CLICK_TIME){
                    sendMs()
                }
            }
        }


    }

    private fun sendMs() {

    }

}