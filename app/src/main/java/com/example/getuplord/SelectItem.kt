package com.example.getuplord

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * list view for selecting clothing items
 */
class SelectItem : AppCompatActivity() {

    private lateinit var photoViewModel: PhotoViewModel

    lateinit var recyclerView: RecyclerView

    lateinit var wordsToObserve: String

    //creating recyclerView adapter including what action to take if clothing item in list is clicked
    private var photoList : MutableList<Photo> = mutableListOf()
        set(value) {
            field = value
            val adapter = PhotoListAdapter(this, field) {
                //sending filepath back to outfit builder
                val intent = Intent()
                intent.putExtra("photoFilePath", it)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
            recyclerView.adapter = adapter
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_item)

        //initialising access to database to retrieve clothing items from
        photoViewModel = ViewModelProvider(this).get(PhotoViewModel::class.java)

        recyclerView = findViewById(R.id.recyclerview)


        recyclerView.layoutManager = LinearLayoutManager(this)

        //make this be the intent passed through
        wordsToObserve = intent.getStringExtra("clothingItem")


        //choose what recycler view (list) shows depending on what is passed through from previous activity
        when(wordsToObserve){
            "headwear" -> {
                photoViewModel.headwearPhotos.observe(this, Observer { photos ->
                    // Update the cached copy of the words in the adapter.
                    photoList = photos.toMutableList()
                })
        }
            "shirt" -> {
                photoViewModel.shirtPhotos.observe(this, Observer { photos ->
                    // Update the cached copy of the words in the adapter.
                    photoList = photos.toMutableList()
                })
            }
            "coat" -> {
                photoViewModel.coatPhotos.observe(this, Observer { photos ->
                    // Update the cached copy of the words in the adapter.
                    photoList = photos.toMutableList()
                })
            }
            "pants" -> {
                photoViewModel.pantsPhotos.observe(this, Observer { photos ->
                    // Update the cached copy of the words in the adapter.
                    photoList = photos.toMutableList()
                })
            }
            "socks" -> {
                photoViewModel.socksPhotos.observe(this, Observer { photos ->
                    // Update the cached copy of the words in the adapter.
                    photoList = photos.toMutableList()
                })
            }
            "shoes" -> {
                photoViewModel.shoesPhotos.observe(this, Observer { photos ->
                    // Update the cached copy of the words in the adapter.
                    photoList = photos.toMutableList()
                })
            }
            else -> {
                Log.d("error1", "wrong sign passed through to recyclerView")
            }
        }

    }
}
