package com.example.umc_hackathon_9

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_hackathon_9.databinding.ActivityMbtiBinding

class MbtiActivity: AppCompatActivity() {
    lateinit var binding: ActivityMbtiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMbtiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.EBtn.setOnClickListener {
            binding.EBtn.setBackgroundResource(R.drawable.mbti_selected_button)
            binding.IBtn.setBackgroundResource(R.drawable.mbti_button)
            binding.EBtn.setTextColor(resources.getColor(R.color.white))
            binding.IBtn.setTextColor(resources.getColor(R.color.main))
        }
        binding.IBtn.setOnClickListener {
            binding.EBtn.setBackgroundResource(R.drawable.mbti_button)
            binding.IBtn.setBackgroundResource(R.drawable.mbti_selected_button)
            binding.EBtn.setTextColor(resources.getColor(R.color.main))
            binding.IBtn.setTextColor(resources.getColor(R.color.white))
        }
        binding.NBtn.setOnClickListener {
            binding.NBtn.setBackgroundResource(R.drawable.mbti_selected_button)
            binding.SBtn.setBackgroundResource(R.drawable.mbti_button)
            binding.NBtn.setTextColor(resources.getColor(R.color.white))
            binding.SBtn.setTextColor(resources.getColor(R.color.main))
        }
        binding.SBtn.setOnClickListener {
            binding.NBtn.setBackgroundResource(R.drawable.mbti_button)
            binding.SBtn.setBackgroundResource(R.drawable.mbti_selected_button)
            binding.NBtn.setTextColor(resources.getColor(R.color.main))
            binding.SBtn.setTextColor(resources.getColor(R.color.white))
        }
        binding.FBtn.setOnClickListener {
            binding.FBtn.setBackgroundResource(R.drawable.mbti_selected_button)
            binding.TBtn.setBackgroundResource(R.drawable.mbti_button)
            binding.FBtn.setTextColor(resources.getColor(R.color.white))
            binding.TBtn.setTextColor(resources.getColor(R.color.main))
        }
        binding.TBtn.setOnClickListener {
            binding.FBtn.setBackgroundResource(R.drawable.mbti_button)
            binding.TBtn.setBackgroundResource(R.drawable.mbti_selected_button)
            binding.FBtn.setTextColor(resources.getColor(R.color.main))
            binding.TBtn.setTextColor(resources.getColor(R.color.white))
        }
        binding.JBtn.setOnClickListener {
            binding.JBtn.setBackgroundResource(R.drawable.mbti_selected_button)
            binding.PBtn.setBackgroundResource(R.drawable.mbti_button)
            binding.JBtn.setTextColor(resources.getColor(R.color.white))
            binding.PBtn.setTextColor(resources.getColor(R.color.main))
        }
        binding.PBtn.setOnClickListener {
            binding.JBtn.setBackgroundResource(R.drawable.mbti_button)
            binding.PBtn.setBackgroundResource(R.drawable.mbti_selected_button)
            binding.JBtn.setTextColor(resources.getColor(R.color.main))
            binding.PBtn.setTextColor(resources.getColor(R.color.white))
        }
        binding.mbtiNextBtn.setOnClickListener {
            startActivity(Intent(this, ToneActivity::class.java))
            finish()
        }
    }
}