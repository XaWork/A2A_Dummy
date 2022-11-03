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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

const val  channelId = "channelId"
const val channelName = "channelName"

@AndroidEntryPoint
class FirebaseInstanceService : FirebaseMessagingService() {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    @Inject
    lateinit var appUtils: AppUtils

    override fun onNewToken(token: String) {
        Log.e("Token", token)

        scope.launch {
            appUtils.saveToken1(token)
        }

        appUtils.saveToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        Firebase.messaging.isAutoInitEnabled = true
        if (message.notification != null) {
            Log.e("notification message", "title: ${message.notification!!.title!!}\n" +
                    "message : ${message.notification!!.body}\n" +
                    "image : ${message.notification!!.imageUrl}")
            addNotification(message.notification!!.title!!, message.notification!!.body!!, message.notification!!.icon)

        }
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun addNotification(title: String, desc: String, imageUrl : String? = "") {

        val notificationIntent = Intent(
            this,
            MainActivity::class.java
        )
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        var pendingIntent: PendingIntent? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(
                this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        } else {
            PendingIntent.getActivity(
                this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }

        /*val pendingIntent = PendingIntent.getActivity(
            this, 0, notificationIntent,
            PendingIntent.FLAG_IMMUTABLE
        )*/
        var builder: NotificationCompat.Builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.logo_120x120)
            .setContentTitle(title)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
            .setOnlyAlertOnce(true)
            .setContentText(desc)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        builder = builder.setContent(getRemoteView(title, desc, imageUrl))

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            manager.createNotificationChannel(notificationChannel)
        }
        manager.notify(0, builder.build())
    }

    @SuppressLint("RemoteViewLayout")
    private fun getRemoteView(title: String, desc: String, imageUrl : String?): RemoteViews {
        val remoteViews = RemoteViews("com.a2a.app", R.layout.lyt_notification)

        remoteViews.setTextViewText(R.id.notificationTitle, title)
        remoteViews.setTextViewText(R.id.notificationMessage, desc)
        remoteViews.setImageViewResource(R.id.notificationImage, R.drawable.logo_120x120)

        return remoteViews
    }
}