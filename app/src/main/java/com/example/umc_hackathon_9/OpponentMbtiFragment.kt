package com.example.umc_hackathon_9

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.umc_hackathon_9.databinding.FragmentAnalysisBinding
import com.example.umc_hackathon_9.databinding.FragmentOpponentMbtiBinding

class OpponentMbtiFragment: Fragment() {
    lateinit var binding: FragmentOpponentMbtiBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOpponentMbtiBinding.inflate(inflater, container, false)
        binding.mbtiNextBtn.setOnClickListener {
            startActivity(Intent(context, LoadingActivity::class.java))
        }
        (activity as? MainActivity)?.showBottomNav(false)
        return binding.root
    }
}