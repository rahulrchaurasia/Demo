package com.example.jetpackdemo.webView

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.JavascriptInterface
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.jetpackdemo.BaseActivity
import com.example.jetpackdemo.R
import com.example.jetpackdemo.Utility.Constant
import com.example.jetpackdemo.Utility.Utility
import com.example.jetpackdemo.Utility.showSnackbar
import com.example.jetpackdemo.databinding.ActivityWebViewDemoBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

class WebViewDemoActivity : BaseActivity() {

     lateinit var binding: ActivityWebViewDemoBinding

    var toolbar: Toolbar? = null
    private lateinit var layout: View
    lateinit var imgUri : Uri

    var  CRNID : String ? = ""

    lateinit var cameraContracts : ActivityResultLauncher<Uri>   // Open Camera Using Uri

    var cameraResult : String = ""

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // Permission has been granted. Start camera preview Activity.
                Log.d(Constant.TAG_WEBVIEW,"Permission Granted via Launcher")
              //  showSnackBar(layout,"Permission Granted via Launcher")

                startCamera()      // Camera Start Using Camera Launcher

            } else {
                // Permission request was denied.
                Log.d(Constant.TAG_WEBVIEW,"Permission Denied via Launcher")
               // showSnackBar(layout,"Permission Denied via Launcher")

                settingDialog()      // Open setting Alert Dialog
            }
        }





    // Initializing other items


    // private String URL = "http://mgfm.co.in/andr.html";
    private val URL = "http://api.magicfinmart.com/images/android.html"

    var perms = arrayOf(
        "android.permission.CAMERA",
        "android.permission.WRITE_EXTERNAL_STORAGE",
        "android.permission.READ_EXTERNAL_STORAGE"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityWebViewDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        layout = binding.root
        setSupportActionBar(binding.toolbar)

        supportActionBar?.apply {

            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setTitle("WebView Kotlin ")
        }

        CRNID = ""
        cameraContracts = registerForActivityResult(ActivityResultContracts.TakePicture()){

            // binding.imgProfile.setImageURI(null)
           //  binding.imgProfile.setImageURI(imgUri)

            Log.d(Constant.TAG_WEBVIEW,"Got camera")





            // BitMap is data structure of image file which store the image in memory
            // startCropImageActivity(imageUri);


            if (this::imgUri.isInitialized){


                showDialog(msg = "Please wait ...")

                // Set the image in imageview for display

                lifecycleScope.launch(Dispatchers.Default) {


                   // delay(3000)
                    var mphoto : Bitmap?  =  Utility.getBitmapFromContentResolver(imgUri, this@WebViewDemoActivity)


                    if(mphoto != null){

                        cameraResult = Utility.bitmapToBase64(mphoto!!)

                        lifecycleScope.launch(Dispatchers.Main) {
                            binding.imgPic.setImageBitmap(mphoto);
                            Log.d(Constant.TAG_WEBVIEW, "Base64String :${cameraResult.take(200)}")
                            cancelDialog()
                        }

                    }



                }




            }

        }


          settingWebview()


        binding.progressBar.visibility = View.VISIBLE

        //endregion

    }

    //region webview Setting add Url
    private fun settingWebview() {
       // val settings: WebSettings = binding.webview.getSettings()
        binding.webview.settings.apply {

            javaScriptEnabled = true

//            javaScriptEnabled = true
//            builtInZoomControls = true
//            useWideViewPort = false
//            javaScriptEnabled = true
//            setSupportMultipleWindows(false)
//            loadsImagesAutomatically = true
//            lightTouchEnabled = true
//            domStorageEnabled = true
//            loadWithOverviewMode = true

        }
        



        binding.webview.addJavascriptInterface(
            MyJavaScriptInterface(),
            "Android"
        ) // comment : Interface bridge name Here
        binding.webview.loadUrl(URL)
    }

    //endregion



    fun startCamera() {

        imgUri = Utility.createImageUri(this@WebViewDemoActivity)
        cameraContracts.launch(imgUri)            // Camera Start Using Camera Launcher
    }


    fun settingDialog(){

        val dialogBuilder = AlertDialog.Builder(this)

        // set message of alert dialog
        dialogBuilder.setMessage(getString(R.string.permission_required))
            // if the dialog is cancelable
            .setCancelable(false)
            // positive button text and action
            .setPositiveButton("OPPEN SETTING", DialogInterface.OnClickListener { dialog, id ->

                dialog.dismiss()
                Utility.openSetting(this)

            }).setNegativeButton("CANCEL", DialogInterface.OnClickListener{ dialog, id ->

                dialog.dismiss()

            })



        // create dialog box
        val alert = dialogBuilder.create()
        // set title for alert dialog box
        alert.setTitle("Permission")
        // show alert dialog
        alert.show()
    }




    fun showCameraDialog() {


        /////////
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED) {


            Log.d(Constant.TAG_WEBVIEW,"Permission Granted via Req : Use Camera")
           // showSnackBar(layout,"Permission Granted via Req : Use Camera")

            startCamera()

        } else {
            requestCameraPermission()
        }
    }

    private fun requestCameraPermission() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // Display a SnackBar with a button to request the missing permission.
            layout.showSnackbar(
                R.string.permission_required,
                Snackbar.LENGTH_INDEFINITE,
                R.string.ok
            ) {

                //  For Rationale :  Again Request using launcher
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        } else {
            // For First time Ask Permission
            // You can directly ask for the permission.
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)

        }
    }


    //region JavascriptHandling
    inner class MyJavaScriptInterface {

        @JavascriptInterface
        fun getraccamera(crnid: String?) {

        //  Toast.makeText(this@WebViewDemoActivity,"data",Toast.LENGTH_SHORT).show()

            var cameraResult : String = "1"
            CRNID = crnid
            showCameraDialog()

        }

        @JavascriptInterface
        fun savecameraimage(): String {

            return  CRNID  +"||"+ cameraResult.take(200)
        }



        @JavascriptInterface
        fun getchromeurl(url: String?) {


           Utility.loadWebViewUrlInBrowser(context = this@WebViewDemoActivity, url = url)

        }


        //getchromeurl
   //loadWebViewUrlInBrowser


    }

    //endregion


}