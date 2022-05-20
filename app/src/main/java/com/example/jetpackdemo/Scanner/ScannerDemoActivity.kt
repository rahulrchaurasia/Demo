package com.example.jetpackdemo.Scanner

import android.R
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.LinearLayout
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import com.example.jetpackdemo.Utility.Constant
import com.example.jetpackdemo.databinding.ActivityScannerDemoBinding
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView

// Note : https://www.youtube.com/watch?v=jtT60yFPelI


class ScannerDemoActivity : AppCompatActivity() , ZXingScannerView.ResultHandler {

    lateinit var binding : ActivityScannerDemoBinding



    var contentFrame: ViewGroup? = null
    private var mScannerView: ZXingScannerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScannerDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        contentFrame = binding.contentFrame
        val hasFlash = packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)




        if (!hasFlash) {
            binding.flashLlight.setVisibility(View.INVISIBLE)
        }
        binding.flashLlight.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                // The toggle is enabled
                mScannerView!!.flash = true
            } else {
                // The toggle is disabled
                mScannerView!!.flash = false
            }
        })

    }

    fun QrScanner() {
        mScannerView = ZXingScannerView(this) // Programmatically initialize the scanner view
        contentFrame?.addView(mScannerView)
        //setContentView(mScannerView);
        mScannerView?.setResultHandler(this) // Register ourselves as a handler for scan results.
        mScannerView?.startCamera() // Start camera
    }

    override fun handleResult(rawResult: Result?) {
        // Do something with the result here

        // Do something with the result here

         Log.d(Constant.TAG_SCANNER,  "Barcode scan result " + rawResult!!.text)

        val i = Intent()
        System.out.println("ScanActivity=" + rawResult!!.text)
        i.putExtra("data", rawResult!!.text)
        setResult(RESULT_OK, i)
        finish()
        // show the scanner result into dialog box.
        /* AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        builder.setMessage(rawResult.getText());
        AlertDialog alert1 = builder.create();
        alert1.show();*/

        // If you would like to resume scanning, call this method below:
        // mScannerView.resumeCameraPreview(this);
    }


    override fun onResume() {
        super.onResume()
        QrScanner()
    }

    override fun onPause() {
        super.onPause()
        mScannerView?.stopCamera()           // Stop camera on pause
    }

    override fun onBackPressed() {

        super.onBackPressed()
        setResult(0);
        finish();
    }

}