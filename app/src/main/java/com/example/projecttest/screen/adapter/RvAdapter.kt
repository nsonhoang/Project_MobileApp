package com.example.projecttest.screen.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projecttest.data.OutData
import com.example.projecttest.databinding.LayoutItemBinding


class RvAdapter( var ds: List<OutData>) : RecyclerView.Adapter<RvAdapter.KhoaTapViewholder>() {

    lateinit var binding: LayoutItemBinding

    open inner class KhoaTapViewholder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KhoaTapViewholder {
        binding = LayoutItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val view = binding.root
        return KhoaTapViewholder(view)
    }

    override fun onBindViewHolder(holder: KhoaTapViewholder, position: Int) {
        holder.itemView.apply {
            binding.txtDetail.text=ds[position].detailCourse
            binding.txtCourse.text=ds[position].nameCourse
            binding.imgCourse.setImageResource(ds[position].imgCourse)
        }
    }

    override fun getItemCount(): Int {
        return ds.size
    }

}