package com.example.homecook.shared_pref

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor

class SharedPref {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: Editor

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
}