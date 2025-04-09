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
import com.bumptech.glide.Glide
import com.example.projecttest.R
import com.google.firebase.firestore.FirebaseFirestore

class Training : AppCompatActivity(){
    private lateinit var tvTimer: TextView
    private lateinit var btnPause: Button
    private lateinit var exerciseImage: ImageView
    private  var countDownTimer: CountDownTimer? = null
    private var timeLeftInMillis: Long=30000
    private lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wait)

        val btnBack : ImageView = findViewById(R.id.btnBack)
        val btnPrevious : ImageButton = findViewById(R.id.btnPrevious)
        val btnSkip : ImageButton = findViewById(R.id.btnSkip)
        tvTimer = findViewById(R.id.tvTimer)
        btnPause = findViewById(R.id.btnPause)
        exerciseImage = findViewById(R.id.exerciseImage)
        firestore = FirebaseFirestore.getInstance()

        loadExerciseData()

        btnBack.setOnClickListener {
            finish()
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
            val intent = Intent(this, Rest::class.java)  // Chuyển đến RestActivity
            startActivity(intent)
        }

    }
    // gán gif

    private fun loadExerciseData() {
        val exerciseId = 1
        firestore.collection("TrainingProgram").document(exerciseId.toString())
            .get().addOnSuccessListener { document ->
                if (document != null) {
                    val gifUrl = document.getString("gifUrl")// thay bằng nơi lưu URL của gif
                    if (gifUrl != null) {
                        Glide.with(this).load(gifUrl).override(380, 300).into(exerciseImage)
                    }
                }

            }


        timeLeftInMillis = 30000
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