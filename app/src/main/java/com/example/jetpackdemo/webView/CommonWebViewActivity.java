package com.example.jetpackdemo.webView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.jetpackdemo.BaseActivity;
import com.example.jetpackdemo.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonWebViewActivity extends BaseActivity {

    WebView webView;
    ImageView imgPic;
    Toolbar toolbar;

    String lat = "";
    String lon = "";
    static  String TAG = "MYLOCATION";


    // initializing
    // FusedLocationProviderClient
    // object
    FusedLocationProviderClient mFusedLocationClient;

    // Initializing other items
    // from layout file
    private static final int LOCATION_PERMISSION_ID = 44;
    private static final int pic_id = 123;

    private static final int CAMERA_REQUEST = 1888;
    public static final int PERMISSION_CAMERA_STORACGE_CONSTANT = 103;
    public static final int PERMISSION_ALL_LOAD = 104;

    private String PHOTO_File = "";
    File Docfile;
    File file;
    Uri imageUri;

   // private String URL = "http://mgfm.co.in/andr.html";

    private String URL =  "http://api.magicfinmart.com/images/android.html";

    String[] perms = {
            "android.permission.CAMERA",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_EXTERNAL_STORAGE"

    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_web_view);

        webView = (WebView) findViewById(R.id.webview);
        imgPic = (ImageView) findViewById(R.id.imgPic);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Webview Demo");

        settingWebview();


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        // getLastLocation();


        if (!checkAllPermission()) {

            requestAllPermissions();


        }


    }



    //region webview Setting add Url

    private void settingWebview() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        settings.setBuiltInZoomControls(true);
        settings.setUseWideViewPort(false);
        settings.setJavaScriptEnabled(true);
        settings.setSupportMultipleWindows(false);

        settings.setLoadsImagesAutomatically(true);
        settings.setLightTouchEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);


      /*  MyWebViewClient webViewClient = new MyWebViewClient(this);
        webView.setWebViewClient(webViewClient);*/

        webView.setWebViewClient(new WebViewClient() {


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO show you progress image

                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO hide your progress image

                super.onPageFinished(view, url);
            }


        });
        webView.getSettings().setBuiltInZoomControls(true);
        webView.addJavascriptInterface(new MyJavaScriptInterface(), "Android");   // comment : Interface bridge name Here

        webView.loadUrl(URL);

    }

    //endregion


    //region JavascriptHandling
    class MyJavaScriptInterface {


        // region For URL1
        // Web to Android
        @JavascriptInterface
        public void showToast(String msg) {

            // Toast.makeText(CommonWebViewActivity.this, msg, Toast.LENGTH_SHORT).show();
            galleryCamPopUp();

        }

        // Android to Web
        @JavascriptInterface
        public String getLatLon() {

            String latlon = lat  +","+lon ;
            if (!checkPermissions()) {
                requestPermissions();
            }else{
                getLastLocation();
            }
            return latlon ;
        }

        //endregion

        @JavascriptInterface
        public String getraccamera(String crnid) {

            // Toast.makeText(CommonWebViewActivity.this, "msg", Toast.LENGTH_SHORT).show();
            galleryCamPopUp();

            return "5";
        }

        // Android to Web
        @JavascriptInterface
        public String savecameraimage() {

            String latlon = lat  +","+lon ;
            if (!checkPermissions()) {
                requestPermissions();
            }else{
                getLastLocation();
            }
            return latlon ;
        }




    }

    //endregion


    // region Handling Google Location
    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                // getting last
                // location from
                // FusedLocationClient
                // object
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {

                            Log.d(TAG,"Latitude: " + location.getLatitude() + "" );
                            Log.d(TAG,"Longitude: " + location.getLongitude() + "" );

                            lat = String.valueOf(location.getLatitude());
                            lon  = String.valueOf(location.getLongitude());

                        }
                    }
                });
            } else {

                oppenSetting();

            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }
    }

    private void oppenSetting() {
        // Create the object of AlertDialog Builder class
        AlertDialog.Builder builder = new AlertDialog.Builder(CommonWebViewActivity.this);

        // Set the message show for the Alert time
        builder.setMessage("Need Location Permission?");

        // Set Alert Title
        builder.setTitle("Alert !");

        // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
        builder.setCancelable(false);

        // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
            // When the user click yes button then app will close
            dialog.cancel();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        });

        // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            // If user click no then dialog box is canceled.
            dialog.cancel();
        });

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();
        // Show the Alert Dialog box
        alertDialog.show();
    }


    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();

            Log.d(TAG,"Latitude: " + mLastLocation.getLatitude() + "" );
            Log.d(TAG,"Longitude: " + mLastLocation.getLongitude() + "" );

            lat = String.valueOf(mLastLocation.getLatitude());
            lon  = String.valueOf(mLastLocation.getLongitude());

        }
    };

    //endregion

    // region Location Permission Handling

    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_ID);
    }

    // method to check
    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


    // If everything is alright then
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if (requestCode == LOCATION_PERMISSION_ID) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                getLastLocation();
//                Snackbar.make(webView,"onRequestPermissionsResult Called",Snackbar.LENGTH_SHORT).show();
//            }
//        }
//    }

    //endregion


    //region  camera handling
    private void openCamera(){


        String FileName = "PHOTO_File";


        Docfile = createImageFile(FileName);

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            imageUri = Uri.fromFile(Docfile);
        } else {
            imageUri = FileProvider.getUriForFile(CommonWebViewActivity.this,
                    getString(R.string.file_provider_authority), Docfile);
        }


        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                imageUri);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);



    }

    public File createImageFile(String name)  {
        // Create an image file name
        File temp;
        String timeStamp  =new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File storageDir  = getAppSpecificAlbumStorageDir(this, Environment.DIRECTORY_PICTURES,"Demo");
        try {
            temp =  File.createTempFile(name + timeStamp, /* prefix */
                    ".jpg", /* suffix */
                    storageDir /* directory */
            );

            Log.d("IMAGE_PATH","File Name"+ temp.getName() + "File Path" +temp.getAbsolutePath());
            //  String  currentPhotoPath = temp.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return  null;
        }
        return  temp;
    }

    public File getAppSpecificAlbumStorageDir(Context context,String albumName ,  String subAlbumName) {
        // Get the pictures directory that's inside the app-specific directory on
        // external storage.
        File file = new File(context.getExternalFilesDir(albumName), subAlbumName);
        if (file.mkdirs()) {
            Log.e(TAG, "Directory not created");
        }

        return file;
    }

    public Bitmap getBitmapFromContentResolver(Uri selectedFileUri) {
        try {
            ParcelFileDescriptor parcelFileDescriptor =
                    getContentResolver().openFileDescriptor(selectedFileUri, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);

            parcelFileDescriptor.close();
            return  image;
        } catch (IOException e) {
            e.printStackTrace();
            return  null;
        }
    }

    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    //endregion



    private boolean checkAllPermission() {

        int camera = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);

        int WRITE_EXTERNAL = ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int READ_EXTERNAL = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int ACCESS_COARSE_LOCATION = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION);
        int ACCESS_FINE_LOCATION = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            return camera == PackageManager.PERMISSION_GRANTED

                    && READ_EXTERNAL == PackageManager.PERMISSION_GRANTED
                    && ACCESS_COARSE_LOCATION == PackageManager.PERMISSION_GRANTED
                    && ACCESS_FINE_LOCATION == PackageManager.PERMISSION_GRANTED;
        }else{
            return camera == PackageManager.PERMISSION_GRANTED
                    &&  WRITE_EXTERNAL == PackageManager.PERMISSION_GRANTED
                    && READ_EXTERNAL == PackageManager.PERMISSION_GRANTED
                    && ACCESS_COARSE_LOCATION == PackageManager.PERMISSION_GRANTED
                    && ACCESS_FINE_LOCATION == PackageManager.PERMISSION_GRANTED;
        }


    }

    private void requestAllPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ALL_LOAD);
    }


    // region permission for camera and Storage
    private boolean checkCameraStoragePermission() {

        int camera = ActivityCompat.checkSelfPermission(getApplicationContext(), perms[0]);

        int WRITE_EXTERNAL = ActivityCompat.checkSelfPermission(getApplicationContext(), perms[1]);
        int READ_EXTERNAL = ActivityCompat.checkSelfPermission(getApplicationContext(), perms[2]);

        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            return camera == PackageManager.PERMISSION_GRANTED

                    && READ_EXTERNAL == PackageManager.PERMISSION_GRANTED;
        }else{
            return camera == PackageManager.PERMISSION_GRANTED
                    &&  WRITE_EXTERNAL == PackageManager.PERMISSION_GRANTED
                    && READ_EXTERNAL == PackageManager.PERMISSION_GRANTED;

        }
    }

    private boolean checkRationalePermission() {

        boolean camera = ActivityCompat.shouldShowRequestPermissionRationale(CommonWebViewActivity.this, perms[0]);

        boolean write_external = ActivityCompat.shouldShowRequestPermissionRationale(CommonWebViewActivity.this, perms[1]);
        boolean read_external = ActivityCompat.shouldShowRequestPermissionRationale(CommonWebViewActivity.this, perms[2]);

        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            return  camera ||  read_external;
        }else{
            return  camera ||write_external   || read_external;

        }
    }

    private void requestCameraStoragePermission() {
        ActivityCompat.requestPermissions(this, perms, PERMISSION_CAMERA_STORACGE_CONSTANT);
    }


    public void galleryCamPopUp() {


        PHOTO_File = "temp_file" ;

        if (!checkCameraStoragePermission()) {

            if (checkRationalePermission()) {
                //Show Information about why you need the permission
                requestCameraStoragePermission();

            } else {
                //Previously Permission Request was cancelled with 'Dont Ask Again',
                // Redirect to Settings after showing Information about why you need the permission

                // permissionAlert(navigationView,"Need Call Permission","This app needs Call permission.");

                Toast.makeText(CommonWebViewActivity.this, "Need Permission of Camera", Toast.LENGTH_SHORT).show();


            }
        } else {

            openCamera();
        }
    }

    //endregion

    // region Event For onActivityResult and  onRequestPermissionsResult

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            // BitMap is data structure of image file which store the image in memory
            // startCropImageActivity(imageUri);

            if(imageUri != null){
                // Set the image in imageview for display
                //  imgPic.setImageBitmap(photo);
                imgPic.setImageURI(imageUri);

                Bitmap mphoto = getBitmapFromContentResolver(imageUri);
                String  bitmapToBase64  = bitmapToBase64(mphoto);

                Log.d(TAG, "Base64String :"+bitmapToBase64 );


                // getUploadImage("123",bitmapToBase64);
                // getUploadImage("123","aGjk0000kkkg");

            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CAMERA_STORACGE_CONSTANT:
                if (grantResults.length > 0) {

                    //boolean writeExternal = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    boolean camera = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean writeExternal = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean readExternal = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean minSdk29 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q;

                    if (camera && (writeExternal || minSdk29) && readExternal) {

                        //showCamerGalleryPopUp();
                        openCamera();

                    }

                }
                break;

            case LOCATION_PERMISSION_ID:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLastLocation();

                }
                break;

        }
    }

    //endregion

}