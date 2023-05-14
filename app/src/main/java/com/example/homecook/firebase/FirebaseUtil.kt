package com.example.homecook.firebase

import android.content.Context
import com.example.homecook.models.FoodItemModel
import com.example.homecook.models.User
import com.example.homecook.repository.DataRepository
import com.example.homecook.utils.CO
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await
import java.io.File

class FirebaseUtil(val context: Context) {
    // Write a message to the database
    private var db: FirebaseFirestore
    private var storage: FirebaseStorage

    init {
        Firebase.initialize(context)
        db = Firebase.firestore
        storage = Firebase.storage
    }

    /*      Admin       */
    fun uploadFoodsData() {
        DataRepository.foodItems.forEach {
            db.collection("foodItems")
                .document(it.name!!)
                .set(it)
                .addOnSuccessListener {
                    CO.log("Food item uploaded")
                }.addOnFailureListener {
                    CO.log("Food item upload failed")
                }
        }
    }


    /*      User        */
    suspend fun loadUser(phoneNumber: String): User? {
        return db.collection("users")
            .document(phoneNumber)
            .get()
            .await()
            .toObject(User::class.java)
    }

    fun createUser(phoneNumber: String, success: () -> Unit, failure: () -> Unit) {
        isUserPresent(phoneNumber, {
            CO.log("User already present")
            success()
        }, {
            CO.log("User not present")
            val user = User(phoneNumber, System.currentTimeMillis())
            createUserInDb(user, success, failure)
        })

        val user = User(phoneNumber, System.currentTimeMillis())
        db.collection("users")
            .document(phoneNumber)
            .set(user)
            .addOnSuccessListener {
                CO.log("User created")
                success()
            }.addOnFailureListener {
                CO.log("User creation failed")
                failure()
            }
    }

    private fun createUserInDb(user: User, success: () -> Unit, failure: () -> Unit) {
        db.collection("users")
            .document(user.phoneNumber!!)
            .set(user)
            .addOnSuccessListener {
                CO.log("User created")
                success()
            }.addOnFailureListener {
                CO.log("User creation failed")
                failure()
            }
    }

    private fun isUserPresent(phoneNumber: String, success: (User) -> Unit, failure: () -> Unit) {
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

    suspend fun updateUser(user: User): User? {
        db.collection("users")
            .document(user.phoneNumber!!)
            .set(user)
            .await()
        return loadUser(user.phoneNumber)
    }

    /*      Foods       */
    suspend fun loadFoods(): ArrayList<FoodItemModel?> {
        val items = arrayListOf<FoodItemModel?>()
        db.collection("foodItems").get().await().documents.forEach {
            items.add(it.toObject(FoodItemModel::class.java))
        }
        return items
    }

    fun loadFoods(
        success: (ArrayList<FoodItemModel>) -> Unit,
        failure: (String) -> Unit
    ) {
        db.collection("foods").get()
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
        if (!foodImagesFolder.exists()) {
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

/*         Cart       */

    fun addToCart(
        user: User,
        foodItemModel: FoodItemModel,
        success: (User) -> Unit,
        failure: (String) -> Unit
    ) {
//        Check if food item present in cart
        /*  db.collection("users").document(user.phoneNumber!!).get()
              .update("cart", FieldValue.arrayUnion(cartItemModel)).addOnSuccessListener {
                  user.cart?.add(CartItemModel(foodItemModel, ))
                  success(user)
              }.addOnFailureListener {
                  failure(it.message.toString())
              }*/
    }

    fun removeFromCart(
        user: User,
        foodItemModel: FoodItemModel,
        success: (User) -> Unit,
        failure: (String) -> Unit
    ) {
        db.collection("users").document(user.phoneNumber!!)
            .update("orders", FieldValue.arrayRemove(foodItemModel)).addOnSuccessListener {
                success(user)
            }.addOnFailureListener {
                failure(it.message.toString())
            }
    }

    fun loadOrderCount(
        user: User,
        foodItemModel: FoodItemModel,
        success: (Int) -> Unit,
        failure: (String) -> Unit
    ) {
        db.collection("users").document(user.phoneNumber!!)
            .collection("orders")
            .get().addOnSuccessListener {
                var ordersCount = 0
                it.documents.forEach { documentSnapshot ->
                    if (foodItemModel == documentSnapshot.toObject(FoodItemModel::class.java)) {
                        ordersCount++
                    }
                    success(ordersCount)
                }
            }.addOnFailureListener {
                failure(it.message.toString())
            }
    }

    fun loadCartItems(
        user: User,
        success: (ArrayList<FoodItemModel>) -> Unit,
        failure: (String) -> Unit
    ) {
        db.collection("users").document(user.phoneNumber!!)
            .collection("orders")
            .get().addOnSuccessListener {
                val ordersList = arrayListOf<FoodItemModel>()
                try {
                    it.documents.forEach { documentSnapshot ->
                        ordersList.add(documentSnapshot.toObject(FoodItemModel::class.java)!!)
                    }
                } catch (e: Exception) {

                }
                success(ordersList)
            }.addOnFailureListener {
                failure(it.message.toString())
            }
    }
}