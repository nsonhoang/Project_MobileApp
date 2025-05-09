package com.example.projecttest.screen.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.projecttest.data.TargetCourse
import com.example.projecttest.databinding.LayoutItemBinding



class RvAdapter(var ds: List<TargetCourse>, val onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<RvAdapter.KhoaTapViewholder>() {

    lateinit var binding: LayoutItemBinding

    open inner class KhoaTapViewholder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KhoaTapViewholder {
        binding = LayoutItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val view = binding.root
        return KhoaTapViewholder(view)
    }

    override fun onBindViewHolder(holder: KhoaTapViewholder, position: Int) {

        holder.itemView.apply {
            binding.txtDetail.text=ds[position].courses.size.toString() + " Bài tập"
            binding.txtCourse.text=ds[position].name

            val imageUrl = ds[position].img

            Glide.with(this)
                .load(imageUrl)
                .into(binding.imgCourse)

            // lắng nghe item click chọn
            holder.itemView.setOnClickListener{
                onItemClickListener.onItemClick(position)
            }
//            val isSmallScreen = resources.configuration.screenWidthDp < 360
//            binding.imgCourse.visibility = if (isSmallScreen) View.GONE else View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return ds.size
    }

}
