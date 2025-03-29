package com.example.projecttest.screen.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projecttest.OutData
import com.example.projecttest.databinding.LayoutRecyclerItemBinding

lateinit var binding: LayoutRecyclerItemBinding

class RvAdapter(var ds: List<OutData>) : RecyclerView.Adapter<RvAdapter.KhoaTapViewholder>() {

    inner class KhoaTapViewholder(itemView: View): RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KhoaTapViewholder {
        binding = LayoutRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val view = binding.root
        return KhoaTapViewholder(view)
    }

    override fun onBindViewHolder(holder: KhoaTapViewholder, position: Int) {
        holder.itemView.apply {
            binding.txtMota.text=ds[position].mieuta
            binding.txtKhoaHoc.text=ds[position].tenkhoa
            binding.imgMota.setImageResource(ds[position].img)
        }
    }

    override fun getItemCount(): Int {
        return ds.size
    }
}