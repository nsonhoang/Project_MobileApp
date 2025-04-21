package com.example.projecttest.screen.setting

import android.app.TimePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projecttest.databinding.SettingNotificationBinding
import com.example.projecttest.screen.setting.setnotification.AppDatabase
import com.example.projecttest.screen.setting.setnotification.ReminderAdapter
import com.example.projecttest.screen.setting.setnotification.ReminderDao
import com.example.projecttest.screen.setting.setnotification.WorkoutReminder
import com.example.projecttest.screen.setting.setnotification.scheduleReminder
import com.example.projecttest.screen.setting.setnotification.checkAndRequestNotificationPermission
import kotlinx.coroutines.launch
import java.util.Calendar

class notification : AppCompatActivity() {
    private lateinit var binding: SettingNotificationBinding
    private lateinit var db: AppDatabase
    private lateinit var reminderDao: ReminderDao
    private lateinit var adapter: ReminderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SettingNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(this)
        reminderDao = db.reminderDao()

        reminderDao.getAllReminders().observe(this) { list ->
            adapter.submitList(list)
        }


        adapter = ReminderAdapter(
            onToggle = { reminder, enabled ->
                lifecycleScope.launch {
                    reminderDao.update(reminder.copy(enabled = enabled))
                    if (enabled) {
                        scheduleReminder(this@notification, reminder.copy(enabled = true))
                    }
                }
            },
            onDelete = { reminder ->
                lifecycleScope.launch {
                    reminderDao.delete(reminder)
                }
            }
        )

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter
        reminderDao.getAllReminders().observe(this) { adapter.submitList(it) }

        binding.btnBack.setOnClickListener { finish() }
        binding.imageButton3.setOnClickListener {
            checkAndRequestNotificationPermission(this) {
                showTimePicker()
            }
        }
    }

    private fun showTimePicker() {
        val now = Calendar.getInstance()
        TimePickerDialog(this, { _, hour, minute ->
            val reminder = WorkoutReminder(hour = hour, minute = minute, enabled = true)
            lifecycleScope.launch {
                val id = reminderDao.insert(reminder).toInt()
                scheduleReminder(this@notification, reminder.copy(id = id))
            }
        }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true).show()
    }
}
