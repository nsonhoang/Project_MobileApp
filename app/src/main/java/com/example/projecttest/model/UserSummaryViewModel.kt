package com.example.projecttest.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.example.projecttest.data.UserSummary

class UserSummaryViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    private val _userSummary = MutableLiveData<UserSummary?>()
    val userSummary: LiveData<UserSummary?> = _userSummary

    fun fetchUserSummary(userId: String) {
        db.collection("User")
            .document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val summary = UserSummary(
                        kcalCount = document.getLong("kcalCount")?.toInt() ?: 0,
                        timeTraining = document.getString("timeTraining") ?: "",
                        trainingCount = document.getLong("trainingCount")?.toInt() ?: 0
                    )
                    _userSummary.value = summary
                }
            }
            .addOnFailureListener {
                // Xử lý lỗi nếu cần
            }
    }
}
