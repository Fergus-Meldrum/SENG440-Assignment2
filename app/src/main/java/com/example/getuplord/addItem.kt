package com.example.getuplord

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.*
import androidx.core.content.FileProvider
import androidx.lifecycle.LifecycleOwner
import java.io.File
import java.io.IOException


private const val REQUEST_IMAGE_CAPTURE = 1
private const val REQUEST_TAKE_PHOTO = 1

private const val REQUEST_CODE_PERMISSIONS = 10

// This is an array of all the permission specified in the manifest.
private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)

class addItem : AppCompatActivity() , LifecycleOwner {

    lateinit var imagePreview: ImageView
    lateinit var takePhoto: ImageButton
    lateinit var imageBitmap: Bitmap

    lateinit var clothingType: String
    lateinit var photoLocation: String
    lateinit var clothingName: String

    private var fileSet: Boolean = false
    private var nameSet: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        //retrieving clothing type selection from previous activity
        clothingType = intent.getStringExtra("selectedClothingType")


        imagePreview = findViewById(R.id.clothingItem)

        takePhoto = findViewById(R.id.capture_button)

        takePhoto.setOnClickListener {
            dispatchTakePictureIntent()
        }

        val editText: EditText = findViewById(R.id.itemName)



        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                nameSet = true
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })


        val addItemButton: Button = findViewById(R.id.addButton)

        addItemButton.setOnClickListener{
            //getting the text input
            val editableClothingName: Editable = editText.text
            clothingName = editableClothingName.toString()

            if (fileSet && nameSet ){
                Log.d("success", "type: $clothingType, location: $photoLocation, name: $clothingName")
                val insertPhotoIntent = Intent()
//                insertPhotoIntent.putExtra("name", clothingName)
//                insertPhotoIntent.putExtra("location", photoLocation)
//                insertPhotoIntent.putExtra("type", clothingType)

                val array = arrayListOf<String>()
                array.addAll(listOf(clothingName, clothingType, photoLocation))
                insertPhotoIntent.putExtra("listOfValues", array)

                setResult(Activity.RESULT_OK, insertPhotoIntent)
                finish()

            } else {
                Toast.makeText(this, "Please enter all of the fields", Toast.LENGTH_SHORT).show()
            }

        }





         //Add this at the end of onCreate function

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
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Log.d("check2", "getting into if statement")
//            val imageBitmap = data?.extras?.get("data") as Bitmap
//            imagePreview.setImageBitmap(imageBitmap)
            setImageToHolder()
        }
    }

    lateinit var currentPhotoPath: String

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val fileName: String = clothingType
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${fileName}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            photoLocation = absolutePath
            Log.d("check1", photoLocation)
        }
    }

    private fun setImageToHolder() {
        BitmapFactory.decodeFile(photoLocation)?.also { bitmap ->
            imageBitmap = bitmap
        }

        val matrix = Matrix()
        matrix.postRotate(90F)

        //scaling photo to fit size of holder
        val scaledBitmap : Bitmap = Bitmap.createScaledBitmap(imageBitmap, 640, 640, true)
        //rotating image to be upright
        val rotatedImage: Bitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, 640, 640, matrix, true)

        imagePreview.setImageBitmap(rotatedImage)
        fileSet = true
    }



}
