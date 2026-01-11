package com.example.umc_hackathon_9

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class AddFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvPercent = view.findViewById<TextView>(R.id.tvPercent)
        val slider = view.findViewById<com.google.android.material.slider.Slider>(R.id.slider)

        val trackContainer = view.findViewById<View>(R.id.trackContainer)
        val trackActive = view.findViewById<View>(R.id.trackActive)
        val thumbDot = view.findViewById<View>(R.id.thumbDot)


        val btnNext = view.findViewById<Button>(R.id.btnNext)

//        btnNext.setOnClickListener {
//            parentFragmentManager.beginTransaction()
//                .replace(R.id.contentArea, ChatFragment())
//                .addToBackStack(null)
//                .commit()
//        }

        fun applyProgress(p: Int) {
            tvPercent.text = "${p}%"

            val w = trackContainer.width
            if (w <= 0) return

            val ratio = p / 100f

            trackActive.pivotX = 0f
            trackActive.scaleX = ratio

            val maxX = (w - thumbDot.width).coerceAtLeast(0)
            thumbDot.translationX = maxX * ratio
        }

        trackContainer.post {
            applyProgress(slider.value.toInt())
        }

        slider.addOnChangeListener { _, value, _ ->
            applyProgress(value.toInt())
        }
    }

}
