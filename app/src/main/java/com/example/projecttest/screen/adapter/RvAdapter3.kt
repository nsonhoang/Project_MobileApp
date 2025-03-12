package com.example.projecttest.screen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projecttest.data.OutData
import com.example.projecttest.databinding.LayoutItem3Binding

class RvAdapter3(var ds: List<OutData>): RecyclerView.Adapter<RvAdapter3.CourseViewholder>() {
    lateinit var  binding: LayoutItem3Binding

    inner class CourseViewholder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewholder {
        binding = LayoutItem3Binding.inflate(LayoutInflater.from(parent.context),parent,false)
        val view = binding.root
        return CourseViewholder(view)
    }

    override fun onBindViewHolder(holder: CourseViewholder, position: Int) {
        holder.itemView.apply {
            binding.imgCourse.setImageResource(ds[position].imgCourse)
            binding.txtCourse.text=ds[position].nameCourse
        }
    }

    override fun getItemCount(): Int {
        return ds.size
    }
}