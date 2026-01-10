package com.example.umc_hackathon_9

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.umc_hackathon_9.databinding.FragmentRelationBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialog

class RelationBottomSheet: BottomSheetDialogFragment(){
    lateinit var binding: FragmentRelationBinding
    companion object{
        const val TAG = "ModalBottomSheetRelation"
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRelationBinding.inflate(inflater, container, false)
        return binding.root
    }
}