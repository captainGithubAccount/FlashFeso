package com.example.flashfeso_lwj.flashfeso.ui.controll.dialog

import com.example.flashfeso_lwj.base.event.CommonDialogEvent
import com.example.flashfeso_lwj.databinding.DialogJumpSysytemLocBinding

class JumpSysytemLocDialog(): CommonDialogFragment<DialogJumpSysytemLocBinding>() {
    var listener: CommonDialogEvent? = null

    override fun DialogJumpSysytemLocBinding.initView() {

    }

    override fun afterBindingView() {
        super.afterBindingView()
        binding.btnCancel.setOnClickListener {
            listener?.onCancel()
        }
        binding.btnConfirm.setOnClickListener {
            listener?.onConfirm()
        }
    }



    override fun isCancel(): Boolean = false

}