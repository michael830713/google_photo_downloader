package com.mike.google_photo_downloader

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mike.google_photo_downloader.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        Log.d(TAG,"onCreate")
//
//        MobileAds.initialize(this) {}
//        galleryIntent = Intent(Intent.ACTION_VIEW)
//        galleryIntent.type = "image/*"
//
//        binding.googlePhotosButton.setOnClickListener {
//            val newIntent: Intent? =
//                packageManager.getLaunchIntentForPackage("com.google.android.apps.photos")
//            val webIntent = Intent(
//                Intent.ACTION_VIEW,
//                Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.photos")
//            )
//            if (newIntent != null)
//                startActivity(newIntent)
//            else startActivity(webIntent)
//        }
//
//        uiScope = CoroutineScope(Dispatchers.Default + Job())
//
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//            != PackageManager.PERMISSION_GRANTED
//        ) {
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
//                MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
//            )
//        } else {
//            uiScope.launch {
//                saveImageToGallery(intent)
//            }
//        }
    }



//    override fun onNewIntent(intent: Intent?) {
//
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//            != PackageManager.PERMISSION_GRANTED
//        ) {
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
//                MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
//            )
//        } else {
//
//            uiScope.launch {
//                saveImageToGallery(intent)
//            }
//
//        }
//        super.onNewIntent(intent)
//
//    }


//    private suspend fun saveImageToGallery(intent: Intent?) {
//        when (intent?.action) {
//            Intent.ACTION_SEND -> {
//                if (intent.type?.startsWith("image/") == true) {
//                    handleSendImage(intent) // Handle single image being sent
//                }
//            }
//            Intent.ACTION_SEND_MULTIPLE
//            -> {
//                if (intent.type?.startsWith("image/") == true) {
//                    handleSendMultipleImages(intent)
//
//                } else {
//                    withContext(Dispatchers.Main) {
//                        Snackbar.make(
//                            binding.root,
//                            "video not supported yet !",
//                            Snackbar.LENGTH_LONG
//                        ).show()
//                    }
//                }
//
//
//            }
//        }
//
//    }


//    private suspend fun handleSendImage(intent: Intent) {
//        (intent.getParcelableExtra<Parcelable>(Intent.EXTRA_STREAM) as? Uri)?.let {
//            // Update UI to reflect image being shared
//            val time = System.currentTimeMillis()
//
//            val bitmap =
//                MediaStore.Images.Media.getBitmap(contentResolver, it)
//            MediaStore.Images.Media.insertImage(
//                contentResolver,
//                bitmap,
//                time.toString(),
//                "description"
//            )
//
//            withContext(Dispatchers.Main) {
//                binding.text.text = " Photo Saved!!"
//
//
//                startActivity(galleryIntent)
//            }
//        }
//    }

//    private suspend fun handleSendMultipleImages(intent: Intent) {
//        withContext(Dispatchers.Default) {
//            intent.getParcelableArrayListExtra<Parcelable>(Intent.EXTRA_STREAM)?.let { it ->
//
//                it.forEachIndexed { index, element ->
//                    (element as? Uri)?.let {
//
//                        val time = System.currentTimeMillis()
//
//                        val bitmap =
//                            MediaStore.Images.Media.getBitmap(contentResolver, element)
//                        MediaStore.Images.Media.insertImage(
//                            contentResolver,
//                            bitmap,
//                            time.toString(),
//                            "description"
//                        )
//                    }
//
//                    withContext(Dispatchers.Main) {
//                        // update UI here
//                        binding.text.text = "${index + 1}/${it.size} saved"
//                        binding.determinateBar.visibility = View.VISIBLE
//                        binding.googlePhotosButton.isClickable = false
//                        binding.determinateBar.progress =
//                            ((index + 1).toDouble().div(it.size) * 100).toInt()
//                        Log.d(TAG, binding.determinateBar.progress.toString())
//
//                        if (index + 1 == it.size) {
//                            binding.text.text = "All Photos Saved!!"
//                            binding.determinateBar.visibility = View.GONE
//                            binding.googlePhotosButton.isClickable = true
//
//
//                            startActivity(galleryIntent)
//
//                        }
//                    }
//
//                }
//            }
//        }
//
//    }


//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        when (requestCode) {
//            MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE -> {
//                // If request is cancelled, the result arrays are empty.
//                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
//                    uiScope.launch {
//                        saveImageToGallery(intent)
//                    }
//                } else {
//                    ActivityCompat.requestPermissions(
//                        this,
//                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
//                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
//                    )
//                }
//            }
//        }
//    }
}
