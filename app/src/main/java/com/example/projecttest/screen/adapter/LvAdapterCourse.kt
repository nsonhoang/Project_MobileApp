package com.example.projecttest.screen.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projecttest.data.Course
import com.example.projecttest.data.OutData
import com.example.projecttest.data.TrainingProgram
import com.example.projecttest.databinding.LayoutItemCourseBinding

class LvAdapterCourse(var ds: List<Course>, val onItemClickListener: OnItemClickListener):RecyclerView.Adapter<LvAdapterCourse.CourseViewHolder >() {

    private lateinit var binding: LayoutItemCourseBinding
    inner class CourseViewHolder(view: View):RecyclerView.ViewHolder(view)

    override fun getItemCount(): Int {
     return ds.size
    }
    override fun onBindViewHolder(holder: LvAdapterCourse.CourseViewHolder, position: Int) {
        holder.itemView.apply {
            binding.txtDetailCourse.text=ds[position].detail
            binding.txtCourseName.text=ds[position].name
            binding.cardBackgroundCourse.setCardBackgroundColor( getBackgroundColor(ds[position].level))

            //lắng nghe sự kiên click
            val imageUrl = ds[position].img

            Glide.with(this)
                .load(imageUrl)
                .into(binding.imgCourse)

            holder.itemView.setOnClickListener{
                onItemClickListener.onItemClick(position)
            }
        }
    }
    private fun getBackgroundColor(level: String): Int {
        val colors = arrayOf(
            "#3F62A3",
             // Màu cho level 1
            "#647FFC", // Màu cho level 2
            "#FF8343",

        )

        return when (level) {
            "Người bắt đầu" -> Color.parseColor(colors[0]) // Màu cho level 1
            "Trung bình" -> Color.parseColor(colors[1]) // Màu cho level 2
            else -> Color.parseColor(colors[2]) // màu cho lecel 3
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LvAdapterCourse.CourseViewHolder{
        binding= LayoutItemCourseBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CourseViewHolder(binding.root)
    }
}