package com.example.projecttest.screen.setting.setnotification


import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ReminderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reminder: WorkoutReminder): Long

    @Update
    suspend fun update(reminder: WorkoutReminder)

    @Delete
    suspend fun delete(reminder: WorkoutReminder)

    @Query("SELECT * FROM reminder_table")
    fun getAllReminders(): LiveData<List<WorkoutReminder>>
}

