package com.example.jetpackdemo.TrucallerModel


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.jetpackdemo.Utility.Constant
import com.example.jetpackdemo.databinding.ActivityCallerDialogBinding

class CallerDialogActivity : AppCompatActivity() {


    private lateinit var binding: ActivityCallerDialogBinding

    //region broadcast receiver
    var mHandleMessageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action != null) {
                if (intent.action.equals(Constant.PUSH_BROADCAST_ACTION, ignoreCase = true)) {

                    if (intent.getStringExtra("mob") != null) {

                        var mob = intent.getStringExtra("mob")

                        binding.txtMob.text ="" + mob
                    }
                }


            }
        }
    }


    //endregion


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCallerDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
      //  requestWindowFeature(Window.FEATURE_NO_TITLE)
       // setSupportActionBar(binding.toolbar)


    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        if(intent?.getStringExtra("mob") != null){

            binding.txtMob.text = intent?.getStringExtra("mob")
        }
    }


    override fun onResume() {
        super.onResume()

      //  LocalBroadcastManager.getInstance(this@CallerDialogActivity).registerReceiver(mHandleMessageReceiver, IntentFilter(Constant.PUSH_BROADCAST_ACTION))



    }


}