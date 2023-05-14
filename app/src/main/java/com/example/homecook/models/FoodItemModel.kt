package com.example.homecook.models

data class FoodItemModel(
    val name: String? = "",
    val image: Int? = 0,
    val price: Float? = 0f,
    val description: String? = "",
    val itemType: ItemType = ItemType.MAIN
) : java.io.Serializable {
    enum class ItemType {
        MAIN,
        OLD,
        CHILDREN,
        SWEET
    }

    constructor() : this(
        "",
        0,
        0f,
        "",
        ItemType.MAIN
    )
}
