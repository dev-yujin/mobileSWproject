package com.example.ysh.firebaseemail

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.example.healdoc_mobile_5.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

import java.util.regex.Pattern

open class NewUserActivity : AppCompatActivity() {

    // 파이어베이스 인증 객체 생성
    private var firebaseAuth: FirebaseAuth? = null

    // 이메일과 비밀번호
    private var editTextEmail: EditText? = null
    private var editTextPassword: EditText? = null

    private var email = ""
    private var password = ""

    // 이메일 유효성 검사
    private// 이메일 공백
    // 이메일 형식 불일치
    val isValidEmail: Boolean
        get() = if (email.isEmpty()) {
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            false
        } else {
            true
        }

    // 비밀번호 유효성 검사
    private// 비밀번호 공백
    // 비밀번호 형식 불일치
    val isValidPasswd: Boolean
        get() = if (password.isEmpty()) {
            false
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            false
        } else {
            true
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_user)

        // 파이어베이스 인증 객체 선언
        firebaseAuth = FirebaseAuth.getInstance()

        editTextEmail = findViewById(R.id.et_eamil)
        editTextPassword = findViewById(R.id.et_password)
    }

    fun singUp(view: View) {
        email = editTextEmail!!.text.toString()
        password = editTextPassword!!.text.toString()

        if (isValidEmail && isValidPasswd) {
            createUser(email, password)
        }
    }

    fun signIn(view: View) {
        email = editTextEmail!!.text.toString()
        password = editTextPassword!!.text.toString()

        if (isValidEmail && isValidPasswd) {
            loginUser(email, password)
        }
    }

    // 회원가입
    private fun createUser(email: String, password: String) {
        firebaseAuth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, OnCompleteListener<AuthResult> { task ->
                if (task.isSuccessful) {
                    // 회원가입 성공
                    Toast.makeText(this@NewUserActivity, R.string.success_signup, Toast.LENGTH_SHORT)
                        .show()
                } else {
                    // 회원가입 실패
                    Toast.makeText(this@NewUserActivity, R.string.failed_signup, Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }

    // 로그인
    private fun loginUser(email: String, password: String) {
        firebaseAuth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, OnCompleteListener<AuthResult> { task ->
                if (task.isSuccessful) {
                    // 로그인 성공
                    Toast.makeText(this@NewUserActivity, R.string.success_login, Toast.LENGTH_SHORT)
                        .show()
                } else {
                    // 로그인 실패
                    Toast.makeText(this@NewUserActivity, R.string.failed_login, Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }

    companion object {

        // 비밀번호 정규식
        private val PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$")
    }
}