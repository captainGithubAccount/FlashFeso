package com.example.flashfeso_lwj.flashfeso.ui.controll.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.example.flashfeso_lwj.R
import com.example.flashfeso_lwj.databinding.LayoutComentariosItemBinding
import com.example.flashfeso_lwj.flashfeso.entity.CustomerFeedListDetailEntity
import com.example.lwj_common.common.ui.controll.tools.ktx.isUseful
import com.example.lwj_common.common.ui.controll.tools.utils.DateUtil
import com.jude.easyrecyclerview.adapter.BaseViewHolder
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class ERvComentariosListAdapter @Inject constructor(
    @ActivityContext private val _context: Context
): RecyclerArrayAdapter<CustomerFeedListDetailEntity>(_context) {
    override fun OnCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder<*> {
        val binding = LayoutComentariosItemBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
        val holder = VH(binding)

        binding.comentariosItemQuestionLl.setOnClickListener {
            val bindItemData = holder.itemView.getTag(R.id.tag_item_comentarios) as CustomerFeedListDetailEntity
            if(bindItemData.isOpen) {
                bindItemData.isOpen = false
                binding.comentariosItemQuestionAnswer.setVisibility(View.GONE)
                binding.comentariosItemQuestionTime.setVisibility(View.GONE)
                binding.comentariosItemQuestionImg.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_arrow_right))

            } else {
                bindItemData.isOpen = true
                binding.comentariosItemQuestionAnswer.setVisibility(View.VISIBLE)
                binding.comentariosItemQuestionTime.setVisibility(View.VISIBLE)
                binding.comentariosItemQuestionImg.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_arrow_down))
            }
        }
        return holder
    }

    inner class VH(val binding: LayoutComentariosItemBinding): BaseViewHolder<CustomerFeedListDetailEntity>(binding.root) {

        override fun setData(data: CustomerFeedListDetailEntity?) { //类似与我自己的bind方法 fun bind(data: CustomerFeedListDetailEntity?){...}
            super.setData(data)
            if(data?.leaveContent.isUseful()) {
                binding.comentariosItemQuestion.text = data?.leaveContent
            } else {
                binding.comentariosItemQuestion.text = ""
            }

            if(data?.replyContent.isUseful()) {
                binding.comentariosItemQuestionAnswer.text = data?.replyContent
            } else {
                binding.comentariosItemQuestionAnswer.text = ""
            }

            if(data?.createTime.isUseful()) {
                val timeText = DateUtil.getTimeMillisForString(data?.createTime)
                binding.comentariosItemQuestionTime.text = timeText
            } else {
                binding.comentariosItemQuestionTime.text = ""
            }

            if(data?.isOpen == true) {
                binding.comentariosItemQuestionAnswer.visibility = View.VISIBLE
                binding.comentariosItemQuestionTime.visibility = View.VISIBLE
                binding.comentariosItemQuestionImg.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_arrow_down))
            } else {
                binding.comentariosItemQuestionAnswer.visibility = View.GONE
                binding.comentariosItemQuestionTime.visibility = View.GONE
                binding.comentariosItemQuestionImg.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_arrow_right))
            }
        }
    }



    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int, payloads: MutableList<Any>) {
        val i = position - headers.size - mObjects.size
        /*if(footers.size != 0 && i >= 0) {
            *//*val bindItemData = footers.get(i)
            holder.itemView.setTag(R.id.tag_item_comentarios, bindItemData)*//*

            val bindItemData = mObjects[i]
            holder.itemView.setTag(R.id.tag_item_comentarios, bindItemData)
        }*/

        val bindItemData = mObjects[position]
        holder.itemView.setTag(R.id.tag_item_comentarios, bindItemData)


        super.onBindViewHolder(holder, position, payloads)
    }


}


