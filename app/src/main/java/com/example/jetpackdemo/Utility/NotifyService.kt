package com.example.jetpackdemo.Utility

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import com.example.jetpackdemo.HomePage.HomePageActivity
import com.example.jetpackdemo.R
import com.example.jetpackdemo.TrucallerModel.CallerDialogActivity

/**
 * Created by Rahul on 10/05/2022.
 */
class NotifyService( var mcontext : Context) {

    private val CHANNEL_ID = "com.example.jetpackdemo.notifications"
    private val CHANNEL_NAME = "jetpackdemo channel"
    var NOTIFICATION_ID = 0



    fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                enableLights(true)
                enableVibration(true)
                lightColor = Color.GREEN
                description = "JetPackDemo"

                lockscreenVisibility =
                    Notification.VISIBILITY_PUBLIC // Notification.VISIBILITY_PRIVATE
            }


            val notificationManager = mcontext.getSystemService( Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }


    }

    fun Flag(){

        // Todo : Check flag
//        val flag = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//            PendingIntent.FLAG_IMMUTABLE
//        }else {0 }
//        val pendingIntent = PendingIntent.getActivity(
//            mcontext,
//            0, notificationIntent, flag
//        )
    }

    fun callNotification() {

        createNotificationChannels()

        NOTIFICATION_ID = Math.round(Math.random() * 1000).toInt()

        val intentHome = Intent(mcontext, HomePageActivity::class.java)

        val pendingIntent = TaskStackBuilder.create(mcontext).run {

            addNextIntentWithParentStack(intentHome)
            getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT)
        }
        val notification = NotificationCompat.Builder(mcontext,CHANNEL_ID)
            .setContentTitle("JetPack Notification")
            .setContentText("this is content")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .build()

        val notificationManager = NotificationManagerCompat.from(mcontext)
        notificationManager.notify(NOTIFICATION_ID,notification)



    }

    // Not inused
    fun callNotification1() {

        createNotificationChannels()

        NOTIFICATION_ID = Math.round(Math.random() * 1000).toInt()

        // Creates an explicit intent for an Activity in your app
        // Creates an explicit intent for an Activity in your app

        val fullScreenIntent = Intent(mcontext, CallerDialogActivity::class.java)
        val fullScreenPendingIntent = PendingIntent.getActivity(
            mcontext,
            0, fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        val notification = NotificationCompat.Builder(mcontext, CHANNEL_ID)
            .setContentTitle("Foreground Service Kotlin Example")
            .setContentTitle("Incoming call")
            .setContentText("(919) 555-1234")
            .setSmallIcon(R.drawable.ic_notification)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_CALL)
            .setColor(Color.GREEN)
            .setFullScreenIntent(fullScreenPendingIntent,true)
            .build()


        val notificationManager = NotificationManagerCompat.from(mcontext)
        notificationManager.notify(NOTIFICATION_ID,notification)



    }

}