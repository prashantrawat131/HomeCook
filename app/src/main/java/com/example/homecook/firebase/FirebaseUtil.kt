package com.example.homecook.firebase

import com.example.homecook.models.FoodItemModel
import com.example.homecook.models.User
import com.example.homecook.utils.CO
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirebaseUtil {
    // Write a message to the database
    private val db = Firebase.firestore


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
}