package com.example.jetpackdemo.ViewModelShareDemo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ShareDemoViewModel : ViewModel() {

    // Mutable LiveData which observed by LiveData
    // and updated to EditTexts when it is changed.
    private val mutableLiveData : MutableLiveData<String> = MutableLiveData()


    fun setData(input : String) {

        mutableLiveData.value = "MeFirst "+ input
    }

    fun getData() : MutableLiveData<String> = mutableLiveData
}