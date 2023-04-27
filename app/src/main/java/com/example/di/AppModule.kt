package com.example.di

import android.content.Context
import com.example.homecook.shared_pref.SharedPref
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
}