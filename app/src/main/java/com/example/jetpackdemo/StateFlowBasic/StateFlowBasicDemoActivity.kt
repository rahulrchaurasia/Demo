package com.example.jetpackdemo.StateFlowBasic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.jetpackdemo.BaseActivity
import com.example.jetpackdemo.R
import com.example.jetpackdemo.Utility.Constant
import com.example.jetpackdemo.databinding.ActivityMvvmdemoBinding
import com.example.jetpackdemo.databinding.ActivityStateFlowBasicDemoBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


// refer : https://www.youtube.com/watch?v=6Jc6-INantQ

class StateFlowBasicDemoActivity : BaseActivity() , View.OnClickListener {

    lateinit var binding: ActivityStateFlowBasicDemoBinding

    val viewModel : StateFlowBasicViewModel by  viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStateFlowBasicDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.apply {

            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)

        }

        binding.btnLiveData.setOnClickListener(this)
        binding.btnStateFlow.setOnClickListener(this)
        binding.btnFlow.setOnClickListener(this)
        binding.btnShareFlow.setOnClickListener(this)

        subscribeToObservables()
    }

    // Observable
    private fun subscribeToObservables(){

        // For Live Data
        viewModel.liveData.observe(this){
            binding.txtLiveData.text =   it
        }


        //  For StateFlow we must use launchWhenStarted
        // emit When Current value and post value is change
        // i.e valu must chnanges
       lifecycleScope.launchWhenStarted {

           viewModel.stateFlow.collectLatest {
               binding.txtStateFlow.text = it

               Snackbar.make(binding.btnStateFlow, it, Snackbar.LENGTH_SHORT).show()
           }
       }

        //  For SharedFlow : always Update
        lifecycleScope.launchWhenStarted {

            viewModel.sharedFlow.collectLatest {
                binding.txtShareFlow.text = it

                Snackbar.make(binding.btnStateFlow, it, Snackbar.LENGTH_SHORT).show()
            }
        }
    }
    override fun onClick(view: View?) {

     when(view!!.id) {

         binding.btnLiveData.id -> {

             viewModel.triggeredLiveData()

         }

         binding.btnStateFlow.id -> {

            // viewModel.triggeredStateFlow()

             for( i in 1..100000){

                 viewModel.executeLongRunningTask()
                 Log.d(Constant.TAG_Coroutine,"i is ${i.toString()}")
             }
         }

         binding.btnFlow.id -> {

            // viewModel.triggeredFlow()
             lifecycleScope.launch{

                 viewModel.triggeredFlow().collectLatest {

                     binding.txtFlow.text = it
                 }


             }
         }

         binding.btnShareFlow.id -> {

             viewModel.triggeredShareFlow()
         }
     }
    }
}