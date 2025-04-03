package com.example.projecttest.screen

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projecttest.databinding.LoginBinding
import android.content.Intent
import android.text.InputType
import com.example.projecttest.R
import com.google.firebase.auth.FirebaseAuth


class Login : AppCompatActivity() {
    private lateinit var binding: LoginBinding
    private lateinit var auth: FirebaseAuth

    private var isPasswordVisible = false
    private var isConfirmPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmail.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener{
                    if (it.isSuccessful) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            }


        }

        // Bắt sự kiện khi nhấn "Sign up"
        binding.txtSignUp.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

        // Hiển thị/ẩn mật khẩu
        binding.imgTogglePassword.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            togglePasswordVisibility()
        }

    }

    // Hàm hiển thị/ẩn mật khẩu
    private fun togglePasswordVisibility() {
        if (isPasswordVisible) {
            binding.edtPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            binding.imgTogglePassword.setImageResource(R.drawable.eye) // Đổi icon mắt mở
        } else {
            binding.edtPassword.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.imgTogglePassword.setImageResource(R.drawable.eyeoff)  // Đổi lại icon mắt đóng
        }
        binding.edtPassword.setSelection(binding.edtPassword.text.length) // Giữ con trỏ ở cuối chữ
    }
}
