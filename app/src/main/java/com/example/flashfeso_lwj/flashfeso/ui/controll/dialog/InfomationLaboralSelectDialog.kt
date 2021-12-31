package com.example.flashfeso_lwj.flashfeso.ui.controll.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.flashfeso_lwj.databinding.DialogInfomationLaboralSelectBinding
import com.example.flashfeso_lwj.flashfeso.event.InfomationLaboralSelectItemOnclickListener
import com.example.flashfeso_lwj.flashfeso.ui.controll.adapter.RvAdapterLaboralSelect

class InfomationLaboralSelectDialog private constructor(): DialogFragment(){
    var isSetAdapter: Boolean = false

    private var _binding: DialogInfomationLaboralSelectBinding? = null
    val binding: DialogInfomationLaboralSelectBinding get() = _binding!!

    companion object{
        fun newInstance(): InfomationLaboralSelectDialog = InfomationLaboralSelectDialog()
    }
    private lateinit var itemListener: InfomationLaboralSelectItemOnclickListener
    private lateinit var dataOnRv: List<String>
    private var flag: Int = 0

    fun addSetting(flag: Int,data: List<String>, listener: InfomationLaboralSelectItemOnclickListener) =
        this.setFlag(flag).setData(data).setListener(listener)

    private fun InfomationLaboralSelectDialog.setFlag(dialogFlag: Int): InfomationLaboralSelectDialog =
        this.apply {  flag = dialogFlag}


    private fun InfomationLaboralSelectDialog.setListener(listener: InfomationLaboralSelectItemOnclickListener): InfomationLaboralSelectDialog =
        this.apply {  itemListener = listener}


    private fun InfomationLaboralSelectDialog.setData(data: List<String>): InfomationLaboralSelectDialog =
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