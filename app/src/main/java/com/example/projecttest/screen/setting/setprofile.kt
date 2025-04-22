package com.example.projecttest.screen.setting

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projecttest.databinding.SettingProfileBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class setprofile : AppCompatActivity() {
    private lateinit var binding: SettingProfileBinding
    private var db= Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SettingProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val edtHoTen = binding.edtHoTen
        val edtNamSinh = binding.edtNamSinh
        val edtGioiTinh = binding.edtGioiTinh
        val edtChieuCao = binding.edtChieuCao
        val edtCanNang = binding.edtCanNang
        val edtEmail = binding.edtEmail
        val btnSave = binding.btnSave

        val userid = FirebaseAuth.getInstance().currentUser!!.uid
        val ref = db.collection("User").document(userid)
        ref.get().addOnSuccessListener {//lấy thông tin tu firestore
            if (it != null) {
                val name = it.getString("hoTen") ?: "Chưa cập nhật"
                val birthday = it.getString("namSinh") ?: "Chưa cập nhật"
                val gender = it.getString("gioiTinh") ?: "Chưa cập nhật"
                val height = it.getString("chieuCao") ?: "Chưa cập nhật"
                val weight = it.getString("canNang") ?: "Chưa cập nhật"

                edtHoTen.setText(name)
                edtNamSinh.setText(birthday)
                edtGioiTinh.setText(gender)
                edtChieuCao.setText(height)
                edtCanNang.setText(weight)
            }
        }
            .addOnFailureListener {
                Toast.makeText(this, "Chưa cập nhật", Toast.LENGTH_SHORT).show()
            }

        // Lấy thông tin email từ Firebase Authentication và hiển thị vào edtEmail
        val currentUser = FirebaseAuth.getInstance().currentUser
        currentUser?.let {
            edtEmail.setText(it.email)
            db.collection("User").document(userid).update("email", it.email)
        }

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnedit.setOnClickListener {
            edtHoTen.isEnabled = true
            edtNamSinh.isEnabled = true
            edtGioiTinh.isEnabled = true
            edtChieuCao.isEnabled = true
            edtCanNang.isEnabled = true
            edtEmail.isEnabled = true
            btnSave.isEnabled = true
        }

        binding.btnSave.setOnClickListener {
            edtHoTen.isEnabled = false
            edtNamSinh.isEnabled = false
            edtGioiTinh.isEnabled = false
            edtChieuCao.isEnabled = false
            edtCanNang.isEnabled = false
            edtEmail.isEnabled = false
            btnSave.isEnabled = false

            val shoTen = edtHoTen.text.toString().trim()
            val snamSinh = edtNamSinh.text.toString().trim()
            val sgioiTinh = edtGioiTinh.text.toString().trim()
            val schieuCao = edtChieuCao.text.toString().trim()
            val scanNang = edtCanNang.text.toString().trim()

            val user = hashMapOf(
                "hoTen" to shoTen,
                "namSinh" to snamSinh,
                "gioiTinh" to sgioiTinh,
                "chieuCao" to schieuCao,
                "canNang" to scanNang,
            )


            db.collection("User").document(userid).set(user)
                .addOnSuccessListener {
                    Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show()
                }


        }
    }
}