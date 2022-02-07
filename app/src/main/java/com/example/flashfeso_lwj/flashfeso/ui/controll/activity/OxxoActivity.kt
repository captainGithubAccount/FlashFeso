package com.example.flashfeso_lwj.flashfeso.ui.controll.activity

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.flashfeso_lwj.R
import com.example.flashfeso_lwj.base.ui.controll.activity.BasePageStyleActivity
import com.example.flashfeso_lwj.databinding.ActivityOxxoBinding
import com.example.flashfeso_lwj.flashfeso.utils.back
import com.example.lwj_common.common.ui.controll.tools.ktx.isUseful
import com.example.lwj_common.common.ui.controll.tools.utils.DoubleUtils
import com.example.lwj_common.common.ui.controll.tools.utils.NumberUtils
import com.facebook.drawee.view.SimpleDraweeView

class OxxoActivity: BasePageStyleActivity<ActivityOxxoBinding>() {
    companion object {
        val TAG_BARCODE_URL = "tag_barcode_url"
        val TAG_SERVICE_MONEY = "tag_service_money"
        val TAG_BARCODE = "tag_barcode"
    }

    private var barcodeUrl: String? = null
    private var serviceMoney: String? = null
    private var barcode: String? = null

    override fun observe() {

    }

    override fun ActivityOxxoBinding.initView() {

        binding.header.tvCommonBarTitle.text = resources.getString(R.string.quiero_pagar)


        barcodeUrl = intent.getStringExtra(OxxoActivity.TAG_BARCODE_URL)
        serviceMoney = intent.getStringExtra(OxxoActivity.TAG_SERVICE_MONEY)
        barcode = intent.getStringExtra(OxxoActivity.TAG_BARCODE)

        if(barcodeUrl.isUseful()) {
            val uri = Uri.parse(barcodeUrl)
            erweimaImg.setImageURI(uri)
        } else {
            erweimaImg.setImageURI("")
        }

        if(serviceMoney.isUseful()) {
            tasaDeTramitacion.text = resources.getString(R.string.tasa_de_tramitacion_requerida)
                .plus(NumberUtils.goToZeroString(DoubleUtils.divToString(serviceMoney, "100", 2)))
        } else {
            tasaDeTramitacion.text = ""
        }

        if(barcode.isUseful()) {
            barcodeTv.text = barcode
        } else {
            barcodeTv.text = ""
        }

    }

    override fun afterBindView() {
        super.afterBindView()
        binding.header.ivCommonBarBack.back(this)
    }

}