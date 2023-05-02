package com.example.homecook.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.request.CachePolicy
import com.example.homecook.R
import com.example.homecook.firebase.FirebaseUtil
import com.example.homecook.models.FoodItemModel
import com.example.homecook.shared_pref.SharedPref
import com.example.homecook.utils.CO
import com.example.homecook.utils.Constants
import java.io.File


private val firebaseUtil = FirebaseUtil()

@Composable
fun FoodItem(foodItemModel: FoodItemModel) {
    val sharedPref = SharedPref(LocalContext.current)
    var user by remember {
        mutableStateOf(sharedPref.getUser()!!)
    }
    val foodImagesFolder = Constants.getFoodImagesFolder(LocalContext.current)
    val imageVisible = remember {
        mutableStateOf(false)
    }
    val imageFile = remember {
        mutableStateOf(File(foodImagesFolder, foodItemModel.imageName!!))
    }
    if (!imageFile.value.exists()) {
        firebaseUtil.loadFoodImage(LocalContext.current, foodItemModel.imageName!!, {
            imageVisible.value = true
        }) {

        }
    } else {
        imageVisible.value = true
    }

    val width = IntrinsicSize.Min
    Column(
        Modifier.width(width)
    ) {

        if (imageVisible.value) {
            CO.log("Painting image: ${imageFile.value.path}")
            val painter: Painter = rememberImagePainter(
                data = imageFile.value,
                builder = {
                    // Optional: set any additional parameters here such as desired image size or transformations
                    crossfade(true) // Enable crossfade transition for smooth image loading
                    memoryCacheKey(foodItemModel.toString())
                    memoryCachePolicy(CachePolicy.ENABLED)
                }
            )

            Image(painter = painter,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .graphicsLayer {
                        shape = RoundedCornerShape(16.dp)
                    }
                    .width(width)
                    .height(100.dp),
                contentDescription = "Food Item")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = foodItemModel.name.toString(),
            softWrap = true,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center
        )
        if (user.orders.contains(foodItemModel)) {
            Row() {
                Button(onClick = { /*TODO*/ }) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_baseline_remove_24),
                        contentDescription = "Decrease Amount",
                        Modifier.size(20.dp)
                    )
                }

                Text(text = "1")

                Button(onClick = { /*TODO*/ }) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_baseline_add_24),
                        contentDescription = "Increase Amount",
                        Modifier
                            .size(20.dp)
                            .background(colorResource(id = R.color.blue))
                    )
                }
            }
        } else {
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
                onClick = {
                    firebaseUtil.addToOrders(user, foodItemModel, {
                        user = it
                        sharedPref.storeUser(it)
                    }) {

                    }
                }) {
                Text(
                    text = "Add",
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun FoodItemPreview() {
    FoodItem(
        FoodItemModel(
            "Vada Pav",
            "lorem_ipsum",
            150.00f,
            "Vada Pav is one of the famous dishes in Mumbai. It comes in fast food  but if made with care it can be healthy and tasty as well."
        )
    )
}