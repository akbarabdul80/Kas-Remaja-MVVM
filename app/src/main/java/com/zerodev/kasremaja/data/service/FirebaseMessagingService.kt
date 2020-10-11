package com.zerodev.kasremaja.data.service
import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.zerodev.kasremaja.BuildConfig
import com.zerodev.kasremaja.R
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
        val data = remoteMessage.notification
        val title = data?.title
        val content = data?.body
        val intent = Intent(applicationContext, SplaceActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val NOTIFICATION_CHANNEL_ID = "123"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "KAS Remaja Notificaton",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.description = "New Information"
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.BLUE
            notificationChannel.enableVibration(true)
            notificationChannel.vibrationPattern =
                longArrayOf(NotificationCompat.DEFAULT_VIBRATE.toLong())
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                notificationChannel.setAllowBubbles(true)
            }
            notificationChannel.lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
            notificationManager?.createNotificationChannel(notificationChannel)
        }
        val resultPendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        val builder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
        builder.setAutoCancel(true)
            .setContentTitle(title)
            .setContentText(content)
            .setDefaults(NotificationCompat.DEFAULT_SOUND)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSmallIcon(R.drawable.ic_coin)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
        builder.priority = Notification.PRIORITY_HIGH
        if (Build.VERSION.SDK_INT >= 21) builder.setVibrate(
            longArrayOf(
                1000,
                1000,
                1000,
                1000,
                1000
            )
        )
        if (false) {
            error("Assertion failed")
        }
        notificationManager.notify(NOTIFICATION_CHANNEL_ID.toInt(), builder.build())
    }

}