package com.example.jetpackdemo.ViewModelShareDemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.FragmentTransaction
import com.example.jetpackdemo.R
import com.example.jetpackdemo.databinding.ActivityDemoViewmodelBinding

class DemoViewmodelActivity : AppCompatActivity() {

    lateinit var binding : ActivityDemoViewmodelBinding
    lateinit var toolbar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDemoViewmodelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        toolbar = supportActionBar!!

        supportActionBar!!.apply {

            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setTitle("ViewModel Demo")
        }


        //region In split way Frag  Trans
//        val fragmentTransaction  : FragmentTransaction = supportFragmentManager.beginTransaction()
//
//        fragmentTransaction.apply {
//
//            add(R.id.fragment_1_holder, DemoFragment1())
//            add(R.id.fragment_2_holder, DemoFragment2())
//            commit()
//        }

        //endregion


      supportFragmentManager.beginTransaction()
          .add(R.id.fragment_1_holder, DemoFragment1())
          .add(R.id.fragment_2_holder, DemoFragment2())
          .commitAllowingStateLoss()


    }
}