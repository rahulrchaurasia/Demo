package com.example.jetpackdemo.Utility

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.util.Base64.encodeToString
import android.util.Log
import androidx.core.content.FileProvider
import com.example.jetpackdemo.BuildConfig
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

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

    open fun createImageFile(name: String,context: Context ): File? {
        // Create an image file name
        val temp: File
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir = getAppSpecificAlbumStorageDir(
            context.applicationContext,
            Environment.DIRECTORY_PICTURES,
            "PolicyBossProElite"
        )
        try {
            temp = File.createTempFile(
                name + timeStamp,  /* prefix */
                ".jpg",  /* suffix */
                storageDir /* directory */
            )
            Log.d("IMAGE_PATH", "File Name" + temp.name + "File Path" + temp.absolutePath)
            //  String  currentPhotoPath = temp.getAbsolutePath();
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        return temp
    }

    open fun getAppSpecificAlbumStorageDir(
        context: Context,
        albumName: String?,
        subAlbumName: String?
    ): File {
        // Get the pictures directory that's inside the app-specific directory on
        // external storage.
        val file = File(context.getExternalFilesDir(albumName), subAlbumName)
        if (file.mkdirs()) {
            Log.e("fssfsf", "Directory not created")
        }
        return file
    }

    // URI TO Bitmap
    open fun getBitmapFromContentResolver(selectedFileUri: Uri?, context: Context): Bitmap? {
        return try {
            val parcelFileDescriptor = context.contentResolver.openFileDescriptor(
                selectedFileUri!!, "r"
            )
            val fileDescriptor = parcelFileDescriptor!!.fileDescriptor
            val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
            parcelFileDescriptor!!.close()
            image
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

//    private fun bitmapToBase64(bitmap: Bitmap): String? {
//        val byteArrayOutputStream = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
//        val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
//        return Base64.encodeToString(byteArray, Base64.DEFAULT)
//    }



}