package com.example.homecook.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.homecook.databinding.ActivitySettingsBinding
import com.example.homecook.shared_pref.SharedPref


import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsActivity : AppCompatActivity() {

    private val TAG = "SettingsActivityTAG"
    private lateinit var _binding: ActivitySettingsBinding
    private val binding get() = _binding
    private lateinit var sharedPref: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPref = SharedPref(this)

        binding.enableLockscreenSwitch.isChecked = sharedPref.isLockscreenEnabled()

        binding.enableLockscreenSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                // The switch is enabled/checked
                sharedPref.storeIsLockscreenEnabled(true)
            } else {
                // The switch is disabled
                sharedPref.storeIsLockscreenEnabled(false)
            }
        }

        /*Go to main activity on back press*/
        onBackPressedDispatcher.addCallback {
            val intent = Intent(this@SettingsActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}