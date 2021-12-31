package com.example.flashfeso_lwj.flashfeso.ui.controll.activity
import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.flashfeso_lwj.R
import com.example.flashfeso_lwj.base.ui.controll.activity.BasePageStyleActivity
import com.example.flashfeso_lwj.databinding.ActivityInfomationLaboralBinding
import com.example.flashfeso_lwj.flashfeso.event.InfomationLaboralSelectItemOnclickListener
import com.example.flashfeso_lwj.flashfeso.ui.controll.dialog.InfomationLaboralSelectDialog
import com.example.flashfeso_lwj.flashfeso.utils.textIsEmpty
import com.example.lwj_base.common.base.BaseConstants
import dagger.hilt.android.AndroidEntryPoint
/*
*
* 认证二
* */
@AndroidEntryPoint
class InfomationLaboralActivity : BasePageStyleActivity<ActivityInfomationLaboralBinding>(), InfomationLaboralSelectItemOnclickListener{
    private var imssText: String? = null
    private var isDaGong: Boolean = true
    lateinit var  mDialogEducation: InfomationLaboralSelectDialog
    lateinit var  mDialogJobTypeDialog: InfomationLaboralSelectDialog
    lateinit var  mDialogZiZhuJobDialog: InfomationLaboralSelectDialog
    lateinit var  mDialogMonthMoneyDialog: InfomationLaboralSelectDialog
    lateinit var  mDialogPayDate: InfomationLaboralSelectDialog
    lateinit var mDataEducation: List<String>
    lateinit var mDataJobType: List<String>
    lateinit var mDataZiZhuJob: List<String>
    lateinit var mDataMonthMoney: List<String>
    lateinit var mDataPayData: List<String>
    override fun observe() {}
    override fun afterBindView() {
        super.afterBindView()
        //教育ll
        binding.educationLl.setOnClickListener {
            hideOthersKeyboard()
            mDataEducation = resources.getStringArray(R.array.array_education_all).toList()
            mDialogEducation = InfomationLaboralSelectDialog.newInstance()
                .addSetting(1 ,mDataEducation, this)
            mDialogEducation.show(supportFragmentManager, "mDialogEducationLl")
        }

        //我有一份工作tv
        binding.daGong.setOnClickListener {
            if(!isDaGong) {
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
            if(isDaGong){
                mDataJobType = resources.getStringArray(R.array.array_dagong_type_all).toList()
                mDialogJobTypeDialog = InfomationLaboralSelectDialog.newInstance().addSetting(2, mDataJobType, this)
                mDialogJobTypeDialog.show(supportFragmentManager, "mDialogJobDialog")
            }else{
                mDataZiZhuJob = resources.getStringArray(R.array.array_zizhu_job_type_all).toList()
                mDialogZiZhuJobDialog = InfomationLaboralSelectDialog.newInstance().addSetting(3, mDataZiZhuJob, this)
                mDialogZiZhuJobDialog.show(supportFragmentManager, "mDialogZiZhuJobDialog")
            }
        }

        //月收入
        binding.monthlySalaryLl.setOnClickListener {
            hideOthersKeyboard()
            mDataMonthMoney = resources.getStringArray(R.array.array_money_all).toList()
            mDialogMonthMoneyDialog = InfomationLaboralSelectDialog.newInstance().addSetting(4, mDataMonthMoney, this)
            mDialogMonthMoneyDialog.show(supportFragmentManager, "mDialogMonthMoneyDialog")
        }
        //发薪日
        binding.diaDePagoLl.setOnClickListener {
            hideOthersKeyboard()
            mDataPayData = resources.getStringArray(R.array.array_pay_date).toList()
            mDialogPayDate = InfomationLaboralSelectDialog.newInstance().addSetting(5, mDataPayData, this)
            mDialogPayDate.show(supportFragmentManager, "mDialogPayDate")
        }
        //确定按钮
        binding.confirm.setOnClickListener {
            if(binding.educationTv.textIsEmpty()){
                Toast.makeText(this@InfomationLaboralActivity, resources.getString(R.string.elija_el_nivel_de_educacion), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(binding.emailEt.textIsEmpty()){
                Toast.makeText(this@InfomationLaboralActivity, resources.getString(R.string.introduzca_su_correo_electronico), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(binding.jobTypeTv.textIsEmpty()){
                Toast.makeText(this@InfomationLaboralActivity, resources.getString(R.string.elija_un_trabajo), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(binding.monthlySalaryTv.textIsEmpty()){
                Toast.makeText(this@InfomationLaboralActivity, resources.getString(R.string.seleccione_ingresos_mensuales), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(binding.diaDePagoTv.textIsEmpty()){
                Toast.makeText(this@InfomationLaboralActivity, resources.getString(R.string.elija_el_dia_del_pago), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            imssText = binding.imssTv.text.toString().trim()

            if(isClickUseful()){
                掉接口
            }
        }

    }

    private fun hideOthersKeyboard() {
        if(binding.emailEt.hasFocus()){
            val imm = binding.emailEt.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.run{
                hideKeyboard(binding.emailEt)
            }
        }

        if(binding.imssTv.hasFocus()){
            val imm = binding.imssTv.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.run{
                hideKeyboard(binding.imssTv)
            }
        }
    }

    override fun ActivityInfomationLaboralBinding.initView() {
        binding.header.tvCommonBarTitle.text = getResources().getString(R.string.informacion_laboral)
    }

    override fun onDialogItemClick(str: String, flag: Int) {
        /*
        * 1: 教育对话框 2: jobType对话框 3: 自主job对话框 4: 月收入对话框 5: 支付日
        * */
        when(flag){
            1 -> {
                mDialogEducation.dismiss()
                binding.educationTv.text = str
            }
            2 -> {
                mDialogJobTypeDialog.dismiss()
                binding.jobTypeTv.text = str
            }
            3 -> {
                mDialogZiZhuJobDialog.dismiss()
                binding.jobTypeTv.text = str
            }
            4 -> {
                mDialogMonthMoneyDialog.dismiss()
                binding.monthlySalaryTv.text = str
            }
            5 ->{
                mDialogPayDate.dismiss()
                binding.diaDePagoTv.text = str
            }
        }
        if(BaseConstants.ISLOG)Log.d("----data on click", str)
    }

}