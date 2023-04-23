package com.example.homecook.ui.composable

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.homecook.R
import com.example.homecook.models.FoodItemModel
import com.example.homecook.shared_pref.SharedPref


@Composable
fun HomeScreen(context: Context, onFoodItemClick: (FoodItemModel) -> Unit) {
    val sharedPref = SharedPref(context)
    val todaysMenuList = arrayListOf(
        FoodItemModel("Daal Makhni", R.drawable.daal_makhni, 150.00f),
        FoodItemModel("Parantha", R.drawable.parantha, 80.00f),
        FoodItemModel("Sandwich", R.drawable.sandwich, 70.00f),
        FoodItemModel("Chole Bhature", R.drawable.chole_bhature, 120.00f),
    )

    val oldPeoplesFoodList = arrayListOf(
        FoodItemModel("Daal Makhni", R.drawable.daal_makhni, 150.00f),
        FoodItemModel("Parantha", R.drawable.parantha, 80.00f),
        FoodItemModel("Sandwich", R.drawable.sandwich, 70.00f),
        FoodItemModel("Chole Bhature", R.drawable.chole_bhature, 120.00f),
    )

    val childrenFoodList = arrayListOf(
        FoodItemModel("Green Stuffed Peppers", R.drawable.greek_stuffed_peppers, 150.00f),
        FoodItemModel("Avacado Salsa", R.drawable.avacado_salsa, 200.00f),
        FoodItemModel("Veggie Dip", R.drawable.veggie_dip, 160.00f),
        FoodItemModel("Mango Salsa", R.drawable.mango_salsa, 250.00f),
    )

    val listHeadingModifier = Modifier.padding(15.dp, 10.dp)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Hi ${sharedPref.getUser()?.name}")
                },
                navigationIcon = {
                    IconButton(onClick = { /* Handle navigation icon click */ }) {
                        Icon(Icons.Filled.Menu, contentDescription = "Navigation Icon")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Handle profile icon click */ }) {
                        Icon(Icons.Filled.Person, contentDescription = "Profile Icon")
                    }
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                // Search Bar
                TextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("Search for food, restaurant or cuisine") },
                    leadingIcon = {
                        Icon(
                            Icons.Filled.Search,
                            contentDescription = "Search Icon"
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Today's Special
                Text(
                    text = "Today's menu",
                    style = MaterialTheme.typography.h5,
                    modifier = listHeadingModifier
                )
                LazyRow() {
                    items(todaysMenuList) { item ->
                        Box(
                            Modifier
                                .clickable {
                                    onFoodItemClick(item)
                                }
                                .padding(8.dp, 0.dp)) {
                            FoodItem(item)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                // Old People
                Text(
                    text = "For Oldies",
                    style = MaterialTheme.typography.h5,
                    modifier = listHeadingModifier
                )
                LazyRow() {
                    items(oldPeoplesFoodList) { item ->
                        Box(
                            Modifier
                                .clickable {
                                    onFoodItemClick(item)
                                }
                                .padding(8.dp, 0.dp)) {
                            FoodItem(item)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

//                Children
                Text(
                    text = "Children's Section",
                    style = MaterialTheme.typography.h5,
                    modifier = listHeadingModifier
                )
                LazyRow() {
                    items(childrenFoodList) { item ->
                        Box(
                            Modifier
                                .clickable {
                                    onFoodItemClick(item)
                                }
                                .padding(8.dp, 0.dp)) {
                            FoodItem(item)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    )
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(LocalContext.current) {

    }
}