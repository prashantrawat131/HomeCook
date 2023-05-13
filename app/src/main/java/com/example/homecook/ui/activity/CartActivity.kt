package com.example.homecook.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homecook.adapters.CartAdapter
import com.example.homecook.databinding.ActivityCartBinding
import com.example.homecook.models.FoodItemModel
import com.example.homecook.repository.DataRepository

class CartActivity : AppCompatActivity() {

    private val TAG = "OrdersActivityTAG"
    private lateinit var _binding: ActivityCartBinding
    private val binding get() = _binding
    var totalPrice = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpCartRecyclerView()

        updateItem(DataRepository.foodItems[0])

        onBackPressedDispatcher.addCallback {
            goToMainActivity()
        }

        binding.apply {
            placeOrderBtn.setOnClickListener {
                if (totalPrice > 0) {
                    goToAddressScreen()
                } else {
                    Toast.makeText(
                        this@CartActivity,
                        "Please order something",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun goToAddressScreen() {
        val intent = Intent(this, AddressActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun setUpCartRecyclerView() {
        val orders = arrayListOf<FoodItemModel>()
        DataRepository.foodItems.forEach {
            if (it.count > 0) {
                orders.add(it)
            }
        }
        binding.itmesRv.layoutManager = LinearLayoutManager(this)
        val adapter = CartAdapter(orders) {
            updateItem(it)
        }
        binding.itmesRv.adapter = adapter
    }


    private fun updateItem(item: FoodItemModel) {
        var index = -1
        DataRepository.foodItems.forEach {
            if (it.image == item.image) {
                index = DataRepository.foodItems.indexOf(it)
                return@forEach
            }
        }

        if (index != -1) {
            DataRepository.foodItems[index] = item
        }

        DataRepository.foodItems.forEach {
            totalPrice += it.price.times(it.count)
        }

        binding.apply {
            totalPriceTv.text = "Rs. ".plus(totalPrice.toString())
        }
    }

}