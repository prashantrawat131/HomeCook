package com.example.homecook.ui.composable

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(context: Context) {

    var phoneNumber by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        Modifier
            .padding(15.dp)
            .width(IntrinsicSize.Max)
            .height(IntrinsicSize.Max)
    ) {
        Text(
            text = "Enter Phone Number",
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(

            value = phoneNumber,
            onValueChange = { newText ->
                phoneNumber = newText
            },
            readOnly = false,
            enabled = true,
            modifier = Modifier
                .border(BorderStroke(1.dp, Color.Gray), RectangleShape)
                .padding(0.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Enter Password",
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = password,
            onValueChange = { newText ->
                password = newText
            },
            readOnly = false,
            enabled = true,
            modifier = Modifier
                .border(BorderStroke(1.dp, Color.Gray), RectangleShape)
                .padding(0.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                if (password.text.isNotEmpty() && phoneNumber.text.isNotEmpty()) {
                    Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Login")
        }
    }

/*
    @Preview
    @Composable
    fun Preview() {
        LoginScreen(context)
    }*/
}


