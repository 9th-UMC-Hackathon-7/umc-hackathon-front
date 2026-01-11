package com.example.umc_hackathon_9

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_hackathon_9.data.entities.AnalysisData
import com.example.umc_hackathon_9.databinding.AnalysisItemBinding
import com.example.umc_hackathon_9.databinding.FragmentAnalysisBinding

class AnalysisFragment: Fragment() {
    lateinit var binding: FragmentAnalysisBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAnalysisBinding.inflate(inflater, container, false)
        (activity as? MainActivity)?.showBottomNav(false)
        val anaylsisText = buildSpannedString {
            append("상대방에게 제공할 답변을 받고 싶거나\n" +
                    "답변 피드백을 받고 싶다면")

            color(resources.getColor(R.color.main)) {
                bold {
                    append("답변 생성")
                }
            }

            append("을 눌러주세요")
        }
        binding.analysisTv.text = anaylsisText
        // Dummy Data
        val analysisList = listOf(
            AnalysisData("현재 상황 한 줄 요약", "반복되는 약속 취소로 인해 그동안 쌓여왔던 서운함이 폭발하며 갈등이 깊어진 상황입니다."),
            AnalysisData("(유저)의 감정 상태", "참아왔던 분노가 한 번에 터져 나오며, 자신의 시간과 노력이 존중받지 못했다는 배신감을 느끼고 있습니다."),
            AnalysisData("상대방의 감정 상태", "유저의 갑작스러운 분노를 당황스러워하며, 상황의 심각성을 인지하기보다 방어적인 태도를 보이고 있습니다."),
            AnalysisData("추천 행동", "잠시 대화를 멈추고 각자의 시간을 가진 뒤 '이번 사건으로 느낀 구체적인 감정' 위주로 다시 전달해 보세요.")
        )
        binding.analysisRv.layoutManager = LinearLayoutManager(context)
        binding.analysisRv.adapter = AnalysisAdapter(analysisList)

        binding.createResponseBtn.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.main_container, CreateResponseFragment())
            transaction.commit()
        }
        return binding.root
    }
}