package com.example.flashfeso_lwj.flashfeso.ui.controll.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flashfeso_lwj.R
import com.example.flashfeso_lwj.databinding.ItemLaboralSelectBinding
import com.example.flashfeso_lwj.flashfeso.event.InfomationLaboralSelectItemOnclickListener

class RvAdapterLaboralSelect(
    private val flag: Int,
    private val data: List<String>,
    private val listener: InfomationLaboralSelectItemOnclickListener
) : RecyclerView.Adapter<RvAdapterLaboralSelect.VH>() {
    inner class VH(val binding: ItemLaboralSelectBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.flLaboralItemContainer.setOnClickListener {
                val list =this.itemView.getTag(R.id.tag_item_laboral) as List<String>
                listener.onDialogItemClick(list, flag)
            }
        }

        fun bind(string: String){
            binding.tvLaboralItemContent.text = string
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemLaboralSelectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val str: String = data[position]
        val list: List<String> = listOf(str, (position+1).toString())
        holder.itemView.setTag(R.id.tag_item_laboral, list)
        holder.bind(str)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}