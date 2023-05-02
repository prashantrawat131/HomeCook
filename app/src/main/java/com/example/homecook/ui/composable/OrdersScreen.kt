package com.example.homecook.ui.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.homecook.firebase.FirebaseUtil
import com.example.homecook.models.FoodItemModel
import com.example.homecook.models.User

@Composable
fun OrdersScreen(user: User) {
    val firebaseUtil = FirebaseUtil()

    val ordersList = remember {
        mutableStateListOf<FoodItemModel>()
    }

    firebaseUtil.loadOrders(user, {
        ordersList.addAll(it)
    }) {

    }

    Column() {
        Text(text = "Orders Screen")

        LazyColumn() {
            items(ordersList) { item ->
                Box(
                    Modifier
                        .clickable {
                        }
                        .padding(8.dp, 0.dp)) {
                    FoodItem(item)
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OrdersScreenPreview() {
    OrdersScreen(
        User(
            "323242",
            "Prashant Rawat",
            "p123",
            3233L
        )
    )
}