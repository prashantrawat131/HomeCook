package com.example.homecook.models

data class OrderItemModel(
    val foodItemModel: FoodItemModel,
    var count: Int
)
