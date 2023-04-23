package com.example.homecook.utils

import android.content.Context
import android.util.Log
import android.widget.Toast

class CO {
    companion object {
        fun log(str: String) {
            Log.d("tagJi", str)
        }

        fun toast(context: Context, str: String) {
            Toast.makeText(context, str, Toast.LENGTH_SHORT).show()
        }
    }
}