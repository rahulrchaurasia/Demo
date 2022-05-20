package com.example.jetpackdemo.Scanner

import android.os.Bundle
import android.view.SurfaceView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.jetpackdemo.Scanner.ScannerUtil.Scanner
import com.example.jetpackdemo.Scanner.ScannerUtil.ScannerListener
import com.example.jetpackdemo.databinding.ActivityTextScannerBinding

class TextScannerActivity : AppCompatActivity() {

    lateinit var surfaceView: SurfaceView

    lateinit var binding : ActivityTextScannerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTextScannerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        surfaceView = binding.surface

        val scanner = Scanner(this, surfaceView, object : ScannerListener {

            override fun onDetected(detections: String?) {

               // Toast.makeText(this@TextScannerActivity, detections, Toast.LENGTH_SHORT).show();

                binding.txtMessage.text = detections
            }
            override fun onStateChanged(state: String?, i: Int) {
               // binding.txtMessage.text = "State Change"
            }
        })



    }
}