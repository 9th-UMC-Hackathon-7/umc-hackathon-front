package com.example.umc_hackathon_9

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val logo = findViewById<ImageView>(R.id.imgLogo)
        val btnStart = findViewById<Button>(R.id.btnStart)

        // 로고 서서히 나타나는 애니메이션 (1초)
        logo.animate()
            .alpha(1f)        // 0 → 1
            .setDuration(1000)
            .setStartDelay(800)   // 0.3초 있다가 시작 (원하면 조절)
            .start()

        // 시작하기 버튼 클릭 시 메인으로 이동
        btnStart.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()   // 스플래시 액티비티는 종료
        }
    }
}
