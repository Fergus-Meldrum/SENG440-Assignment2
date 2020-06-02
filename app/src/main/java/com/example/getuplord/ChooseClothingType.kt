package com.example.getuplord

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*

class ChooseClothingType : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    lateinit var selectedType: String

    //request code for result activity
    private val newWordActivityRequestCode2 = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_clothing_type)

        val typeSpinner: Spinner = findViewById(R.id.types_spinner)
        typeSpinner.onItemSelectedListener = this
        ArrayAdapter.createFromResource(
            this,
            R.array.clothing_types,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            typeSpinner.adapter = adapter
        }

        //initializing next button
        val nextButton : Button = findViewById(R.id.nextButton)

        nextButton.setOnClickListener{
            val intent = Intent(this, addItem::class.java)
            intent.putExtra("selectedClothingType", selectedType)
            // make this for result later
            startActivityForResult(intent, newWordActivityRequestCode2, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }

    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        selectedType = parent.getItemAtPosition(pos).toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
        Log.d("problem", "problem selecting clothing type")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newWordActivityRequestCode2 && resultCode == Activity.RESULT_OK){
            setResult(Activity.RESULT_OK, data)
            finish()
        } else {
            //could change this to finish with a bad result code
            Toast.makeText(
                applicationContext,
                "photo not saved properly!",
                Toast.LENGTH_LONG).show()
        }
    }

}
