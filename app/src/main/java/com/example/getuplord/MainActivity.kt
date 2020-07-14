package com.example.getuplord

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.lifecycle.ViewModelProvider
import com.example.getuplord.R.id.toolbar

class MainActivity : AppCompatActivity() {

    private val newPhotoActivityRequestCode = 1

    private lateinit var photoViewModel: PhotoViewModel

    /**
     * Landing page
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initialising the viewModel
        photoViewModel = ViewModelProvider(this).get(PhotoViewModel::class.java)

        val addItemButton: Button = findViewById(R.id.addItemButton)

        //click listener for button to add an item
        addItemButton.setOnClickListener {
            val intent = Intent(this, ChooseClothingType::class.java)
            startActivityForResult(intent, newPhotoActivityRequestCode, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }

        //button for creating an outfit
        val createOutfitButton: Button = findViewById(R.id.createOutfitButton)
        createOutfitButton.setOnClickListener {
            val intent = Intent(this, CreateOutfit::class.java)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }

        //setting action bar on home landing page
        setSupportActionBar(findViewById(toolbar))
    }

    /**
     * sets options for hamburger menu in top right of landing page
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu_actions, menu)
        return true
    }

    /**
     * determines what do do if user clicks on an option from the hamburger menu
      */
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_settings -> {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    /**
     * adds new photo to database if result code is satisfied
     * else displays error message
     * requestCode - the code given (newPhotoActivityRequestCode) when add item activity is started
     * data - the data sent back after add new item activity finishes
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newPhotoActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringArrayListExtra("listOfValues")?.let {
                val newPhotoName = it[0].toString()
                val newPhotoType = it[1].toString()
                val newPhotoLocation = it[2].toString()
                Log.d("check400", "$newPhotoName, $newPhotoType, $newPhotoLocation")
                val photo = Photo(newPhotoName, newPhotoType, newPhotoLocation)
                photoViewModel.insert(photo)
                Toast.makeText(
                    applicationContext,
                    "photo saved!",
                    Toast.LENGTH_LONG).show()
            }

        } else {
            Toast.makeText(
                applicationContext,
                "Photo not saved!",
                Toast.LENGTH_LONG).show()
        }
    }
}
