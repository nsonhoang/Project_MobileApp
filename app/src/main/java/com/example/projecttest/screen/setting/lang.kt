package com.example.projecttest.screen.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.projecttest.databinding.SettingLanguageBinding

class lang : AppCompatActivity() {
    private lateinit var binding: SettingLanguageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SettingLanguageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgback.setOnClickListener {
            finish()
        }
    }
}