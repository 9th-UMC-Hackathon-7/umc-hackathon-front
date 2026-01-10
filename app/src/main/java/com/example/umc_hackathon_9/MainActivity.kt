package com.example.umc_hackathon_9

import android.os.Bundle
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

        // 첫 화면
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, HomeFragment())
                .commit()
<<<<<<< HEAD
=======
        }

        bottomNav.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.nav_home -> replaceFragment(HomeFragment())
                R.id.nav_add -> replaceFragment(AddFragment())
                R.id.nav_mypage-> replaceFragment(MypageFragment())
            }
            true
>>>>>>> dev
        }

        bottomNav.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.nav_home -> replaceFragment(HomeFragment())
                R.id.nav_add -> replaceFragment(AddFragment())
                R.id.nav_mypage-> replaceFragment(MypageFragment())
            }
            true
        }

//        val relationBottomSheet = RelationBottomSheet()
//        relationBottomSheet.show(supportFragmentManager, relationBottomSheet.tag)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragment)
            .commit()
    }
}

