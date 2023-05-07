package com.example.homecook.ui.composable

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.homecook.R
import com.example.homecook.firebase.FirebaseUtil
import com.example.homecook.models.User
import com.example.homecook.shared_pref.SharedPref
import com.example.homecook.utils.CO
import java.util.*

enum class LoginSteps {
    PHONE_NUMBER,
    PASSWORD,
    FORGOT_PASSWORD,
    USER_DETAILS
}

@Composable
fun LoginScreen(setNavDest: (String) -> Unit) {
    val sharedPref = SharedPref(LocalContext.current)
    val firebase = FirebaseUtil(LocalContext.current)
    var phoneNumber by remember { mutableStateOf(TextFieldValue("8587096891")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var name by remember { mutableStateOf(TextFieldValue("")) }
    val context = LocalContext.current
    var step by remember {
        mutableStateOf(LoginSteps.PHONE_NUMBER)
    }

    fun checkForPassword() {
        if (password.text == sharedPref.getUser()?.password) {
            setNavDest("main")
        }
    }

    fun checkForPhoneNumber(phoneNumber: String) {
        firebase.isUserPresent(phoneNumber, success = {
            CO.log("User already present")
            sharedPref.storeUser(it)
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
//            Toast.makeText(LocalContext.current, "Unable to register", Toast.LENGTH_SHORT).show()
        })
    }

    Column(
        modifier = Modifier
            .fillMaxWidth(1f)
            .height(IntrinsicSize.Min)
            .padding(16.dp, 100.dp, 16.dp, 0.dp)
            .background(Color.White)
            .clickable {
            }
    ) {

        Image(
            painter = painterResource(id = R.mipmap.ic_launcher_foreground),
            contentDescription = "App Image",
            Modifier.size(200.dp)
                .align(Alignment.CenterHorizontally)
        )

        if (step == LoginSteps.PHONE_NUMBER) {
            Column(
            ) {
                Text(
                    text = "Phone Number",
                    color = Color.Black,
                    fontSize = 24.sp,
                    fontFamily = FontFamily.Serif
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { newText ->
                        phoneNumber = newText
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    readOnly = false,
                    enabled = true
                )
            }
        }

        if (step == LoginSteps.USER_DETAILS) {
            Text(
                text = "Enter Full Name",
                color = Color.Black,
                fontSize = 24.sp,
                fontFamily = FontFamily.Serif
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
                color = Color.Black,
                fontSize = 24.sp,
                fontFamily = FontFamily.Serif
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
        ) {
            Text(text = "Next")
        }

    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginPreview() {
    LoginScreen({})
}


