package com.example.umc_hackathon_9

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.umc_hackathon_9.databinding.FragmentCreateResponseBinding

class CreateResponseFragment: Fragment() {
    lateinit var binding: FragmentCreateResponseBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateResponseBinding.inflate(inflater, container, false)
        (activity as? MainActivity)?.showBottomNav(false)
        return binding.root
    }
}