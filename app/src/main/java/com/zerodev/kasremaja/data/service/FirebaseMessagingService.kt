package com.zerodev.kasremaja.data.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.zerodev.kasremaja.R
import com.zerodev.kasremaja.root.App
import com.zerodev.kasremaja.ui.splash.SplaceActivity


class FirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(s: String) {
        super.onNewToken(s)
    }

    var TAG_FIREBASE = "FIREBASE MESSAGING"

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        remoteMessage.data
        sendNotif(remoteMessage)
        Log.e(TAG_FIREBASE, "Data : " + remoteMessage.data)
    }

    private fun sendNotif(remoteMessage: RemoteMessage) {
        val data = remoteMessage.data

        val title = data["title"]
        val content = data["body"]

        val intent = Intent(applicationContext, SplaceActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val sound: Uri =
            Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + packageName + "/" + R.raw.sound_notification)

        val NOTIFICATION_CHANNEL_ID = "123"

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val audioAttributes: AudioAttributes =
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build()

            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "KAS Remaja Notificaton",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.description = "New Information"
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.BLUE
            notificationChannel.enableVibration(true)
            notificationChannel.setSound(sound, audioAttributes)
            notificationChannel.vibrationPattern =
                longArrayOf(
                    1000,
                    1000,
                    1000,
                    1000,
                    1000
                )
            notificationChannel.enableVibration(true)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                notificationChannel.setAllowBubbles(true)
            }
            notificationChannel.lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
            notificationManager.createNotificationChannel(notificationChannel)
        }


        val builder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
        builder.setAutoCancel(true)
            .setContentTitle(title)
            .setContentText(content)
            .setStyle(NotificationCompat.BigTextStyle().bigText(content))
            .setSound(sound)
            .setDefaults(NotificationCompat.DEFAULT_VIBRATE)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSmallIcon(R.drawable.ic_notif)
            .setContentIntent(pendingIntent)

        builder.priority = 2

        if (Build.VERSION.SDK_INT >= 21) builder.setVibrate(
            longArrayOf(
                1000,
                1000,
                1000,
                1000,
                1000
            )
        )
        notificationManager.notify(NOTIFICATION_CHANNEL_ID.toInt(), builder.build())
    }

}