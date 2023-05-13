package com.example.homecook.models

class OrderItemModel(
    var foodItemsList: ArrayList<FoodItemModel>,
    var totalPrice: Float,
    var address: String,
    var phoneNumber: String,
    var paymentMethod: String,
    var orderStatus: String,
    var orderTime: Long,
) {
    constructor() : this(
        arrayListOf(),
        0f,
        "",
        "",
        "",
        "",
        0L
    )
}