package com.mike.google_photo_downloader

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.provider.MediaStore
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {
    private lateinit var job: Job
    private lateinit var uiScope: CoroutineScope

    val MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 0
    val TAG = "MainActivity"
    private lateinit var textView: TextView
    private lateinit var layout: ConstraintLayout
    private lateinit var mAdView: AdView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MobileAds.initialize(this) {}
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
        textView = findViewById(R.id.text)
        layout = findViewById(R.id.mainView)
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
                saveImageToGallery(intent)
            }

        }

    }

    override fun onNewIntent(intent: Intent?) {

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
                saveImageToGallery(intent)
            }

        }
        super.onNewIntent(intent)

    }


    private suspend fun saveImageToGallery(intent: Intent?) {
        when (intent?.action) {
            Intent.ACTION_SEND -> {
                if (intent.type?.startsWith("image/") == true) {
                    handleSendImage(intent) // Handle single image being sent
                } else {
                }
            }
            Intent.ACTION_SEND_MULTIPLE
            -> {
                if (intent.type?.startsWith("image/") == true) {
                    handleSendMultipleImages(intent)

                } else {
                    withContext(Dispatchers.Main) {
                        Snackbar.make(
                            layout.rootView,
                            "video not supported yet !",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }


            }
        }

    }


    private suspend fun handleSendImage(intent: Intent) {
        (intent.getParcelableExtra<Parcelable>(Intent.EXTRA_STREAM) as? Uri)?.let {
            // Update UI to reflect image being shared
            val time = System.currentTimeMillis()

            val bitmap =
                MediaStore.Images.Media.getBitmap(contentResolver, it)
            MediaStore.Images.Media.insertImage(
                contentResolver,
                bitmap,
                time.toString(),
                "description"
            )

            withContext(Dispatchers.Main) {
                textView.text = " Photo Saved!!"
            }
        }
    }

    private suspend fun handleSendMultipleImages(intent: Intent) {
        withContext(Dispatchers.Default) {
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
                        saveImageToGallery(intent)
                    }
                } else {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
                    )
                }
            }
        }
    }
}
