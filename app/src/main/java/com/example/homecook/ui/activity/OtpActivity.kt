package com.example.homecook.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.homecook.databinding.ActivityOtpBinding
import com.example.homecook.firebase.FirebaseUtil
import com.example.homecook.shared_pref.SharedPref
import com.example.homecook.utils.CO

class OtpActivity : AppCompatActivity() {
    private val TAG = "OtpActivityTAG"
    private lateinit var _binding: ActivityOtpBinding
    private val binding get() = _binding
    private lateinit var phoneNumber: String
    private lateinit var otp: String
    private lateinit var sharedPref: SharedPref
    private lateinit var firebaseUtil: FirebaseUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseUtil = FirebaseUtil(this)
        sharedPref = SharedPref(this)

        phoneNumber = intent.getStringExtra("phoneNumber") ?: ""
        otp = intent.getStringExtra("otp") ?: ""

        binding.submitBtn.setOnClickListener {
            val userOtp = binding.otp.editText?.text.toString()
            if (userOtp.isEmpty()) {
                CO.toast(this, "Please enter the OTP")
                return@setOnClickListener
            }
            if (userOtp != otp) {
                CO.toast(this, "Incorrect OTP")
                return@setOnClickListener
            }
            sharedPref.storePhoneNumber(phoneNumber)
            sharedPref.storeIsLogin(true)
            CO.toast(this, "OTP verified")
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}