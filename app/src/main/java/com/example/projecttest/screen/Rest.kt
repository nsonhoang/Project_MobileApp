package com.example.projecttest.screen

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.projecttest.R
import com.example.projecttest.screen.Training

class Rest : AppCompatActivity() {
    private lateinit var countDownTimer: CountDownTimer
    private var timeLeft = 30

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rest_activity)

        val tvTimer: TextView = findViewById(R.id.tvTimer)
        val btnAddTime: Button = findViewById(R.id.btnAddTime)
        val btnSkip: Button = findViewById(R.id.btnSkip)

        // Bắt đầu đếm ngược ngay khi khởi tạo Activity
        startCountdown(tvTimer)

        btnAddTime.setOnClickListener {
            timeLeft += 20 // Thêm 20 giây vào thời gian
            startCountdown(tvTimer) // Khởi tạo lại bộ đếm thời gian
        }

        btnSkip.setOnClickListener {
            // Chuyển đến màn hình Training
            val intent = Intent(this, ReadyActivity::class.java)
            startActivity(intent)
            finish() // Đóng Activity hiện tại
        }
    }

    private fun startCountdown(tvTimer: TextView) {
        // Kiểm tra nếu bộ đếm đang chạy, hủy nó
        try {
            countDownTimer.cancel()
        } catch (e: Exception) {
            // Nếu không có bộ đếm nào đang chạy thì bỏ qua
        }

        // Khởi tạo bộ đếm thời gian mới
        countDownTimer = object : CountDownTimer((timeLeft * 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = (millisUntilFinished / 1000).toInt()
                tvTimer.text = String.format("00:%02d", timeLeft)
            }

            override fun onFinish() {
                // Sau khi đếm ngược xong, chuyển đến màn hình Training
                val intent = Intent(this@Rest, Training::class.java)
                startActivity(intent)
                finish() // Đóng Activity Rest
            }
        }.start()
    }

    override fun onPause() {
        super.onPause()
        // Hủy bộ đếm khi Activity bị tạm dừng
        try {
            countDownTimer.cancel()
        } catch (e: Exception) {
            // Không làm gì nếu không có bộ đếm nào đang chạy
        }
    }

    override fun onResume() {
        super.onResume()
        // Tiếp tục bộ đếm khi Activity được tiếp tục
        startCountdown(findViewById(R.id.tvTimer))
    }
}
