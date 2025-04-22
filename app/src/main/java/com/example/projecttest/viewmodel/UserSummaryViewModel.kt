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
    private val auth = FirebaseAuth.getInstance()

    private val _userSummary = MutableLiveData<UserSummary?>()
    val userSummary: LiveData<UserSummary?> = _userSummary

    fun fetchUserSummary() {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            Log.e("UserSummaryViewModel", "User not logged in")
            return
        }

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

    fun updateUserSummary(timeTraining: Long, kcalCount: Int, trainingCount: Int) {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            Log.e("UserSummaryViewModel", "User not logged in")
            return
        }

        val userRef = db.collection("User")
            .document(userId)
            .collection("infoTraining")
            .document("1")

        // Kiểm tra timeTraining đã là phút chưa. Nếu là giây, thì cần chia lại.
        val timeMinutes = if (timeTraining > 60000) { // Nếu timeTraining tính bằng giây
            timeTraining / 60 // Chuyển sang phút
        } else {
            timeTraining // Nếu timeTraining đã là phút, không cần chia
        }

        val updatedData = mapOf(
            "timeTraining" to timeMinutes,
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
