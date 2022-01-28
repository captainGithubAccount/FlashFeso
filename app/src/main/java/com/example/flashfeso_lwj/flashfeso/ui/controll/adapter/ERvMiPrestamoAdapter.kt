package com.example.flashfeso_lwj.flashfeso.ui.controll.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.flashfeso_lwj.R
import com.example.flashfeso_lwj.databinding.LayoutMiPrestamoItemBinding
import com.example.flashfeso_lwj.flashfeso.entity.OrderHistoryDataEntity
import com.example.flashfeso_lwj.flashfeso.utils.Constants
import com.example.lwj_common.common.ui.controll.tools.ktx.isUseful
import com.example.lwj_common.common.ui.controll.tools.utils.DoubleUtils
import com.example.lwj_common.common.ui.controll.tools.utils.NumberUtils
import com.jude.easyrecyclerview.adapter.BaseViewHolder
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

class ERvMiPrestamoAdapter @Inject constructor(
    @ActivityContext private val _context: Context
): RecyclerArrayAdapter<OrderHistoryDataEntity>(_context) {
    override fun OnCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder<OrderHistoryDataEntity> {
        val binding = LayoutMiPrestamoItemBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
        val viewholder = VH(binding)
        val data = viewholder.itemView.getTag(R.id.tag_item_orderHistoryData) as OrderHistoryDataEntity
        return viewholder
    }

    inner class VH(val binding: LayoutMiPrestamoItemBinding): BaseViewHolder<OrderHistoryDataEntity>(binding.root) {
        override fun setData(data: OrderHistoryDataEntity?) {
            super.setData(data)

            if(data?.orderStatus.isUseful()) {
                when(data?.orderStatus) {

                    "1" -> {
                        binding.orderStatusTv.text = context.resources.getString(R.string.el_prestamo_fallo)
                        binding.orderStatusTv.setTextColor(ContextCompat.getColor(itemView.context, R.color.color_D81E06))
                        binding.orderStatusImg.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.icon_fail))
                    }
                    "4" -> {
                        binding.orderStatusTv.text = context.resources.getString(R.string.reembolso_exitoso)
                        binding.orderStatusTv.setTextColor(ContextCompat.getColor(itemView.context, R.color.color_00DE00))
                        binding.orderStatusImg.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.icon_green_gou))
                    }
                    "5" -> {
                        binding.orderStatusTv.text = context.resources.getString(R.string.reembolso_atrasado)
                        binding.orderStatusTv.setTextColor(ContextCompat.getColor(itemView.context, R.color.color_02A7F0))
                        binding.orderStatusImg.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.icon_blue_gou))
                    }
                    else -> {
                        binding.orderStatusTv.text = context.resources.getString(R.string.sin_resolver)
                        binding.orderStatusTv.setTextColor(ContextCompat.getColor(itemView.context, R.color.color_02A7F0))
                        binding.orderStatusImg.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.icon_blue_gou))
                    }
                }
            }

            if(data?.applyNumber.isUseful()) {
                binding.numeroTv.text = data?.applyNumber
            }else{
                binding.numeroTv.text = Constants.EMPTY_STRING
            }

            if(data?.loanMoney.isUseful()){
                binding.montoTv.text = "$".plus(NumberUtils.goToZeroString(DoubleUtils.divToString(data?.loanMoney, "100", 2)))
            }else{
                binding.montoTv.text = Constants.EMPTY_STRING
            }

            if(data?.loanTerm.isUseful()){
                binding.plazoTv.text = data?.loanTerm.toString().plus(" ").plus(context.resources.getString(R.string.días))
            }else{
                binding.plazoTv.text = Constants.EMPTY_STRING
            }

        }

    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int, payloads: MutableList<Any>) {
        val data = mObjects[position]
        holder.itemView.setTag(R.id.tag_item_orderHistoryData, data)
        super.onBindViewHolder(holder, position, payloads)
    }


}