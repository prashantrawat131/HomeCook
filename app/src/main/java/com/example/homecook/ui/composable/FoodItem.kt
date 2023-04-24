package com.example.homecook.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.homecook.R
import com.example.homecook.models.FoodItemModel

@Composable
fun FoodItem(foodItemModel: FoodItemModel) {
    val width = 100.dp
    Column(
        Modifier.width(width)
    ) {

        val painter: Painter = rememberImagePainter(
            data = foodItemModel.image,
            builder = {
                // Optional: set any additional parameters here such as desired image size or transformations
                crossfade(true) // Enable crossfade transition for smooth image loading
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
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = foodItemModel.name.toString(),
            softWrap = true,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun FoodItemPreview() {
    FoodItem(
        FoodItemModel(
            "Vada Pav",
            "https://picsum.photos/200/300",
            150.00f,
            "Vada Pav is one of the famous dishes in Mumbai. It comes in fast food  but if made with care it can be healthy and tasty as well."
        )
    )
}