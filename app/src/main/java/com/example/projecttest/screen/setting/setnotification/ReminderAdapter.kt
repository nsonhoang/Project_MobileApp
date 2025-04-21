package com.example.projecttest.screen.setting.setnotification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.projecttest.databinding.ItemReminderBinding

class ReminderAdapter(
    private val onToggle: (WorkoutReminder, Boolean) -> Unit,
    private val onDelete: (WorkoutReminder) -> Unit
) : ListAdapter<WorkoutReminder, ReminderAdapter.ReminderViewHolder>(DiffCallback()) {

    inner class ReminderViewHolder(val binding: ItemReminderBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val binding = ItemReminderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReminderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        val reminder = getItem(position)
        with(holder.binding) {
            timeText.text = String.format("%02d:%02d", reminder.hour, reminder.minute)
            switchEnable.isChecked = reminder.enabled

            switchEnable.setOnCheckedChangeListener { _, isChecked -> onToggle(reminder, isChecked) }
            deleteButton.setOnClickListener { onDelete(reminder) }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<WorkoutReminder>() {
        override fun areItemsTheSame(oldItem: WorkoutReminder, newItem: WorkoutReminder) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: WorkoutReminder, newItem: WorkoutReminder) = oldItem == newItem
    }
}
