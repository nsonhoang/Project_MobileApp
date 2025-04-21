
package com.example.projecttest.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.example.projecttest.data.UserSummary
import com.google.firebase.auth.FirebaseAuth

class UserSummaryViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    private val _userSummary = MutableLiveData<UserSummary?>()
    val userSummary: LiveData<UserSummary?> = _userSummary

    fun fetchUserSummary(userId: String) {
        db.collection("User")
            .document(userId)
            .collection("infoTraining")
            .document("1")
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val summary = UserSummary(
                        kcalCount = document.getLong("kcalCount")?.toInt() ?: 0,
                        timeTraining = document.getLong("timeTraining")?.toInt() ?: 0,
                        trainingCount = document.getLong("trainingCount")?.toInt() ?: 0
                    )
                    _userSummary.value = summary
                } else {
                    _userSummary.value = null
                }
            }
            .addOnFailureListener { exception ->
                _userSummary.value = null
                Log.e("UserSummaryViewModel", "Error getting user summary", exception)
            }
    }

    fun updateUserSummary(userId: String, timeTraining: Long, kcalCount: Int, trainingCount: Int) {
        val userRef = db.collection("User")
            .document(userId)
            .collection("infoTraining")
            .document("1")

        val timeInSeconds = (timeTraining / 1000).toInt()
        val timeInMinutes = (timeInSeconds/ 60).toInt()

        val updatedData = mapOf(
            "timeTraining" to timeInMinutes,
            "kcalCount" to kcalCount,
            "trainingCount" to trainingCount
        )

        userRef.update(updatedData)
            .addOnSuccessListener {
                // Cập nhật thành công
            }
            .addOnFailureListener {
                // Nếu lỗi (ví dụ user chưa có document), tạo mới
                userRef.set(updatedData)
                    .addOnSuccessListener {
                        // Tạo mới thành công
                    }
                    .addOnFailureListener { exception ->
                        Log.e("UserSummaryViewModel", "Error updating user summary", exception)
                    }
            }
    }
}