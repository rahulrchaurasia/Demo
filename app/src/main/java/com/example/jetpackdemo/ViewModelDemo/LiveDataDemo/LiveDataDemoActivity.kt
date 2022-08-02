package com.example.jetpackdemo.ViewModelDemo.LiveDataDemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.jetpackdemo.BaseActivity
import com.example.jetpackdemo.R
import com.example.jetpackdemo.databinding.ActivityLiveDataDemoBinding
import com.example.jetpackdemo.databinding.ActivityRoomDemoBinding


class LiveDataDemoActivity : BaseActivity() {

    lateinit var binding: ActivityLiveDataDemoBinding

    lateinit var viewModel: LiveDataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLiveDataDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        supportActionBar!!.apply {

            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }


        viewModel = ViewModelProvider(this).get(LiveDataViewModel::class.java)

        //step 1
       // By Default Live data is   set  as factLiveData : "This is fact"
        viewModel.factLiveData.observe(this, {

            binding.txtDemo.setText(it)

        })

        //step2
        // On Click of Button :  we set Live Data i.e. factLiveData.value = "Another fact"
        // hence when Change the LiveData value, That Activity who observe it trigger automatically at that point i.e  step2 again called.

        binding.btnDemo1.setOnClickListener{

            viewModel.updateLiveData()
        }



    }
}