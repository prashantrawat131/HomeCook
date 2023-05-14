package com.example.homecook.utils

import android.content.ClipData
import android.content.ClipboardManager
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


        fun copyToClipboard(context: Context, text: String) {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Copied Text", text)
            clipboard.setPrimaryClip(clip)
        }
    }
}