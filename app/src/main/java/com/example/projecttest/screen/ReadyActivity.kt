package com.example.projecttest.screen

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.projecttest.R
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore

class ReadyActivity : AppCompatActivity(){
    private lateinit var tvCountdownReady: TextView
    private lateinit var progressCountdownReady: ProgressBar
    private var countDownTimer : CountDownTimer? =null
    private var exerciseId: Int = 1
    private lateinit var imgExerciseReady: ImageView
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ready)

        val btnNextReady : Button = findViewById(R.id.btnNextReady)
        btnNextReady.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        tvCountdownReady = findViewById(R.id.tvCountdownReady)
        progressCountdownReady = findViewById(R.id.progressCountdownReady)
        imgExerciseReady = findViewById(R.id.imgExerciseReady)

        exerciseId = intent.getIntExtra("EXCERCISE_ID" ,1)
        startCountdown()
        firestore= FirebaseFirestore.getInstance()
        loadGifFromFirestore()

        btnNextReady.setOnClickListener {
            navigateToExercise()
        }
    }
    private fun loadGifFromFirestore(){
        firestore.collection("TrainingProgram").document(exerciseId.toString())
            .get().addOnSuccessListener { document ->
                if (document != null){
                    val gifUrl = document.getString("gifUrl")// thay bằng nơi lưu URL của gif
                    if (gifUrl != null){
                        Glide.with(this).load(gifUrl).override(380,300).into(imgExerciseReady)
                    }
                }

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