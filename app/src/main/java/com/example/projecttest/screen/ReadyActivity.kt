package com.example.projecttest.screen

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.projecttest.data.CourseModule
import com.example.projecttest.R
import com.bumptech.glide.Glide

class ReadyActivity : AppCompatActivity() {

    private lateinit var tvCountdownReady: TextView
    private lateinit var progressCountdownReady: ProgressBar
    private lateinit var tvExerciseName: TextView
    private lateinit var imgExerciseReady: ImageView
    private var countDownTimer: CountDownTimer? = null
    private var currentTrainingTime: Long = 30 // Mặc định 30 giây
    private var modules: ArrayList<CourseModule>? = null
    private var currentIndex: Int = 0 // Khởi tạo currentIndex

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

        modules?.takeIf { it.isNotEmpty() }?.let { moduleList ->
            val currentModule = moduleList[currentIndex]
            tvExerciseName.text = currentModule.name
            Glide.with(this).load(currentModule.img).override(380, 300).into(imgExerciseReady)

            currentTrainingTime = currentModule.trainingTime.toLong()
            startCountdown(currentTrainingTime)
        } ?: run {
            tvExerciseName.text = "Tên bài tập không có"
            startCountdown(currentTrainingTime)
        }

        btnNextReady.setOnClickListener {
            navigateToTraining()
        }
    }

    private fun startCountdown(seconds: Long) {
        countDownTimer = object : CountDownTimer(seconds * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                tvCountdownReady.text = secondsRemaining.toString()
                progressCountdownReady.progress = (currentTrainingTime - secondsRemaining).toInt()
            }

            override fun onFinish() {
                tvCountdownReady.text = "0"
                navigateToTraining()
            }
        }.start()
    }

    private fun navigateToTraining() {
        countDownTimer?.cancel()
        Intent(this, Training::class.java).apply {
            putParcelableArrayListExtra("Courses", modules)
            putExtra("CURRENT_INDEX", currentIndex)
        }.also { startActivity(it) }
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
    }
}
