package com.mike.google_photo_downloader

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var job: Job
    private lateinit var uiScope: CoroutineScope

    val MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 0
    val TAG = "MainActivity"
    private lateinit var imageView: ImageView
    private lateinit var textView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageView = findViewById(R.id.imageView)
        textView = findViewById(R.id.text)
        job = Job()
        uiScope = CoroutineScope(Dispatchers.Default + job)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
            )
        } else {

            uiScope.launch {
                saveImageToGallery()

            }

        }

    }


    private suspend fun saveImageToGallery() {
        when {
            intent?.action == Intent.ACTION_SEND -> {
                if (intent.type?.startsWith("image/") == true) {
                    handleSendImage(intent) // Handle single image being sent
                }
            }
            intent?.action == Intent.ACTION_SEND_MULTIPLE
                    && intent.type?.startsWith("image/") == true -> {

                Log.d(TAG, "Launched coroutine")
                handleSendMultipleImages(intent)

            }

        }

    }


    private suspend fun handleSendImage(intent: Intent) {
        (intent.getParcelableExtra<Parcelable>(Intent.EXTRA_STREAM) as? Uri)?.let {
            // Update UI to reflect image being shared
            val time = System.currentTimeMillis()

            val bitmap =
                MediaStore.Images.Media.getBitmap(contentResolver, it)
            Log.d(TAG, bitmap.byteCount.toString())
            MediaStore.Images.Media.insertImage(
                contentResolver,
                bitmap,
                time.toString(),
                "description"
            )
            withContext(Dispatchers.Main) {
            textView.text = " Photo Saved!!"}
        }
    }

    private suspend fun handleSendMultipleImages(intent: Intent) {
        withContext(Dispatchers.Default) {
            Log.d(TAG, "handleSendMultipleImages called")
            intent.getParcelableArrayListExtra<Parcelable>(Intent.EXTRA_STREAM)?.let { it ->

                it.forEachIndexed { index, element ->
                    (element as? Uri)?.let {

                        val time = System.currentTimeMillis()

                        val bitmap =
                            MediaStore.Images.Media.getBitmap(contentResolver, element)
                        MediaStore.Images.Media.insertImage(
                            contentResolver,
                            bitmap,
                            time.toString(),
                            "description"
                        )
                    }

                    Log.d(TAG, "finished")
                    withContext(Dispatchers.Main) {
                        // update UI here
                        textView.text = "${index + 1}/${it.size} saved"

                        if (index + 1 == it.size) textView.text = "All Photos Saved!!"
                    }

                }
            }
        }

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    uiScope.launch {
                        saveImageToGallery()

                    }
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
                    )
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }
}
