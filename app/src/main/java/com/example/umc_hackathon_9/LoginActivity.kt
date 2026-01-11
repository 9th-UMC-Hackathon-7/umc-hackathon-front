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
import androidx.lifecycle.lifecycleScope
import com.example.umc_hackathon_9.network.ApiClient
import com.example.umc_hackathon_9.network.ProjectModels
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var etId: EditText
    private lateinit var etPw: EditText
    private lateinit var tvError: TextView
    private lateinit var btnLogin: Button
    private lateinit var tvFindAccount: TextView
    private lateinit var tvSignUp: TextView
    private lateinit var btnPwToggle: ImageButton

    private var isPwVisible = false   // false면 ●●●, true면 평문

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

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

        // 기본은 ●●● 로 보이게
        etPw.transformationMethod = PasswordTransformationMethod.getInstance()
    }

    private fun initListeners() {
        // 로그인 버튼 클릭
        btnLogin.setOnClickListener {
            val id = etId.text.toString().trim()
            val pw = etPw.text.toString().trim()

            // 간단 유효성 검사
            if (id.isEmpty() || pw.isEmpty()) {
                tvError.text = "아이디와 비밀번호를 모두 입력해주세요."
                tvError.visibility = View.VISIBLE
                return@setOnClickListener
            }

            tvError.visibility = View.GONE
            doLogin(id, pw)
        }

        tvFindAccount.setOnClickListener {
            Toast.makeText(this, "아이디/비밀번호 찾기 준비 중입니다", Toast.LENGTH_SHORT).show()
        }

        tvSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        // 비밀번호 보기 토글
        btnPwToggle.setOnClickListener {
            isPwVisible = !isPwVisible
            togglePassword(etPw, btnPwToggle, isPwVisible)
        }
    }

    // 실제 로그인 API 호출
    private fun doLogin(id: String, pw: String) {
        btnLogin.isEnabled = false

        lifecycleScope.launch {
            try {
                val response = ApiClient.authApi.login(
                    ProjectModels.LoginRequest(loginId = id, password = pw)
                )

                if (response.isSuccessful) {
                    val body = response.body()

                    if (body?.resultType == "SUCCESS" && body.success != null) {
                        // ✅ 로그인 성공
                        val success = body.success

                        // TODO: 여기서 accessToken / refreshToken / userId 저장 (SharedPreferences 등)
                        // ex) saveTokens(success.accessToken, success.refreshToken)

                        Toast.makeText(
                            this@LoginActivity,
                            "로그인 성공!",
                            Toast.LENGTH_SHORT
                        ).show()

                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // resultType == FAIL 이거나 body null
                        val msg = body?.error?.reason ?: "아이디 또는 비밀번호가 올바르지 않습니다."
                        tvError.text = msg
                        tvError.visibility = View.VISIBLE
                    }
                } else {
                    // 400, 404, 500 등 HTTP 에러
                    val msg = when (response.code()) {
                        400 -> "로그인 정보가 일치하지 않습니다."
                        404 -> "존재하지 않는 사용자입니다."
                        500 -> "서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요."
                        else -> "로그인에 실패했습니다. (${response.code()})"
                    }
                    tvError.text = msg
                    tvError.visibility = View.VISIBLE
                }
            } catch (e: Exception) {
                e.printStackTrace()
                tvError.text = "네트워크 오류가 발생했습니다. 인터넷 연결을 확인해주세요."
                tvError.visibility = View.VISIBLE
            } finally {
                btnLogin.isEnabled = true
            }
        }
    }

    private fun togglePassword(
        editText: EditText,
        button: ImageButton,
        visible: Boolean
    ) {
        val cursorPos = editText.selectionStart

        if (visible) {
            // 👀 평문으로 보이기
            editText.transformationMethod = null
        } else {
            // ●●● 로 숨기기
            editText.transformationMethod = PasswordTransformationMethod.getInstance()
        }

        // 커서 위치 유지
        editText.setSelection(if (cursorPos >= 0) cursorPos else editText.text.length)
    }
}
