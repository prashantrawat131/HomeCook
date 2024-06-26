package com.example.homecook.models

data class FoodItemModel(
    val name: String,
    val image: Int,
    val price: Float,
    val description: String,
    var count: Int = 0,
    val itemType: ItemType = ItemType.MAIN
) : java.io.Serializable {
    enum class ItemType {
        MAIN,
        OLD,
        CHILDREN,
        SWEET
    }
}
