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
import androidx.lifecycle.ViewModelProvider
import com.example.projecttest.R
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.example.projecttest.model.CourseViewModel

class ReadyActivity : AppCompatActivity(){
    private lateinit var tvCountdownReady: TextView
    private lateinit var progressCountdownReady: ProgressBar
    private var countDownTimer : CountDownTimer? =null
    private var exerciseId: Int = 1
    private lateinit var imgExerciseReady: ImageView
    private lateinit var firestore: FirebaseFirestore
    private lateinit var viewModel: CourseViewModel
    private lateinit var programId: String
    private lateinit var targetCourseId: String
    private lateinit var courseId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ready)

        val btnNextReady : Button = findViewById(R.id.btnNextReady)
        btnNextReady.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        tvCountdownReady = findViewById(R.id.tvCountdownReady)
        progressCountdownReady = findViewById(R.id.progressCountdownReady)
        imgExerciseReady = findViewById(R.id.imgExerciseReady)

        viewModel = ViewModelProvider(this)[CourseViewModel::class.java]
        startCountdown()
        firestore= FirebaseFirestore.getInstance()
        loadGifFromFirestore()

        btnNextReady.setOnClickListener {
            navigateToExercise()
        }
    }
    private fun loadGifFromFirestore() {
        firestore.collection("TrainingProgram").document(programId)
            .collection("TargetCourses").document(targetCourseId)
            .collection("Courses").document(courseId)
            .collection("modules")
            .limit(1) // Lấy module đầu tiên
            .get()
            .addOnSuccessListener { result ->
                if (!result.isEmpty) {
                    val document = result.documents[0]
                    val gifUrl = document.getString("img") // hoặc "gifUrl" tùy bạn lưu
                    if (!gifUrl.isNullOrEmpty()) {
                        Glide.with(this).load(gifUrl).override(380, 300).into(imgExerciseReady)
                    }
                }
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
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