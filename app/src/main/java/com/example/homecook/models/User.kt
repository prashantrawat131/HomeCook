package com.example.homecook.models

data class User(
    val phoneNumber: String? = "",
    val name: String? = "",
    val password: String? = "",
    val created_at: Long? = 0L,
    val orders: ArrayList<FoodItemModel>
) : java.io.Serializable
