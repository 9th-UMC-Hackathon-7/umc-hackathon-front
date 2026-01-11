package com.example.umc_hackathon_9

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_hackathon_9.databinding.ActivityMbtiBinding

class MbtiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMbtiBinding

    // 이전 화면에서 넘어온 값들
    private var loginId: String? = null
    private var name: String? = null
    private var password: String? = null
    private var gender: String? = null    // ⭐ 성별 추가

    // 사용자가 선택한 MBTI 각 요소
    private var ei: String? = null   // E or I
    private var ns: String? = null   // N or S
    private var ft: String? = null   // F or T
    private var jp: String? = null   // J or P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMbtiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 🔹 SignUpActivity 에서 넘어온 값 받기
        loginId = intent.getStringExtra("loginId")
        name = intent.getStringExtra("name")
        password = intent.getStringExtra("password")
        gender = intent.getStringExtra("gender")   // ⭐ 성별 받기

        initButtons()
        initNextButton()
    }

    private fun initButtons() {
        // E / I
        binding.EBtn.setOnClickListener {
            ei = "E"
            binding.EBtn.setBackgroundResource(R.drawable.mbti_selected_button)
            binding.IBtn.setBackgroundResource(R.drawable.mbti_button)
            binding.EBtn.setTextColor(resources.getColor(R.color.white))
            binding.IBtn.setTextColor(resources.getColor(R.color.main))
        }
        binding.IBtn.setOnClickListener {
            ei = "I"
            binding.EBtn.setBackgroundResource(R.drawable.mbti_button)
            binding.IBtn.setBackgroundResource(R.drawable.mbti_selected_button)
            binding.EBtn.setTextColor(resources.getColor(R.color.main))
            binding.IBtn.setTextColor(resources.getColor(R.color.white))
        }

        // N / S
        binding.NBtn.setOnClickListener {
            ns = "N"
            binding.NBtn.setBackgroundResource(R.drawable.mbti_selected_button)
            binding.SBtn.setBackgroundResource(R.drawable.mbti_button)
            binding.NBtn.setTextColor(resources.getColor(R.color.white))
            binding.SBtn.setTextColor(resources.getColor(R.color.main))
        }
        binding.SBtn.setOnClickListener {
            ns = "S"
            binding.NBtn.setBackgroundResource(R.drawable.mbti_button)
            binding.SBtn.setBackgroundResource(R.drawable.mbti_selected_button)
            binding.NBtn.setTextColor(resources.getColor(R.color.main))
            binding.SBtn.setTextColor(resources.getColor(R.color.white))
        }

        // F / T
        binding.FBtn.setOnClickListener {
            ft = "F"
            binding.FBtn.setBackgroundResource(R.drawable.mbti_selected_button)
            binding.TBtn.setBackgroundResource(R.drawable.mbti_button)
            binding.FBtn.setTextColor(resources.getColor(R.color.white))
            binding.TBtn.setTextColor(resources.getColor(R.color.main))
        }
        binding.TBtn.setOnClickListener {
            ft = "T"
            binding.FBtn.setBackgroundResource(R.drawable.mbti_button)
            binding.TBtn.setBackgroundResource(R.drawable.mbti_selected_button)
            binding.FBtn.setTextColor(resources.getColor(R.color.main))
            binding.TBtn.setTextColor(resources.getColor(R.color.white))
        }

        // J / P
        binding.JBtn.setOnClickListener {
            jp = "J"
            binding.JBtn.setBackgroundResource(R.drawable.mbti_selected_button)
            binding.PBtn.setBackgroundResource(R.drawable.mbti_button)
            binding.JBtn.setTextColor(resources.getColor(R.color.white))
            binding.PBtn.setTextColor(resources.getColor(R.color.main))
        }
        binding.PBtn.setOnClickListener {
            jp = "P"
            binding.JBtn.setBackgroundResource(R.drawable.mbti_button)
            binding.PBtn.setBackgroundResource(R.drawable.mbti_selected_button)
            binding.JBtn.setTextColor(resources.getColor(R.color.main))
            binding.PBtn.setTextColor(resources.getColor(R.color.white))
        }
    }

    private fun initNextButton() {
        binding.mbtiNextBtn.setOnClickListener {
            // 네 글자 다 선택했는지 체크
            val eiPart = ei
            val nsPart = ns
            val ftPart = ft
            val jpPart = jp

            if (eiPart == null || nsPart == null || ftPart == null || jpPart == null) {
                Toast.makeText(this, "MBTI 네 글자를 모두 선택해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val mbti = eiPart + nsPart + ftPart + jpPart  // 예: ENFP

            // ToneActivity로 이동 + 모든 정보 전달
            val intent = Intent(this, ToneActivity::class.java).apply {
                putExtra("loginId", loginId)
                putExtra("name", name)
                putExtra("password", password)
                putExtra("mbti", mbti)
                putExtra("gender", gender)   // ⭐ 성별도 같이 넘기기
            }
            startActivity(intent)
            finish()
        }
    }
}
