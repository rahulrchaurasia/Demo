package com.example.jetpackdemo.WorkManager

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.*
import com.example.jetpackdemo.Utility.Constant
import com.example.jetpackdemo.WorkManager.Worker.*
import com.example.jetpackdemo.databinding.ActivityWorkManagerDemoBinding
import java.util.concurrent.TimeUnit


//Todo :-  https://www.youtube.com/watch?v=HNYr1ay3yjo&t=709s

class WorkManagerDemoActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var toolbar: ActionBar
    private lateinit var binding: ActivityWorkManagerDemoBinding

    private lateinit var txtDemo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWorkManagerDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        toolbar = supportActionBar!!

        supportActionBar!!.apply {

            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            //setTitle("Work Manger Demo")
        }

        init()

    }

    fun init() {

        var includedLayout = binding.includeWorkManager
        txtDemo = includedLayout.txtDemo


            includedLayout.btnWorkManagerDemo.setOnClickListener(this)
            includedLayout.btnWorkManagerDemoPeriodic.setOnClickListener(this)


    }

    private fun setOneTimeRequest() {

        val workManager: WorkManager = WorkManager.getInstance(applicationContext)

        val data: Data = Data.Builder()
            .putInt(Constant.KEY_COUNT_VALUE, 1000)
            .build()

        val constraint: Constraints = Constraints.Builder()
            // .setRequiresCharging(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val upladRequest: OneTimeWorkRequest =
            OneTimeWorkRequest.Builder(UploadWorkerDemo::class.java)
                .setConstraints(constraint)
                .setInputData(data)
                .build()

        val filteringRequest: OneTimeWorkRequest =
             OneTimeWorkRequest.Builder(FilteringWorker::class.java)
                 .build()

        val compressionRequest : OneTimeWorkRequest =
               OneTimeWorkRequest.Builder(CompressionWorker::class.java)
                   .build()


        val downloadingRequest : OneTimeWorkRequest =
                 OneTimeWorkRequest.Builder(DownloadingWorker::class.java)
                     .build()

        //Todo : For Single
      //  workManager.enqueue(upladRequest)

        // Todo : For Chain (Chaining in Series)
//            workManager.beginWith(filteringRequest)
//                .then(compressionRequest)
//                .then(upladRequest)
//                .enqueue()


        // Todo : For Chain (Parallel Chaining)
        val parallelWorks:MutableList<OneTimeWorkRequest> = mutableListOf<OneTimeWorkRequest>()
        parallelWorks.add(downloadingRequest)
        parallelWorks.add(filteringRequest)
        parallelWorks.add(compressionRequest)

        workManager.beginWith(parallelWorks)
            .then(upladRequest)
            .enqueue()

        workManager.getWorkInfoByIdLiveData(upladRequest.id)
            .observe(this, Observer {
                txtDemo.text = it.state.name

                if(it != null && it.state.isFinished){

                    val opData : Data  = it.outputData
                    val msg : String? = opData.getString(Constant.KEY_COUNT_VALUE1)

                    txtDemo.text = it.state.name + "\n\n" +msg
                    Toast.makeText(applicationContext,msg, Toast.LENGTH_LONG).duration
                }
            })


          // Alternative way to write
        /*
        workManager.getWorkInfoByIdLiveData(upladRequest.id)
            .observe(this, Observer {  workInfo ->
                val wasSuccess = if (workInfo != null && workInfo.state == WorkInfo.State.SUCCEEDED) {
                    workInfo.outputData.getBoolean("is_success", false)
                } else {
                    false
                }
            })

         */
    }

    private fun setPeriodicWorkRequest(){
        val workManager: WorkManager = WorkManager.getInstance(applicationContext)

        val periodicWorkRequest : PeriodicWorkRequest = PeriodicWorkRequest
                      .Builder(DownloadingPeriodicWorker::class.java, 15,TimeUnit.MINUTES)
                      .build()

       // workManager.getInstance(applicationContext).enqueue(periodicWorkRequest)

        workManager.getWorkInfoByIdLiveData(periodicWorkRequest.id)
            .observe(this, Observer {
                txtDemo.text = it.state.name

                if(it.state.isFinished){

                    val opData : Data  = it.outputData
                    val msg : String? = opData.getString(Constant.KEY_COUNT_VALUE1)

                    txtDemo.text = it.state.name + "\n\n" +msg
                    Toast.makeText(applicationContext,msg, Toast.LENGTH_LONG).duration
                }
            })
    }

    override fun onClick(view: View?) {

        when (view!!.id) {

            binding.includeWorkManager.btnWorkManagerDemo.id -> {

                setOneTimeRequest()
            }

            binding.includeWorkManager.btnWorkManagerDemoPeriodic.id -> {

                setPeriodicWorkRequest()
            }
        }
    }
}