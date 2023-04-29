package com.example.homecook.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
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

@Composable
fun FoodItemDetail(
    user: User,
    foodItemModel: FoodItemModel,
    setNavDest: (String) -> Unit,
    addToOrders: (FoodItemModel) -> Unit
) {

    Column(modifier = Modifier.padding(0.dp, 15.dp)) {
        Image(
            painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
            contentDescription = "Back Button",
            modifier = Modifier
                .size(32.dp)
                .padding(16.dp)
                .clickable {
                    setNavDest("main")
                },
            colorFilter = ColorFilter.tint(color = Color(R.color.black))
        )

        Spacer(modifier = Modifier.height(24.dp))

        val painter: Painter = rememberImagePainter(
            data = foodItemModel.image,
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

        if (user.orders.contains(foodItemModel)) {
            Row() {
                Button(onClick = { /*TODO*/ }) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_baseline_remove_24),
                        contentDescription = "Decrease Amount"
                    )
                }

                Text(text = user.orders.groupingBy{foodItemModel}.eachCount().toString())

                Button(onClick = { /*TODO*/ }) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_baseline_add_24),
                        contentDescription = "Decrease Amount"
                    )
                }
            }
        } else {
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
                onClick = {
                    addToOrders(foodItemModel)
                }) {
                Text(
                    text = "Add",
                    fontSize = 24.sp
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun FoodItemDetailPreview() {
    FoodItemDetail(
        User("423424232", "Sample name", "cww", 42323232L, arrayListOf()),
        FoodItemModel(
            "Vada Pav",
            "https://picsum.photos/200/300",
            "lorem_ipsum",
            150.00f,
            "Vada Pava Description"
        ), {

        }
    ) {

    }
}