package com.example.flashfeso_lwj.flashfeso.ui.controll.activity

import android.content.ClipboardManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.flashfeso_lwj.R
import com.example.flashfeso_lwj.base.ui.controll.activity.BaseDbActivity
import com.example.flashfeso_lwj.databinding.ActivityStpactivityBinding
import com.example.flashfeso_lwj.flashfeso.entity.RepaymentEntity
import com.example.flashfeso_lwj.flashfeso.utils.back
import com.example.lwj_common.common.ui.controll.tools.ktx.isUseful
import com.example.lwj_common.common.ui.controll.tools.utils.SystemServiceUtil

class STPActivity: BaseDbActivity<ActivityStpactivityBinding>(){
    companion object{

        val TAG_CLABE = "tag_clabe"
    }

    lateinit var repaymentBean: RepaymentEntity

    override fun observe() {

    }

    override fun beforeCreateView() {
        super.beforeCreateView()
        repaymentBean = intent.getParcelableExtra(STPActivity.TAG_CLABE)!!
    }

    override fun ActivityStpactivityBinding.initView() {
        binding.header.tvCommonBarTitle.text = resources.getString(R.string.quiero_pagar)

        if(repaymentBean != null) {
            if(repaymentBean.clabe.isUseful()) {
                numberTv.setText(repaymentBean.clabe)
            }
            if(repaymentBean.bank.isUseful()) {
                bancoTv.setText(repaymentBean.bank)
            }
            if(repaymentBean.empresa.isUseful()) {
                beneficlarioTv.setText(repaymentBean.empresa)
                val string1 = resources.getString(R.string.solo_deposita_a_cuenta_a_nombre)
                val string2 = resources.getString(R.string.no_aceptes_cuentas_a_nombre_de_terceros)
                stpB.text = string1.plus(" ").plus( repaymentBean.empresa + string2)
            }
        }
    }

    override fun afterBindView() {
        super.afterBindView()
        binding.header.ivCommonBarBack.back(this)

        binding.numberLl.setOnClickListener {

            if(isClickUseful() && binding.numberTv.isUseful()){
                SystemServiceUtil.copyTextToClipboard(this, binding.numberTv.text.toString())
                Toast.makeText(this@STPActivity, resources.getString(R.string.copia_exitosa), Toast.LENGTH_LONG).show()

            }
        }
    }

}