package com.example.jetpackdemo.TrucallerModel.BroadCastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.telephony.TelephonyManager
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.example.jetpackdemo.ServiceDemo.Service.ForegroundService
import com.example.jetpackdemo.TrucallerModel.CallerDialogActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import java.util.*


class CallReceiver()  : BroadcastReceiver() {

    private var lastState = TelephonyManager.CALL_STATE_IDLE
    private var callStartTime: Date? = null
    private var isIncoming = false
    private var savedNumber: String? = null

    private val scope = CoroutineScope(SupervisorJob())

    override fun onReceive(context: Context?, intent: Intent?) {

        val stateStr: String = intent!!.getExtras()!!.getString(TelephonyManager.EXTRA_STATE)!!
        var state = 0
        if (stateStr == TelephonyManager.EXTRA_STATE_IDLE) {
            state = TelephonyManager.CALL_STATE_IDLE


        } else if (stateStr == TelephonyManager.EXTRA_STATE_OFFHOOK) {
            state = TelephonyManager.CALL_STATE_OFFHOOK

        } else if (stateStr == TelephonyManager.EXTRA_STATE_RINGING) {
            state = TelephonyManager.CALL_STATE_RINGING

        }
        onCallStateChanged(context, state)
    }


    private fun toast(context:Context? , text: String) = Toast.makeText( context, text, Toast.LENGTH_LONG).show()


    fun onCallStateChanged(context: Context?, state: Int) {
        // String cname=getContactName(context,number);
//        if (lastState == state) {
//            return
//        }
        when (state) {
            TelephonyManager.CALL_STATE_RINGING -> {

            }
            TelephonyManager.CALL_STATE_OFFHOOK -> {


            }
            TelephonyManager.CALL_STATE_IDLE -> {
                toast(context, "Phone call is ended")

//               NotifyService(context!!).callNotification1()
//                moveToCallerDialog(context)
                openExternalApp(context)
                ForegroundService.startService(context!!,"data")



            }
        }
         lastState = state
    }




    fun moveToCallerDialog(context: Context?){



//         val intent = Intent(Constant.PUSH_BROADCAST_ACTION)
//        intent.putExtra("mob","980808")
//        LocalBroadcastManager.getInstance(context!!.getApplicationContext()).sendBroadcast(intent)

       // ForegroundService.startService(context!! ,"Calling Start" )

//        val intent = Intent(context, CallerDialogActivity::class.java)
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
//        intent.putExtra("phone_no", "number")
//
        Handler(Looper.getMainLooper()).postDelayed(Runnable
        {
            val intentone = Intent(context?.getApplicationContext(), CallerDialogActivity::class.java)

            intentone.putExtra("mob", "98080")
          // intentone.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK  or Intent.FLAG_ACTIVITY_NEW_TASK)
            intentone.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            context?.startActivity(intentone)
        }, 3000
        )

//        if (!Settings.canDrawOverlays(context)) {
//            val intent1 = Intent(
//                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
//                Uri.parse("package:" + context!!.getPackageName())
//            )



           // startActivityForResult(OpenAnotherActivity,intent,0)

        }


    private fun openExternalApp(context: Context?) {

        Handler(Looper.getMainLooper()).postDelayed(Runnable
        {
            var packageName1 =
                "com.utility.finmartcontact"  //"com.utility.finmartcontact/.login.LoginActivity"
            var packageName = "com.example.jetpackdemo"  //"com.google.android.youtube"
            val launchIntent =
                context!!.packageManager.getLaunchIntentForPackage(packageName)
            if (launchIntent != null) {

                launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                context?.startActivity(launchIntent)

            }
        }, 2000
        )

    }




    private fun RequestPermission() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (!Settings.canDrawOverlays(this)) {
//                val intent = Intent(
//                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
//                    Uri.parse("package:" + this.getPackageName())
//                )
//                startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE)
//            } else {
//                //Permission Granted-System will work
//            }
//        }
    }

    fun canDrawOverlays1(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) true else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            Settings.canDrawOverlays(context)
        } else {
            if (Settings.canDrawOverlays(context)) return true
            try {
                val mgr = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                    ?: return false
                //getSystemService might return null
                val viewToAdd = View(context)
                val params = WindowManager.LayoutParams(
                    0,
                    0,
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY else WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSPARENT
                )
                viewToAdd.setLayoutParams(params)
                mgr.addView(viewToAdd, params)
                mgr.removeView(viewToAdd)
                return true
            } catch (e: Exception) {
                e.printStackTrace()
            }
            false
        }
    }

    fun canDrawOverlays(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) true else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            Settings.canDrawOverlays(context)
        } else {
            if (Settings.canDrawOverlays(context)) return true
            try {
                val mgr = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                    ?: return false
                //getSystemService might return null
                val viewToAdd = View(context)
                val params = WindowManager.LayoutParams(
                    0,
                    0,
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY else WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSPARENT
                )
                viewToAdd.layoutParams = params
                mgr.addView(viewToAdd, params)
                mgr.removeView(viewToAdd)
                return true
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
            false
        }
    }

//    fun canDrawOverlaysAfterUserWasAskedToEnableIt(
//        context: Context?,
//        listener: OverlayCheckedListener?
//    ) {
//        if (context == null || listener == null) return
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
//            listener.onOverlayPermissionChecked(true)
//            return
//        } else {
//            if (Settings.canDrawOverlays(context)) {
//                listener.onOverlayPermissionChecked(true)
//                return
//            }
//            try {
//                val mgr = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
//                if (mgr == null) {
//                    listener.onOverlayPermissionChecked(false)
//                    return  //getSystemService might return null
//                }
//                val viewToAdd = View(context)
//                val params = WindowManager.LayoutParams(
//                    0,
//                    0,
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY else WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
//                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
//                    PixelFormat.TRANSPARENT
//                )
//                viewToAdd.layoutParams = params
//                mgr.addView(viewToAdd, params)
//                val handler = Handler(Looper.getMainLooper())
//                handler.postDelayed(Runnable {
//                    if (listener != null && viewToAdd != null && mgr != null) {
//                        listener.onOverlayPermissionChecked(viewToAdd.isAttachedToWindow)
//                        mgr.removeView(viewToAdd)
//                    }
//                }, 500)
//            } catch (e: java.lang.Exception) {
//                listener.onOverlayPermissionChecked(false)
//            }
//        }
//    }

}