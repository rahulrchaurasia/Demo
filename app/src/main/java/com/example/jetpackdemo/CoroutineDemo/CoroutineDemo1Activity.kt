package com.example.jetpackdemo.CoroutineDemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.jetpackdemo.BaseActivity
import com.example.jetpackdemo.Utility.Constant
import com.example.jetpackdemo.ViewModelDemo.ViewModelMainActivity
import com.example.jetpackdemo.databinding.ActivityCoroutineDemo1Binding
import kotlinx.coroutines.*

class CoroutineDemo1Activity : BaseActivity(), View.OnClickListener {

    lateinit var binding : ActivityCoroutineDemo1Binding

    private val viewModel: CoroutineViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoroutineDemo1Binding.inflate(layoutInflater)


        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)


        supportActionBar!!.apply {

            setDisplayShowHomeEnabled(true)
            setDisplayShowHomeEnabled(true)
            setTitle("Couroutine Demo")
        }

        binding.btnDemo1.setOnClickListener(this)
        binding.btnDemo2.setOnClickListener(this)
        binding.btnDemo3.setOnClickListener(this)
        binding.btnDemo4.setOnClickListener(this)

        binding.btnDemo5.setOnClickListener(this)
        binding.btnDemo6.setOnClickListener(this)
        binding.btnDemo7.setOnClickListener(this)
        binding.btnDemo8.setOnClickListener(this)

        binding.btnDemo10.setOnClickListener(this)
        binding.btnDemo11.setOnClickListener(this)




        //region Comment LifeCycle DEmo
        /***************************************************************************
        A LifecycleScope is defined for each Lifecycle & LifecycleOwner object.
        Any coroutine launched in this scope is canceled when the Lifecycle is destroyed.
        From Activity you can access LifecycleScope in two ways:


        a> lifecycle.coroutineScope.launch {...}

        b> lifecycleScope.launch {...}   // we use this
        ****************************************************************************/

//        lifecycleScope.launch {
//
//            while (true) {
//                Log.d(Constant.TAG_Coroutine, "OnCreate Continue Start lifecycleScope :")
//                delay(300)
//            }
//
//
//        }

        //endregion


        //region Comment Viewmodel DEmo
        /***************************************************************************
         A ViewModelScope is defined for each ViewModel in our app.
         Any coroutine launched in this scope is automatically canceled if the ViewModel is cleared.
         ****************************************************************************/

         // viewModel.getData()

        //endregion

    }

    suspend fun networkCall() : Int{

        Log.d(Constant.TAG_Coroutine,"network Call Start")
        delay(3000)

        Log.d(Constant.TAG_Coroutine,"network Call End")

        return 21
    }

    suspend fun networkCall1() : String{

        delay(3000)

        Log.d(Constant.TAG_Coroutine,"network Call 1")
        return  "Data 1"

    }

    suspend fun networkCall2() : String{

        delay(5000)

        Log.d(Constant.TAG_Coroutine,"network Call 2")

        return  "Data 2"

    }

    suspend fun networkCall3() : String{

       delay(1000)

        Log.d(Constant.TAG_Coroutine,"network Call 3")

        return  "Data 3"

    }

    suspend fun JobDemo(){
    // Note : Since Job is run asynchronously

        Log.d(Constant.TAG_Coroutine, "Coroutine Start  :")
        val job = lifecycleScope.launch{

            delay(2000)
            Log.d(Constant.TAG_Coroutine, "Job Done :")
        }

        Log.d(Constant.TAG_Coroutine, "Test 1 Complete")

    }

    suspend fun test2JobJoin(){
        // Note :  job.join() wait for asynchronous task to complete

        Log.d(Constant.TAG_Coroutine, "Coroutine Start  :")
        val job = lifecycleScope.launch{

            delay(2000)
            Log.d(Constant.TAG_Coroutine, "Job Done :")
        }

        job.join()
        Log.d(Constant.TAG_Coroutine, "Test 1 Complete")

    }


    suspend fun test3Async(){
        // Note : Since Job is run asynchronously

        Log.d(Constant.TAG_Coroutine, "Coroutine Start  :")
        val deffere  = lifecycleScope.async{

            delay(2000)
            Log.d(Constant.TAG_Coroutine, "Job Done :")

            "20"
        }

     var result =  deffere.await()

        Log.d(Constant.TAG_Coroutine, "Test 3 Async Demo Complete with Result : ${result}")

    }

    override fun onClick(view: View?) {

        when(view!!.id){

            binding.btnDemo1.id ->{

                GlobalScope.launch {

                    Log.d(Constant.TAG_Coroutine, "Start :")
                    networkCall1()
                    networkCall2()
                }

            }

            binding.btnDemo2.id ->{

                //  So GlobalScope, simply means the scope of this coroutines is the entire application.
                //  It will not get finished, if it is running in the background it won’t stop unless the whole application stop.
                GlobalScope.launch {

                    while (true){
                        Log.d(Constant.TAG_Coroutine, "Continue Start GlobalScope :")
                        delay(300)
                    }


                }

                startActivity(Intent(this, ViewModelMainActivity:: class.java))
                this.finish()



            }


            binding.btnDemo3.id -> {



                startActivity(Intent(this, ViewModelMainActivity:: class.java))
                this.finish()

            }
            ///////////////  Job //////////////////

         //   Job : job will be returned when a new coroutine will be launched.

//                    start()
//                    join()
//                    cancel()


             binding.btnDemo4.id -> {


                lifecycleScope.launch{
                    JobDemo()
                }


            }
            /*
            join() function is a suspending function, i.e it can be called from a coroutine or from within another suspending function.
            Job blocks all the threads until the coroutine in which it is written or have context finished its work.
             Only when the coroutine gets finishes, lines after the join() function will be executed.
             */
            binding.btnDemo5.id -> {


                lifecycleScope.launch{
                    test2JobJoin()
                }


            }
            binding.btnDemo6.id -> {


                lifecycleScope.launch{

                    val job1 = launch { networkCall1() }


                    job1.join()

                    val job2 =  launch { networkCall2() }

                    job2.join()

                    val job3 =  launch { networkCall3() }

                    job3.join()



                    Log.d(Constant.TAG_Coroutine, "Coroutine Synchronous called .  ")
                }



            }

            binding.btnDemo7.id -> {


                lifecycleScope.launch{
                    test3Async()
                }
            }


            binding.btnDemo8.id -> {


                lifecycleScope.launch{

                    val s1 = async {networkCall1()  }

                    val s2 = async { networkCall2() }

                    val s3 = async { networkCall3() }



                    Log.d(Constant.TAG_Coroutine, "Continue async parellel result : FOR 1> ${s1.await()} ,2> ${s2.await()} and 3> ${s3.await()} ")
                }
            }


            binding.btnDemo10.id -> {


                lifecycleScope.launch(Dispatchers.IO){
                   val result =  networkCall()
                    Log.d(Constant.TAG_Coroutine, "Continue result : ${result}")
                }

            }

            binding.btnDemo11.id -> {


                lifecycleScope.launch(Dispatchers.IO){
                    val result =  networkCall()
                    Log.d(Constant.TAG_Coroutine, "Continue my result : ${result}")
                   // binding.txtDemo.setText(result.toString())  // We required Main thread to bind the view

                    withContext(Dispatchers.Main){
                        binding.txtDemo.setText(result.toString())
                    }
                }

            }
        }
    }


}