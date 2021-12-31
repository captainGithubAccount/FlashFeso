/*
package com.example.flashfeso_lwj.flashfeso.ui.controll.activity

import android.util.Log
import com.example.flashfeso_lwj.base.ui.controll.activity.BasePageStyleActivity
import com.example.flashfeso_lwj.databinding.TestDialogBinding
import com.example.flashfeso_lwj.flashfeso.event.InfomationLaboralSelectItemOnclickListener
import com.example.flashfeso_lwj.flashfeso.ui.controll.dialog.InfomationLaboralSelectDialog
import com.example.lwj_base.common.base.BaseConstants
import dagger.hilt.android.AndroidEntryPoint

*/
/*
*
* 认证二
* *//*




@AndroidEntryPoint
class InfomationLaboralTestActivity : BasePageStyleActivity<TestDialogBinding>(), InfomationLaboralSelectItemOnclickListener{
    lateinit var  dialog: InfomationLaboralSelectDialog
    lateinit var  dialog2: InfomationLaboralSelectDialog
    lateinit var  dialog3: InfomationLaboralSelectDialog
    lateinit var  dialog4: InfomationLaboralSelectDialog
    override fun observe() {

    }

    override fun TestDialogBinding.initView() {

        binding.tvTest.setOnClickListener {
            dialog = InfomationLaboralSelectDialog.newInstance().addSetting(list, this@InfomationLaboralTestActivity)
            dialog.show(supportFragmentManager, "a")
            //注意这里的tag必须不同否则会出现点击事件无效
        }

        binding.tvTest2.setOnClickListener {
            dialog2 = InfomationLaboralSelectDialog.newInstance().addSetting(list2, this@InfomationLaboralTestActivity)
            dialog2.show(supportFragmentManager, "ab")
        }

        binding.tvTest3.setOnClickListener {
            dialog3 = InfomationLaboralSelectDialog.newInstance().addSetting(list3, this@InfomationLaboralTestActivity)
            dialog3.show(supportFragmentManager, "abc")
        }

        binding.tvTest4.setOnClickListener {
            dialog4 = InfomationLaboralSelectDialog.newInstance().addSetting(list4, this@InfomationLaboralTestActivity)
            dialog4.show(supportFragmentManager, "abcd")
        }


    }

    override fun onDialogItemClick(str: String) {
        if(BaseConstants.ISLOG)Log.d("----data on click", str)
    }

    val list: List<String> = listOf("dddddddd",
        "aaaaaaaa",
        "ccccdjwalgnalwnglajdlaw",
        "daflwanglnawlngwa",
        "diwajglnawlnglkaw",
        "aaaaaaaa",
        "ccccdjwalgnalwnglajdlaw",
        "daflwanglnawlngwa",
        "diwajglnawlnglkaw",
        "aaaaaaaa",
        "ccccdjwalgnalwnglajdlaw",
        "daflwanglnawlngwa",
        "diwajglnawlnglkaw",
        "aaaaaaaa",
        "ccccdjwalgnalwnglajdlaw",
        "daflwanglnawlngwa",
        "diwajglnawlnglkaw",
        "aaaaaaaa",
        "ccccdjwalgnalwnglajdlaw",
        "daflwanglnawlngwa",
        "diwajglnawlnglkaw",
        "aaaaaaaa",
        "ccccdjwalgnalwnglajdlaw",
        "daflwanglnawlngwa",
        "diwajglnawlnglkaw"




    )

    val list2: List<String> = listOf("dddddddd",
        "aaaaaaaa",
        "ccccdjwalgnalwnglajdlaw",
        "daflwanglnawlngwa",
        "diwajglnawlnglkaw",
        "aaaaaaaa",
        "ccccdjwalgnalwnglajdlaw",
        "daflwanglnawlngwa",
        "diwajglnawlnglkaw",
        "aaaaaaaa",
        "ccccdjwalgnalwnglajdlaw",
        "daflwanglnawlngwa",
        "diwajglnawlnglkaw",





        )

    val list3: List<String> = listOf("dddddddd",
        "aaaaaaaa",
        "ccccdjwalgnalwnglajdlaw",
    )

    val list4: List<String> = listOf("dddddddd",
        "aaaaaaaa",
        "ccccdjwalgnalwnglajdlaw",
        "daflwanglnawlngwa",
        "diwajglnawlnglkaw",
        "aaaaaaaa",
        "ccccdjwalgnalwnglajdlaw",
        "daflwanglnawlngwa",
        "diwajglnawlnglkaw",
        "aaaaaaaa",
        "ccccdjwalgnalwnglajdlaw",
        "daflwanglnawlngwa",
        "diwajglnawlnglkaw",
        "aaaaaaaa",
        "ccccdjwalgnalwnglajdlaw",
        "daflwanglnawlngwa",
        "diwajglnawlnglkaw",
        "aaaaaaaa",
        "ccccdjwalgnalwnglajdlaw",
        "daflwanglnawlngwa",
        "diwajglnawlnglkaw",
        "aaaaaaaa",
        "ccccdjwalgnalwnglajdlaw",
        "daflwanglnawlngwa",
        "diwajglnawlnglkaw",
        "ccccdjwalgnalwnglajdlaw",
        "daflwanglnawlngwa",
        "diwajglnawlnglkaw",
        "aaaaaaaa",
        "ccccdjwalgnalwnglajdlaw",
        "daflwanglnawlngwa",
        "diwajglnawlnglkaw",
        "aaaaaaaa",
        "ccccdjwalgnalwnglajdlaw",
        "daflwanglnawlngwa",
        "diwajglnawlnglkaw",
        "aaaaaaaa",
        "ccccdjwalgnalwnglajdlaw",
        "daflwanglnawlngwa",
        "diwajglnawlnglkaw"
    )



}*/
