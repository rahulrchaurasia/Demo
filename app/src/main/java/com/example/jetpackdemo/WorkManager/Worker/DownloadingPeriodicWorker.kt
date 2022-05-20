package com.example.jetpackdemo.WorkManager.Worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.jetpackdemo.HomePage.HomePageActivity
import com.example.jetpackdemo.R
import com.example.jetpackdemo.Utility.Constant
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Rahul on 09/05/2022.
 */
class DownloadingPeriodicWorker(context: Context, workerParams: WorkerParameters) : Worker(context,
    workerParams
) {
    // declaring variables
    private val CHANNEL_ID = "com.example.jetpackdemo.notifications"
    private val CHANNEL_NAME = "jetpackdemo channel"
    var NOTIFICATION_ID = 0

    override fun doWork(): Result {
        try {

            Thread.sleep(2000)
            for(i : Int in 0..500){

                Log.d(Constant.TAG_WORKER,"Downloading..${i}")


            }
            val time     = SimpleDateFormat("dd/m/yyyy hh:mm:ss")
            val currentData : String = time.format(Date())
            Log.d(Constant.TAG_WORKER,"Completed ..${currentData}")

            val outPutData : Data = Data.Builder()
                .putString(Constant.KEY_COUNT_VALUE1,currentData)
                .build()


            sendNotification("Periodic Background Task","Succcessfully done ${currentData}" )
            return Result.success(outPutData)

        }catch (e :Exception){

            return Result.failure()
        }
    }

    private fun sendNotification(title: String, message: String) {
        NOTIFICATION_ID = Math.round(Math.random() * 1000).toInt()

        val intentHome = Intent(applicationContext, HomePageActivity::class.java)
        val pendingIntent = TaskStackBuilder.create(applicationContext).run {

            addNextIntentWithParentStack(intentHome)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        //If on Oreo then notification required a notification channel.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }
        val notification: NotificationCompat.Builder = NotificationCompat.Builder(
            applicationContext,
            CHANNEL_ID
        )
            .setContentTitle(title)
            .setContentText(message)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.mipmap.ic_launcher)
        notificationManager.notify(NOTIFICATION_ID, notification.build())
    }

}