package com.example.projecttest.screen.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.projecttest.databinding.ItemWorkoutProgramBinding
import com.example.projecttest.Data.WorkoutProgram

class WorkoutProgramAdapter(
    private var programs: List<WorkoutProgram>
) : RecyclerView.Adapter<WorkoutProgramAdapter.WorkoutViewHolder>() {

    inner class WorkoutViewHolder(val binding: ItemWorkoutProgramBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(program: WorkoutProgram) {
            binding.tvTitle.text = program.tenbaitap
            binding.progressBar.progress = program.tiendo
            binding.tvProgress.text = "${program.tiendo}% Đã hoàn thành"
            binding.ivThumbnail.setImageResource(program.imageResId)

            // Thêm contentDescription cho hình ảnh
            binding.ivThumbnail.contentDescription = "Hình ảnh bài tập: ${program.tenbaitap}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val binding = ItemWorkoutProgramBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WorkoutViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        if (position in programs.indices) { // Kiểm tra tránh lỗi IndexOutOfBounds
            holder.bind(programs[position])
        }
    }

    override fun getItemCount() = programs.size

    // Cập nhật danh sách bằng DiffUtil (hiệu suất tốt hơn)
    fun updateData(newPrograms: List<WorkoutProgram>) {
        val diffCallback = object : DiffUtil.Callback() {
            override fun getOldListSize() = programs.size
            override fun getNewListSize() = newPrograms.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return programs[oldItemPosition].imageResId == newPrograms[newItemPosition].imageResId
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return programs[oldItemPosition] == newPrograms[newItemPosition]
            }
        }

        val diffResult = DiffUtil.calculateDiff(diffCallback)
        programs = newPrograms
        diffResult.dispatchUpdatesTo(this)
    }
}
