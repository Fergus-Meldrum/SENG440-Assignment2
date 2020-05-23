package com.example.getuplord

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        imagePreview = findViewById(R.id.photoPreview)

        takePhoto = findViewById(R.id.button2)

        takePhoto.setOnClickListener {
            dispatchTakePictureIntent()
        }

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
                    Log.d("check1", "photoURI created successfully")
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

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Log.d("check2", "getting into if statement")
//            val imageBitmap = data?.extras?.get("data") as Bitmap
//            imagePreview.setImageBitmap(imageBitmap)
//        }
//    }

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


//    private val executor = Executors.newSingleThreadExecutor()
//    private lateinit var viewFinder: TextureView
//
//    private fun startCamera() {
//        // Create configuration object for the viewfinder use case
//        val previewConfig = PreviewConfig.Builder().apply {
//            setTargetResolution(Size(640, 480))
//        }.build()
//
//
//        // Build the viewfinder use case
//        val preview = Preview(previewConfig)
//
//        // Every time the viewfinder is updated, recompute layout
//        preview.setOnPreviewOutputUpdateListener {
//
//            // To update the SurfaceTexture, we have to remove it and re-add it
//            val parent = viewFinder.parent as ViewGroup
//            parent.removeView(viewFinder)
//            parent.addView(viewFinder, 0)
//
//            viewFinder.surfaceTexture = it.surfaceTexture
//            updateTransform()
//        }
//
//        // Add this before CameraX.bindToLifecycle
//
//        // Create configuration object for the image capture use case
//        val imageCaptureConfig = ImageCaptureConfig.Builder()
//            .apply {
//                // We don't set a resolution for image capture; instead, we
//                // select a capture mode which will infer the appropriate
//                // resolution based on aspect ration and requested mode
//                setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
//            }.build()
//
//        // Build the image capture use case and attach button click listener
//        val imageCapture = ImageCapture(imageCaptureConfig)
//        findViewById<ImageButton>(R.id.capture_button).setOnClickListener {
//            val file = File(externalMediaDirs.first(),
//                "${System.currentTimeMillis()}.jpg")
//
//            imageCapture.takePicture(file, executor,
//                object : ImageCapture.OnImageSavedListener {
//                    override fun onError(
//                        imageCaptureError: ImageCapture.ImageCaptureError,
//                        message: String,
//                        exc: Throwable?
//                    ) {
//                        val msg = "Photo capture failed: $message"
//                        Log.e("CameraXApp", msg, exc)
//                        viewFinder.post {
//                            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
//                        }
//                    }
//
//                    override fun onImageSaved(file: File) {
//                        val msg = "Photo capture succeeded: ${file.absolutePath}"
//                        Log.d("CameraXApp", msg)
//                        viewFinder.post {
//                            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
//                        }
//                    }
//                })
//        }
//
//        CameraX.bindToLifecycle(this, preview, imageCapture)
//    }
//
//    private fun updateTransform() {
//        val matrix = Matrix()
//
//        val centerX = viewFinder.width / 2f
//        val centerY = viewFinder.height / 2f
//
//        val rotationDegrees = when(viewFinder.display.rotation) {
//            Surface.ROTATION_0 -> 0
//            Surface.ROTATION_90 -> 90
//            Surface.ROTATION_180 -> 180
//            Surface.ROTATION_270 -> 270
//            else -> return
//        }
//        matrix.postRotate(-rotationDegrees.toFloat(), centerX, centerY)
//
//        viewFinder.setTransform(matrix)
//    }
//
//    /**
//     * Process result from permission request dialog box, has the request
//     * been granted? If yes, start Camera. Otherwise display a toast
//     */
//    override fun onRequestPermissionsResult(
//        requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
//        if (requestCode == REQUEST_CODE_PERMISSIONS) {
//            if (allPermissionsGranted()) {
//                viewFinder.post { startCamera() }
//            } else {
//                Toast.makeText(this,
//                    "Permissions not granted by the user.",
//                    Toast.LENGTH_SHORT).show()
//                finish()
//            }
//        }
//    }
//
//    /**
//     * Check if all permission specified in the manifest have been granted
//     */
//    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
//        ContextCompat.checkSelfPermission(
//            baseContext, it) == PackageManager.PERMISSION_GRANTED
//    }
//
//    //code from alternate method
//    // Exercise 5
//    private fun takePictureFromCamera() {
//        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        intent.resolveActivity(packageManager)?.let {
//            //code for
////            val uri = dayUri(year, month, day)
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
//            startActivityForResult(intent, REQUEST_CAMERA)
//        }
//    }



}
