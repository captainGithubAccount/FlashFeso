package com.example.flashfeso_lwj.flashfeso.ui.controll.activity

import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.flashfeso_lwj.R
import com.example.flashfeso_lwj.flashfeso.base.DataResult
import com.example.flashfeso_lwj.base.ui.controll.activity.BasePageStyleActivity
import com.example.flashfeso_lwj.base.utils.SimpleProgressDialogUtil
import com.example.flashfeso_lwj.databinding.ActivityInfomationLaboralBinding
import com.example.flashfeso_lwj.flashfeso.event.InfomationSelectItemOnClickListener
import com.example.flashfeso_lwj.flashfeso.ui.controll.dialog.InfomationSelectDialog
import com.example.flashfeso_lwj.flashfeso.utils.InfoUtil
import com.example.flashfeso_lwj.flashfeso.utils.textIsEmpty
import com.example.flashfeso_lwj.flashfeso.viewmodel.InfomationLaboralAuthWorkViewModdel
import com.example.flashfeso_lwj.flashfeso.viewmodel.LoginViewModel
import com.example.lwj_base.common.base.BaseConstants
import com.example.lwj_common.common.ui.controll.tools.utils.StringUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/*
*
* 认证二
* */
@AndroidEntryPoint
class InfomationLaboralActivity : BasePageStyleActivity<ActivityInfomationLaboralBinding>(), InfomationSelectItemOnClickListener {

    var educationPosition: Int = 0
    var jobPosition: Int = 0
    var monthlySalaryPosition: Int = 0

    private var isDaGong: Boolean = true

    @Inject
    @JvmField
    var mSimpleProgressDialogUtil: SimpleProgressDialogUtil? = null
    lateinit var mDialogEducation: InfomationSelectDialog
    lateinit var mDialogJobTypeDialog: InfomationSelectDialog
    lateinit var mDialogZiZhuJobDialog: InfomationSelectDialog
    lateinit var mDialogMonthMoneyDialog: InfomationSelectDialog
    lateinit var mDialogPayDate: InfomationSelectDialog
    lateinit var mDataEducation: List<String>
    lateinit var mDataJobType: List<String>
    lateinit var mDataZiZhuJob: List<String>
    lateinit var mDataMonthMoney: List<String>
    lateinit var mDataPayData: List<String>

    private val mAuthWorkViewModel: InfomationLaboralAuthWorkViewModdel by viewModels()
    val mLoginViewModel: LoginViewModel by viewModels()

    override fun observe() {
        mAuthWorkViewModel.livedata.observe(this, Observer {
            it.whenError {
                mSimpleProgressDialogUtil?.closeHUD()
                Toast.makeText(this, (it as DataResult.Error).errorMessage, Toast.LENGTH_SHORT).show()
            }
            it.whenClear {
                mSimpleProgressDialogUtil?.closeHUD()
                InfoUtil.clear()
                mLoginViewModel.queryNotifyUpdateLoginLiveData()
                Toast.makeText(this, (it as DataResult.Clear).clearMessage, Toast.LENGTH_SHORT).show()
                onBackPressed()
            }
            it.whenSuccessResponse {
                mSimpleProgressDialogUtil?.closeHUD()


                if ((it as DataResult.Success).successMessagle.equals(getResources().getString(R.string.success))) {
                    Toast.makeText(this, (it as DataResult.Success).successMessagle, Toast.LENGTH_SHORT).show()
                    onBackPressed()
                    startActivity(HistorialCrediticioActivity::class.java)
                    mLoginViewModel.queryNotifyInicioBeanLiveData()
                }
            }
        })

    }

    override fun afterBindView() {
        super.afterBindView()
        //教育ll
        binding.educationLl.setOnClickListener {
            hideOthersKeyboard()
            mDataEducation = resources.getStringArray(R.array.array_education_all).toList()
            mDialogEducation = InfomationSelectDialog.newInstance().addSetting(1, mDataEducation, this)
            mDialogEducation.show(supportFragmentManager, "mDialogEducationLl")
        }

        //我有一份工作tv
        binding.daGong.setOnClickListener {
            if (!isDaGong) {
                hideOthersKeyboard()
                isDaGong = true
                binding.daGong.setBackgroundDrawable(resources.getDrawable(R.drawable.bg_confirm))
                binding.daGong.setTextColor(resources.getColor(R.color.color_FFFFFF))
                binding.ziZhuJob.setBackgroundDrawable(resources.getDrawable(R.drawable.bg_confirm_stroke))
                binding.ziZhuJob.setTextColor(resources.getColor(R.color.color_main_background))
                binding.jobTypeTitleTv.setText(resources.getString(R.string.informacion_laboral))
                binding.jobTypeTv.setText("")
                //todo -删除
                //jobPosition = -1
            }
        }

        //自主工作
        binding.ziZhuJob.setOnClickListener(View.OnClickListener {

            if (isDaGong) {
                hideOthersKeyboard()
                isDaGong = false
                binding.ziZhuJob.setBackgroundDrawable(resources.getDrawable(R.drawable.bg_confirm))
                binding.ziZhuJob.setTextColor(resources.getColor(R.color.color_FFFFFF))
                binding.daGong.setBackgroundDrawable(resources.getDrawable(R.drawable.bg_confirm_stroke))
                binding.daGong.setTextColor(resources.getColor(R.color.color_main_background))
                binding.jobTypeTitleTv.setText(resources.getString(R.string.fuentes_de_ingresos))
                binding.jobTypeTv.setText("")
                //todo -删除
                //jobPosition = -1
            }
        })

        //工作类型
        binding.jobTypeLl.setOnClickListener {
            if (isDaGong) {
                mDataJobType = resources.getStringArray(R.array.array_dagong_type_all).toList()
                mDialogJobTypeDialog = InfomationSelectDialog.newInstance().addSetting(2, mDataJobType, this)
                mDialogJobTypeDialog.show(supportFragmentManager, "mDialogJobDialog")
            } else {
                mDataZiZhuJob = resources.getStringArray(R.array.array_zizhu_job_type_all).toList()
                mDialogZiZhuJobDialog = InfomationSelectDialog.newInstance().addSetting(3, mDataZiZhuJob, this)
                mDialogZiZhuJobDialog.show(supportFragmentManager, "mDialogZiZhuJobDialog")
            }
        }

        //月收入
        binding.monthlySalaryLl.setOnClickListener {
            hideOthersKeyboard()
            mDataMonthMoney = resources.getStringArray(R.array.array_money_all).toList()
            mDialogMonthMoneyDialog = InfomationSelectDialog.newInstance().addSetting(4, mDataMonthMoney, this)
            mDialogMonthMoneyDialog.show(supportFragmentManager, "mDialogMonthMoneyDialog")
        }
        //发薪日
        binding.diaDePagoLl.setOnClickListener {
            hideOthersKeyboard()
            mDataPayData = resources.getStringArray(R.array.array_pay_date).toList()
            mDialogPayDate = InfomationSelectDialog.newInstance().addSetting(5, mDataPayData, this)
            mDialogPayDate.show(supportFragmentManager, "mDialogPayDate")
        }
        //确定按钮
        binding.confirm.setOnClickListener {
            val education = binding.educationTv.text.toString().trim()
            if (binding.educationTv.textIsEmpty()) {
                Toast.makeText(this@InfomationLaboralActivity, resources.getString(R.string.elija_el_nivel_de_educacion), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val email = binding.emailEt.text.toString().trim()
            if (!StringUtils.isEmail(email)) {
                Toast.makeText(this@InfomationLaboralActivity, resources.getString(R.string.introduzca_su_correo_electronico), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val jobType = binding.jobTypeTv.text.toString().trim()
            if (binding.jobTypeTv.textIsEmpty()) {
                Toast.makeText(this@InfomationLaboralActivity, resources.getString(R.string.elija_un_trabajo), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val monthlySalary = binding.monthlySalaryTv.text.toString().trim()
            if (binding.monthlySalaryTv.textIsEmpty()) {
                Toast.makeText(this@InfomationLaboralActivity, resources.getString(R.string.seleccione_ingresos_mensuales), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val diaDePago = binding.diaDePagoTv.text.toString().trim()
            if (binding.diaDePagoTv.textIsEmpty()) {
                Toast.makeText(this@InfomationLaboralActivity, resources.getString(R.string.elija_el_dia_del_pago), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val imssText = binding.imssTv.text.toString().trim()

            val soIncome: Int
            val isEmp: Int
            if (isDaGong) {
                soIncome = 1
                isEmp = 1
            } else {
                soIncome = 0
                isEmp = 0
            }

            if (isClickUseful()) {

                mSimpleProgressDialogUtil?.showHUD(this, false)
                val map = HashMap<String, Any>()
                map["education"] = educationPosition
                map["email"] = email
                map["isEmp"] = isEmp
                map["soIncome"] = soIncome
                map["jobPosit"] = jobPosition
                map["inRange"] = monthlySalaryPosition
                map["seCode"] = imssText
                map["payday"] = diaDePago

                queryAuthWork(map)
            }
        }

    }

    fun String.getEducationPosition(): Int? = when (this) {
        "Escuela primaria" -> 1
        "secundaria" -> 2
        "Preparatpria" -> 3
        "Educación media superior" -> 4
        "Facultad" -> 5
        "Universidades" -> 6
        "Educación superior" -> 7
        else -> null
    }

    private fun queryAuthWork(map: HashMap<String, Any>) {

        mAuthWorkViewModel.query(map)
    }

    private fun hideOthersKeyboard() {
        if (binding.emailEt.hasFocus()) {
            val imm = binding.emailEt.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.run {
                hideKeyboard(binding.emailEt)
            }
        }

        if (binding.imssTv.hasFocus()) {
            val imm = binding.imssTv.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.run {
                hideKeyboard(binding.imssTv)
            }
        }
    }

    override fun ActivityInfomationLaboralBinding.initView() {
        binding.header.tvCommonBarTitle.text = getResources().getString(R.string.informacion_laboral)
    }

    override fun onDialogItemClick(list: List<String>, flag: Int) {
        /*
        * 1: 教育对话框 2: jobType对话框 3: 自主job对话框 4: 月收入对话框 5: 支付日
        * */
        when (flag) {
            1 -> {
                mDialogEducation.dismiss()
                binding.educationTv.text = list.get(0)
                educationPosition = list.get(1).toInt()
            }
            2 -> {
                mDialogJobTypeDialog.dismiss()
                binding.jobTypeTv.text = list.get(0)
                jobPosition = list.get(1).toInt()
            }
            3 -> {
                mDialogZiZhuJobDialog.dismiss()
                binding.jobTypeTv.text = list.get(0)
            }
            4 -> {
                mDialogMonthMoneyDialog.dismiss()
                binding.monthlySalaryTv.text = list.get(0)
                monthlySalaryPosition = list.get(1).toInt()
            }
            5 -> {
                mDialogPayDate.dismiss()
                binding.diaDePagoTv.text = list.get(0)
            }
        }
        if (BaseConstants.ISLOG) Log.d("----data on click", list.get(0))
    }

    override fun onDestroy() {
        super.onDestroy()
        mSimpleProgressDialogUtil = null
    }

}