package com.example.jetpackdemo.Utility

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import com.example.jetpackdemo.BuildConfig
import java.io.File

object Utility {


    fun uriFromFile(context: Context, file: File): Uri {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            return FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file)
        }
        else
        {
            return Uri.fromFile(file)
        }
    }


     fun createImageUri(context: Context) : Uri {

        val image = File(context.filesDir,"camera_photo.png")

        return FileProvider.getUriForFile(context.applicationContext,
            "com.example.jetpackdemo.fileprovider",
            image
        )

    }


    fun openSetting(context: Context){
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val uri = Uri.fromParts("package", context.packageName, null)
        intent.data = uri
        context.startActivity(intent)
    }

}