package com.example.flashfeso_lwj.flashfeso.ui.controll.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.example.flashfeso_lwj.databinding.ActivityLoginBinding
import com.example.flashfeso_lwj.flashfeso.base.BaseDbActivity

class LoginActivity : BaseDbActivity<ActivityLoginBinding>(){
    override fun observe() {

    }

    override fun ActivityLoginBinding.initView() {
        binding.inclLoginEnterTelephone.etLoginPhoneNumber.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

    }

}