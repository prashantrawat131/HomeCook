package com.example.homecook.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homecook.R
import com.example.homecook.adapters.FoodItemRvAdapter
import com.example.homecook.databinding.ActivityMainBinding
import com.example.homecook.models.FoodItemModel
import com.example.homecook.repository.DataRepository
import com.example.homecook.shared_pref.SharedPref
import com.example.homecook.utils.CO
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivityTAG"
    private lateinit var _binding: ActivityMainBinding
    private val binding get() = _binding
    private lateinit var sharedPref: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = SharedPref(this)
        setUpTodayMenuRecyclerView()

        onBackPressedDispatcher.addCallback {
            finishAffinity()
        }

//        Handle material navigation drawer
        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.cart->{
                    goToCartActivity()
                }
                R.id.orders -> {
                    goToOrdersActivity()
                }
                R.id.logout -> {
                    showLogoutConfirmationDialog()
                }
                R.id.privacy -> {
//                    Open a link on chrome
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = android.net.Uri.parse("https://www.example.com")
                    startActivity(intent)
                }
                R.id.terms -> {
//                    Open a link on chrome
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = android.net.Uri.parse("https://www.example.com")
                    startActivity(intent)
                }
                R.id.settings -> {
                    val intent = Intent(this, SettingsActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            true
        }

        binding.topAppBar.setNavigationOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        val headerView = binding.navView.getHeaderView(0)
        headerView.findViewById<TextView>(R.id.phoneNumber).text = sharedPref.getPhoneNumber()

//        Click listener for topAppBar menu
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.orders -> {
                    var total = 0f
                    for (item in DataRepository.foodItems) {
                        total += item.price * item.count
                    }
                    if (total == 0f) {
                        CO.toast(this, "Please add some items to orders first.")
                        return@setOnMenuItemClickListener true
                    } else {
                        val intent = Intent(this, CartActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    true
                }
                else -> false
            }
        }
    }

    //    Function for alert dialog of logout confirmation
    private fun showLogoutConfirmationDialog() {
        val builder = MaterialAlertDialogBuilder(this)
        builder.setTitle("Logout")
        builder.setMessage("Are you sure you want to logout?")
        builder.setPositiveButton("Yes") { dialog, which ->
            sharedPref.clearAllData()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        builder.setNegativeButton("No") { dialog, which ->
            dialog.dismiss()
        }
        builder.show()
    }

    private fun goToCartActivity() {
        val intent = Intent(this, CartActivity::class.java)
        startActivity(intent)
        finish()
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
            val adapter = FoodItemRvAdapter(it.value) {
                val intent = Intent(this, FoodDetailActivity::class.java)
                intent.putExtra("foodItem", it)
                startActivity(intent)
                finish()
            }
            recyclerView.adapter = adapter
        }
    }

}