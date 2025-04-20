package com.example.projecttest.screen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.projecttest.databinding.ItemWorkoutProgramBinding
import com.example.projecttest.data.WorkoutProgram

class WorkoutProgramAdapter(
    private var programs: List<WorkoutProgram>,
    private val onItemClick: (WorkoutProgram) -> Unit
) : RecyclerView.Adapter<WorkoutProgramAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemWorkoutProgramBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(program: WorkoutProgram) {
            binding.tvTitle.text = program.tenbaitap
            binding.progressBar.progress = program.tiendo
            binding.tvProgress.text = "${program.tiendo}% Đã hoàn thành"
            binding.ivThumbnail.setImageResource(program.imageResId)

            // Thêm contentDescription cho hình ảnh
            binding.ivThumbnail.contentDescription = "Hình ảnh bài tập: ${program.tenbaitap}"

            // Gọi hàm onItemClick khi người dùng nhấn vào item
            binding.root.setOnClickListener {
                onItemClick(program)  // Gọi onItemClick và truyền program vào
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemWorkoutProgramBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(programs[position])
    }

    override fun getItemCount() = programs.size

    // Cập nhật danh sách bằng DiffUtil (hiệu suất tốt hơn)
    fun updateData(newPrograms: List<WorkoutProgram>) {
        val diffCallback = object : DiffUtil.Callback() {
            override fun getOldListSize() = programs.size
            override fun getNewListSize() = newPrograms.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return programs.getOrNull(oldItemPosition)?.imageResId == newPrograms.getOrNull(newItemPosition)?.imageResId
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return programs.getOrNull(oldItemPosition) == newPrograms.getOrNull(newItemPosition)
            }
        }

        val diffResult = DiffUtil.calculateDiff(diffCallback)
        programs = newPrograms
        diffResult.dispatchUpdatesTo(this)
    }
}