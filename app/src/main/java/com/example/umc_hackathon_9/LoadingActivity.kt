package com.example.umc_hackathon_9

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import com.example.umc_hackathon_9.databinding.ActivityLoadingBinding

class LoadingActivity: AppCompatActivity() {
    lateinit var binding: ActivityLoadingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("target", "analysis_fragment")
            startActivity(intent)
            finish()
        }, 3000)
    }
}