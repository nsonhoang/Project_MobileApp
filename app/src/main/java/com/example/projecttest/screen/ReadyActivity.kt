package com.example.projecttest.screen

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.projecttest.R

class ReadyActivity : AppCompatActivity(){
    private lateinit var tvCountdownReady: TextView
    private lateinit var progressCountdownReady: ProgressBar
    private var countDownTimer : CountDownTimer? =null
    private var exerciseId: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ready)

        val btnNextReady : Button = findViewById(R.id.btnNextReady)
        btnNextReady.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        tvCountdownReady = findViewById(R.id.tvCountdownReady)
        progressCountdownReady = findViewById(R.id.progressCountdownReady)

        exerciseId = intent.getIntExtra("EXCERCISE_ID" ,1)
         startCountdown()

        btnNextReady.setOnClickListener {
            navigateToExercise()
        }
    }
    private fun startCountdown() {
        countDownTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                tvCountdownReady.text = secondsRemaining.toString()
                progressCountdownReady.progress = (30 - secondsRemaining).toInt()
            }

            override fun onFinish() {
                navigateToExercise()
            }
        }.start()
    }
    private fun navigateToExercise() {
        countDownTimer?.cancel()
        val intent = Intent(this, Training::class.java)
        intent.putExtra("EXERCISE_ID", exerciseId)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
    }
}