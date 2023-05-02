package com.example.homecook.utils

import android.content.Context
import java.io.File

class Constants {
    companion object {
        fun getFoodImagesFolder(context: Context): String {
            val folder = File(context.filesDir, "foods")
            if (!folder.exists()) {
                folder.mkdirs()
            }
            return folder.path
        }
    }
}