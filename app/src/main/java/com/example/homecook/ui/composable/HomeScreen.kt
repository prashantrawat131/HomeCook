package com.example.homecook.ui.composable

import android.content.Context
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.homecook.shared_pref.SharedPref

@Composable
fun HomeScreen(context: Context) {
    val sharedPref = SharedPref(context)
    Text(text = "Home Screen: ${sharedPref.getUser().toString()}")
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(LocalContext.current)
}