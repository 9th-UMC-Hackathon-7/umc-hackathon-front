package com.example.umc_hackathon_9

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNav = findViewById(R.id.bottomNav)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, HomeFragment())
                .commit()

        }

        bottomNav.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.nav_home -> replaceFragment(HomeFragment())
                R.id.nav_add -> replaceFragment(AddFragment())
                R.id.nav_mypage-> replaceFragment(MypageFragment())
            }
            true

        }

        bottomNav.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.nav_home -> replaceFragment(HomeFragment())
                R.id.nav_add -> replaceFragment(OpponentMbtiFragment())
                R.id.nav_mypage-> replaceFragment(MypageFragment())
            }
            true
        }
        val target = intent.getStringExtra("target")
        if(target == "analysis_fragment"){
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, AnalysisFragment())
                .commit()
        }

//        val relationBottomSheet = RelationBottomSheet()
//        relationBottomSheet.show(supportFragmentManager, relationBottomSheet.tag)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragment)
            .commit()
    }
    fun showBottomNav(show: Boolean) {
        bottomNav.visibility = if (show) View.VISIBLE else View.GONE
    }
}

