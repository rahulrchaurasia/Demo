package com.example.jetpackdemo.Utility

import android.content.Context
import android.net.Uri
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.FileProvider
import java.io.File

object Constant {

    val TAG_HILT : String = "HiltDEMO"

    val TAG_KOTLIN : String = "KotlinDEMO"

    val TAG_Coroutine : String = "COROUTINE"

    val TAG_WORKER : String = "WorkerDEMO"

    val TAG_SCANNER : String = "ScannerDEMO"

    const val KEY_COUNT_VALUE = "key_count"

    const val KEY_COUNT_VALUE1 = "key_count1"

     val KEY_DATA : String = "keydata"

    val ErrorMessage : String = "Error Occoured"

    val ErrorDefault : String = "Unknown Error"

    val NetworkError : String = "No Inerenet Connection!!"


    fun hideKeyBoard(view: View?, context: Context) {
        if (view != null) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }


}