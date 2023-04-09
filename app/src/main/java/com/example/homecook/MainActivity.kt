package com.example.homecook

import android.os.Bundle
import androidx.activity.ComponentActivity
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
import com.example.homecook.shared_pref.SharedPref
import com.example.homecook.ui.composable.HomeScreen
import com.example.homecook.ui.composable.LoginScreen
import com.example.homecook.ui.theme.HomeCookTheme
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize

class MainActivity : ComponentActivity() {

    private lateinit var sharedPref: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Firebase.initialize(this)

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
            mutableStateOf("login")
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

            composable("main"){
                HomeScreen(applicationContext)
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

