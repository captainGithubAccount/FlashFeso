package com.example.flashfeso_lwj.flashfeso.ui.controll.activity

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.flashfeso_lwj.App
import com.example.flashfeso_lwj.R
import com.example.flashfeso_lwj.common.entity.DataResult
import com.example.flashfeso_lwj.databinding.ActivityLoginBinding
import com.example.flashfeso_lwj.common.ui.controll.activity.BaseDbActivity
import com.example.flashfeso_lwj.common.utils.SimpleProgressDialogUtil
import com.example.flashfeso_lwj.flashfeso.utils.StringUtils
import com.example.flashfeso_lwj.flashfeso.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : BaseDbActivity<ActivityLoginBinding>(){
    private var mTime = 60
    val mLoginViewModel: LoginViewModel by viewModels()


     @Inject @JvmField var mSimpleProgressDialogUtil: SimpleProgressDialogUtil? = null

    private var mIsYzmVisible = false
    private lateinit var mEnterPhoneNumber: String

    override fun observe() {
        /*mLoginViewModel.loginYzmLiveData.observe(this, Observer {
            it.whenSuccess {

            }
            it.whenError {

            }
        })*/
    }

    override suspend fun observeWhenCreatedWithLifecycle() {
        super.observeWhenCreatedWithLifecycle()

        mLoginViewModel.loginYzmLiveData.observe(this, Observer {
            val scope =  CoroutineScope(Dispatchers.Main)
            scope.launch {
                it.whenSuccessAndSuspend {
                    mSimpleProgressDialogUtil?.closeHUD()
                    observeWithCycle()
                    binding.inclLoginVerificationCode.etLoginVerificationCode.requestFocus()
                    //todo test
                    Toast.makeText(App.context, "SUCCESS: ${it}", Toast.LENGTH_LONG).show()
                }
                it.whenError {
                    binding.inclLoginVerificationCode.llLoginUpdateTime.visibility = View.GONE
                    binding.inclLoginVerificationCode.tvLoginYzmSend.visibility = View.VISIBLE
                    Log.d("---", "ERROR: ${(it as DataResult.Error).errorMessage}")
                    Toast.makeText(App.context, "ERROR: ${it.errorMessage}", Toast.LENGTH_LONG).show()
                    mSimpleProgressDialogUtil?.closeHUD()
                }
            }




        })
    }

    suspend fun observeWithCycle() =
        coroutineScope {
            /*val data = async(Dispatchers.IO) { // <- extension on current scope
                ... load some UI data for the Main thread ...
            }

            withContext(Dispatchers.Main) {
                doSomeWork()
                val result = data.await()
                display(result)
            }*/

            repeat(60){
                async(Dispatchers.Default) {
                    if(mTime < 1){
                        withContext(Dispatchers.Main) {
                            binding.inclLoginVerificationCode.llLoginUpdateTime.visibility =
                                View.GONE
                            binding.inclLoginVerificationCode.tvLoginYzmSend.visibility =
                                View.VISIBLE
                            mTime = 60
                            binding.inclLoginVerificationCode.tvUpdateTime.setText("${mTime}s")
                        }
                    }else{

                        withContext(Dispatchers.Main){

                            binding.inclLoginVerificationCode.llLoginUpdateTime.visibility = View.VISIBLE
                            binding.inclLoginVerificationCode.tvLoginYzmSend.visibility = View.GONE
                            binding.inclLoginVerificationCode.tvUpdateTime.setText("${mTime}s")
                        }
                        mTime--
                        delay(1000)
                    }
                }

            }

    }

    override fun onDestroy() {
        super.onDestroy()
        //原项目工具类有内存泄漏风险, 但是目前这种方式通过installIn(application)中实现单例, 仍然会有内存泄漏, 需要置null解决内存泄漏
        mSimpleProgressDialogUtil = null
    }

    override fun ActivityLoginBinding.initView() {

        binding.inclLoginEnterTelephone.let{ ll ->
            ll.etLoginPhoneNumber.addTextChangedListener(object: TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {


                }

                override fun afterTextChanged(p0: Editable?) {
                    mEnterPhoneNumber = p0.toString()
                    if(!StringUtils.isEmpty(mEnterPhoneNumber) && mEnterPhoneNumber.length == 10){
                        if(StringUtils.isPhone(mEnterPhoneNumber)){
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
            ll.tvLoginYzmSend.setOnClickListener {
                /*mFirstClick = mSecoundClick
                mSecoundClick = System.currentTimeMillis()

                if(mSecoundClick - mFirstClick > Constants.DOUBLE_CLICK_TIME){
                    sendMs()
                }*/
                if(isClickUseful()){
                    sendMs()
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

        mLoginViewModel.query(map)

    }

}