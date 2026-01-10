package com.example.umc_hackathon_9

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import com.example.umc_hackathon_9.databinding.ActivityStartBinding

class StartActivity: AppCompatActivity() {
    lateinit var binding: ActivityStartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val welcomeText = buildSpannedString {
            append("반갑습니다.\n당신의 갈등 중재자 ")

            color(resources.getColor(R.color.main)) {
                bold {
                    append("U-MC")
                }
            }

            append("입니다.")
        }
        binding.tvWelcome.text = welcomeText
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}