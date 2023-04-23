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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.homecook.R
import com.example.homecook.models.FoodItemModel

@Composable
fun FoodItem(foodItem: FoodItemModel) {
    val width = 100.dp
    Column(
        Modifier.width(width)
    ) {
        Image(painter = painterResource(id = foodItem.image),
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
            text = foodItem.name,
            softWrap = true,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun FoodItemPreview() {
    FoodItem(FoodItemModel("Vada Pav", R.drawable.vada_pav, 150.00f))
}