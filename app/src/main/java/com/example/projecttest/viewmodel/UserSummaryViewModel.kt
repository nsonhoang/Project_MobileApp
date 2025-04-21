package com.example.projecttest.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.example.projecttest.data.UserSummary

class UserSummaryViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    private val _userSummary = MutableLiveData<UserSummary?>()
    val userSummary: LiveData<UserSummary?> = _userSummary

    // Phương thức lấy dữ liệu từ Firestore
    fun fetchUserSummary(userId: String) {
        val userRef = db.collection("User")
            .document(userId)
            .collection("infoTraining")
            .document("1")

        userRef.get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val summary = UserSummary(
                        kcalCount = document.getLong("kcalCount")?.toInt() ?: 0,
                        timeTraining = document.getLong("timeTraining")?.toInt() ?: 0,
                        trainingCount = document.getLong("trainingCount")?.toInt() ?: 0
                    )
                    _userSummary.value = summary
                } else {
                    // Nếu document chưa tồn tại, tạo document mới mặc định
                    val defaultSummary = UserSummary(kcalCount = 0, timeTraining = 0, trainingCount = 0)
                    userRef.set(
                        mapOf(
                            "kcalCount" to defaultSummary.kcalCount,
                            "timeTraining" to defaultSummary.timeTraining,
                            "trainingCount" to defaultSummary.trainingCount
                        )
                    ).addOnSuccessListener {
                        _userSummary.value = defaultSummary
                    }.addOnFailureListener { exception ->
                        Log.e("UserSummaryViewModel", "Error creating new user summary", exception)
                        _userSummary.value = null
                    }
                }
            }
            .addOnFailureListener { exception ->
                _userSummary.value = null
                Log.e("UserSummaryViewModel", "Error fetching user summary", exception)
            }
    }


    // Phương thức cập nhật thông tin người dùng
    fun updateUserSummary(userId: String, timeTraining: Long, kcalCount: Int, trainingCount: Int) {
        val userRef = db.collection("User")
            .document("CmdNGAdOkVaFqdVHD9ISsbPCZHa2")
            .collection("infoTraining")
            .document("1")

        // Chuyển thời gian từ giây sang phút
        val timeInMinutes = (timeTraining / 60).toInt()

        val updatedData = mapOf(
            "timeTraining" to timeInMinutes,
            "kcalCount" to kcalCount,
            "trainingCount" to trainingCount
        )

        userRef.update(updatedData)
            .addOnSuccessListener {
                // Cập nhật thành công
            }
            .addOnFailureListener { exception ->
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
