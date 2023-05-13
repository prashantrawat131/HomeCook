package com.example.homecook.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.homecook.databinding.ActivityLoginBinding
import com.example.homecook.utils.CO

class LoginActivity : AppCompatActivity() {
    private val TAG = "LoginActivity"
    private lateinit var _binding: ActivityLoginBinding
    private val binding get() = _binding
    private val SEND_SMS_PERMISSION_REQUEST_CODE = 111

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.submitBtn.setOnClickListener {
            val phoneNumber = binding.phoneNumber.editText?.text.toString()
            if (phoneNumber.isEmpty()) {
                CO.toast(this, "Please enter your phone number")
                return@setOnClickListener
            }
            if (phoneNumber.length != 10) {
                CO.toast(this, "Phone number must be 10 digits.")
                return@setOnClickListener
            }

            sendOtp(phoneNumber)
        }

        askForSmsPermission()

        onBackPressedDispatcher.addCallback{
            finishAffinity()
        }
    }

    private fun sendOtp(phoneNumber: String) {
      /*  val otp = (1000..9999).random().toString()
        SmsManager.getDefault().sendTextMessage(
            phoneNumber,
            null,
            "Hi, welcome to Home Cook.\n\nYour OTP is $otp",
            null,
            null
        )*/
        val otp = "1234"
        CO.toast(this, "OTP sent to $phoneNumber")
        val intent = Intent(this, OtpActivity::class.java)
        intent.putExtra("phoneNumber", phoneNumber)
        intent.putExtra("otp", otp)
        startActivity(intent)
        finish()
    }

    //    Ask for send sms permission
    private fun askForSmsPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.SEND_SMS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.SEND_SMS),
                SEND_SMS_PERMISSION_REQUEST_CODE
            )
        }
    }

    //    Handle permission request result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if ((requestCode == SEND_SMS_PERMISSION_REQUEST_CODE) && (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
            CO.toast(this, "Permission granted")
        } else {
            CO.toast(this, "Permission denied")
        }
    }
}