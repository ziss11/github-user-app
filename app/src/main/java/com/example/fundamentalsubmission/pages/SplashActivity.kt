package com.example.fundamentalsubmission.pages

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fundamentalsubmission.MainActivity
import com.example.fundamentalsubmission.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}