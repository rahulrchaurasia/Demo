package com.example.jetpackdemo.CoroutineDemo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackdemo.Utility.Constant
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CoroutineViewModel : ViewModel(){

   fun getData() {

       viewModelScope.launch {

           while (true){
               Log.d(Constant.TAG_Coroutine, "Continue Start ViewModelScope :")
               delay(300)
           }
       }
   }
}