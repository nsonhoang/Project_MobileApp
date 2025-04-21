package com.example.projecttest.screen.setting.setnotification


import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.projecttest.data.UserEntity

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

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("UPDATE user SET isLoggedIn = 0")
    suspend fun logoutAllUsers()

    @Query("SELECT * FROM user WHERE isLoggedIn = 1 LIMIT 1")
    suspend fun getLoggedInUser(): UserEntity?
}

