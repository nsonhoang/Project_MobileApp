package com.example.projecttest.screen

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projecttest.R
import com.example.projecttest.databinding.LoginBinding
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.CallbackManager.Factory.create
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class Login : AppCompatActivity() {
    private lateinit var binding: LoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
//    private lateinit var callbackManager: CallbackManager


    private val RC_SIGN_IN = 1001
    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        FacebookSdk.sdkInitialize(applicationContext) // Khởi tạo Facebook SDK
        binding = LoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
//        callbackManager = CallbackManager.Factory.create()

        // Cấu hình Google Sign-In
        configureGoogleSignIn()

        // Sự kiện đăng nhập bằng Google
        setupGoogleSignIn()

        // Sự kiện đăng nhập với email và mật khẩu
        setupEmailLogin()

        // Sự kiện chuyển sang màn hình đăng ký
        setupSignUp()

        // Hiển thị/ẩn mật khẩu
        setupPasswordVisibilityToggle()

//        setupFacebookSignIn()
    }

//    private fun setupFacebookSignIn() {
//        binding.imgfb.setOnClickListener {
//            LoginManager.getInstance().logInWithReadPermissions(this, listOf("public_profile", "email"))
//            LoginManager.getInstance().registerCallback(callbackManager,
//                object : FacebookCallback<LoginResult> {
//                    override fun onSuccess(loginResult: LoginResult) {
//                        handleFacebookAccessToken(loginResult.accessToken)
//                    }
//
//                    override fun onCancel() {
//                        showToast("Đăng nhập Facebook bị hủy.")
//                    }
//
//                    override fun onError(error: FacebookException) {
//                        showToast("Lỗi Facebook: ${error.message}")
//                    }
//                })
//        }
//    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    showToast("Đăng nhập Facebook thành công!")
                    navigateToMainActivity()
                } else {
                    showToast("Đăng nhập Facebook thất bại.")
                }
            }
    }

    private fun configureGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_client_id)) // từ google-services.json
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun setupGoogleSignIn() {
        binding.btnGG.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    private fun setupEmailLogin() {
        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmail.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        navigateToMainActivity()
                    } else {
                        showToast("Đăng nhập thất bại")
                    }
                }
            } else {
                showToast("Vui lòng nhập đầy đủ thông tin")
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

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            handleGoogleSignInResult(data)
        }
    }

    private fun handleGoogleSignInResult(data: Intent?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        if (task.isSuccessful) {
            val account: GoogleSignInAccount? = task.result
            val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
            auth.signInWithCredential(credential)
                .addOnCompleteListener(this) { authTask ->
                    if (authTask.isSuccessful) {
                        showToast("Đăng nhập Google thành công!")
                        navigateToMainActivity()
                    } else {
                        showToast("Đăng nhập Google thất bại!")
                    }
                }
        } else {
            showToast("Đăng nhập Google thất bại!")
        }
    }

}
