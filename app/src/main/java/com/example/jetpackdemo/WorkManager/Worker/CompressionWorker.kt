package com.example.jetpackdemo.WorkManager.Worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.jetpackdemo.Utility.Constant

/**
 * Created by Rahul on 09/05/2022.
 */
class CompressionWorker(context: Context, workerParams: WorkerParameters) : Worker(context,
    workerParams
) {
    override fun doWork(): Result {
        try {

            for(i : Int in 0..500){

                Log.d(Constant.TAG2,"Compression..${i}")


            }


            return Result.success()
        }catch (e :Exception){

            return Result.failure()
        }
    }

}