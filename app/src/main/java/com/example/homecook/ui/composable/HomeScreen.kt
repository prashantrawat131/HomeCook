package com.example.homecook.ui.composable

import android.content.Context
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.homecook.R
import com.example.homecook.firebase.FirebaseUtil
import com.example.homecook.models.FoodItemModel
import com.example.homecook.shared_pref.SharedPref
import com.example.homecook.utils.CO

@Composable
fun HomeScreen(
    context: Context,
    onFoodItemClick: (FoodItemModel) -> Unit,
    setNavDest: (String) -> Unit
) {
    val firebaseUtil = FirebaseUtil(LocalContext.current)
    val sharedPref = SharedPref(context)
    val mainMenuList = remember {
        mutableStateListOf<FoodItemModel>()
    }

//        arrayListOf(
//        FoodItemModel("Daal Makhni", R.drawable.daal_makhni, 150.00f,),
//        FoodItemModel("Parantha", R.drawable.parantha, 80.00f),
//        FoodItemModel("Sandwich", R.drawable.sandwich, 70.00f),
//        FoodItemModel("Chole Bhature", R.drawable.chole_bhature, 120.00f),
//    )

    val oldPeoplesFoodList = remember {
        mutableStateListOf<FoodItemModel>()
    }

//        arrayListOf(
//        FoodItemModel("Daal Makhni", R.drawable.daal_makhni, 150.00f),
//        FoodItemModel("Parantha", R.drawable.parantha, 80.00f),
//        FoodItemModel("Sandwich", R.drawable.sandwich, 70.00f),
//        FoodItemModel("Chole Bhature", R.drawable.chole_bhature, 120.00f),
//    )

    val childrenFoodList = remember {
        mutableStateListOf<FoodItemModel>()
    }

//        arrayListOf(
//        FoodItemModel("Green Stuffed Peppers", R.drawable.greek_stuffed_peppers, 150.00f),
//        FoodItemModel("Avacado Salsa", R.drawable.avacado_salsa, 200.00f),
//        FoodItemModel("Veggie Dip", R.drawable.veggie_dip, 160.00f),
//        FoodItemModel("Mango Salsa", R.drawable.mango_salsa, 250.00f),
//    )

    firebaseUtil.loadFoods("main", {
        mainMenuList.clear()
        mainMenuList.addAll(it)
    }, {
        CO.log(it)
    })

    firebaseUtil.loadFoods("old", {
        oldPeoplesFoodList.clear()
        oldPeoplesFoodList.addAll(it)
    }, {
        CO.log(it)
    })

    firebaseUtil.loadFoods("children", {
        childrenFoodList.clear()
        childrenFoodList.addAll(it)
    }, {
        CO.log(it)
    })

    val listHeadingModifier = Modifier.padding(15.dp, 10.dp)

    Box(modifier = Modifier.fillMaxSize()) {

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Hi ${sharedPref.getUser()?.name}")
                    },
                    actions = {
                        IconButton(onClick = { /* Handle profile icon click */ }) {
                            Icon(Icons.Filled.Person, contentDescription = "Orders Icon")
                        }
                    }
                )
            },
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    // Main Menu
                    Text(
                        text = "Main menu",
                        style = MaterialTheme.typography.h5,
                        modifier = listHeadingModifier
                    )
                    LazyRow() {
                        items(mainMenuList) { item ->
                            Box(
                                Modifier
                                    .clickable {
                                        onFoodItemClick(item)
                                    }
                                    .padding(8.dp, 0.dp)
                            ) {
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

        Box(
            modifier = Modifier
                .padding(16.dp, 16.dp)
                .align(Alignment.BottomCenter)
                .background(colorResource(id = R.color.purple_200), RoundedCornerShape(10.dp))
        ) {
            Row(modifier = Modifier.clickable {
                setNavDest("orders")
            }) {
                Column(
                    modifier = Modifier
                        .weight(1f, true)
                        .padding(8.dp, 8.dp)
                ) {
                    Text(
                        text = "Orders",
                        Modifier.padding(20.dp, 0.dp),
                        color = colorResource(id = R.color.white),
                        fontSize = 24.sp
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.ic_baseline_navigate_next_24),
                    contentDescription = "See Selected Items",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(8.dp, 8.dp),
                    colorFilter = ColorFilter.tint(colorResource(id = R.color.white))
                )
            }
        }
    }

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(LocalContext.current, {

    }) {

    }
}