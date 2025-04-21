    package com.example.projecttest.screen
    
    import android.content.Intent
    import android.os.Bundle
    import android.os.CountDownTimer
    import android.util.Log
    import android.widget.Button
    import android.widget.ImageView
    import android.widget.TextView
    import androidx.appcompat.app.AppCompatActivity
    import com.bumptech.glide.Glide
    import com.example.projecttest.R
    import com.example.projecttest.data.CourseModule
    import com.google.firebase.auth.FirebaseAuth
    import com.google.firebase.firestore.FirebaseFirestore
    
    class Rest : AppCompatActivity() {
        private lateinit var countDownTimer: CountDownTimer
        private lateinit var imgExercise: ImageView
        private lateinit var firestore: FirebaseFirestore
        private lateinit var tvExerciseName: TextView
        private lateinit var tvProgress: TextView
    
        private var timeLeft = 30
        private var modules: ArrayList<CourseModule>? = null
        private var currentIndex = 0
        private var startTime: Long = 0

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.rest_activity)
            val userid = FirebaseAuth.getInstance().currentUser!!.uid


            val tvTimer: TextView = findViewById(R.id.tvTimer)
            val btnAddTime: Button = findViewById(R.id.btnAddTime)
            val btnSkip: Button = findViewById(R.id.btnSkip)
            imgExercise = findViewById(R.id.imgExercise)
            tvExerciseName = findViewById(R.id.tvExerciseName)
            tvProgress = findViewById(R.id.tvProgress)


            startTime = intent.getLongExtra("startTime", 0L)

            // lay thoi gian nghi tu firebase
            firestore = FirebaseFirestore.getInstance()
            firestore.collection("User").document(userid).collection("training_setting").document("train")
                .get()
                .addOnSuccessListener { document ->
                    val firestoreRestTime = (document.getLong("restTime") ?: 30L).toInt()
                    timeLeft = firestoreRestTime

                    // Nhận dữ liệu từ TrainingActivity
                    modules = intent.getParcelableArrayListExtra("Courses")
                    currentIndex = intent.getIntExtra("CURRENT_INDEX", 0)
                    if (modules != null && currentIndex < modules!!.size) {
                        val nextModule = modules!![currentIndex]
                        tvExerciseName.text = nextModule.name
                        Glide.with(this).load(nextModule.img).override(380, 300).into(imgExercise)

                    }
                    updateProgress()
                    startCountdown(tvTimer)
                }
                .addOnFailureListener {
                    // Nếu lỗi khi lấy từ Firebase thì fallback dùng 30s
                    timeLeft = 30
                    updateProgress()
                    startCountdown(tvTimer)
                }


            btnAddTime.setOnClickListener {
                timeLeft += 20
                startCountdown(tvTimer)
            }
    
            btnSkip.setOnClickListener {
                goToReadyScreen()
            }
        }
    
        private fun startCountdown(tvTimer: TextView) {
            try {
                countDownTimer.cancel()
            } catch (_: Exception) {
            }
    
            countDownTimer = object : CountDownTimer((timeLeft * 1000).toLong(), 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    timeLeft = (millisUntilFinished / 1000).toInt()
                    tvTimer.text = String.format("00:%02d", timeLeft)
                }
    
                override fun onFinish() {
                    goToTrainingScreen()
                }
            }.start()
        }
    
        private fun goToTrainingScreen() {
            val intent = Intent(this, Training::class.java).apply {
                putParcelableArrayListExtra("Courses", modules)
                putExtra("CURRENT_INDEX", currentIndex + 1) // sang bài kế tiếp
                putExtra("startTime", startTime)
            }
            startActivity(intent)
            finish()
        }
    
        private fun goToReadyScreen() {
            val intent = Intent(this, ReadyActivity::class.java).apply {
                putParcelableArrayListExtra("Courses", modules)
                putExtra("CURRENT_INDEX", currentIndex) // Truyền lại chính xác vị trí
            }
            startActivity(intent)
            finish()
        }
    
        private fun updateProgress() {
            // Hiển thị tiến trình bài tập (ví dụ: 2/10)
            tvProgress.text = "TIẾP THEO: ${currentIndex + 1 }/${modules?.size}"
        }
    
        override fun onPause() {
            super.onPause()
            try {
                countDownTimer.cancel()
            } catch (_: Exception) {
            }
        }
    
        override fun onResume() {
            super.onResume()
            // Đảm bảo bộ đếm ngược tiếp tục khi trở lại
            startCountdown(findViewById(R.id.tvTimer))
        }
    }
