package com.example.fundamentalsubmission

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val duration:Long = 3000

        Handler(Looper.getMainLooper()).postDelayed({
            val moveIntent = Intent(this, MainActivity::class.java)
            startActivity(moveIntent)
        }, duration)
    }
}