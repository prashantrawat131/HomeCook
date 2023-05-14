package com.example.homecook.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homecook.adapters.CartAdapter
import com.example.homecook.databinding.ActivityCartBinding
import com.example.homecook.models.FoodItemModel
import com.example.homecook.models.User
import com.example.homecook.repository.DataRepository
import com.example.homecook.utils.CO
import com.example.viewmodels.MainViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartActivity : AppCompatActivity() {
    private val TAG = "OrdersActivityTAG"
    private lateinit var _binding: ActivityCartBinding
    private val binding get() = _binding
    var totalPrice = 0f
    private lateinit var viewModel: MainViewModel
    private lateinit var user: User
    private lateinit var adapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        user = intent.getSerializableExtra("user") as User
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setUpCartRecyclerView()

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
        binding.itmesRv.layoutManager = LinearLayoutManager(this)
         adapter = CartAdapter(user.cart!!, { cartItem ->
            val index = user.cart?.indexOf(cartItem)
            cartItem.count = cartItem.count?.plus(1)
            user.cart?.set(index!!, cartItem)
            viewModel.updateUser(user) { exception ->
                CO.log(exception)
            }
        }) { cartItem ->
            val index = user.cart?.indexOf(cartItem)
            cartItem.count = cartItem.count?.minus(1)
            user.cart?.set(index!!, cartItem)
            if (cartItem.count == 0) {
                user.cart?.remove(cartItem)
            }
            viewModel.updateUser(user) { exception ->
                CO.log(exception)
            }
        }
        binding.itmesRv.adapter = adapter
    }

}