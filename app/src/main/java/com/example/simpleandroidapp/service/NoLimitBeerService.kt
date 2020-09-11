package com.example.simpleandroidapp.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import android.widget.Toast
import com.example.simpleandroidapp.MainActivity
import com.example.simpleandroidapp.R
import com.example.simpleandroidapp.messaging.EventMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import timber.log.Timber
import java.util.concurrent.TimeUnit

class NoLimitBeerService : Service() {
    // Inspired by https://robertohuertas.com/2019/06/29/android_foreground_services/
    companion object {
        private const val MAX_BEERS_ON_THE_WALL = 99
        private const val LET_IT_SINK_IN = 2L
        private const val NOTIFICATION_CHANNEL_ID = "Simple Android App channel"
    }

    private var wakeLock: PowerManager.WakeLock? = null
    private var isServiceStarted = false

    override fun onBind(intent: Intent?): IBinder? {
        Timber.d("No binding provided")
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {
            val action = intent.action
            Timber.d("using an intent with action $action")
            when (action) {
                ServiceAction.START.name -> startService()
                ServiceAction.STOP.name -> stopService()
                else -> Timber.e("This should never happen. No action in the received intent")
            }
        } else {
            Timber.e("Check this")
        }
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        val notification = createChannelWithNotification(getString(R.string.filling_up), 0)
        startForeground(1, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("The service has been destroyed")
        Toast.makeText(this, R.string.no_beer, Toast.LENGTH_LONG).show()
    }

    private fun startService() {
        if (isServiceStarted) return
        Timber.d("Starting the foreground service task")
        isServiceStarted = true

        // To avoid https://developer.android.com/training/monitoring-device-state/doze-standby
        wakeLock =
            (getSystemService(Context.POWER_SERVICE) as PowerManager).run {
                newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "NoLimitBeer::lock").apply {
                    acquire()
                }
            }

        // we're starting a loop in a coroutine
        GlobalScope.launch(Dispatchers.IO) {
            var i = MAX_BEERS_ON_THE_WALL
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            while (isServiceStarted) {
                launch(Dispatchers.IO) {
                    Timber.d("Looping the service")
                    val oneless = i - 1
                    val beersOnTheWall = if (i > 0) getString(R.string.beers_on_the_wall, i, oneless)
                    else getString(R.string.no_beers_on_the_wall)
                    val createNotification = createNotification(beersOnTheWall, i)
                    notificationManager.notify(1, createNotification)
                    EventBus.getDefault().post(EventMessage(beersOnTheWall))
                }
                i -= 1
                if (i < 0) i = MAX_BEERS_ON_THE_WALL
                delay(TimeUnit.MINUTES.toMillis(LET_IT_SINK_IN))
            }
            Timber.d("End of the loop for the service")
        }
    }

    private fun stopService() {
        Timber.d("Stopping the foreground service")
        Toast.makeText(this, "Service stopping", Toast.LENGTH_SHORT).show()
        wakeLock?.let {
            if (it.isHeld) {
                it.release()
            }
        }
        stopForeground(true)
        stopSelf()
        isServiceStarted = false
    }

    private fun createChannelWithNotification(beerStatus: String, beers: Int): Notification? {

        // depending on the Android API that we're dealing with we will have
        // to use a specific method to create the notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Simple Android App notifications channel",
                NotificationManager.IMPORTANCE_HIGH
            ).let {
                it.description = NOTIFICATION_CHANNEL_ID
                it.enableLights(true)
                it.setShowBadge(true)
                it.enableVibration(false)
                it
            }
            notificationManager.createNotificationChannel(channel)
        }

        return createNotification(beerStatus, beers)
    }

    private fun createNotification(beersOnTheWall: String, beers: Int): Notification {
        val pendingIntent: PendingIntent =
            Intent(this, MainActivity::class.java).let { notificationIntent ->
                PendingIntent.getActivity(this, 0, notificationIntent, 0)
            }

        val builder: Notification.Builder =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) Notification.Builder(
                this,
                NOTIFICATION_CHANNEL_ID
            ) else Notification.Builder(this)

        builder
            .setNumber(beers)
            .setContentTitle(getString(R.string.app_name))
            .setContentText(beersOnTheWall)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_beer)
            .setTicker(beersOnTheWall)

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O) {
            builder.setPriority(Notification.PRIORITY_HIGH) // for under android 26 compatibility
        }

        return builder.build()
    }
}
