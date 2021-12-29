
package com.example.flashfeso_lwj.common.ui.controll.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.flashfeso_lwj.base.event.CommonDialogEvent
import com.example.lwj_common.databinding.DialogCommonBinding
import javax.inject.Inject

class CommonDialog constructor(
    private val title: String,
): DialogFragment() {
    private lateinit var _binding: DialogCommonBinding
    val binding get() = _binding
    lateinit var mCommonDialogEvent: CommonDialogEvent

    private fun afterInitView() {
        binding.tvTitle.text = title

        binding.tvCancel.setOnClickListener {
            mCommonDialogEvent.cancelListener()
        }

        binding.tvConfirm.setOnClickListener {
            mCommonDialogEvent.confirmListener()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
        setStyle(STYLE_NO_TITLE, 0)
        beforeInitView()

    }

    private fun beforeInitView() {
        dialog?.let{
            it.setOnCancelListener{
                mCommonDialogEvent.cancelListener()
            }
        }
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
