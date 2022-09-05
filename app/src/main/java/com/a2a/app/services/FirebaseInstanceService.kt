package com.a2a.app.services

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.a2a.app.MainActivity
import com.a2a.app.R
import com.a2a.app.utils.AppUtils
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.ktx.messaging
import kotlinx.coroutines.DelicateCoroutinesApi

const val  channelId = "channelId"
const val channelName = "channelName"

class FirebaseInstanceService : FirebaseMessagingService() {

    @OptIn(DelicateCoroutinesApi::class)
    override fun onNewToken(token: String) {
        Log.e("Token", token)
        AppUtils(applicationContext).saveToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        Firebase.messaging.isAutoInitEnabled = true
        if (message.notification != null) {
            Log.e("notification message", "onMessageReceived: ${message.notification!!.title!!}")
            addNotification(message.notification!!.title!!, message.notification!!.body!!)

        }
    }

    private fun getFirebaseMessage(title: String, desc: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        //PendingIntent.FLAG_UPDATE_CURRENT
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        var builder: NotificationCompat.Builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.a2a_logo)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)

        builder = builder.setContent(getRemoteView(title, desc))

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        notificationManager.notify(0, builder.build())

    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun addNotification(title: String, desc: String) {
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.a2a_logo)
            .setContentTitle(title)
            .setContentText(desc)
            .setAutoCancel(true)

        val notificationIntent = Intent(
            this,
            MainActivity::class.java
        )

        var pendingIntent: PendingIntent? = null

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getActivity(
                this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        } else {
            pendingIntent = PendingIntent.getActivity(
                this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }

        /*val pendingIntent = PendingIntent.getActivity(
            this, 0, notificationIntent,
            PendingIntent.FLAG_IMMUTABLE
        )*/

        builder.setContentIntent(pendingIntent)
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(0, builder.build())
    }

    @SuppressLint("RemoteViewLayout")
    private fun getRemoteView(title: String, desc: String): RemoteViews? {
        val remoteViews = RemoteViews("com.a2a.app.services", R.layout.lyt_notification)

        remoteViews.setTextViewText(R.id.notificationTitle, title)
        remoteViews.setTextViewText(R.id.notificationMessage, desc)
        remoteViews.setImageViewResource(R.id.notificationImage, R.drawable.a2a_logo)

        return remoteViews
    }
}