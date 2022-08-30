package com.example.jetpackdemo.StateFlowBasic

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class StateFlowBasicViewModel : ViewModel() {

    private val _liveData = MutableLiveData("Hello world 1 :  ")
    val liveData : LiveData<String> = _liveData
    // or : using proprerty
   // get() = _liveData


    private val _stateFlow = MutableStateFlow("Hello World 2: ")
    val stateFlow = _stateFlow.asStateFlow()


    private val _sharedFlow= MutableSharedFlow<String>()
    val sharedFlow=  _sharedFlow.asSharedFlow()


    fun triggeredLiveData(){
        _liveData.value = "LiveData"
    }

    fun triggeredStateFlow(){
        _stateFlow.value = "StateFlowData"         // same as liveData
    }

    fun triggeredFlow() :Flow<String>{

        return flow {
            repeat(5){

                emit("Item $it")
                delay(1000)
            }

        }
    }
    fun triggeredShareFlow(){

        viewModelScope.launch {
            _sharedFlow.emit("Shared Flow")
        }
    }


    public  fun executeLongRunningTask(){


        for( i in 1..100000000){

        }


    }
}