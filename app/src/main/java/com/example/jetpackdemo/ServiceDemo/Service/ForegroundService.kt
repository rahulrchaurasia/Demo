package com.example.jetpackdemo.ServiceDemo.Service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.os.Message
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.work.ForegroundInfo
import androidx.work.WorkManager
import com.example.jetpackdemo.HomePage.HomePageActivity
import com.example.jetpackdemo.R
import java.security.Provider

/**
 * Created by Rahul on 13/05/2022.
 */
class ForegroundService : Service() {
    private val CHANNEL_ID = "com.example.jetpackdemo.notifications444"

    companion object {

        fun startService(context: Context, message: String){

         val startIntent = Intent(context, ForegroundService::class.java)
            startIntent.putExtra("inputExtra",message)
            ContextCompat.startForegroundService(context,startIntent)
        }

        fun stopService(context: Context){

            val stopIntent = Intent(context, ForegroundService::class.java)
            context.stopService(stopIntent)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //do heavy work on a background thread
        val input = intent?.getStringExtra("inputExtra")
        createNotificationChannel()
        val notificationIntent = Intent(this, HomePageActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, 0
        )
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Foreground Service Kotlin Example")
            .setContentText(input)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentIntent(pendingIntent)
            .setColor(Color.GREEN)
            .build()
        startForeground(1, notification)
        return START_NOT_STICKY
    }
    override fun onBind(p0: Intent?): IBinder? {

        return null
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(CHANNEL_ID, "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            manager!!.createNotificationChannel(serviceChannel)
        }
    }


    //Creates notifications for service
    private fun createForegroundInfo(): ForegroundInfo {
        val id = "com.example.jetpackdemo.notifications1225"
        val channelName = "jetpackdemo channel"
        val title = "Downloading"
        val cancel = "Cancel"

        val body = "Long running task is running"


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
           // createChannel(id, channelName)
        }

        val notification = NotificationCompat.Builder(applicationContext, id)
            .setContentTitle(title)
            .setTicker(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.ic_notification)
            .setColor(Color.RED)
            .setOngoing(true)

            .build()

        return ForegroundInfo(1, notification)
    }

//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun createChannel(id: String, channelName: String) {
//        notificationManager.createNotificationChannel(
//            NotificationChannel(id, channelName, NotificationManager.IMPORTANCE_DEFAULT)
//        )
//    }
}