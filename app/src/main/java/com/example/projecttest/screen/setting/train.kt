package com.example.projecttest.screen.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.projecttest.databinding.SettingTrainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User

class train : AppCompatActivity() {
    private lateinit var binding: SettingTrainBinding
    private val db = FirebaseFirestore.getInstance()
    val userid = FirebaseAuth.getInstance().currentUser!!.uid
    private val settingsRef = db.collection("User").document(userid).collection("training_setting").document("train")

    private var workoutTime = 15
    private var restTime = 30


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SettingTrainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener { finish() }

        // Lấy dữ liệu từ Firestore
        settingsRef.get().addOnSuccessListener { doc ->
            if (doc.exists()) {
                workoutTime = doc.getLong("workoutTime")?.toInt() ?: 15
                restTime = doc.getLong("restTime")?.toInt() ?: 30
                updateUI()
            } else {
                saveSettingsToFirestore()
            }
        }

        // Các nút điều chỉnh thời gian
        binding.imgbtnAddTrain.setOnClickListener {
            workoutTime += 1
            updateUI()
            saveSettingsToFirestore()
        }

        binding.imgbtnMinusTrain.setOnClickListener {
            if (workoutTime > 1) {
                workoutTime -= 1
                updateUI()
                saveSettingsToFirestore()
            }
        }

        binding.imgbtnAddRest.setOnClickListener {
            restTime += 1
            updateUI()
            saveSettingsToFirestore()
        }

        binding.imgbtnMinusRest.setOnClickListener {
            if (restTime > 1) {
                restTime -= 1
                updateUI()
                saveSettingsToFirestore()
            }
        }
    }

    private fun updateUI() {
        binding.textView13.text = "${restTime}s"
        binding.textView14.text = "${workoutTime}s"
    }

    private fun saveSettingsToFirestore() {
        val data = hashMapOf(
            "workoutTime" to workoutTime,
            "restTime" to restTime
        )
        settingsRef.set(data)
    }
}
