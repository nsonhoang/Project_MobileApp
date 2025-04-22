package com.example.projecttest.screen.training

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.projecttest.R
import com.example.projecttest.model.UserSummaryViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlin.random.Random

class TongKet : AppCompatActivity() {

    private val viewModel: UserSummaryViewModel by viewModels()
    private lateinit var tvTimeTraining: TextView
    private lateinit var tvKcalCount: TextView
    private lateinit var tvCountTraining: TextView
    private lateinit var btnTongKet: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tongket)

        // Ánh xạ view
        tvTimeTraining = findViewById(R.id.tvTimeTraining)
        tvKcalCount = findViewById(R.id.tvKcalCount)
        tvCountTraining = findViewById(R.id.tvCountTraining)
        btnTongKet = findViewById(R.id.btnTongKet)

        // Lấy id người dùng từ Firebase Authentication
        val userId = FirebaseAuth.getInstance().currentUser!!.uid

        // Lấy startTime từ ReadyActivity
        val startTime = intent.getLongExtra("startTime", 0L)
        if (startTime == 0L) {
            Toast.makeText(this, "Không lấy được thời gian bắt đầu", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        val endTime = System.currentTimeMillis()
        val trainingDurationSeconds = (endTime - startTime) / 1000
        val kcalBurned = Random.Default.nextInt(50, 121)
        val trainingDurationMinutes = trainingDurationSeconds / 60

        // Gọi ViewModel để fetch dữ liệu
        viewModel.fetchUserSummary(userId)

        viewModel.userSummary.observe(this) { summary ->
            val currentTrainingCount = summary?.trainingCount ?: 0
            val newTrainingCount = currentTrainingCount + 1
            val currentKcalCount = summary?.kcalCount ?: 0
            val newKcalCount = currentKcalCount + kcalBurned
            val currenTimeTraining = summary?.timeTraining ?: 0
            val newTimeTraining = currenTimeTraining + trainingDurationMinutes


            // Hiển thị lên UI
            tvTimeTraining.text = "Thời gian tập \n$trainingDurationMinutes phút"
            tvKcalCount.text = "Kcal \n$kcalBurned"
            tvCountTraining.text = "Số Lần Tập \n$newTrainingCount"

            // Cập nhật dữ liệu mới lên Firestore thông qua ViewModel
            viewModel.updateUserSummary(
                userId,
                newTimeTraining,
                newKcalCount,
                newTrainingCount
            )
        }

        // Nút hoàn thành
        btnTongKet.setOnClickListener {
            finish()
        }
    }
}