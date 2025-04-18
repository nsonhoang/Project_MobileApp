package com.example.projecttest.screen

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.projecttest.R
import com.example.projecttest.model.UserSummaryViewModel

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

        // Giả sử bạn có userId (truyền từ màn trước hoặc lấy từ FirebaseAuth)
        val userId = "IRKX1O9ZDAhH2lrVwSHi" // <-- sửa lại theo user của bạn

        // Gọi ViewModel để fetch dữ liệu
        viewModel.fetchUserSummary(userId)

        // Quan sát dữ liệu từ ViewModel
        viewModel.userSummary.observe(this) { summary ->
            if (summary != null) {
                tvTimeTraining.text = "Thời gian tập: ${summary.timeTraining}"
                tvKcalCount.text = "Kcal: ${summary.kcalCount}"
                tvCountTraining.text = "Số buổi: ${summary.trainingCount}"
            }
        }

        // Xử lý nút hoàn thành
        btnTongKet.setOnClickListener {
            finish() // Quay về màn hình trước hoặc chuyển tiếp
        }
    }
}