package com.example.flashfeso_lwj.flashfeso.ui.controll.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.example.lwj_base.common.base.GetBinding

abstract class CommonDialogFragment<T: ViewBinding>: DialogFragment(), GetBinding<T> {
    private var _binding: T? = null
    val binding: T get() = _binding!!

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
        _binding = getAtvOrFrgmBindingByReflex(inflater)
        afterBindingView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.initView()
        afterInitView()
    }

    abstract fun T.initView()

    private fun afterInitView() {

    }

    private fun afterBindingView() {

    }


}