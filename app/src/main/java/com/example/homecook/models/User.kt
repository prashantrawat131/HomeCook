package com.example.homecook.models

data class User(
    val phoneNumber: String? = "",
    val created_at: Long? = 0L,
    val cart: ArrayList<CartItemModel>? = arrayListOf(),
    val orders: ArrayList<OrderItemModel>? = arrayListOf(),
) : java.io.Serializable


