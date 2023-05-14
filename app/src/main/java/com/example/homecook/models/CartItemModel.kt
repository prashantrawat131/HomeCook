package com.example.homecook.models

data class CartItemModel(
    val foodItem: FoodItemModel? = null,
    var count: Int? = 0,
):java.io.Serializable{
    constructor():this(null,0)
}