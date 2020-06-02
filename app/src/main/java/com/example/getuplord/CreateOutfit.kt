package com.example.getuplord

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast

class CreateOutfit : AppCompatActivity() {

    private val headwearCode: Int = 1
    private val shirtCode: Int = 2
    private val coatCode: Int = 3
    private val pantsCode: Int = 4
    private val socksCode: Int = 5
    private val shoesCode: Int = 6

    lateinit var imagePreview: ImageView
    lateinit var imageBitmap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_outfit)

        val headwearButton: Button = findViewById(R.id.headwearButton)
        val shirtButton: Button = findViewById(R.id.shirtButton)
        val coatButton: Button = findViewById(R.id.coatButton)
        val pantsButton: Button = findViewById(R.id.pantsButton)
        val socksButton: Button = findViewById(R.id.sockButton)
        val shoesButton: Button = findViewById(R.id.shoesButton)

        headwearButton.setOnClickListener{
            val intent = Intent(this, SelectItem::class.java)
            intent.putExtra("clothingItem", "headwear")
            startActivityForResult(intent, headwearCode)
        }

        shirtButton.setOnClickListener{
            val intent = Intent(this, SelectItem::class.java)
            intent.putExtra("clothingItem", "shirt")
            startActivityForResult(intent, shirtCode)
        }

        coatButton.setOnClickListener{
            val intent = Intent(this, SelectItem::class.java)
            intent.putExtra("clothingItem", "coat")
            startActivityForResult(intent, coatCode)
        }

        pantsButton.setOnClickListener{
            val intent = Intent(this, SelectItem::class.java)
            intent.putExtra("clothingItem", "pants")
            startActivityForResult(intent, pantsCode)
        }

        socksButton.setOnClickListener{
            val intent = Intent(this, SelectItem::class.java)
            intent.putExtra("clothingItem", "socks")
            startActivityForResult(intent, socksCode)
        }

        shoesButton.setOnClickListener{
            val intent = Intent(this, SelectItem::class.java)
            intent.putExtra("clothingItem", "shoes")
            startActivityForResult(intent, shoesCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == headwearCode && resultCode == Activity.RESULT_OK) {
            imagePreview = findViewById(R.id.headwear)

        } else if (requestCode == shirtCode && resultCode == Activity.RESULT_OK) {
            imagePreview = findViewById(R.id.shirt)

        } else if (requestCode == coatCode && resultCode == Activity.RESULT_OK) {
            imagePreview = findViewById(R.id.coat)

        } else if (requestCode == pantsCode && resultCode == Activity.RESULT_OK) {
            imagePreview = findViewById(R.id.pants)

        } else if (requestCode == socksCode && resultCode == Activity.RESULT_OK) {
            imagePreview = findViewById(R.id.socks)

        } else if (requestCode == shoesCode && resultCode == Activity.RESULT_OK) {
            imagePreview = findViewById(R.id.shoes)

        }
        else {
            Toast.makeText(
                applicationContext,
                "photo selection failed",
                Toast.LENGTH_LONG).show()
        }

        data?.getStringExtra("photoFilePath")?.let {
            val photoFilePath: String = it
            setImageToHolder(photoFilePath, imagePreview)
            Toast.makeText(
                applicationContext,
                "photo set!",
                Toast.LENGTH_LONG).show()
        }
    }

    private fun setImageToHolder(imageFilePath: String, imagePreview: ImageView) {
        Log.d("check2", "$imageFilePath")
        BitmapFactory.decodeFile(imageFilePath)?.also { bitmap ->
            imageBitmap = bitmap
        }

        val matrix = Matrix()
        matrix.postRotate(90F)

        //scaling photo to fit size of holder
        val scaledBitmap : Bitmap = Bitmap.createScaledBitmap(imageBitmap, 170, 170, true)
        //rotating image to be upright
        val rotatedImage: Bitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, 170, 170, matrix, true)

        imagePreview.setImageBitmap(rotatedImage)
    }
}
