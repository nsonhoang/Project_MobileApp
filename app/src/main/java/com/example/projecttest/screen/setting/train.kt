package com.example.projecttest.screen.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.projecttest.databinding.SettingTrainBinding

class train : AppCompatActivity() {
    private lateinit var binding: SettingTrainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SettingTrainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgback.setOnClickListener {
            finish()
        }
    }
}