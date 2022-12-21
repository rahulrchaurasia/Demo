package com.example.jetpackdemo.CameraGalleryDemo

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.jetpackdemo.Utility.Constant
import com.example.jetpackdemo.databinding.ActivityMultiPermiisionDemo2Binding
import com.example.jetpackdemo.databinding.ActivityMultiplePermissionBinding

class MultiPermiisionDemo2Activity : AppCompatActivity() {

    private lateinit var binding: ActivityMultiPermiisionDemo2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMultiPermiisionDemo2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        supportActionBar!!.apply {

            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setTitle("Multiple Permission")
        }

        binding.btnMultiPermission.setOnClickListener {

            checkAndRequestPermissions()
        }


    }

    private fun checkAndRequestPermissions(): Boolean {
        val readPhoneState =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
        val read_call_log =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG)
        val listPermissionsNeeded: MutableList<String> = ArrayList()
        if (readPhoneState != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE)
        }
        if (read_call_log != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_CALL_LOG)
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                listPermissionsNeeded.toTypedArray(),
                Constant.REQUEST_ID_MULTIPLE_PERMISSIONS
            )
            return false
        }
        return true
    }
}