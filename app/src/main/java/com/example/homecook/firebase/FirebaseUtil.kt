package com.example.homecook.firebase

import com.example.homecook.models.User
import com.example.homecook.utils.CO
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirebaseUtil {
    // Write a message to the database
    private val db = Firebase.firestore

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
}