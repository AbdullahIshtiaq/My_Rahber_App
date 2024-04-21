package com.example.my_rahber_app

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.my_rahber_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.apply {

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, CourseFragment()).commit()

            bottomNavigationView.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.nav_profile -> {
                        // Handle Home
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, ProfileFragment()).commit()

                        true
                    }

                    R.id.nav_course -> {
                        // Handle Search
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, CourseFragment()).commit()
                        true
                    }

                    else -> false
                }
            }


        }
    }
}