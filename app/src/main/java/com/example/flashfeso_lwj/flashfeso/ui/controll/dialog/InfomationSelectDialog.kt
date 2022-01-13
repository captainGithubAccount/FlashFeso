package com.example.flashfeso_lwj.flashfeso.ui.controll.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.flashfeso_lwj.databinding.DialogInfomationLaboralSelectBinding
import com.example.flashfeso_lwj.flashfeso.event.InfomationSelectItemOnClickListener
import com.example.flashfeso_lwj.flashfeso.ui.controll.adapter.RvAdapterLaboralSelect

class InfomationSelectDialog private constructor(): DialogFragment(){
    var isSetAdapter: Boolean = false

    private var _binding: DialogInfomationLaboralSelectBinding? = null
    val binding: DialogInfomationLaboralSelectBinding get() = _binding!!

    companion object{
        fun newInstance(): InfomationSelectDialog = InfomationSelectDialog()
    }
    private lateinit var itemListener: InfomationSelectItemOnClickListener
    private lateinit var dataOnRv: List<String>
    private var flag: Int = 0

    /*
    * @Param: flag: flag不同的数值代表不一样的列表数据源, 通过手动传flag然后再由rv适配器返回该flag, 最后通过flag的值进行判断
    * 不同的flag表示可以对不同的列表进行不同的操作
    * */
    fun addSetting(flag: Int,data: List<String>, listener: InfomationSelectItemOnClickListener) =
        this.setFlag(flag).setData(data).setListener(listener)

    private fun InfomationSelectDialog.setFlag(dialogFlag: Int): InfomationSelectDialog =
        this.apply {  flag = dialogFlag}


    private fun InfomationSelectDialog.setListener(listener: InfomationSelectItemOnClickListener): InfomationSelectDialog =
        this.apply {  itemListener = listener}


    private fun InfomationSelectDialog.setData(data: List<String>): InfomationSelectDialog =
        this.apply {  dataOnRv = data}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
        setStyle(DialogFragment.STYLE_NO_TITLE, 0)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogInfomationLaboralSelectBinding.inflate(inflater, container, false)
        afterBindView()
        return binding.root
    }

    private fun afterBindView() {

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        afterInitView()
    }
    /*
    fun <T> setAdapter(listener: T){
        this.itemListener = listener
        isSetAdapter = true
    }*/

    private fun afterInitView() {
        if(isSetAdapter){

        }else{
            binding.rvLaboralSelectContent.adapter = RvAdapterLaboralSelect(flag, dataOnRv, itemListener)
        }
    }


}