package com.example.flashfeso_lwj.flashfeso.ui.controll.activity

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.flashfeso_lwj.R
import com.example.flashfeso_lwj.base.entity.DataResult
import com.example.flashfeso_lwj.base.ui.controll.activity.BasePageStyleActivity
import com.example.flashfeso_lwj.base.utils.SimpleProgressDialogUtil
import com.example.flashfeso_lwj.databinding.ActivityInformacionBasicaBinding
import com.example.flashfeso_lwj.flashfeso.utils.InfoUtil
import com.example.flashfeso_lwj.flashfeso.utils.addToast
import com.example.flashfeso_lwj.flashfeso.utils.back
import com.example.flashfeso_lwj.flashfeso.utils.textIsEmpty
import com.example.flashfeso_lwj.flashfeso.viewmodel.InfomationAddressViewModel
import com.example.flashfeso_lwj.flashfeso.viewmodel.LoginViewModel
import com.example.lwj_base.common.base.BaseConstants
import dagger.hilt.android.AndroidEntryPoint
import java.util.HashMap
import javax.inject.Inject
/*
*
* 认证一
* */

@AndroidEntryPoint
class InfomationBasicaActivity : BasePageStyleActivity<ActivityInformacionBasicaBinding>() {
    val mLoginViewModel: LoginViewModel by viewModels()

    @Inject
    @JvmField
    var mSimpleProgressDialogUtil: SimpleProgressDialogUtil? = null

    val mViewModel: InfomationAddressViewModel by viewModels()

    override fun observe() {
        mViewModel.addressLiveData.observe(this, Observer {
            it.whenSuccessResponse {

                mSimpleProgressDialogUtil?.closeHUD()
                if ((it as DataResult.Success).successMessagle.equals(getResources().getString(R.string.success))) {
                    onBackPressed()//将界面销毁, 相当于点击了返回键, 或相当于finish()
                    mLoginViewModel.queryNotify2LiveData()


                    startActivity( InfomationLaboralActivity::class.java)
                    Toast.makeText(this, it.successMessagle, Toast.LENGTH_SHORT).show()
                }

            }
            it.whenError {
                mSimpleProgressDialogUtil?.closeHUD()
            }
            it.whenClear {
                InfoUtil.clear()
                mLoginViewModel.queryNotifyLiveData()//相当于mEventBus.post(new UpdateLoginBean());
                Toast.makeText(this, (it as DataResult.Clear).clearMessage, Toast.LENGTH_SHORT).show()
                onBackPressed()
            }
        })
    }

    override fun ActivityInformacionBasicaBinding.initView() {
        binding.header.tvCommonBarTitle.text = resources.getString(R.string.Informacion_basica)
    }


    override fun afterBindView() {
        super.afterBindView()

        binding.confirm.setOnClickListener {
            if (binding.youBian.textIsEmpty()) {
                addToast(this, resources.getString(R.string.introduzca_el_codigo_postal))
                if (BaseConstants.ISLOG) Log.d("---校验", "error")
                return@setOnClickListener
            }

            if (binding.bang.textIsEmpty()) {
                addToast(this, resources.getString(R.string.introduzca_el_estado))
                if (BaseConstants.ISLOG) Log.d("---校验", "error")
                return@setOnClickListener
            }


            if (binding.city.textIsEmpty()) {
                addToast(this, resources.getString(R.string.introduzca_la_ciudad))
                if (BaseConstants.ISLOG) Log.d("---校验", "error")
                return@setOnClickListener
            }

            if (binding.shengHuoQu.textIsEmpty()) {
                addToast(this, resources.getString(R.string.introduzca_la_colonia))
                if (BaseConstants.ISLOG) Log.d("---校验", "error")
                return@setOnClickListener
            }

            if (binding.waibuBianhao.textIsEmpty()) {
                addToast(this, resources.getString(R.string.introduzca_un_numero_externo))
                if (BaseConstants.ISLOG) Log.d("---校验", "error")
                return@setOnClickListener
            }


            if (binding.neibuBianhao.textIsEmpty()) {
                addToast(this, resources.getString(R.string.introduzca_un_numero_interno))
                if (BaseConstants.ISLOG) Log.d("---校验", "error")
                return@setOnClickListener
            }

            if (isClickUseful()) {
                mSimpleProgressDialogUtil?.showHUD(this, false)

                val map = HashMap<String, Any>()
                map["postCode"] = binding.youBian.text.toString().trim()
                map["state"] = binding.bang.text.toString().trim()
                map["city"] = binding.city.text.toString().trim()
                map["area"] = binding.shengHuoQu.text.toString().trim()
                map["street"] = binding.jieDao.toString().trim()
                map["outHouseNumber"] = binding.waibuBianhao.toString().trim()
                map["inHouseNumber"] = binding.neibuBianhao.toString().trim()
                mViewModel.query(map)
            }
        }


        binding.header.ivCommonBarBack.back(this)
    }

}