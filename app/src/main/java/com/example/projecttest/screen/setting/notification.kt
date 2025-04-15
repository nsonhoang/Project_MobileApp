package com.example.projecttest.screen.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.projecttest.databinding.SettingNotificationBinding

class notification : AppCompatActivity() {
    private lateinit var binding: SettingNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SettingNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageButton5.setOnClickListener {
            finish()
        }
    }
}