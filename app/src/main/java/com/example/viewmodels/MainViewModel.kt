package com.example.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homecook.firebase.FirebaseUtil
import com.example.homecook.models.CartItemModel
import com.example.homecook.models.FoodItemModel
import com.example.homecook.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val firebaseUtil: FirebaseUtil) : ViewModel() {
    private val TAG = "MainViewModelTAG"
    val foodsResponse = MutableLiveData<ArrayList<FoodItemModel?>>()
    val userResponse = MutableLiveData<User>()
    val updateUserResponse = MutableLiveData<Boolean>()

    fun loadFoods(onException: (String) -> Unit) {
        try {
            viewModelScope.launch {
                foodsResponse.value = firebaseUtil.loadFoods()
            }
        } catch (e: Exception) {
            onException(e.message.toString())
        }
    }

    fun loadUser(phoneNumber: String, onException: (String) -> Unit) {
        try {
            viewModelScope.launch {
                userResponse.value = firebaseUtil.loadUser(phoneNumber)
            }
        } catch (e: Exception) {
            onException(e.message.toString())
        }
    }

    fun updateUser(user: User, onException: (String) -> Unit) {
        try {
            viewModelScope.launch {
                firebaseUtil.updateUser(user)
            }
        } catch (e: Exception) {
            onException(e.message.toString())
        }
    }

}