package com.example.flashfeso_lwj.flashfeso.ui.controll.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.flashfeso_lwj.R
import com.example.flashfeso_lwj.databinding.LayoutPreguntasItemBinding
import com.example.flashfeso_lwj.flashfeso.entity.ComPsEntity
import com.example.lwj_common.common.ui.controll.tools.ktx.isUseful
import com.jude.easyrecyclerview.adapter.BaseViewHolder
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter

class ERvPreguntasAdapter(_context: Context): RecyclerArrayAdapter<ComPsEntity>(_context) {
    override fun OnCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder<ComPsEntity> {
        val binding = LayoutPreguntasItemBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
        val viewHolder = VH(binding)

//        val data = viewHolder.itemView.getTag(R.id.tag_item_preguntas) as ComPsEntity
        //注意: 这行放外面会导致空类型强转错误
        binding.questionLl.setOnClickListener {
            val data = viewHolder.itemView.getTag(R.id.tag_item_preguntas) as ComPsEntity
            if(data.isOpen) {
                data.isOpen = false
                binding.questionAnswerLl.setVisibility(View.GONE)
                binding.questionImg.setImageDrawable(context.resources.getDrawable(R.drawable.icon_arrow_right))
            } else {
                data.isOpen = true
                binding.questionAnswerLl.setVisibility(View.VISIBLE)
                binding.questionImg.setImageDrawable(context.resources.getDrawable(R.drawable.icon_arrow_down))
            }
        }

        return viewHolder
    }


    inner class VH(val binding: LayoutPreguntasItemBinding): BaseViewHolder<ComPsEntity>(binding.root) {


        override fun setData(data: ComPsEntity?) {
            if(data?.question.isUseful()) {
                binding.question.text = data?.question
            } else {
                binding.question.text = ""
            }

            if(data?.answer.isUseful()) {
                binding.questionAnswer.text = data?.answer?.replace("\\n", "\n")
            } else {
                binding.questionAnswer.text = ""
            }

            if(data?.isOpen == true) {
                binding.questionAnswerLl.visibility = View.VISIBLE
                binding.questionImg.setImageDrawable(getContext().getResources().getDrawable(R.drawable.icon_arrow_down))
            } else {
                binding.questionAnswerLl.setVisibility(View.GONE)
                binding.questionImg.setImageDrawable(context.resources.getDrawable(R.drawable.icon_arrow_right))
            }

        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int, payloads: MutableList<Any>) {
        val data = mObjects[position]
        holder.itemView.setTag(R.id.tag_item_preguntas, data)
        super.onBindViewHolder(holder, position, payloads)
    }

}