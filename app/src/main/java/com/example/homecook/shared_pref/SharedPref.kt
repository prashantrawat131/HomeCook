package com.example.homecook.shared_pref

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import com.example.homecook.models.User
import com.google.gson.Gson

class SharedPref {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: Editor
    private val gson = Gson()

    constructor(context: Context) {
        sharedPreferences = context.getSharedPreferences("home-cook-sp", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    fun storeIsLogin(isLoggedIn: Boolean) {
        editor.putBoolean("isLoggedIn", isLoggedIn)
        editor.apply()
    }

    fun isUserLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }

    fun storeUser(user: User) {
        val json = gson.toJson(user)
        editor.putString("user", json)
        editor.commit()
    }

    fun getUser(): User {
        val json = sharedPreferences.getString("user", null)
        return gson.fromJson(json,User::class.java)
    }
}