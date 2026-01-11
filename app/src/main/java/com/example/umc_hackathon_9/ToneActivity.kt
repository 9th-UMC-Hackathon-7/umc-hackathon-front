package com.example.umc_hackathon_9

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.umc_hackathon_9.databinding.ActivityToneBinding
import kotlinx.coroutines.launch

class ToneActivity : AppCompatActivity() {

    private lateinit var binding: ActivityToneBinding
    private val authRepository = AuthRepository()

    // 이전 화면들에서 전달받은 값들
    private var loginId: String? = null
    private var name: String? = null
    private var password: String? = null
    private var mbti: String? = null
    private var avatar: String? = null
    private var gender: String? = null

    // 이 화면에서 선택한 말투 (NORMAL / FRIENDLY / COUNSELOR)
    private var intonation: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityToneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 🔹 MbtiActivity에서 넘긴 값 받기
        loginId = intent.getStringExtra("loginId")
        name = intent.getStringExtra("name")
        password = intent.getStringExtra("password")
        mbti = intent.getStringExtra("mbti")
        avatar = intent.getStringExtra("avatar")   // 아직 안 넘기면 null
        gender = intent.getStringExtra("gender")   // 아직 안 넘기면 null

        // avatar / gender 를 아직 안 넘기고 있다면 기본값 지정 (원하면 수정!)
        if (avatar.isNullOrEmpty()) avatar = "AVATAR1"
        if (gender.isNullOrEmpty()) gender = "FEMALE"

        initToneButtons()
        initConfirmButton()
    }

    private fun initToneButtons() {
        // NORMAL (기본)
        binding.defaultToneCl.setOnClickListener {
            intonation = "NORMAL"

            binding.defaultToneCl.setBackgroundResource(R.drawable.tone_selected_button)
            binding.counselorToneCl.setBackgroundResource(R.drawable.tone_button)
            binding.friendlyToneCl.setBackgroundResource(R.drawable.tone_button)

            binding.defaultTitleTv.setTextColor(resources.getColor(R.color.main))
            binding.defaultDescriptionTv.setTextColor(resources.getColor(R.color.main))
            binding.defaultCheckIv.visibility = View.VISIBLE

            binding.friendlyTitleTv.setTextColor(Color.parseColor("#525252"))
            binding.friendlyDescriptionTv.setTextColor(Color.parseColor("#525252"))
            binding.friendlyCheckIv.visibility = View.GONE

            binding.counselorTitleTv.setTextColor(Color.parseColor("#525252"))
            binding.counselorDescriptionTv.setTextColor(Color.parseColor("#525252"))
            binding.counselorCheckIv.visibility = View.GONE
        }

        // FRIENDLY (친근)
        binding.friendlyToneCl.setOnClickListener {
            intonation = "FRIENDLY"

            binding.defaultToneCl.setBackgroundResource(R.drawable.tone_button)
            binding.counselorToneCl.setBackgroundResource(R.drawable.tone_button)
            binding.friendlyToneCl.setBackgroundResource(R.drawable.tone_selected_button)

            binding.defaultTitleTv.setTextColor(Color.parseColor("#525252"))
            binding.defaultDescriptionTv.setTextColor(Color.parseColor("#525252"))
            binding.defaultCheckIv.visibility = View.GONE

            binding.friendlyTitleTv.setTextColor(resources.getColor(R.color.main))
            binding.friendlyDescriptionTv.setTextColor(resources.getColor(R.color.main))
            binding.friendlyCheckIv.visibility = View.VISIBLE

            binding.counselorTitleTv.setTextColor(Color.parseColor("#525252"))
            binding.counselorDescriptionTv.setTextColor(Color.parseColor("#525252"))
            binding.counselorCheckIv.visibility = View.GONE
        }

        // COUNSELOR (상담)
        binding.counselorToneCl.setOnClickListener {
            intonation = "COUNSELOR"

            binding.defaultToneCl.setBackgroundResource(R.drawable.tone_button)
            binding.counselorToneCl.setBackgroundResource(R.drawable.tone_selected_button)
            binding.friendlyToneCl.setBackgroundResource(R.drawable.tone_button)

            binding.defaultTitleTv.setTextColor(Color.parseColor("#525252"))
            binding.defaultDescriptionTv.setTextColor(Color.parseColor("#525252"))
            binding.defaultCheckIv.visibility = View.GONE

            binding.friendlyTitleTv.setTextColor(Color.parseColor("#525252"))
            binding.friendlyDescriptionTv.setTextColor(Color.parseColor("#525252"))
            binding.friendlyCheckIv.visibility = View.GONE

            binding.counselorTitleTv.setTextColor(resources.getColor(R.color.main))
            binding.counselorDescriptionTv.setTextColor(resources.getColor(R.color.main))
            binding.counselorCheckIv.visibility = View.VISIBLE
        }
    }

    private fun initConfirmButton() {
        binding.toneConfirmBtn.setOnClickListener {
            // 말투 선택했는지 확인
            val selectedIntonation = intonation
            if (selectedIntonation == null) {
                Toast.makeText(this, "말투를 하나 선택해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 필수 값들 확인
            val loginId = this.loginId
            val name = this.name
            val password = this.password
            val mbti = this.mbti
            val avatar = this.avatar
            val gender = this.gender

            if (loginId.isNullOrEmpty() || name.isNullOrEmpty() || password.isNullOrEmpty() || mbti.isNullOrEmpty()) {
                Toast.makeText(this, "회원 정보가 올바르지 않습니다. 처음부터 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // ✅ 여기서 실제 회원가입 API 호출
            binding.toneConfirmBtn.isEnabled = false

            lifecycleScope.launch {
                val result = authRepository.signUp(
                    name = name,
                    loginId = loginId,
                    password = password,
                    avatar = avatar ?: "AVATAR1",
                    gender = gender ?: "FEMALE",
                    mbti = mbti,
                    intonation = selectedIntonation
                )

                result
                    .onSuccess { response ->
                        if (response.resultType == "SUCCESS" && response.success != null) {
                            Toast.makeText(
                                this@ToneActivity,
                                "회원가입이 완료되었습니다!",
                                Toast.LENGTH_SHORT
                            ).show()

                            // 회원가입 성공 → StartActivity로 이동
                            startActivity(Intent(this@ToneActivity, StartActivity::class.java))
                            finish()
                        } else {
                            val msg = response.error?.reason ?: "회원가입에 실패했습니다."
                            Toast.makeText(this@ToneActivity, msg, Toast.LENGTH_SHORT).show()
                        }
                    }
                    .onFailure { e ->
                        e.printStackTrace()
                        Toast.makeText(
                            this@ToneActivity,
                            "네트워크 또는 서버 오류가 발생했습니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                binding.toneConfirmBtn.isEnabled = true
            }
        }
    }
}
