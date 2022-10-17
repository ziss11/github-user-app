package com.example.fundamentalsubmission.presentation.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.example.fundamentalsubmission.MainActivity
import com.example.fundamentalsubmission.R
import com.example.fundamentalsubmission.dataStore
import com.example.fundamentalsubmission.databinding.ActivitySplashBinding
import com.example.fundamentalsubmission.presentation.viewmodels.MainViewModel
import com.example.fundamentalsubmission.presentation.viewmodels.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private lateinit var factory: ViewModelFactory

    private val mainViewModel: MainViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactory.getInstance(this, dataStore)

        getThemeData()

        CoroutineScope(Dispatchers.Main).launch {
            delay(delaySec)
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }
    }

    private fun getThemeData() {
        mainViewModel.getThemeSetting().observe(this) {
            when (it) {
                0 -> {
                    binding.splashActivity.setBackgroundColor(
                        ContextCompat.getColor(
                            this,
                            R.color.white
                        )
                    )
                    DrawableCompat.setTint(
                        binding.logo.drawable,
                        ContextCompat.getColor(this, R.color.black)
                    )
                }
                1 -> {
                    DrawableCompat.setTint(
                        binding.logo.drawable,
                        ContextCompat.getColor(this, R.color.white)
                    )
                }
            }
        }
    }

    companion object {
        private const val delaySec = 3000L
    }
}