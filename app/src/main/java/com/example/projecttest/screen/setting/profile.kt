package com.example.projecttest.screen.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.projecttest.databinding.SettingProfileBinding

class Profile : AppCompatActivity() {
    private lateinit var binding: SettingProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SettingProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgbtnBack.setOnClickListener {
            finish()
        }
    }
}
