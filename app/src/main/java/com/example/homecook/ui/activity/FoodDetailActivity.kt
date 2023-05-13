package com.example.homecook.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.homecook.databinding.ActivityFoodDetailBinding
import com.example.homecook.models.FoodItemModel
import com.example.homecook.repository.DataRepository

class FoodDetailActivity : AppCompatActivity() {

    private val TAG = "FoodDetailActivityTAG"
    private lateinit var _binding: ActivityFoodDetailBinding
    private val binding get() = _binding
    private lateinit var item: FoodItemModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFoodDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        item = intent.getSerializableExtra("foodItem") as FoodItemModel

        updateUI(item)

        onBackPressedDispatcher.addCallback {
            val intent = Intent(this@FoodDetailActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun updateUI(item: FoodItemModel) {
        binding.foodName.text = item.name
        binding.foodPrice.text = "₹ ".plus(item.price.toString())
        binding.foodImage.setImageResource(item.image)
        binding.addBtn.isVisible = item.count == 0
        binding.incrementLayout.isVisible = item.count > 0
        binding.count.text = item.count.toString()
        binding.addBtn.setOnClickListener {
            val index = DataRepository.foodItems.indexOf(item)
            item.count++
            updateUI(item)
            DataRepository.foodItems[index] = item
        }
        binding.plusBtn.setOnClickListener {
            val index = DataRepository.foodItems.indexOf(item)
            item.count++
            updateUI(item)
            DataRepository.foodItems[index] = item
        }
        binding.minusBtn.setOnClickListener {
            val index = DataRepository.foodItems.indexOf(item)
            item.count--
            updateUI(item)
            DataRepository.foodItems[index] = item
        }
    }
}