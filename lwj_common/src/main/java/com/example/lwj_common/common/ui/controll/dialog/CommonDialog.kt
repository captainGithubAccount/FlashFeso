
package com.example.flashfeso_lwj.common.ui.controll.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.flashfeso_lwj.base.event.CommonDialogEvent
import com.example.lwj_common.databinding.DialogCommonBinding

/**
 * frgm作view
 * */
class CommonDialog constructor(
    private val title: String,
): DialogFragment() {
    private lateinit var _binding: DialogCommonBinding
    val binding get() = _binding
    lateinit var mCommonDialogEvent: CommonDialogEvent

    private fun afterInitView() {
        binding.tvTitle.text = title

        binding.tvCancel.setOnClickListener {
            mCommonDialogEvent.onCancel()

            //或者也可以取消35 - 41行注释dialog?.cancel()
        }

        binding.tvConfirm.setOnClickListener {
            mCommonDialogEvent.onConfirm()
        }

        /*
        dialog?.let{
            //当返回箭头和视图里面或点击对话框外面后取消对话框的逻辑是一样的话可以设置这个监听
            it.setOnCancelListener{
                mCommonDialogEvent.cancelListener()
                //Log.d("---", "dialog cancel")
            }
        }*/
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
        setStyle(STYLE_NO_TITLE, 0)
        beforeInitView()

    }

    private fun beforeInitView() {
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = DialogCommonBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        afterInitView()
    }



    /*override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(requireContext(), 0)
    }*/
}
