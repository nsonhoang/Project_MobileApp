package com.example.projecttest.screen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.projecttest.R

class Training : AppCompatActivity(){
    private lateinit var tvTimer: TextView
    private lateinit var btnPause: Button
    private  var countDownTimer: CountDownTimer? = null
    private var timeLeftInMillis: Long=30000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wait)

        val btnBack : ImageView = findViewById(R.id.btnBack)
        val btnVideo : ImageView = findViewById(R.id.btnVideo)
        val btnPrevious : ImageButton = findViewById(R.id.btnPrevious)
        val btnSkip : ImageButton = findViewById(R.id.btnSkip)
        tvTimer = findViewById(R.id.tvTimer)
        btnPause = findViewById(R.id.btnPause)

        loadExerciseData()

        btnBack.setOnClickListener {
            finish()
        }
        btnVideo.setOnClickListener {
            val youtubeIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=VIDEO_ID"))
            startActivity(youtubeIntent)
        }
        btnPause.setOnClickListener {
            if (countDownTimer != null) {
                countDownTimer?.cancel()
                btnPause.text = "TIẾP TỤC"
                countDownTimer = null
            } else {
                startTimer(timeLeftInMillis)
                btnPause.text = "TẠM DỪNG"
            }
        }
        btnPrevious.setOnClickListener {
            finish()
        }
        btnSkip.setOnClickListener {
//            val intent(this, Tranining::class.java)
            startActivity(intent)
        }

    }
    private fun loadExerciseData(){
        timeLeftInMillis=30000
        startTimer(timeLeftInMillis)
    }
    private fun startTimer(time: Long){
        countDownTimer=object: CountDownTimer(time,1000){
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis=millisUntilFinished
                tvTimer.text= String.format("%02d:%02d", millisUntilFinished / 1000 / 60, millisUntilFinished / 1000 % 60)
            }

            override fun onFinish() {
                tvTimer.text="00:00"
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
    }
}