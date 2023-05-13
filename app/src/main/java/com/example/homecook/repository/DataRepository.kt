package com.example.homecook.repository

import com.example.homecook.R
import com.example.homecook.models.FoodItemModel
import com.example.homecook.models.OrderItemModel

class DataRepository {
    companion object {
        val ordersList = arrayListOf<OrderItemModel>()

        val foodItems = arrayListOf(
            FoodItemModel(
                "Daal Makhni",
                R.drawable.daal_makhni,
                150f,
                "Daal Makhni is a popular dish from the Punjab region of the Indian subcontinent. The primary ingredients are whole black lentil, red kidney beans, butter and cream."
            ),
            FoodItemModel(
                "Butter Chicken",
                R.drawable.butter_chicken,
                200f,
                "Butter chicken or murgh makhani is a curry of chicken in a spiced tomato, butter and cream sauce. It originated in India as a curry."
            ),
            FoodItemModel(
                "Chicken Biryani",
                R.drawable.chicken_biryani,
                250f,
                "Biryani, also known as biriyani, biriani, birani or briyani, is a mixed rice dish originating among the Muslims of the Indian subcontinent."
            ),
            FoodItemModel(
                "Chicken Tikka Masala",
                R.drawable.chicken_tikka_masala,
                300f,
                "Chicken tikka masala is a dish consisting of roasted marinated chicken chunks in spiced curry sauce. The curry is usually creamy and orange-coloured."
            ),
            FoodItemModel(
                "Chole Bhature",
                R.drawable.chole_bhature,
                150f,
                "Chole bhature, is a food dish originating from northern India. It is a combination of chana masala and bhatura, a fried bread made from maida flour."
            ),
            FoodItemModel(
                "Kadhai Paneer",
                R.drawable.kadhai_paneer,
                200f,
                "Kadai paneer is a preparation of paneer, the Indian cheese, in a spiced gravy made of tomatoes, onions and capsicum. It is a mainstay of Indian cuisine."
            ),
            FoodItemModel(
                "Kadhi Pakoda",
                R.drawable.kadhi_pakoda,
                150f,
                "Kadhi or karhi is a dish originating from the Indian subcontinent. It consists of a thick gravy based on chickpea flour, and contains vegetable fritters called pakoras, to which dahi is added to give it a bit of sour taste."
            ),

            FoodItemModel(
                "Matar Paneer",
                R.drawable.matar_paneer,
                200f,
                "Matar paneer is a vegetarian north Indian dish consisting of peas and paneer in a tomato based sauce, spiced with garam masala."
            ),

            /*      Old         */


            FoodItemModel(
                "Khichdi",
                R.drawable.khichdi,
                150f,
                "Khichdi, or khichri, is a dish from the Indian subcontinent made from rice and lentils, but other variations include bajra and mung dal kichri. In Indian culture, it is considered one of the first solid foods that babies eat.",
                0,
                FoodItemModel.ItemType.OLD
            ),
            FoodItemModel(
                "Daliya",
                R.drawable.daliya,
                100f,
                "Dalia is a term for a soup made from crushed wheat, barley, millet or lentils. It is a popular breakfast in the Indian subcontinent and originated from the Indian state of Haryana.",
                0,
                FoodItemModel.ItemType.OLD
            ),
            FoodItemModel(
                "Tomato Soup",
                R.drawable.tomato_soup,
                100f,
                "Tomato soup is a soup made with tomatoes as the primary ingredient. It may be served hot or cold in a bowl, and may be made in a variety of ways. It may be smooth in texture, and there are also recipes which include chunks of tomato, cream, chicken or vegetable stock, vermicelli, chunks of other vegetables and meatballs.",
                0,
                FoodItemModel.ItemType.OLD
            ),

            /*      Children        */

            FoodItemModel(
                "Avacdo Salsa",
                R.drawable.avacado_salsa,
                150f,
                "Avocado salsa is a type of salsa prepared using avocados as a primary ingredient. Additional ingredients may include tomatoes, onions, and seasonings. It is used as a dip for tortilla chips and served with various Mexican dishes.",
                0,
                FoodItemModel.ItemType.CHILDREN
            ),
            FoodItemModel(
                "Green Stuffed Peppers",
                R.drawable.green_stuffed_peppers,
                200f,
                "Stuffed peppers is a dish common in many cuisines. It consists of hollowed or halved peppers filled with any of a variety of fillings, often including meat, vegetables, cheese, rice, or sauce.",
                0,
                FoodItemModel.ItemType.CHILDREN
            ),
            FoodItemModel(
                "Pasta",
                R.drawable.pasta,
                150f,
                "Pasta is a type of food typically made from an unleavened dough of wheat flour mixed with water or eggs, and formed into sheets or other shapes, then cooked by boiling or baking.",
                0,
                FoodItemModel.ItemType.CHILDREN
            ),
            FoodItemModel(
                "Sandwich",
                R.drawable.sandwich,
                100f,
                "A sandwich is a food typically consisting of vegetables, sliced cheese or meat, placed on or between slices of bread, or more generally any dish wherein bread serves as a container or wrapper for another food type.",
                0,
                FoodItemModel.ItemType.CHILDREN
            ),
            FoodItemModel(
                "Mango Salsa",
                R.drawable.mango_salsa,
                150f,
                "Mango salsa is a spicy-sweet sauce made from diced mangoes, red and green peppers and chilli peppers, as served at Mexican restaurants.",
                0,
                FoodItemModel.ItemType.CHILDREN
            ),
            FoodItemModel(
                "Veggie Dip",
                R.drawable.veggie_dip,
                100f,
                "A vegetable dip is a dip for vegetables. It is often sour cream or yogurt-based, but can be made with any kind of creamy base, and may include any number of herbs or spices.",
                0,
                FoodItemModel.ItemType.CHILDREN
            ),

            /*      Sweet       */

            FoodItemModel(
                "Gulab Jamun",
                R.drawable.gulab_jamun,
                100f,
                "Gulab jamun is a milk-solid-based sweet from the Indian subcontinent, and a type of mithai, popular in India, Nepal, Pakistan, the Maldives, and Bangladesh, as well as Myanmar."
            ),
            FoodItemModel(
                "Kheer",
                R.drawable.kheer,
                100f,
                "Kheer or Kiru is a rice pudding, originating from the Indian subcontinent, made by boiling milk, sugar, and rice, although rice may be substituted with one of the following: bulgar wheat, millet, tapioca, vermicelli, or sweet corn."
            )
        )
    }
}