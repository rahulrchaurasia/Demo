package com.example.jetpackdemo.ViewModelDemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.activity.viewModels
import com.example.jetpackdemo.R
import com.example.jetpackdemo.Utility.Constant
import com.example.jetpackdemo.ViewModelDemo.ViewModelFragment.TestFragment1
import com.example.jetpackdemo.ViewModelDemo.ViewModelFragment.TestFragment2
import com.example.jetpackdemo.databinding.ActivityViewModeDemo2Binding
import com.google.android.material.snackbar.Snackbar


// Link :--> https://www.youtube.com/watch?v=_aMEOCwb5Ls&t=338s

class ViewModeDemo2Activity : AppCompatActivity() {

    lateinit var binding:ActivityViewModeDemo2Binding
    private val viewModelDemo2 : ViewModelDemo2 by viewModels()   // Initialize directly

    lateinit var  etView  : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityViewModeDemo2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        supportActionBar!!.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)


        }
        etView = binding.includeViewmodelDemo2.etData  //(Multiple time called therefore we written )




        binding.includeViewmodelDemo2.btnFrag1.setOnClickListener{



            Constant.hideKeyBoard(etView,this)

            if(validate())
            {
                viewModelDemo2.setData(binding.includeViewmodelDemo2.etData.text.toString())

                supportFragmentManager.beginTransaction()
                    .replace(binding.includeViewmodelDemo2.container.id, TestFragment1())
                    .commit()

            }


        }

        binding.includeViewmodelDemo2.btnFrag2.setOnClickListener{
            Constant.hideKeyBoard(etView,this)
            if(validate()) {
                viewModelDemo2.setData(binding.includeViewmodelDemo2.etData.text.toString())
                supportFragmentManager.beginTransaction()
                    .replace(binding.includeViewmodelDemo2.container.id, TestFragment2())
                    .commit()
            }
        }


    }

    fun validate() : Boolean{

        var blnCheck : Boolean = true
        if(binding.includeViewmodelDemo2.etData.text.isNullOrBlank()){
            Snackbar.make(etView,"Required Value!!",Snackbar.LENGTH_SHORT).show()
            blnCheck =  false
        }
        return  blnCheck
    }
}