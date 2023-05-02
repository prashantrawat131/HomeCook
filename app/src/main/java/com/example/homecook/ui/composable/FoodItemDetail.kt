package com.example.homecook.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.request.CachePolicy
import com.example.homecook.R
import com.example.homecook.firebase.FirebaseUtil
import com.example.homecook.models.FoodItemModel
import com.example.homecook.models.User
import com.example.homecook.shared_pref.SharedPref
import com.example.homecook.utils.CO
import com.example.homecook.utils.Constants
import java.io.File


@Composable
fun FoodItemDetail(
    user: User,
    foodItemModel: FoodItemModel,
    setNavDest: (String) -> Unit,
) {
    val firebaseUtil = FirebaseUtil()
    val sharedPref = SharedPref(LocalContext.current)
    var orderCount = remember {
        mutableStateOf(0)
    }
    firebaseUtil.loadOrderCount(user, foodItemModel, {
        orderCount.value = it
    }) {
        CO.log("Error: $it")
    }
    val increaseDecreaseButtonModifier = Modifier
        .size(40.dp)

    Column(modifier = Modifier.padding(0.dp, 15.dp)) {
        Image(
            painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
            contentDescription = "Back Button",
            modifier = Modifier
                .size(50.dp, 50.dp)
                .padding(16.dp)
                .clickable {
                    setNavDest("main")
                },
            colorFilter = ColorFilter.tint(color = Color(R.color.black))
        )

        Spacer(modifier = Modifier.height(24.dp))

        val painter: Painter = rememberImagePainter(
            data = File(
                Constants.getFoodImagesFolder(LocalContext.current),
                foodItemModel.imageName!!
            ),
            builder = {
                // Optional: set any additional parameters here such as desired image size or transformations
                crossfade(true) // Enable crossfade transition for smooth image loading
                memoryCacheKey(foodItemModel.toString())
                memoryCachePolicy(CachePolicy.ENABLED)
            }
        )

        Image(
            painter = painter,
            contentDescription = foodItemModel.name,
            modifier = Modifier
                .fillMaxWidth(1f),
            contentScale = ContentScale.FillWidth
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = foodItemModel.name.toString(),
                fontSize = 24.sp,
                modifier = Modifier.padding(16.dp, 16.dp),
                style = MaterialTheme.typography.h4.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color.Black
                )
            )

            Text(
                text = "₹" + foodItemModel.price.toString(),
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(16.dp, 16.dp),
                style = MaterialTheme.typography.h6.copy(
                    fontWeight = FontWeight.Light,
                    fontSize = 16.sp,
                    color = Color.Gray,
                    fontStyle = FontStyle.Italic
                ),
            )
        }

        if (orderCount.value != 0) {
            Row(modifier = Modifier.fillMaxWidth(1f)) {
                Button(
                    onClick = {
                        firebaseUtil.removeOrder(user, foodItemModel, {
                            orderCount.value--
                        }) {

                        }
                    },
                    modifier = increaseDecreaseButtonModifier
                ) {
                    Text(text = "-", fontSize = 30.sp)
                }

                Text(
                    text = orderCount.value.toString(),
                    fontSize = 20.sp,
                    modifier = Modifier.padding(10.dp, 0.dp)
                )

                Button(
                    onClick = {
                        firebaseUtil.addToOrders(user, foodItemModel, {
                            orderCount.value++
                        }) {

                        }
                    },
                    modifier = increaseDecreaseButtonModifier
                ) {
                    Text(text = "+", fontSize = 30.sp)
                }
            }
        } else {
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
                onClick = {
                    firebaseUtil.addToOrders(user, foodItemModel, {
                        orderCount.value++
                    }) {

                    }
                }) {
                Text(
                    text = "Add",
                    fontSize = 12.sp
                )
            }
        }

        Text(
            text = foodItemModel.description.toString(),
            fontSize = 14.sp,
            modifier = Modifier
                .padding(16.dp, 16.dp)
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun FoodItemDetailPreview() {
    FoodItemDetail(
        User("423424232", "Sample name", "cww", 42323232L),
        FoodItemModel(
            "Vada Pav",
            "lorem_ipsum",
            150.00f,
            "Vada Pava Description"
        )
    ) {

    }
}