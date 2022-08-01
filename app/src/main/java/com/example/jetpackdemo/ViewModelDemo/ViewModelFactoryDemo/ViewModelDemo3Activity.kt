package com.example.jetpackdemo.ViewModelDemo.ViewModelFactoryDemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.jetpackdemo.R
import com.example.jetpackdemo.ViewModelDemo.ViewModelDemo2
import com.example.jetpackdemo.databinding.ActivityViewModeDemo2Binding
import com.example.jetpackdemo.databinding.ActivityViewModelDemo3Binding

/*

  ViewModelFactory : Without Live Data role

  First Create Factory Instance and pass it to ViewModel

  Note : Mostly we pass Repository
 */

class ViewModelDemo3Activity : AppCompatActivity() {

    lateinit var binding: ActivityViewModelDemo3Binding

    lateinit var viewModelDemo3 : ViewModelDemo3

    lateinit var viewModelFactoryDemo3 : ViewModelFactoryDemo3

  // var viewModelDemo3: ViewModelDemo3 by viewModels { ViewModelFactoryDemo3(10) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewModelDemo3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        supportActionBar!!.apply {

            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }



            viewModelFactoryDemo3 =  ViewModelFactoryDemo3(10)
             viewModelDemo3 = ViewModelProvider(this,viewModelFactoryDemo3).get(ViewModelDemo3::class.java)



         fun setText(){

            binding.includeViewmodelDemo3.txtDemo.setText(viewModelDemo3.getCountData().toString())

        }

        binding.includeViewmodelDemo3.btnCount.setOnClickListener{

            setText()
        }

    }
}



