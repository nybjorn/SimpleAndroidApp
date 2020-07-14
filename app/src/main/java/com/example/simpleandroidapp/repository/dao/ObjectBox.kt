package com.example.simpleandroidapp.repository.dao

import android.app.Application
import android.content.Context
import com.example.simpleandroidapp.BuildConfig
import com.example.simpleandroidapp.repository.pojo.MyObjectBox
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import io.objectbox.BoxStore
import io.objectbox.android.AndroidObjectBrowser
import timber.log.Timber
import javax.inject.Singleton



object ObjectBox {
    lateinit var boxStore: BoxStore
        private set

    fun init(applicationContext: Context): BoxStore {
        if (::boxStore.isInitialized && !boxStore.isClosed) {
            return boxStore
        }

        boxStore = MyObjectBox.builder().androidContext(applicationContext).build()
        if (BuildConfig.DEBUG) {
            Timber.d("Using ObjectBox ${BoxStore.getVersion()} (${BoxStore.getVersionNative()})")
            AndroidObjectBrowser(boxStore).start(applicationContext)
        }
        return boxStore
    }
}
