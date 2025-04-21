package com.example.projecttest.screen

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.projecttest.R
import com.example.projecttest.data.CourseModule

class Training : AppCompatActivity() {
    private lateinit var tvTimer: TextView
    private lateinit var btnPause: Button
    private lateinit var exerciseImage: ImageView
    private lateinit var tvExerciseName: TextView
    private var countDownTimer: CountDownTimer? = null
    private var timeLeftInMillis: Long = 30000
    private var isPaused = false
    private var currentIndex = 0
    private var modules: ArrayList<CourseModule>? = null
    private var startTime: Long = 0 // Thay kiểu về Long để phù hợp với dữ liệu Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wait)

        val btnBack: ImageView = findViewById(R.id.btnBack)
        val btnPrevious: ImageButton = findViewById(R.id.btnPrevious)
        val btnSkip: ImageButton = findViewById(R.id.btnSkip)
        tvTimer = findViewById(R.id.tvTimer)
        btnPause = findViewById(R.id.btnPause)
        exerciseImage = findViewById(R.id.exerciseImage)
        tvExerciseName = findViewById(R.id.tvExerciseName)

        modules = intent.getParcelableArrayListExtra("Courses")
        currentIndex = intent.getIntExtra("CURRENT_INDEX", 0)
        startTime = intent.getLongExtra("startTime", 0L) // Dùng Long cho startTime

        modules?.getOrNull(currentIndex)?.let {
            timeLeftInMillis = it.trainingTime * 1000L
            tvExerciseName.text = it.name
            Glide.with(this).load(it.img).into(exerciseImage)
            startTimer(timeLeftInMillis)
        }

        btnBack.setOnClickListener { finish() }
        btnPause.setOnClickListener { togglePause() }
        btnPrevious.setOnClickListener { finish() }
        btnSkip.setOnClickListener { navigateToRest() }
    }

    private fun startTimer(time: Long) {
        countDownTimer = object : CountDownTimer(time, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                val minutes = (millisUntilFinished / 1000) / 60
                val seconds = (millisUntilFinished / 1000) % 60
                tvTimer.text = String.format("%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                navigateToRest()
            }
        }.start()
    }

    private fun togglePause() {
        if (isPaused) {
            startTimer(timeLeftInMillis)
            btnPause.text = "TẠM DỪNG"
        } else {
            countDownTimer?.cancel()
            btnPause.text = "TIẾP TỤC"
        }
        isPaused = !isPaused
    }

    private fun navigateToRest() {
        countDownTimer?.cancel()
        val nextIndex = currentIndex + 1
        if (modules != null && nextIndex < modules!!.size) {
            val intent = Intent(this, Rest::class.java).apply {
                putParcelableArrayListExtra("Courses", modules)
                putExtra("CURRENT_INDEX", nextIndex)
                putExtra("startTime", startTime)
            }
            startActivity(intent)
        } else {
            val intent = Intent(this, TongKet::class.java).apply {
                putExtra("startTime", startTime)
            }
            startActivity(intent)
        }
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
    }
}
