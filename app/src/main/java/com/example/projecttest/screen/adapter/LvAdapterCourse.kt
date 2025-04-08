package com.example.projecttest.screen.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.example.projecttest.data.OutData
import com.example.projecttest.data.TrainingProgram
import com.example.projecttest.databinding.LayoutItemCourseBinding

class LvAdapterCourse(var ds: List<OutData>, val onItemClickListener: OnItemClickListener):RecyclerView.Adapter<LvAdapterCourse.CourseViewHolder >() {

    private lateinit var binding: LayoutItemCourseBinding
    inner class CourseViewHolder(view: View):RecyclerView.ViewHolder(view)

    override fun getItemCount(): Int {
     return ds.size
    }
    override fun onBindViewHolder(holder: LvAdapterCourse.CourseViewHolder, position: Int) {
        holder.itemView.apply {
            binding.txtDetailCourse.text=ds[position].detail
            binding.txtCourseName.text=ds[position].name
            binding.imgCourse.setImageResource(ds[position].img)
            binding.cardBackgroundCourse.setCardBackgroundColor( getBackgroundColor(position))

            //lắng nghe sự kiên click
            holder.itemView.setOnClickListener{
                onItemClickListener.onItemClick(position)
            }
        }
    }
    private fun getBackgroundColor(position: Int): Int {
        val colors = arrayOf(
            "#FF8343",
            "#647FFC",
            "#3F62A3"
        ) // Store colors in an array
        val colorIndex = position % colors.size // Use modulo for a repeating pattern
        return Color.parseColor(colors[colorIndex])
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LvAdapterCourse.CourseViewHolder{
        binding= LayoutItemCourseBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CourseViewHolder(binding.root)
    }
}