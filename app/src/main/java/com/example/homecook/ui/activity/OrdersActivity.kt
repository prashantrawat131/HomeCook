package com.example.homecook.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homecook.adapters.OrdersAdapter
import com.example.homecook.databinding.ActivityOrdersBinding
import com.example.homecook.repository.DataRepository

class OrdersActivity : AppCompatActivity() {
    private val TAG = "OrdersActivityTAG"
    private lateinit var _binding: ActivityOrdersBinding
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpOrdersRecyclerView()

        onBackPressedDispatcher.addCallback {
            val intent = Intent(this@OrdersActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setUpOrdersRecyclerView() {
        binding.ordersRecyclerview.apply {
            layoutManager = LinearLayoutManager(this@OrdersActivity)
            adapter = OrdersAdapter(DataRepository.ordersList)
        }
    }
}