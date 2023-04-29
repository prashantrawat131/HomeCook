package com.example.homecook.ui.composable

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun OrderItem() {
    Text(text = "Order Item")
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun OrderItemPreview() {
    OrderItem()
}