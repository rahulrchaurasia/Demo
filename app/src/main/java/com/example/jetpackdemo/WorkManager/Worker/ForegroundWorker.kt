package com.example.jetpackdemo.WorkManager.Worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.*
import com.example.jetpackdemo.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

//WorkManager: use foreground service for executing long running tasks

//https://medium.com/@sdex/run-worker-as-foreground-service-b607819fd263
/*

Foreground service requires notification
(don’t forget that for android 8+ notification channel should be created before showing any notification):

val notification = NotificationCompat.Builder(applicationContext, channelId)
    .setSmallIcon(R.mipmap.ic_launcher)
    .setContentTitle("Important background job")
    .build()

    Then need to create ForegroundInfo object and pass notification as parameter:

    val foregroundInfo = ForegroundInfo(notification)

    And run the worker in foreground service:

    setForeground(foregroundInfo)

    And that’s all, your worker is run in foreground service.

You can observe the progress:

workManager.getWorkInfoByIdLiveData(workRequest.id)
    .observe(this, Observer { workInfo: WorkInfo? ->
        if (workInfo != null) {
            val progress = workInfo.progress
            val value = progress.getInt(Progress, 0)
            binding.progressBar.progress = value
        }
    })
 */
class ForegroundWorker(context: Context, parameters: WorkerParameters) :
    CoroutineWorker(context, parameters) {

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        setForeground(createForegroundInfo())
        return@withContext runCatching {
            runTask()
            Result.success()
        }.getOrElse {
            Result.failure()
        }
    }

    //Fake long running task for 60 seconds
    private suspend fun runTask() {
        delay(60000)
    }

    //Creates notifications for service
    private fun createForegroundInfo(): ForegroundInfo {
        val id = "com.example.jetpackdemo.notifications1225"
        val channelName = "jetpackdemo channel"
        val title = "Downloading"
        val cancel = "Cancel"

        val body = "Long running task is running"

        val intent = WorkManager.getInstance(applicationContext)
            .createCancelPendingIntent(getId())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel(id, channelName)
        }

        val notification = NotificationCompat.Builder(applicationContext, id)
            .setContentTitle(title)
            .setTicker(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.ic_notification)
            .setColor(Color.RED)
            .setOngoing(true)
            .addAction(R.drawable.ic_close_24, cancel, intent)
            .build()

        return ForegroundInfo(1, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel(id: String, channelName: String) {
        notificationManager.createNotificationChannel(
            NotificationChannel(id, channelName, NotificationManager.IMPORTANCE_DEFAULT)
        )
    }
}