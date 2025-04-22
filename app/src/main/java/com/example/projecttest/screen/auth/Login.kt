package com.example.projecttest.screen.auth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.projecttest.R
import com.example.projecttest.data.UserEntity
import com.example.projecttest.databinding.LoginBinding
import com.example.projecttest.screen.MainActivity
import com.example.projecttest.screen.auth.SignUp
import com.example.projecttest.screen.setting.setnotification.AppDatabase
import com.example.projecttest.screen.setting.setnotification.UserDao
import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.launch

class Login : AppCompatActivity() {
    private lateinit var binding: LoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var userDao: UserDao

    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        val db = AppDatabase.Companion.getInstance(this)
        userDao = db.userDao()

        binding.btnGG.setOnClickListener{
            signOutGoogle {
                signWithGoogle()
            }
        }


        // Sự kiện đăng nhập với email và mật khẩu
        setupEmailLogin()

        // Sự kiện chuyển sang màn hình đăng ký
        setupSignUp()

        // Hiển thị/ẩn mật khẩu
        setupPasswordVisibilityToggle()

        lifecycleScope.launch {
            val existingUser = userDao.getLoggedInUser()
            if (existingUser != null) {
                navigateToMainActivity()
            }
        }

    }

    private fun signWithGoogle(){
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private fun signOutGoogle(onComplete: () -> Unit) {
        auth.signOut()  // Đăng xuất Firebase
        googleSignInClient.signOut().addOnCompleteListener {
            onComplete()  // Sau khi sign-out hoàn tất, tiến hành đăng nhập lại
        }
    }


    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    {result->
        if (result.resultCode == Activity.RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResults(task)
        }


    }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful){
            val account : GoogleSignInAccount? = task.result
            if (account != null){
                updateUI(account)
            }
        }else{
            Toast.makeText(this,"Đăng nhập Google thất bại", Toast.LENGTH_SHORT).show()
        }

    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken,null)
        auth.signInWithCredential(credential).addOnCompleteListener{
            if (it.isSuccessful){
                val email = account.email ?: ""
                val password = ""
                lifecycleScope.launch {
                    userDao.logoutAllUsers()
                    userDao.insertUser(UserEntity(email,password, isLoggedIn = true))
                    navigateToMainActivity()
                }
            }else{
                Toast.makeText(this,"Đăng nhập thất bại", Toast.LENGTH_SHORT).show()
            }
            }
        }

    private fun setupEmailLogin() {
        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmail.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        lifecycleScope.launch {
                            userDao.logoutAllUsers()
                            userDao.insertUser(UserEntity(email, password, isLoggedIn = true))
                            navigateToMainActivity()
                        }
                    } else {
                        Toast.makeText(this,"Đăng nhập thất bại", Toast.LENGTH_SHORT).show()
                    }

                }
            } else {
                Toast.makeText(this,"Thiếu thông tin", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupSignUp() {
        binding.txtSignUp.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }
    }

    private fun setupPasswordVisibilityToggle() {
        binding.imgTogglePassword.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            togglePasswordVisibility()
        }
    }

    private fun togglePasswordVisibility() {
        if (isPasswordVisible) {
            binding.edtPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            binding.imgTogglePassword.setImageResource(R.drawable.eye) // Mở mắt
        } else {
            binding.edtPassword.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.imgTogglePassword.setImageResource(R.drawable.eyeoff) // Đóng mắt
        }
        binding.edtPassword.setSelection(binding.edtPassword.text.length) // Giữ con trỏ ở cuối chữ
    }


    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}