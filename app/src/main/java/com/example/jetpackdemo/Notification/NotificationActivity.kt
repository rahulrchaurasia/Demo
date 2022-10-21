package com.example.jetpackdemo.Notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import com.example.jetpackdemo.BaseActivity
import com.example.jetpackdemo.HomePage.HomePageActivity
import com.example.jetpackdemo.R
import com.example.jetpackdemo.Utility.NotifyService
import com.example.jetpackdemo.databinding.ActivityNotificationBinding

class NotificationActivity : BaseActivity() {

 lateinit var binding:ActivityNotificationBinding

    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val CHANNEL_ID = "com.example.jetpackdemo.notifications"
    private val CHANNEL_NAME = "jetpackdemo channel"
    var NOTIFICATION_ID = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)



        supportActionBar!!.apply {

            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setTitle("Notification Demo")
        }

       // createNotificationChannels()
        val intentHome = Intent(this, HomePageActivity::class.java)
        val pendingIntent = TaskStackBuilder.create(this).run {

            addNextIntentWithParentStack(intentHome)
            getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT)
        }


        binding.btnSubmit.setOnClickListener {

            //callNotification(pendingIntent!!)
            NotifyService(this).callNotification()


        }

    }


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
           

           val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }


    }

    fun callNotification(pendingIntent: PendingIntent) {

        NOTIFICATION_ID = Math.round(Math.random() * 1000).toInt()

        val notification = NotificationCompat.Builder(this,CHANNEL_ID)
            .setContentTitle("JetPack Notification")
            .setContentText("this is content")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .build()

        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(NOTIFICATION_ID,notification)

    }
}