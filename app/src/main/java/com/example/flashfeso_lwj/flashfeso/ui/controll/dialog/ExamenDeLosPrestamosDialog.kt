package com.example.flashfeso_lwj.flashfeso.ui.controll.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.flashfeso_lwj.R
import com.example.flashfeso_lwj.base.event.CommonDialogEvent
import com.example.flashfeso_lwj.databinding.DialogExamenDeLosPrestamosBinding

class ExamenDeLosPrestamosDialog private constructor(): DialogFragment() {

     var listener: CommonDialogEvent? = null
    private var _binding: DialogExamenDeLosPrestamosBinding? = null
    val binding: DialogExamenDeLosPrestamosBinding get() = _binding!!

    companion object {
        fun newInstance(importe: String, fecha: String): ExamenDeLosPrestamosDialog {
            val instance = ExamenDeLosPrestamosDialog()
            val args = Bundle()
            args.putString("importe", importe)
            args.putString("fecha", fecha)
            instance.arguments = args
            return instance
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        _binding = DialogExamenDeLosPrestamosBinding.inflate(inflater, container, false)
        afterBindView()
        return binding.root
    }

    private fun afterBindView() {
        binding.btnConfirm.setOnClickListener {
            listener?.onConfirm()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, 0)
        isCancelable = false
    }

}