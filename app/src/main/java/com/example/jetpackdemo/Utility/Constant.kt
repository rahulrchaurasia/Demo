package com.example.jetpackdemo.Utility

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

object Constant {

    val TAG_HILT : String = "HiltDEMO"

    val TAG_KOTLIN : String = "KotlinDEMO"

    val TAG_Coroutine : String = "COROUTINE"

    val TAG_WORKER : String = "WorkerDEMO"

    val TAG_SCANNER : String = "ScannerDEMO"

    const val KEY_COUNT_VALUE = "key_count"

    const val KEY_COUNT_VALUE1 = "key_count1"


    fun hideKeyBoard(view: View?, context: Context) {
        if (view != null) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}