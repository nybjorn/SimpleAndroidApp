package com.example.simpleandroidapp

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import com.example.simpleandroidapp.net.networkModule
import com.example.simpleandroidapp.repository.beerModule
import com.example.simpleandroidapp.repository.dao.boxModule
import com.example.simpleandroidapp.ui.second.beerViewModule
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.core.FlipperClient
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.soloader.SoLoader
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(listOf(
                networkModule,
                boxModule,
                beerModule,
                beerViewModule))
        }

        SoLoader.init(this, false)

        if (BuildConfig.DEBUG) {
            if (FlipperUtils.shouldEnableFlipper(this)) {
                val client: FlipperClient = AndroidFlipperClient.getInstance(this)
                client.addPlugin(NetworkFlipperPlugin())
                client.addPlugin(InspectorFlipperPlugin(this, DescriptorMapping.withDefaults()))
                client.start()
            }
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashReportingTree())
        }
    }

    /** A tree which logs important information for crash reporting.  */
    private class CrashReportingTree : Timber.Tree() {
        @SuppressLint("LogNotTimber")
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return
            }

            Log.e("FAKE_LOG", "$(priority) $(tag) $(message)")

            if (t != null) {
                if (priority == Log.ERROR) {
                    Log.e("FAKE_LOG", "$(priority) $(tag) $(message)", t)
                } else if (priority == Log.WARN) {
                    Log.w("FAKE_LOG", "$(priority) $(tag) $(message)", t)
                }
            }
        }
    }
}
