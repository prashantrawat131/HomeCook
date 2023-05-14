package com.example.homecook.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.homecook.databinding.ActivityAdminBinding
import com.example.homecook.firebase.FirebaseUtil

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminActivity : AppCompatActivity() {
    private val TAG = "AdminActivityTAG"
    private lateinit var _binding: ActivityAdminBinding
    private val binding get() = _binding
    private lateinit var firebaseUtil: FirebaseUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseUtil = FirebaseUtil(this)

        binding.refreshServer.setOnClickListener {
            firebaseUtil.uploadFoodsData()
        }
    }
}