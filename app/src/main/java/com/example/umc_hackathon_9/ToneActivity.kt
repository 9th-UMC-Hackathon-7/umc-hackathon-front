package com.example.umc_hackathon_9

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.umc_hackathon_9.databinding.ActivityToneBinding

class ToneActivity: AppCompatActivity() {
    lateinit var binding: ActivityToneBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityToneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.defaultToneCl.setOnClickListener {
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
        binding.friendlyToneCl.setOnClickListener {
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
        binding.counselorToneCl.setOnClickListener {
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
        binding.toneConfirmBtn.setOnClickListener {
            startActivity(Intent(this, StartActivity::class.java))
            finish()
        }
    }
}