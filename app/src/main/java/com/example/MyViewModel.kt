package com.example

import android.content.Context
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homecook.firebase.FirebaseUtil
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {
    private val firebaseUtil = FirebaseUtil()
    val foodImageResponse = MediatorLiveData<String>()

    fun loadFoodImage(context: Context, imageUrl: String, imageName: String) {
        viewModelScope.launch {
            foodImageResponse.value = firebaseUtil.loadFoodImage(context, imageUrl, imageName)
        }
    }
}