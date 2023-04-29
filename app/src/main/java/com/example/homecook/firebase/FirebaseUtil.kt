package com.example.homecook.firebase

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.homecook.models.FoodItemModel
import com.example.homecook.models.User
import com.example.homecook.utils.CO
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class FirebaseUtil {
    // Write a message to the database
    private val db = Firebase.firestore
    private val storage = Firebase.storage

    /*      User        */
    fun isUserPresent(phoneNumber: String, success: (User) -> Unit, failure: () -> Unit) {
        db.collection("users")
            .document(phoneNumber).get().addOnSuccessListener {
                try {
                    CO.log(it.data.toString())
                    val user = it.toObject(User::class.java)
                    success(user!!)
                } catch (e: Exception) {
                    CO.log("Exception: ${e.message.toString()}")
                    failure()
                }
            }.addOnFailureListener { e ->
                CO.log("Exception: ${e.message.toString()}")
                failure()
            }
    }

    fun registerUser(user: User, success: (User) -> Unit, failure: () -> Unit) {
        db.collection("users")
            .document(user.phoneNumber!!)
            .set(user)
            .addOnSuccessListener {
                success(user)
            }.addOnFailureListener {
                failure()
            }
    }

    /*      Foods       */
    fun loadFoods(
        categoryName: String,
        success: (ArrayList<FoodItemModel>) -> Unit,
        failure: (String) -> Unit
    ) {
        db.collection("foods").document(categoryName).collection("meals").get()
            .addOnSuccessListener {
                val items = arrayListOf<FoodItemModel>()
                it.documents.forEach { documentSnapshot ->
                    val meal = documentSnapshot.toObject(FoodItemModel::class.java)
                    CO.log("Meal: $meal")
                    items.add(meal!!)
                }
                success(items)
            }.addOnFailureListener {
                failure(it.message.toString())
            }
    }


    /*      Storage      */
    suspend fun loadFoodImage(
        context: Context,
        imageUrl: String,
        imageName: String
    ): String? {
        val file = File(context.filesDir, imageName)
        return try {
            withContext(Dispatchers.IO) {
                val url = URL(imageUrl)
                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()
                val inputStream: InputStream = connection.inputStream
                val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)

                val fileOutputStream = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
                fileOutputStream.flush()
                fileOutputStream.close()
                inputStream.close()

                CO.log("Image downloaded and saved: $file")
                imageName
            }
        } catch (e: Exception) {
            CO.log("Error downloading image: ${e.message}")
            e.printStackTrace()
            null
        }
    }

    /*         Orders       */

    fun addToOrders(
        user: User,
        foodItemModel: FoodItemModel,
        success: (User) -> Unit,
        failure: (String) -> Unit
    ) {
        db.collection("users").document(user.phoneNumber!!)
            .update("orders", FieldValue.arrayUnion(foodItemModel)).addOnSuccessListener {
                user.orders.add(foodItemModel)
                success(user)
            }.addOnFailureListener {
                failure(it.message.toString())
            }
    }
}