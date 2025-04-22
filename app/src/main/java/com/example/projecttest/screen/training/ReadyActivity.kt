package com.example.projecttest.screen.training

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.projecttest.R
import com.example.projecttest.data.CourseModule
import com.example.projecttest.screen.training.Training

class ReadyActivity : AppCompatActivity() {

    private lateinit var tvCountdownReady: TextView
    private lateinit var progressCountdownReady: ProgressBar
    private lateinit var tvExerciseName: TextView
    private lateinit var imgExerciseReady: ImageView
    private var countDownTimer: CountDownTimer? = null
    private var currentTrainingTime: Long = 30 // Mặc định 30 giây
    private var modules: ArrayList<CourseModule>? = null
    private var currentIndex: Int = 0 // Khởi tạo currentIndex
    private var startTime: Long = 0L //tính thời gian luyện tập

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ready)

        // Khởi tạo view
        tvCountdownReady = findViewById(R.id.tvCountdownReady)
        progressCountdownReady = findViewById(R.id.progressCountdownReady)
        tvExerciseName = findViewById(R.id.tvExerciseName)
        imgExerciseReady = findViewById(R.id.imgExerciseReady)
        val btnNextReady: Button = findViewById(R.id.btnNextReady)

        // Nhận dữ liệu từ Intent
        modules = intent.getParcelableArrayListExtra("Courses")
        currentIndex = intent.getIntExtra("CURRENT_INDEX", 0)
        startTime = System.currentTimeMillis()

        modules?.takeIf { it.isNotEmpty() }?.let { moduleList ->
            val currentModule = moduleList[currentIndex]
            tvExerciseName.text = currentModule.name
            Glide.with(this).load(currentModule.img).override(380, 300).into(imgExerciseReady)

            currentTrainingTime = currentModule.trainingTime.toLong() // Thời gian luyện tập từ module
            startCountdown(currentTrainingTime) // Bắt đầu đếm ngược
        } ?: run {
            tvExerciseName.text = "Tên bài tập không có"
            startCountdown(currentTrainingTime)
        }

        // Bắt đầu nút Next
        btnNextReady.setOnClickListener {
            navigateToTraining()
        }
    }

    private fun startCountdown(seconds: Long) {
        countDownTimer = object : CountDownTimer(seconds * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                tvCountdownReady.text = secondsRemaining.toString() // Hiển thị số giây còn lại
                progressCountdownReady.progress = ((seconds - secondsRemaining).toInt()) // Cập nhật progress bar
            }

            override fun onFinish() {
                tvCountdownReady.text = "0" // Khi hết thời gian, hiển thị 0
                navigateToTraining() // Điều hướng sang màn Training
            }
        }.start()
    }

    private fun navigateToTraining() {
        countDownTimer?.cancel()
        Intent(this, Training::class.java).apply {
            putParcelableArrayListExtra("Courses", modules) // Chuyển modules
            putExtra("CURRENT_INDEX", currentIndex) // Chuyển index hiện tại
            putExtra("startTime", startTime) // Chuyển thời gian bắt đầu
        }.also { startActivity(it) }
        finish() // Đóng màn hình ReadyActivity
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel() // Dừng countdown nếu activity bị hủy
    }
}