package com.example.flashfeso_lwj.flashfeso.ui.controll.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flashfeso_lwj.R
import com.example.flashfeso_lwj.databinding.ItemTakePhotoSelectBinding
import com.example.flashfeso_lwj.flashfeso.event.InfomationSelectItemOnClickListener

class RvAdapterTakePhotoSelect(
    private val data: List<String>,
    private val listener: InfomationSelectItemOnClickListener,
    private val flag: Int = 0
): RecyclerView.Adapter<RvAdapterTakePhotoSelect.VH>() {

    inner class VH(val binding: ItemTakePhotoSelectBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.tvTakePhoto.setOnClickListener {
                val list = this.itemView.getTag(R.id.tag_item_take_photo) as List<String>
                listener.onDialogItemClick(list, flag)
            }
        }

        fun bind(str: String){
            binding.tvTakePhoto.text = str
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding: ItemTakePhotoSelectBinding = ItemTakePhotoSelectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val string: String = data[position]
        val list: List<String> = listOf(string, position.toString())
        holder.itemView.setTag(R.id.tag_item_take_photo, list)
        holder.bind(string)
    }

    override fun getItemCount(): Int = data.size
}