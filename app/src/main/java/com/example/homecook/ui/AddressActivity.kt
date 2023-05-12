package com.example.homecook.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.homecook.databinding.ActivityAddressBinding
import com.example.homecook.utils.CO

class AddressActivity : AppCompatActivity() {
    private val TAG = "AddressActivityTAG"
    private lateinit var _binding: ActivityAddressBinding
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.nextBtn.setOnClickListener {
            if (binding.editTextStreetAddress.text.toString().isEmpty()) {
                CO.toast(this, "Please enter your street address")
                flashView(binding.editTextStreetAddress)
                return@setOnClickListener
            }

            if (binding.editTextCity.text.toString().isEmpty()) {
                CO.toast(this, "Please enter your city")
                return@setOnClickListener
            }

            if (binding.editTextState.text.toString().isEmpty()) {
                CO.toast(this, "Please enter your state")
                return@setOnClickListener
            }

            if (binding.editTextZipCode.text.toString().isEmpty()) {
                CO.toast(this, "Please enter your zip code")
                return@setOnClickListener
            }

            val intent = Intent(this, OrderPlacedActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun flashView(view: View) {
        view.animate()
            .alpha(0.5f)
            .setDuration(100)
            .withEndAction {
                view.animate()
                    .alpha(1f)
                    .setDuration(100)
                    .start()
            }
            .start()
    }
}