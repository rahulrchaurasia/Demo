package com.example.jetpackdemo

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.jetpackdemo.databinding.ActivityBaseBinding
import com.example.jetpackdemo.databinding.DialogLoadingBinding
import com.google.android.material.snackbar.Snackbar

open class BaseActivity : AppCompatActivity() {

       private lateinit var bindingMain : ActivityBaseBinding

       private lateinit var dialog : Dialog

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            bindingMain = ActivityBaseBinding.inflate(layoutInflater)
            setContentView(bindingMain.root)

             dialog = Dialog(this)


        }

        open fun showDialog(msg: String = "Loading Please Wait!!"){

            if(!dialog.isShowing) {
                val dialogLoadingBinding = DialogLoadingBinding.inflate(layoutInflater)
                dialog.setContentView(dialogLoadingBinding.root)
                if (dialog.window != null) {

                    dialog.window!!.setBackgroundDrawable(ColorDrawable(0))

                }
                if(msg.isNotEmpty()){
                    dialogLoadingBinding.txtMessage.text = msg

                }
                dialog.setCancelable(false)
                dialog.show()
            }
        }

        fun cancelDialog(){

            if(dialog != null){

                if(dialog.isShowing){

                    dialog.dismiss()
                }
            }

        }


        fun showAlert(msg : String){

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Alert")
            builder.setMessage(msg)
    //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

            builder.setPositiveButton(android.R.string.ok) { dialog, which ->

            }


            builder.show()
        }

    fun showSnackBar(view : View, strMessage: String){

        Snackbar.make(view, strMessage, Snackbar.LENGTH_SHORT).show()

    }

}