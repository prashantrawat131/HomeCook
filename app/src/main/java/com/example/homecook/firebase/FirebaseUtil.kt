package com.example.homecook.firebase

import android.content.Context
import com.example.homecook.models.FoodItemModel
import com.example.homecook.models.User
import com.example.homecook.utils.CO
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File

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
    fun loadFoodImage(
        context: Context,
        imageName: String,
        success: () -> Unit,
        failure: () -> Unit
    ) {
        val foodImagesFolder = File(context.filesDir, "foods")
        if(!foodImagesFolder.exists()){
            foodImagesFolder.mkdirs()
        }
        val file = File(foodImagesFolder, "$imageName")
        CO.log("File path to download image: ${file.path}")
        val islandRef = storage.reference.child("foods/$imageName")
        islandRef.getFile(file).addOnSuccessListener {
            CO.log("Success downloading image")
            success()
        }.addOnFailureListener {
            CO.log("Error downloading image: ${it.message}")
            failure()
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