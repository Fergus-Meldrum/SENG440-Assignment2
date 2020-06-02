package com.example.getuplord

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner

class ChooseClothingType : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    lateinit var selectedType: String

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
            startActivity(intent)
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
}
