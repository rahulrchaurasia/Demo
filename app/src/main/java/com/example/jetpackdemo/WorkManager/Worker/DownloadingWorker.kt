package com.example.jetpackdemo.WorkManager.Worker

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.jetpackdemo.Utility.Constant
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Rahul on 09/05/2022.
 */
class DownloadingWorker(context: Context, workerParams: WorkerParameters) : Worker(context,
    workerParams
) {
    override fun doWork(): Result {
        try {

            for(i : Int in 0..500){

                Log.d(Constant.TAG2,"Downloading..${i}")


            }
            val time     = SimpleDateFormat("dd/m/yyyy hh:mm:ss")
            val currentData : String = time.format(Date())
            Log.d(Constant.TAG2,"Completed ..${currentData}")


            return Result.success()

        }catch (e :Exception){

            return Result.failure()
        }
    }

}