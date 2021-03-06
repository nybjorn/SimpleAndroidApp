package com.example.simpleandroidapp.repository.dao

import android.content.Context
import com.example.simpleandroidapp.BuildConfig
import com.example.simpleandroidapp.repository.pojo.MyObjectBox
import io.objectbox.BoxStore
import io.objectbox.android.AndroidObjectBrowser
import timber.log.Timber

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
