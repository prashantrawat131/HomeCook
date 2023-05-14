package com.example.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.homecook.firebase.FirebaseUtil
import com.example.homecook.shared_pref.SharedPref
import com.example.viewmodels.MainViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideSharedPref(@ApplicationContext context: Context): SharedPref {
        return SharedPref(context)
    }

    @Provides
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    fun provideFirebaseUtil(@ApplicationContext context: Context): FirebaseUtil {
        return FirebaseUtil(context)
    }

}