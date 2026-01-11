package com.example.umc_hackathon_9

import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var etId: EditText
    private lateinit var etPw: EditText
    private lateinit var tvError: TextView
    private lateinit var btnLogin: Button
    private lateinit var tvFindAccount: TextView
    private lateinit var tvSignUp: TextView

    private lateinit var btnPwToggle: ImageButton   // 👈 추가

    private var isPwVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)   // 네 레이아웃 이름

        initViews()
        initListeners()
    }

    private fun initViews() {
        etId = findViewById(R.id.etId)
        etPw = findViewById(R.id.etPw)
        tvError = findViewById(R.id.tvError)
        btnLogin = findViewById(R.id.btnLogin)
        tvFindAccount = findViewById(R.id.tvFindAccount)
        tvSignUp = findViewById(R.id.tvSignUp)
        btnPwToggle = findViewById(R.id.btnPwToggle)

        etPw.transformationMethod = PasswordTransformationMethod.getInstance()
    }

    private fun initListeners() {
        // 로그인 버튼 클릭
        btnLogin.setOnClickListener {
            val id = etId.text.toString().trim()
            val pw = etPw.text.toString().trim()

            tvError.visibility = View.GONE
            Toast.makeText(this, "로그인 성공 가정 👌", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        tvFindAccount.setOnClickListener {
            Toast.makeText(this, "아이디/비밀번호 찾기 준비 중입니다", Toast.LENGTH_SHORT).show()
        }

        tvSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            // finish()는 굳이 안 해도 됨 (회원가입에서 뒤로가기 누르면 로그인으로 돌아오게)
        }

        // 👇 비밀번호 보기 토글
        btnPwToggle.setOnClickListener {
            isPwVisible = !isPwVisible
            togglePassword(etPw, btnPwToggle, isPwVisible)
        }
    }

    private fun togglePassword(
        editText: EditText,
        button: ImageButton,
        visible: Boolean
    ) {
        val cursorPos = editText.selectionStart

        if (visible) {
            // ●●● 로 숨기기
            editText.transformationMethod = PasswordTransformationMethod.getInstance()
        } else {

            // 비밀번호 평문으로 보이기
            editText.transformationMethod = null
        }

        // 커서 위치 유지
        editText.setSelection(if (cursorPos >= 0) cursorPos else editText.text.length)
    }

}
