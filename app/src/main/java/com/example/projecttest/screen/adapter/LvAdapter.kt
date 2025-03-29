package com.example.projecttest.screen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projecttest.data.OutData
import com.example.projecttest.databinding.ListviewItemBinding

class LvAdapter(var ds:List<OutData>,val onItemClick: OnItemClickListener):RecyclerView.Adapter<LvAdapter.ListViewHolder>() {

    private lateinit var binding: ListviewItemBinding
    open inner class ListViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    override fun getItemCount(): Int {
        return ds.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ListViewHolder {
       binding = ListviewItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ListViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: LvAdapter.ListViewHolder, position: Int) {
        holder.itemView.apply {
            binding.txtEx.text=ds[position].detail
            binding.txtFoodName.text=ds[position].name
            binding.imgFood.setImageResource(ds[position].img)

            holder.itemView.setOnClickListener {
                onItemClick.onItemClick(position)
            }
        }
    }
}