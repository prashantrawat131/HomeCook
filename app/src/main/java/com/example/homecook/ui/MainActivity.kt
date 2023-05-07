package com.example.homecook.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homecook.adapters.FoodItemRvAdapter
import com.example.homecook.databinding.ActivityMainBinding
import com.example.homecook.models.FoodItemModel
import com.example.homecook.repository.DataRepository

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivityTAG"
    private lateinit var _binding: ActivityMainBinding
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpTodayMenuRecyclerView()

        binding.ordersBtn.setOnClickListener {
            goToOrdersActivity()
        }

        onBackPressedDispatcher.addCallback {
            finishAffinity()
        }
    }

    private fun goToOrdersActivity() {
        val intent = Intent(this, OrdersActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun setUpTodayMenuRecyclerView() {
//        Set up the recycler view

        val todayFoodMenu = arrayListOf<FoodItemModel>()
        val oldPeopleFoodMenu = arrayListOf<FoodItemModel>()
        val childrenFoodMenu = arrayListOf<FoodItemModel>()
        val sweetFoodMenu = arrayListOf<FoodItemModel>()

        DataRepository.foodItems.forEach {
            when (it.itemType) {
                FoodItemModel.ItemType.MAIN -> todayFoodMenu.add(it)
                FoodItemModel.ItemType.OLD -> oldPeopleFoodMenu.add(it)
                FoodItemModel.ItemType.CHILDREN -> childrenFoodMenu.add(it)
                FoodItemModel.ItemType.SWEET -> sweetFoodMenu.add(it)
            }
        }

        val lists = hashMapOf(
            binding.todayMenuRv to todayFoodMenu,
            binding.oldRv to oldPeopleFoodMenu,
            binding.childrenRv to childrenFoodMenu,
            binding.sweetRv to sweetFoodMenu
        )

        lists.forEach {
            val recyclerView = it.key
            val linearLayoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.layoutManager = linearLayoutManager
            val adapter = FoodItemRvAdapter(it.value)
            recyclerView.adapter = adapter
        }
    }
}