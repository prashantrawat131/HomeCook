package com.example.homecook.firebase

import com.example.homecook.models.User
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseUtil {
    // Write a message to the database
    private val database = Firebase.database
    private val myRef = database.getReference("message")

    fun test() {
        myRef.setValue("Hello, World!")
    }

    fun isUserPresent(phoneNumber: String, success: (User) -> Unit, failure: () -> Unit) {
        myRef.child("users").child(phoneNumber).get().addOnSuccessListener {
            try {
                val user = it.getValue(User::class.java)
                success(user!!)
            } catch (e: Exception) {
                failure()
            }
        }.addOnFailureListener {
            failure()
        }
    }

    fun registerUser(user: User, success: (User) -> Unit, failure: () -> Unit) {
        myRef.child("users").child(user.phoneNumber).setValue(user)
            .addOnSuccessListener {
                success(user)
            }.addOnFailureListener {
                failure()
            }
    }
}