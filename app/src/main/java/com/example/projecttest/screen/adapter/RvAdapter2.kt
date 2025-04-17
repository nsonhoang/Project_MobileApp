package com.example.projecttest.screen.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projecttest.data.Course
import com.example.projecttest.data.OutData
import com.example.projecttest.data.TrainingProgram
import com.example.projecttest.databinding.LayoutItem2Binding

class RvAdapter2(var ds: List<Course>, val onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<RvAdapter2.KhoaTapViewholder2>() {

    lateinit var binding: LayoutItem2Binding

    inner class KhoaTapViewholder2(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KhoaTapViewholder2 {
        binding = LayoutItem2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        val view = binding.root
        return KhoaTapViewholder2(view)
    }



    override fun onBindViewHolder(holder: KhoaTapViewholder2, position: Int) {
        holder.itemView.apply {
            binding.txtDetail.text=ds[position].totalTime.toString()+ " Phút + " + ds[position].level
            binding.txtCourse.text=ds[position].name

            val imageUrl = ds[position].img

            Glide.with(this)
                .load(imageUrl)
                .into(binding.imgCourse)

            // lắng nghe item click chọn
            holder.itemView.setOnClickListener{
                onItemClickListener.onItemClick(position)
            }

        }
    }

    override fun getItemCount(): Int {
        return ds.size
    }
}