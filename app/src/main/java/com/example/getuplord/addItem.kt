package com.example.getuplord

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Size
import android.graphics.Matrix
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.Surface
import android.view.TextureView
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.camera.core.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.LifecycleOwner
import java.io.File
import java.io.IOException
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


private const val REQUEST_IMAGE_CAPTURE = 1
private const val REQUEST_TAKE_PHOTO = 1

private const val REQUEST_CODE_PERMISSIONS = 10

// This is an array of all the permission specified in the manifest.
private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)

class addItem : AppCompatActivity() , LifecycleOwner {

    lateinit var imagePreview: ImageView
    lateinit var takePhoto: Button
    lateinit var imageBitmap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        imagePreview = findViewById(R.id.photoPreview)

        takePhoto = findViewById(R.id.button2)

        takePhoto.setOnClickListener {
            dispatchTakePictureIntent()
        }



        BitmapFactory.decodeFile("/storage/emulated/0/Android/data/com.example.getuplord/files/Pictures/JPEG_testPic1_7510796440909568314.jpg")?.also { bitmap ->
            imageBitmap = bitmap
        }

        val matrix = Matrix()
        matrix.postRotate(90F)

        val scaledBitmap : Bitmap = Bitmap.createScaledBitmap(imageBitmap, 640, 640, true)
        val rotatedImage: Bitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, 640, 640, matrix, true)

        imagePreview.setImageBitmap(rotatedImage)

        // Add this at the end of onCreate function

//        viewFinder = findViewById(R.id.view_finder)
//
//        // Request camera permissions
//        if (allPermissionsGranted()) {
//            viewFinder.post { startCamera() }
//        } else {
//            ActivityCompat.requestPermissions(
//                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
//        }
//
//        // Every time the provided texture view changes, recompute layout
//        viewFinder.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
//            updateTransform()
//        }


    }

    //new code from andriod docs


    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    Log.d("error", "Error occurred while creating the File")
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.example.android.fileprovider",
                        it
                    )
                    val photoPath: String = photoFile.invariantSeparatorsPath
                    Log.d("check1", photoPath)
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }
    }

//    private fun dispatchTakePictureIntent() {
//        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
//            takePictureIntent.resolveActivity(packageManager)?.also {
//                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
//            }
//        }
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Log.d("check2", "getting into if statement")
//            val imageBitmap = data?.extras?.get("data") as Bitmap
//            imagePreview.setImageBitmap(imageBitmap)
            BitmapFactory.decodeFile("/storage/emulated/0/Android/data/com.example.getuplord/files/Pictures/JPEG_testPic1_3304633960654210402.jpg")?.also { bitmap ->
                imagePreview.setImageBitmap(bitmap)
            }
        }
    }

    lateinit var currentPhotoPath: String

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val fileName: String = "testPic1"
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${fileName}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }



}
