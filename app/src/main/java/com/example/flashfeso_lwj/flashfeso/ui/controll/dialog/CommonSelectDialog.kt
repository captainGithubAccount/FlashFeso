package com.example.flashfeso_lwj.flashfeso.ui.controll.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.flashfeso_lwj.base.entity.DataResult
import com.example.flashfeso_lwj.databinding.DialogCommonSelectBinding
import com.example.flashfeso_lwj.databinding.DialogInfomationLaboralSelectBinding
import com.example.flashfeso_lwj.flashfeso.event.InfomationSelectItemOnClickListener
import com.example.flashfeso_lwj.flashfeso.ui.controll.adapter.RvAdapterLaboralSelect

/*
* 认证五
* */
class CommonSelectDialog : DialogFragment() {
    private var _binding: DialogCommonSelectBinding? = null
    val binding: DialogCommonSelectBinding get() = _binding!!

    fun <VH : RecyclerView.ViewHolder> setAdapter(adpter: RecyclerView.Adapter<VH>) {
        binding.rvLaboralSelectContent.adapter = adpter
    }

    fun <T> setData(data: List<T>) {

    }

    fun <R> setDataResult(dataResult: LiveData<DataResult<R>>) {

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
        setStyle(DialogFragment.STYLE_NO_TITLE, 0)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = DialogCommonSelectBinding.inflate(inflater, container, false)
        afterBindView()
        return binding.root
    }

    private fun afterBindView() {

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        afterInitView()
    }

    private fun afterInitView() {


    }


}