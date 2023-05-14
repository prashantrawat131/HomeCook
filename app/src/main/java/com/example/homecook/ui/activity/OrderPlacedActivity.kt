package com.example.homecook.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Looper
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.homecook.databinding.ActivityOrderPlacedBinding
import com.example.homecook.repository.DataRepository

class OrderPlacedActivity : AppCompatActivity() {
    private val TAG = "OrderPlacedActivityTAG"
    private lateinit var _binding: ActivityOrderPlacedBinding
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityOrderPlacedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.orderPlacedLayout.isVisible = false
        binding.loadingLayout.isVisible = true

        binding.apply {
            bckToHomeBtn.setOnClickListener {
                goToMainActivity()
            }
        }

        val handler = android.os.Handler(Looper.getMainLooper())
        val update = Runnable {
            binding.orderPlacedLayout.isVisible = true
            binding.loadingLayout.isVisible = false
//            start konfetti animation

        }
        val timer = java.util.Timer()
        timer.schedule(object : java.util.TimerTask() {
            override fun run() {
                handler.post(update)
            }
        }, 3000)

        onBackPressedDispatcher.addCallback {
            goToMainActivity()
        }
    }

    private fun goToMainActivity() {
        DataRepository.foodItems.forEach {
            it.count = 0
        }

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}