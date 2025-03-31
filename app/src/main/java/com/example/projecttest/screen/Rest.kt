package com.example.projecttest.screen

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.projecttest.R
import com.example.projecttest.screen.Training

class Rest: AppCompatActivity(){
    private lateinit var countDownTimer: CountDownTimer
    private var timeLeft = 30

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rest_activity)

        val tvTimer: TextView = findViewById(R.id.tvTimer)
        val btnAddTime : Button = findViewById(R.id.btnAddTime)
        val btnSkip : Button = findViewById(R.id.btnSkip)

        startCountdown(tvTimer)

        btnAddTime.setOnClickListener {
            timeLeft+= 20
            startCountdown(tvTimer)
        }
        btnSkip.setOnClickListener {
            val intent = Intent(this, Training::class.java)
        }
    }
    private fun startCountdown(tvTimer: TextView) {
        countDownTimer?.cancel()

        countDownTimer = object : CountDownTimer((timeLeft * 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = (millisUntilFinished / 1000).toInt()
                tvTimer.text = String.format("00:%02d", timeLeft)
            }

            override fun onFinish() {
                val intent = Intent(this@Rest, Training::class.java)
                startActivity(intent)
                finish()
            }
        }.start()
    }

}