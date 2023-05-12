package com.example.homecook.shared_pref

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import com.example.homecook.models.User
import com.google.gson.Gson

class SharedPref(context: Context) {
    private val sharedPreferences: SharedPreferences
    private val editor: Editor
    private val gson = Gson()

    init {
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

    fun getUser(): User? {
        val json = sharedPreferences.getString("user", null) ?: return null
        return gson.fromJson(json, User::class.java)
    }

    fun storePhoneNumber(phoneNumber: String) {
        editor.putString("phoneNumber", phoneNumber)
        editor.commit()
    }

    fun getPhoneNumber(): String? {
        return sharedPreferences.getString("phoneNumber", null)
    }

    fun storeIntroShown(isIntroShown: Boolean) {
        editor.putBoolean("isIntroShown", isIntroShown)
        editor.apply()
    }

    fun isIntroShown(): Boolean {
        return sharedPreferences.getBoolean("isIntroShown", false)
    }

    fun clearAllData() {
        editor.clear()
        editor.commit()
    }
}