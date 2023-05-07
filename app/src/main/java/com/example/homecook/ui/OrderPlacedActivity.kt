package com.example.homecook.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
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

        binding.apply {
            bckToHomeBtn.setOnClickListener {
                goToMainActivity()
            }
        }

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