package com.example.projecttest.screen.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projecttest.data.OutData
import com.example.projecttest.databinding.LayoutItem2Binding

class RvAdapter2(var ds: List<OutData>) : RecyclerView.Adapter<RvAdapter2.KhoaTapViewholder2>() {

    lateinit var binding: LayoutItem2Binding

    inner class KhoaTapViewholder2(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KhoaTapViewholder2 {
        binding = LayoutItem2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        val view = binding.root
        return KhoaTapViewholder2(view)
    }

    override fun onBindViewHolder(holder: KhoaTapViewholder2, position: Int) {
        holder.itemView.apply {
            binding.txtDetail.text=ds[position].detail
            binding.txtCourse.text=ds[position].name
            binding.imgCourse.setImageResource(ds[position].img)
        }
    }

    override fun getItemCount(): Int {
        return ds.size
    }
}