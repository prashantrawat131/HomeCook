package com.example.homecook.ui

import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.homecook.R
import com.example.homecook.databinding.ActivitySplashScreenBinding
import com.example.homecook.shared_pref.SharedPref

class SplashScreen : AppCompatActivity() {
    private val TAG = "SplashScreenTAG"
    private lateinit var _binding: ActivitySplashScreenBinding
    private val binding get() = _binding
    private lateinit var sharedPref: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPref = SharedPref(this)

        binding.logo.alpha = 0f
        binding.logo.animate()
            .alpha(1f)
            .setDuration(2000)
            .withEndAction {
                if(sharedPref.isIntroShown()){
                    if (!sharedPref.isUserLoggedIn()) {
                        val intent = android.content.Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        val intent = android.content.Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }else{
                    val intent = android.content.Intent(this, IntroActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

    }
}