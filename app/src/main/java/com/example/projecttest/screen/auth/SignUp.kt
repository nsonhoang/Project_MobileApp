package com.example.projecttest.screen.auth

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.projecttest.R
import com.example.projecttest.data.UserEntity
import com.example.projecttest.databinding.SignupBinding
import com.example.projecttest.screen.MainActivity
import com.example.projecttest.screen.setting.setnotification.AppDatabase
import com.example.projecttest.screen.setting.setnotification.UserDao
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class SignUp :  AppCompatActivity() {
    private lateinit var binding: SignupBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var userDao: UserDao


    private var isPasswordVisible = false
    private var isConfirmPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignupBinding.inflate(layoutInflater)
        setContentView(binding.root)


        auth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {
            val name = binding.edtName.text.toString()
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            val confirmPassword = binding.edtAgainPassword.text.toString()
            val db = AppDatabase.Companion.getInstance(this)
            userDao = db.userDao()

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (password.length >= 6) {
                    if (password == confirmPassword) {
                        auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this) { task ->
                                if (task.isSuccessful) {
                                    val intent = Intent(this, MainActivity::class.java)
                                    lifecycleScope.launch {
                                        userDao.logoutAllUsers()
                                        userDao.insertUser(UserEntity(email, password, isLoggedIn = true))
                                        startActivity(intent)
                                    }

                                } else {
                                    Toast.makeText(this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show()
                                }
                            }
                    } else {
                        Toast.makeText(this, "Mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Mật khẩu phải có ít nhất 6 ký tự", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            }

        }
        // chuyển Login
        binding.textView10.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        // Hiển thị/ẩn mật khẩu
        binding.btnseepass.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            togglePasswordVisibility()
        }

        binding.btnseepassagain.setOnClickListener {
            isConfirmPasswordVisible = !isConfirmPasswordVisible
            toggleConfirmPasswordVisibility()
        }
    }

    // Hàm hiển thị/ẩn mật khẩu
    private fun togglePasswordVisibility() {
        if (isPasswordVisible) {
            binding.edtPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            binding.btnseepass.setImageResource(R.drawable.eye) // Đổi icon mắt mở
        } else {
            binding.edtPassword.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.btnseepass.setImageResource(R.drawable.eyeoff)
        }
        binding.edtPassword.setSelection(binding.edtPassword.text.length) //
    }

    private fun toggleConfirmPasswordVisibility() {
        if (isConfirmPasswordVisible) {
            binding.edtAgainPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            binding.btnseepassagain.setImageResource(R.drawable.eye)
        } else {
            binding.edtAgainPassword.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.btnseepassagain.setImageResource(R.drawable.eyeoff)
        }
        binding.edtAgainPassword.setSelection(binding.edtAgainPassword.text.length)
    }
}