package com.example.flashfeso_lwj.flashfeso.ui.controll.activity

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Handler
import android.provider.ContactsContract
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.flashfeso_lwj.R
import com.example.flashfeso_lwj.base.entity.DataResult
import com.example.flashfeso_lwj.base.ui.controll.activity.BasePageStyleActivity
import com.example.flashfeso_lwj.base.utils.SimpleProgressDialogUtil
import com.example.flashfeso_lwj.databinding.ActivityInformacionDeContactosBinding
import com.example.flashfeso_lwj.flashfeso.event.InfomationSelectItemOnClickListener
import com.example.flashfeso_lwj.flashfeso.ui.controll.dialog.InfomationSelectDialog
import com.example.flashfeso_lwj.flashfeso.utils.InfoUtil
import com.example.flashfeso_lwj.flashfeso.utils.back
import com.example.flashfeso_lwj.flashfeso.viewmodel.InformacionDeContactosViewModel
import com.example.flashfeso_lwj.flashfeso.viewmodel.LoginViewModel
import com.example.lwj_common.common.ui.controll.tools.utils.StringUtils
import java.lang.Exception
import java.util.regex.Pattern
import javax.inject.Inject

class InformacionDeContactosActivity : BasePageStyleActivity<ActivityInformacionDeContactosBinding>(), InfomationSelectItemOnClickListener{
    var mHandler = Handler()
    private val mViewModel: InformacionDeContactosViewModel by viewModels()
    val mLoginViewModel: LoginViewModel by viewModels()
    @Inject
    @JvmField
    var mSimpleProgressDialogUtil: SimpleProgressDialogUtil? = null
    lateinit var mRelativeTwoDialog: InfomationSelectDialog
    lateinit var mRelativeOneDialog: InfomationSelectDialog
    private val REQUEST_CODE_TWO: Int = 2
    private val REQUEST_CODE_ONE: Int = 1
    private var mRelativeTwoPosition: Int = -1
    private var mRelativesOnePosition = -1
    override fun observe() {
        mViewModel.liveData.observe(this, Observer {
            mSimpleProgressDialogUtil?.closeHUD()
            it.whenError {
                Toast.makeText(this@InformacionDeContactosActivity, (it as DataResult.Error).errorMessage, Toast.LENGTH_SHORT).show()
            }
            it.whenSuccessResponse {
                Toast.makeText(this@InformacionDeContactosActivity, (it as DataResult.Success).successMessagle, Toast.LENGTH_SHORT).show()
                onBackPressed()
                mLoginViewModel.queryNotify2LiveData()
                startActivity(InformacionDeIdetidadActivity::class.java)
            }
            it.whenClear {
                InfoUtil.clear()
                mLoginViewModel.queryNotifyLiveData()
                Toast.makeText(this@InformacionDeContactosActivity, (it as DataResult.Error).errorMessage, Toast.LENGTH_SHORT).show()
                onBackPressed()
            }
        })
    }

    override fun ActivityInformacionDeContactosBinding.initView() {
        binding.header.tvCommonBarTitle.text = resources.getString(R.string.informacion_de_contactos)
    }

    override fun afterBindView() {
        super.afterBindView()
        binding.header.ivCommonBarBack.back(this@InformacionDeContactosActivity)

        binding.relativesOneLl.setOnClickListener {
            if(isClickUseful()){
                hideKeyAll()
                val data = resources.getStringArray(R.array.array_relatives_all).toList()
                mRelativeOneDialog = InfomationSelectDialog.newInstance().addSetting(1, data, this)
                mRelativeOneDialog.show(supportFragmentManager, "mRelativeOneDialog")
            }
        }

        binding.personalPhoneOneLl.setOnClickListener {
            if(isClickUseful()){
                hideKeyAll()
                //等待200毫秒，是为了防止软键盘未完全关闭收回，跳转回来下方空白（红米手机易产生的BUG）
                mHandler.postDelayed(object: Runnable{
                    override fun run() {
                        //调用本地联系人
                        val intent = Intent(Intent.ACTION_PICK)
                        intent.type = ContactsContract.Contacts.CONTENT_TYPE
                        startActivityForResult(intent, REQUEST_CODE_ONE)
                    }
                }, 200)
            }
        }

        binding.relativesTwoLl.setOnClickListener {
            if(isClickUseful()){
                hideKeyAll()
                val data = resources.getStringArray(R.array.array_relatives_all).toList()
                mRelativeTwoDialog = InfomationSelectDialog.newInstance().addSetting(2, data, this )
                mRelativeTwoDialog.show(supportFragmentManager, "mRelativeTwoDialog")
            }
        }

        binding.personalPhoneTwoLl.setOnClickListener {
            if(isClickUseful()){
                hideKeyAll()
                //等待200毫秒，是为了防止软键盘未完全关闭收回，跳转回来下方空白（红米手机易产生的BUG）
                mHandler.postDelayed(object: Runnable{
                    override fun run() {
                        var intent = Intent(Intent.ACTION_PICK)
                        intent.type = ContactsContract.Contacts.CONTENT_TYPE
                        startActivityForResult(intent, REQUEST_CODE_TWO)
                    }
                },200)
            }
        }

        binding.confirm.setOnClickListener {
            hideKeyAll()

            val relativesOne = binding.relativesOneTv.getText().toString().trim()
            if(StringUtils.isEmpty(relativesOne)){
                Toast.makeText(this@InformacionDeContactosActivity, resources.getString(R.string.seleccione_la_relacion_con_el_primer_contacto), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val personalNameOne: String = binding.personalNameOneEt.getText().toString().trim()
            if (StringUtils.isEmpty(personalNameOne)) {
                Toast.makeText(this@InformacionDeContactosActivity, resources.getString(R.string.introduzca_el_nombre_completo_del_primer_contacto), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val personalPhoneOne: String = binding.personalPhoneOneTv.getText().toString().trim()
            if (StringUtils.isEmpty(personalPhoneOne)) {
                Toast.makeText(this@InformacionDeContactosActivity, resources.getString(R.string.por_favor_seleccione_el_numero_de_telefono_del_primer_contacto), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val relativesTwo: String = binding.relativesTwoTv.getText().toString().trim()
            if (StringUtils.isEmpty(relativesTwo) ) {
                Toast.makeText(this@InformacionDeContactosActivity, resources.getString(R.string.seleccione_la_relacion_con_el_segundo_contacto), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val personalNameTwo: String = binding.personalNameTwoEt.getText().toString().trim()
            if (StringUtils.isEmpty(personalNameTwo)) {
                Toast.makeText(this@InformacionDeContactosActivity, resources.getString(R.string.introduzca_el_nombre_completo_del_segundo_contacto), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val personalPhoneTwo: String = binding.personalPhoneTwoTv.getText().toString().trim()
            if (StringUtils.isEmpty(personalPhoneTwo)) {
                Toast.makeText(this@InformacionDeContactosActivity, resources.getString(R.string.por_favor_seleccione_el_numero_de_telefono_del_segundo_contacto), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (personalPhoneOne == personalPhoneTwo) {
                Toast.makeText(this@InformacionDeContactosActivity, resources.getString(R.string.dos_contactos_no_pueden_tener_el_mismo_nmero), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (personalNameOne == personalNameTwo) {
                Toast.makeText(this@InformacionDeContactosActivity, resources.getString(R.string.dos_contactos_no_pueden_tener_el_miusmo_nombre), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (relativesOne == relativesTwo && relativesOne == "Padres") {
                Toast.makeText(this@InformacionDeContactosActivity, resources.getString(R.string.no_puede_haber_dos_padres), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (relativesOne == relativesTwo && relativesOne == "Parejas") {
                Toast.makeText(this@InformacionDeContactosActivity, resources.getString(R.string.no_puede_tener_dos_conyuges), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(isClickUseful()){
                queryAuthContactPerson(mRelativesOnePosition, personalNameOne, personalPhoneOne, mRelativeTwoPosition, personalNameTwo, personalPhoneTwo)
            }
        }
    }

    override fun onDestroy() {
        mHandler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }

    private fun queryAuthContactPerson(
        firCPNexus: Int, firCPName: String, firCPPhone: String,
        secCPNexus: Int, secCPName: String, secCPPhone: String,
    ) {
        mSimpleProgressDialogUtil?.showHUD(this, false)

        val map: MutableMap<String, Any> = HashMap()
        map["firCPNexus"] = firCPNexus
        map["firCPName"] = firCPName
        map["firCPPhone"] = firCPPhone
        map["secCPNexus"] = secCPNexus
        map["secCPName"] = secCPName
        map["secCPPhone"] = secCPPhone
        mViewModel.query(map as HashMap<String, Any>)

    }

    //todo need to study contentReciver
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(resultCode){
            REQUEST_CODE_ONE -> {
                //回掉, 选择通讯录联系人返回
                if(data == null){
                    return
                }
                val projection: Array<String> = arrayOf(
                    ContactsContract.CommonDataKinds.Phone.NUMBER,
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                )
                try {
                    val cursor: Cursor? = contentResolver.query(
                        data.data!!,
                        projection,
                        null,null,null
                    )
                    cursor?.let {
                        while(it.moveToNext()){
                            val number: String = it.getString(0)
                            val displayName: String = it.getString(1)
                            binding.personalPhoneOneTv.text = number.apply {
                                replace(" ", "")
                                Pattern.compile("[^0-9]]").matcher(this).replaceAll("").trim()
                            }
                            binding.personalNameOneEt.setText( displayName)

                        }
                        it.close()
                    }
                }catch (e: Exception){
                    e.printStackTrace()
                }

            }


            REQUEST_CODE_TWO -> {
                //回掉, 选择通讯录联系人返回
                if(data == null){
                    return
                }

                val projections: Array<String> = arrayOf(
                    ContactsContract.CommonDataKinds.Phone.NUMBER,
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                )
                try {
                    val cursor: Cursor? = contentResolver.query(
                        data?.data!!,
                        projections,
                        null, null, null
                    )
                    cursor?.let{
                        while(cursor.moveToNext()){
                            val number: String = it.getString(0)
                            val displayName: String = it.getString(1)
                            binding.personalPhoneTwoTv.text = number.apply {
                                replace(" ", "")
                                Pattern.compile("[^0-9]").matcher(this).replaceAll("").trim()
                            }
                            binding.personalNameTwoEt.setText(displayName)
                        }
                        it.close()
                    }
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }

            else -> {}
        }
    }

    private fun hideKeyAll() {
        if(binding.personalNameOneEt.isFocused){
            val imm = binding.personalNameOneEt.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.personalNameOneEt.windowToken, 0)
        }
        if(binding.personalNameTwoEt.isFocused){
            val imm = binding.personalNameTwoEt.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.personalNameTwoEt.windowToken, 0)
        }
    }

    override fun onDialogItemClick(list: List<String>, flag: Int) {
        when(flag){
            RelativeLl.ONE.value -> {
                mRelativeOneDialog.dismiss()
                binding.relativesOneTv.text = list[0]
                mRelativesOnePosition = list[1].toInt() + 1
            }
            RelativeLl.TWO.value -> {
                mRelativeTwoDialog.dismiss()
                binding.relativesTwoTv.text = list[0]
                mRelativeTwoPosition = list[1].toInt() + 1
            }
        }
    }

    enum class RelativeLl(val value: Int){
        ONE(1), TWO(2)
    }

}