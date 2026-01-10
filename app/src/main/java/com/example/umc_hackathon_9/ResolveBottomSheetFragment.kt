package com.example.umc_hackathon_9

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.slider.Slider

class ResolveBottomSheetFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.bottom_sheet_resolve, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvPercent = view.findViewById<TextView>(R.id.tvPercent)
        val slider = view.findViewById<com.google.android.material.slider.Slider>(R.id.slider)

        val trackContainer = view.findViewById<View>(R.id.trackContainer)
        val trackActive = view.findViewById<View>(R.id.trackActive)
        val thumbDot = view.findViewById<View>(R.id.thumbDot)

        fun applyProgress(p: Int) {
            tvPercent.text = "${p}%"

            val w = trackContainer.width
            if (w <= 0) return

            val ratio = p / 100f

            // 민트 트랙은 전체폭에서 비율로 scale
            trackActive.scaleX = ratio

            // thumb는 컨테이너 안에서 이동 (끝에선 원이 안 삐져나오게 보정)
            val maxX = (w - thumbDot.width).coerceAtLeast(0)
            thumbDot.translationX = maxX * ratio
        }

        // trackContainer와 thumbDot이 측정된 뒤 최초 적용
        trackContainer.post {
            applyProgress(slider.value.toInt())
        }

        slider.addOnChangeListener { _, value, _ ->
            applyProgress(value.toInt())
        }
    }

}
