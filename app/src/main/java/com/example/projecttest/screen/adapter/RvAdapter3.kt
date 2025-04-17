package com.example.projecttest.screen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projecttest.data.Course
import com.example.projecttest.data.OutData
import com.example.projecttest.data.TrainingProgram
import com.example.projecttest.databinding.LayoutItem3Binding

class RvAdapter3(var ds: List<Course>, val onItemClickListener: OnItemClickListener): RecyclerView.Adapter<RvAdapter3.CourseViewholder>() {
    lateinit var  binding: LayoutItem3Binding

    inner class CourseViewholder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewholder {
        binding = LayoutItem3Binding.inflate(LayoutInflater.from(parent.context),parent,false)
        val view = binding.root
        return CourseViewholder(view)
    }

    override fun onBindViewHolder(holder: CourseViewholder, position: Int) {
        holder.itemView.apply {
            binding.txtCourse.text=ds[position].name

            val imageUrl = ds[position].img

            Glide.with(this)
                .load(imageUrl)
                .into(binding.imgCourse)

            holder.itemView.setOnClickListener{
                onItemClickListener.onItemClick(position)
            }

        }
    }

    override fun getItemCount(): Int {
        return ds.size
    }
}