package com.example.flashfeso_lwj.flashfeso.ui.controll.activity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.flashfeso_lwj.BuildConfig
import com.example.flashfeso_lwj.R

import com.example.flashfeso_lwj.base.ui.controll.activity.BasePageStyleActivity
import com.example.flashfeso_lwj.databinding.ActivityConfiguracionBinding
import com.example.flashfeso_lwj.flashfeso.utils.Constants
import com.example.flashfeso_lwj.flashfeso.utils.InfoUtil
import com.example.flashfeso_lwj.flashfeso.utils.back
import com.example.flashfeso_lwj.flashfeso.viewmodel.ConfiguracionViewModel
import com.example.flashfeso_lwj.flashfeso.viewmodel.LoginViewModel
import com.example.lwj_base.common.base.BaseConstants
import com.example.lwj_common.common.ui.controll.tools.ktx.isUseful
import com.example.lwj_common.common.ui.controll.tools.ktx.toTrim
import java.lang.Exception

class ConfiguracionActivity: BasePageStyleActivity<ActivityConfiguracionBinding>() {
    val VERSION_VALUE: String = BuildConfig.VERSION_NAME
    private val viewModel: ConfiguracionViewModel by viewModels()
    val loginViewModel: LoginViewModel by viewModels()
    override fun observe() {
        viewModel.proSupportLiveData.observe(this, Observer {
            it.whenError {
                showError()
            }
            it.whenSuccess {
                if(it.data != null) {
                    showSuccess()
                    if(it.data?.me_email.isUseful()) {
                        binding.emailTv.text = it.data?.me_email
                    } else {
                        binding.emailTv.text = Constants.EMPTY_STRING
                    }

                    if(it.data?.me_phone.isUseful()) {
                        binding.phoneTv.text = it.data?.me_phone
                    } else {
                        binding.phoneTv.text = Constants.EMPTY_STRING
                    }
                } else {
                    if(BaseConstants.ISLOG) Log.e("---ConfiguracionAtv", "数据为null")
                    showError()
                }
            }
            it.whenClear {
                InfoUtil.clear()
                loginViewModel.queryNotifyUpdateLoginLiveData()
                Toast.makeText(this@ConfiguracionActivity, it.msg, Toast.LENGTH_SHORT).show()
                onBackPressed()
            }
        })
    }

    override fun afterBindView() {
        super.afterBindView()
        binding.header.ivCommonBarBack.back(this)

        binding.error.llError.setOnClickListener {
            if(isClickUseful()) {
                queryProSupport()
            }
        }


        binding.emailLl.setOnClickListener {
            if(! binding.emailTv.isUseful() || binding.emailTv.toTrim() == Constants.EMPTY_STRING) {
                return@setOnClickListener
            }

            if(isClickUseful()) {
                composeEmail(binding.emailTv.toTrim() !!)
            }
        }

        binding.phoneLl.setOnClickListener {
            if(! binding.phoneTv.isUseful() || binding.phoneTv.toTrim() == Constants.EMPTY_STRING) {
                return@setOnClickListener
            }
            if(isClickUseful()) {
                copyTextToClipboard(this, binding.phoneTv)
            }
        }
    }

    /**
     * 调用系统粘贴版实现文本复制
     * */
    private fun copyTextToClipboard(context: Context, textSource: TextView){
        //获取ClipboardManager对象
        val clipboard: ClipboardManager = context.getSystemService(Context
            .CLIPBOARD_SERVICE) as ClipboardManager
        //把文本封装到ClipData中
        val clip = ClipData.newPlainText(null, textSource.toTrim())
        // Set the clipboard's primary clip.
        clipboard.setPrimaryClip(clip)
    }

    /**
     * 调用系统邮件软件   发送邮件
     * @param addresses 邮箱集合
     */
    private fun composeEmail(address: String) {

        try {
            val intent = Intent(Intent.ACTION_SEND) //收件人集合如: String[] tos = {"1@abc.com", "2@abc.com"};
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(address)) //短信收件人, address为varag类型
            intent.putExtra(Intent.EXTRA_SUBJECT, "Account：" + InfoUtil.getAccount()) //设置短信主题
            if(intent.resolveActivity(packageManager) != null) { //能正常找到目标Activity
                //选择电子邮件客户端
                startActivity(Intent.createChooser(intent, resources.getString(R.string.seleccionar_cliente_de_correo_electronico)))
            } else {
                Toast.makeText(this, getResources().getString(R.string.software_relacionado_no_encontrado), Toast.LENGTH_SHORT).show()
            }
        } catch(e: Exception) {
            e.printStackTrace()
        }
    }


    override fun ActivityConfiguracionBinding.initView() {
        header.tvCommonBarTitle.text = resources.getString(R.string.atencion_al_cliente)
        versionTv.text = "Version:".plus(VERSION_VALUE)
        queryProSupport()

    }

    fun queryProSupport() {
        showLoading()
        viewModel.queryProSupport()
    }

    fun showSuccess() {
        binding.progress.llProgress.visibility = View.GONE
        binding.error.llError.visibility = View.GONE
    }

    fun showError() {
        binding.progress.llProgress.visibility = View.GONE
        binding.error.llError.visibility = View.VISIBLE
        binding.error.viewErrorUpdate.visibility = View.VISIBLE
    }

    fun showLoading() {
        binding.progress.llProgress.visibility = View.VISIBLE
        binding.error.llError.visibility = View.GONE
    }

}


