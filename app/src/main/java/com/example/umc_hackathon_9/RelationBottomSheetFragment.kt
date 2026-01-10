package com.example.umc_hackathon_9

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class RelationBottomSheetFragment : BottomSheetDialogFragment(R.layout.fragment_relation) {

    companion object {
        const val RESULT_KEY = "relation_result_key"
        const val BUNDLE_RELATION = "bundle_relation"
    }

    private var selected: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnClose = view.findViewById<View>(R.id.close)
        val btnConfirm = view.findViewById<AppCompatButton>(R.id.complete)

        val lovers = view.findViewById<AppCompatButton>(R.id.lovers_btn)
        val family = view.findViewById<AppCompatButton>(R.id.family_btn)
        val friend = view.findViewById<AppCompatButton>(R.id.friend_btn)
        val work = view.findViewById<AppCompatButton>(R.id.work_btn)
        val others = view.findViewById<AppCompatButton>(R.id.others_btn)
        val write = view.findViewById<AppCompatButton>(R.id.write_btn)

        val options = listOf(lovers, family, friend, work, others, write)

        fun setPicked(btn: AppCompatButton, value: String) {
            selected = value
            // selector(drawable + text color selector) 쓴다고 했으니 selected 상태만 잡아주면 됨
            options.forEach { it.isSelected = (it == btn) }

            // 선택 완료 버튼 활성화(원하면)
            btnConfirm.isEnabled = true
            btnConfirm.alpha = 1f
        }

        // 초기: 비활성
        btnConfirm.isEnabled = false
        btnConfirm.alpha = 0.5f

        lovers.setOnClickListener { setPicked(lovers, "연인") }
        family.setOnClickListener { setPicked(family, "가족") }
        friend.setOnClickListener { setPicked(friend, "친구") }
        work.setOnClickListener { setPicked(work, "직장") }
        others.setOnClickListener { setPicked(others, "타인") }

        // 직접 입력: 일단 “직접 입력” 선택으로 처리(원하면 다음에 EditText 띄우자)
        write.setOnClickListener { setPicked(write, "직접 입력") }

        btnClose.setOnClickListener { dismiss() }

        btnConfirm.setOnClickListener {
            val result = selected ?: return@setOnClickListener
            parentFragmentManager.setFragmentResult(
                RESULT_KEY,
                Bundle().apply { putString(BUNDLE_RELATION, result) }
            )
            dismiss()
        }
    }
}
