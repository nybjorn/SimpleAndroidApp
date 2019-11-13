package com.example.simpleandroidapp

import android.annotation.SuppressLint
import android.app.Application
import com.facebook.stetho.Stetho
import timber.log.Timber
import android.util.Log



class App : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
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

            Log.e("FAKE_LOG", String.format("%d %s %s", priority, tag, message))

            if (t != null) {
                if (priority == Log.ERROR) {
                    Log.e("FAKE_LOG", String.format("%d %s %s", priority, tag, message), t)
                } else if (priority == Log.WARN) {
                    Log.w("FAKE_LOG", String.format("%d %s %s", priority, tag, message), t)
                }
            }
        }
    }
}