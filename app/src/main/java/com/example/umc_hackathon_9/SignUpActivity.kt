package com.example.umc_hackathon_9

import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class SignUpActivity : AppCompatActivity() {

    private lateinit var etId: EditText       // 아이디(이메일)
    private lateinit var etName: EditText     // 이름
    private lateinit var etPw: EditText       // 비밀번호
    private lateinit var etPwCheck: EditText  // 비밀번호 확인

    private lateinit var btnPwToggle: ImageButton
    private lateinit var btnPwCheckToggle: ImageButton
    private lateinit var btnNext: Button

    // ⭐ 성별 라디오 버튼 가정 (Male / Female)
    private lateinit var rgGender: RadioGroup
    private lateinit var rbMale: RadioButton
    private lateinit var rbFemale: RadioButton

    private var isPwVisible = false
    private var isPwCheckVisible = false

    // ⭐ 유저가 선택한 성별 값을 담을 변수 (FEMALE / MALE 등)
    private var selectedGender: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        etId = findViewById(R.id.etId)
        etName = findViewById(R.id.etName)
        etPw = findViewById(R.id.etPw)
        etPwCheck = findViewById(R.id.etPwCheck)
        btnPwToggle = findViewById(R.id.btnPwToggle)
        btnPwCheckToggle = findViewById(R.id.btnPwToggleCheck)
        btnNext = findViewById(R.id.btnNext)

        // ⭐ 레이아웃에 이렇게 있다고 가정
        rgGender = findViewById(R.id.rgGender)
        rbMale = findViewById(R.id.rbMale)
        rbFemale = findViewById(R.id.rbFemale)

        // 기본은 ●●● 로 숨기기
        etPw.transformationMethod = PasswordTransformationMethod.getInstance()
        etPwCheck.transformationMethod = PasswordTransformationMethod.getInstance()

        // 비밀번호 보기 토글
        btnPwToggle.setOnClickListener {
            isPwVisible = !isPwVisible
            togglePassword(etPw, isPwVisible)
        }

        btnPwCheckToggle.setOnClickListener {
            isPwCheckVisible = !isPwCheckVisible
            togglePassword(etPwCheck, isPwCheckVisible)
        }

        // ⭐ 성별 선택 시 selectedGender 업데이트
        rgGender.setOnCheckedChangeListener { _, checkedId ->
            selectedGender = when (checkedId) {
                R.id.rbMale -> "MALE"
                R.id.rbFemale -> "FEMALE"
                else -> null
            }
        }

        // ✅ 다음 버튼: 비번 두 개 일치하면 아이디/비번/이름/성별 들고 MbtiActivity로 이동
        btnNext.setOnClickListener {
            val id = etId.text.toString().trim()
            val name = etName.text.toString().trim()
            val pw = etPw.text.toString().trim()
            val pwCheck = etPwCheck.text.toString().trim()
            val gender = selectedGender  // ⭐ 여기서 읽어옴

            if (id.isEmpty() || name.isEmpty() || pw.isEmpty() || pwCheck.isEmpty()) {
                Toast.makeText(this, "아이디, 이름, 비밀번호를 모두 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pw != pwCheck) {
                Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (gender.isNullOrEmpty()) {
                Toast.makeText(this, "성별을 선택해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(this, MbtiActivity::class.java).apply {
                putExtra("loginId", id)
                putExtra("name", name)
                putExtra("password", pw)
                putExtra("gender", gender)
            }
            startActivity(intent)
        }
    }

    private fun togglePassword(
        editText: EditText,
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
