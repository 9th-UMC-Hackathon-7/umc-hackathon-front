package com.example.umc_hackathon_9

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_hackathon_9.R

class SignUpActivity : AppCompatActivity() {

    private lateinit var etPw: EditText
    private lateinit var etPwCheck: EditText
    private lateinit var btnPwToggle: ImageButton
    private lateinit var btnPwCheckToggle: ImageButton

    private var isPwVisible = false
    private var isPwCheckVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)   // 지금 만든 xml 이름

        etPw = findViewById(R.id.etPw)
        etPwCheck = findViewById(R.id.etPwCheck)
        btnPwToggle = findViewById(R.id.btnPwToggle)
        btnPwCheckToggle = findViewById(R.id.btnPwToggleCheck)

        btnPwToggle.setOnClickListener {
            isPwVisible = !isPwVisible
            togglePassword(etPw, btnPwToggle, isPwVisible)
        }

        btnPwCheckToggle.setOnClickListener {
            isPwCheckVisible = !isPwCheckVisible
            togglePassword(etPwCheck, btnPwCheckToggle, isPwCheckVisible)
        }
    }

    private fun togglePassword(
        editText: EditText,
        button: ImageButton,
        visible: Boolean
    ) {
        val cursorPos = editText.selectionStart

        if (visible) {
            // 비밀번호 평문으로 보이기
            editText.transformationMethod = null
        } else {
            // ●●● 로 숨기기
            editText.transformationMethod = PasswordTransformationMethod.getInstance()
        }

        // 커서 위치 유지
        editText.setSelection(if (cursorPos >= 0) cursorPos else editText.text.length)
    }

}
