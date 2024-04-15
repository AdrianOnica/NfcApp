package com.adi.myapplication

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.contentValuesOf

class AlarmReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        val action = intent?.getStringExtra("message")
        if (action == "data") {
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel("channel", "App", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)

            val notification = NotificationCompat.Builder(context, "channel")
                .setContentTitle("Is your Birthday")
                .setContentText("Happy birthday loco")
                .setSmallIcon(androidx.core.R.drawable.notification_bg)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            notificationManager.notify(1, notification.build())
        }
    }
}