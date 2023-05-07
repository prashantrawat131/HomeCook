package com.example.homecook.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homecook.adapters.OrdersAdapter
import com.example.homecook.databinding.ActivityOrdersBinding
import com.example.homecook.models.FoodItemModel
import com.example.homecook.repository.DataRepository

class OrdersActivity : AppCompatActivity() {

    private val TAG = "OrdersActivityTAG"
    private lateinit var _binding: ActivityOrdersBinding
    private val binding get() = _binding
    var totalPrice = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpOrdersRecyclerView()

        updateItem(DataRepository.foodItems[0])

        onBackPressedDispatcher.addCallback {
            goToMainActivity()
        }

        binding.apply {
            placeOrderBtn.setOnClickListener {
                if (totalPrice > 0) {
                    goToOrderPlacedScreen()
                } else {
                    Toast.makeText(
                        this@OrdersActivity,
                        "Please order something",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun goToOrderPlacedScreen() {
        val intent = Intent(this, OrderPlacedActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun setUpOrdersRecyclerView() {
        val orders = arrayListOf<FoodItemModel>()
        DataRepository.foodItems.forEach {
            if (it.count > 0) {
                orders.add(it)
            }
        }
        binding.ordersRv.layoutManager = LinearLayoutManager(this)
        val adapter = OrdersAdapter(orders) {
            updateItem(it)
        }
        binding.ordersRv.adapter = adapter
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