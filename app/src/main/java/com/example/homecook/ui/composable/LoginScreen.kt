package com.example.homecook.ui.composable

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.homecook.firebase.FirebaseUtil
import com.example.homecook.models.User
import com.example.homecook.shared_pref.SharedPref
import java.util.*

enum class LoginSteps {
    PHONE_NUMBER,
    PASSWORD,
    FORGOT_PASSWORD,
    USER_DETAILS
}


@Composable
fun LoginScreen(context: Context, setNavDest: (String) -> Unit) {

    val sharedPref = SharedPref(context)
    val firebase = FirebaseUtil()
    var phoneNumber by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var step by remember {
        mutableStateOf(LoginSteps.PHONE_NUMBER)
    }

    fun checkForPassword() {

    }

    fun checkForPhoneNumber(phoneNumber: String) {
        firebase.isUserPresent(phoneNumber, success = {
            step = LoginSteps.PASSWORD
        }, failure = {
            step = LoginSteps.USER_DETAILS
        })
    }

    fun registerUser(user: User) {
        firebase.registerUser(user, success = {
            sharedPref.storeUser(user)
            setNavDest("main")
        }, failure = {
            Toast.makeText(context, "Unable to register", Toast.LENGTH_SHORT).show()
        })
    }

    Column(
        modifier = Modifier
            .width(IntrinsicSize.Max)
            .height(IntrinsicSize.Max)
            .background(Color.White)
    ) {
        if (step == LoginSteps.PHONE_NUMBER) {
            Text(
                text = "Enter Phone Number",
                color = Color.Black,
                modifier = Modifier
                    .padding(15.dp)
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
                    .padding(15.dp)
            )
        }

        if (step == LoginSteps.USER_DETAILS) {
            Text(
                text = "Enter Full Name",
                color = Color.Black,
                modifier = Modifier
                    .padding(15.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                value = name,
                onValueChange = { newText ->
                    name = newText
                },
                readOnly = false,
                enabled = true,
                modifier = Modifier
                    .border(BorderStroke(1.dp, Color.Gray), RectangleShape)
                    .padding(15.dp)
            )
        }

        if (step == LoginSteps.PASSWORD || step == LoginSteps.USER_DETAILS) {

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
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                when (step) {
                    LoginSteps.PHONE_NUMBER -> {
                        checkForPhoneNumber(phoneNumber.text)
                    }
                    LoginSteps.PASSWORD -> {
                        checkForPassword()
                    }
                    LoginSteps.FORGOT_PASSWORD -> {

                    }
                    LoginSteps.USER_DETAILS -> {
                        val user = User(
                            phoneNumber.text,
                            name.text,
                            password.text,
                            Calendar.getInstance().timeInMillis
                        )
                        registerUser(user)
                    }
                    else -> {

                    }
                }
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
        ) {
            Text(text = "Next")
        }

    }

}


@Preview
@Composable
fun LoginPreview() {
    LoginScreen(LocalContext.current, {})
}


