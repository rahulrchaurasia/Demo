package com.example.jetpackdemo.WorkManager.Worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.jetpackdemo.Utility.Constant

/**
 * Created by Rahul on 09/05/2022.
 */
class FilteringWorker(context: Context, workerParams: WorkerParameters) :Worker(context,
    workerParams
) {
    override fun doWork(): Result {
        try {

            for(i : Int in 0..800){

                Log.d(Constant.TAG_WORKER,"Filtering..${i}")


            }


            return Result.success()
        }catch (e :Exception){

            return Result.failure()
        }
    }
}