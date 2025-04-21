package com.example.projecttest.screen.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projecttest.data.Course
import com.example.projecttest.data.CourseModule
import com.example.projecttest.data.OutData
import com.example.projecttest.databinding.LayoutItemDetailcourseBinding

class RvAdapterDetailCourse(var ds: List<CourseModule>, val onItemClickListener: OnItemClickListener):RecyclerView.Adapter<RvAdapterDetailCourse.DetailCourseViewHolder >() {

    private lateinit var binding: LayoutItemDetailcourseBinding
    inner class DetailCourseViewHolder(view: View):RecyclerView.ViewHolder(view)

    override fun getItemCount(): Int {
        return ds.size
    }
    override fun onBindViewHolder(holder: RvAdapterDetailCourse.DetailCourseViewHolder, position: Int) {
        holder.itemView.apply {
            binding.txtDetailCourse.text= "00:"+ ds[position].trainingTime.toString()
            binding.txtCourseName.text=ds[position].name


            val imageUrl = ds[position].img

            Glide.with(this)
                .load(imageUrl)
                .into(binding.imgCourse)
            binding.cardBackgroundCourse.setCardBackgroundColor(Color.WHITE)

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
    ): RvAdapterDetailCourse.DetailCourseViewHolder{
        binding= LayoutItemDetailcourseBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DetailCourseViewHolder(binding.root)
    }
}