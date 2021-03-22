package com.example.simpleandroidapp.di

import android.content.Context
import com.example.simpleandroidapp.repository.dao.ObjectBox
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.objectbox.BoxStore
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BoxModule {
    @Provides
    @Singleton
    fun provideObjectBox(@ApplicationContext applicationContext: Context): BoxStore {
        return ObjectBox.init(applicationContext)
    }
}
