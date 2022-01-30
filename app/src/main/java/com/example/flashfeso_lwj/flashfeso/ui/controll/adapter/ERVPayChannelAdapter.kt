package com.example.flashfeso_lwj.flashfeso.ui.controll.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.flashfeso_lwj.R
import com.example.flashfeso_lwj.databinding.LayoutComentariosItemBinding
import com.example.flashfeso_lwj.databinding.LayoutPayChannelListItemBinding
import com.example.flashfeso_lwj.flashfeso.entity.PayChannelEntity
import com.example.flashfeso_lwj.flashfeso.utils.Constants
import com.jude.easyrecyclerview.adapter.BaseViewHolder
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class ERVPayChannelAdapter @Inject constructor(@ActivityContext _context: Context): RecyclerArrayAdapter<PayChannelEntity>(_context) {
    override fun OnCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder<*> {
        val binding = LayoutPayChannelListItemBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
        val holder = VH(binding) //val data = holder.itemView.getTag(R.id.tag_item_paychannel) as PayChannelEntity
        return holder

    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int, payloads: MutableList<Any>) {
        val data = mObjects[position]
        holder.itemView.setTag(R.id.tag_item_paychannel, data)
        super.onBindViewHolder(holder, position, payloads)
    }

    inner class VH(val binding: LayoutPayChannelListItemBinding): BaseViewHolder<PayChannelEntity>(binding.root) {
        override fun setData(data: PayChannelEntity?) {
            super.setData(data)
            when(data?.payItem) {
                Constants.PAY_CHANNEL_STP -> binding.payChannelSimple.setImageDrawable(context.resources.getDrawable(R.drawable.icon_stp_logo))
                Constants.PAY_CHANNEL_OPENPAY -> binding.payChannelSimple.setImageDrawable(context.resources.getDrawable(R.drawable.icon_openpay_logo))
                Constants.PAY_CHANNEL_OXXO -> binding.payChannelSimple.setImageDrawable(context.resources.getDrawable(R.drawable.icon_oxxo_logo))
            }

            if(data?.isSelector!!) {
                binding.payChannelRl.setBackgroundDrawable(context.resources.getDrawable(R.drawable.bg_pay_channel_select))
                binding.payChannelImg.setVisibility(View.VISIBLE)
            } else {
                binding.payChannelRl.setBackgroundDrawable(context.resources.getDrawable(R.drawable.bg_pay_channel_un_select))
                binding.payChannelImg.setVisibility(View.GONE)
            }

        }
    }


}
