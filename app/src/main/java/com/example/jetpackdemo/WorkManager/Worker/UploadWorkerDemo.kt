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
class UploadWorkerDemo(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {


    override fun doWork(): Result {

        try {
            val  count  = inputData.getInt(Constant.KEY_COUNT_VALUE,0)
            for(i : Int in 0 until count){

                Log.d(Constant.TAG2,"Uploading..${i}")


            }

            val time     = SimpleDateFormat("dd/m/yyyy hh:mm:ss")
            val currentData : String = time.format(Date())

            val outPutData : Data = Data.Builder()
                .putString(Constant.KEY_COUNT_VALUE1,currentData)
                .build()

            return Result.success(outPutData)
        }catch (e :Exception){

            return Result.failure()
        }



    }


}