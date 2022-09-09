package com.example.jetpackdemo.FileDemo.ViewModel

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.util.FileUtil
import com.google.android.gms.common.util.IOUtils.copyStream
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.*
import java.net.HttpURLConnection
import java.net.URL


class FileDemoViewMoldel(val fileDir : File) : ViewModel() {

//    private var pdfFile : File
//    private var dirPath : String
//    private var fileName : String

    var isFileReadyToObserve = MutableLiveData<Boolean>()

    var getUriFile : Uri?

    init {

        getUriFile = null

    }

   // init {

//        dirPath = "${fileDir}/cert/pdffiles"
//        val  dirFile = File(dirPath)
//
//        if(!dirFile.exists()){
//
//            dirFile.mkdir()
//        }
//        fileName = "Demographs.pdf"
//
//        val file = "${dirPath}/${fileName}"
//        pdfFile = File(file)
//
//        Log.d(Constant.TAG_Coroutine,pdfFile.absolutePath.toString())
//
//        if(pdfFile.exists()) {
//
//            pdfFile.delete()
//        }

  //  }


//     fun getPdfFileUrl(): File = pdfFile
//
//    fun writeToFile(strUrl : String){
//
//       try {
//           val url = URL(strUrl)
//           var inputStream: InputStream? = null
//
//           // on below line we are creating our http url connection.
//           val urlConnection: HttpURLConnection = url.openConnection() as HttpsURLConnection
//
//
//           val fileReader = ByteArray(4096)
//
//           var fileSizeDownload = 0
//
//           val fos : OutputStream = FileOutputStream(pdfFile)
//           do{
//               val read = inputStream!!.read(fileReader)
//               if(read !=1){
//                   fos.write(fileReader,0,read)
//
//                   fileSizeDownload += read
//
//               }
//           }while ( read != -1)
//
//           fos.flush()
//           fos.close()
//
//           isFileReadyToObserve.postValue(true)
//       }catch (ex :Exception){
//
//           isFileReadyToObserve.postValue(false)
//       }
//
//   }


    @RequiresApi(Build.VERSION_CODES.Q)
    fun downloadPdfWithMediaStore(context : Context) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                //"https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf"

              //  https://unec.edu.az/application/uploads/2014/12/pdf-sample.pdf
                val url =
                    URL("http://mgfm.co.in/invoice/Invoice9019044.pdf")
                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.doOutput = true
                connection.connect()
                val pdfInputStream: InputStream = connection.inputStream

                val values = ContentValues().apply {
                    put(MediaStore.Downloads.DISPLAY_NAME, "test")
                    put(MediaStore.Downloads.MIME_TYPE, "application/pdf")
                    put(MediaStore.Downloads.IS_PENDING, 1)
                }

                val resolver = context.contentResolver

                val collection =
                    MediaStore.Downloads.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)

                val itemUri = resolver.insert(collection, values)

                getUriFile = itemUri

                if (itemUri != null) {
                    resolver.openFileDescriptor(itemUri, "w").use { parcelFileDescriptor ->
                        ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescriptor)
                            .write(pdfInputStream.readBytes())
                    }
                    values.clear()
                    values.put(MediaStore.Downloads.IS_PENDING, 0)
                    resolver.update(itemUri, values, null, null)
                    isFileReadyToObserve.postValue(true)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                isFileReadyToObserve.postValue(false)
            }
        }
    }



    @RequiresApi(Build.VERSION_CODES.Q)
     fun saveFileUsingMediaStore(context: Context, url: String, fileName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                    put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
                }
                val resolver = context.contentResolver
                val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
                if (uri != null) {
                    URL(url).openStream().use { input ->
                        resolver.openOutputStream(uri).use { output ->
                            input.copyTo(output!!, DEFAULT_BUFFER_SIZE)
                        }
                    }

                    getUriFile = uri
                     isFileReadyToObserve.postValue(true)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                isFileReadyToObserve.postValue(false)
            }
        }
    }





}