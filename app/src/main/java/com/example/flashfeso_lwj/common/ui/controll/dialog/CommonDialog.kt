package com.example.flashfeso_lwj.common.ui.controll.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.flashfeso_lwj.databinding.DialogCommonBinding
import javax.inject.Inject

class CommonDialog @Inject constructor(): DialogFragment() {
    private lateinit var _binding: DialogCommonBinding
    val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogCommonBinding.inflate(layoutInflater, container, false)
        initDialog()
        return binding.root
    }

    private fun initDialog() {

    }
}