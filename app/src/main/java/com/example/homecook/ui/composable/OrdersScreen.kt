package com.example.homecook.ui.composable

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.homecook.firebase.FirebaseUtil

private val firebase = FirebaseUtil()

@Composable
fun OrdersScreen() {
    Text(text = "Orders Screen")
//    val ordersList = firebase.
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OrdersScreenPreview() {
    OrdersScreen()
}