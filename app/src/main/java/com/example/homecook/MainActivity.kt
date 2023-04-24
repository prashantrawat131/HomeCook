package com.example.homecook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.homecook.firebase.FirebaseUtil
import com.example.homecook.models.FoodItemModel
import com.example.homecook.shared_pref.SharedPref
import com.example.homecook.ui.composable.FoodItemDetail
import com.example.homecook.ui.composable.HomeScreen
import com.example.homecook.ui.composable.LoginScreen
import com.example.homecook.ui.composable.OrdersScreen
import com.example.homecook.ui.theme.HomeCookTheme
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize

class MainActivity : ComponentActivity() {
    private lateinit var sharedPref: SharedPref
    private lateinit var firebaseUtil: FirebaseUtil
    private lateinit var selectedFoodItemModel: FoodItemModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Firebase.initialize(this)
        firebaseUtil = FirebaseUtil()
        sharedPref = SharedPref(this)
        setContent {
            HomeCookTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    App()
                }
            }
        }
    }

    @Composable
    fun App() {
        var navDest by remember {
            mutableStateOf(
                if (sharedPref.getUser() == null) {
                    "login"
                } else {
                    "main"
                }
            )
        }

        fun setNavDest(dest: String) {
            navDest = dest
        }


        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = navDest
        )
        {
            composable("login") {
                LoginScreen(context = applicationContext, setNavDest = {
                    setNavDest(it)
                })
            }

            composable("main") {
                HomeScreen(applicationContext, { foodItemModel ->
                    selectedFoodItemModel = foodItemModel
                    setNavDest("foodItemDetail")
                }, {
                    setNavDest(it)
                })
            }

            composable("foodItemDetail") {
                FoodItemDetail(foodItemModel = selectedFoodItemModel) {
                    setNavDest(it)
                }
            }

            composable("orders") {
                OrdersScreen()
            }
        }

        onBackPressedDispatcher.addCallback {
            when (navDest) {
                "orders", "foodItemDetail" -> {
                    setNavDest("main")
                }
                else -> {
                    finishAffinity()
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        HomeCookTheme {
            App()
        }
    }
}

