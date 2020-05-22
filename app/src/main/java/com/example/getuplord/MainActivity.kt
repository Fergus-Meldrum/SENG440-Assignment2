package com.example.getuplord

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val addItemButton: Button = findViewById(R.id.addItemButton)

        addItemButton.setOnClickListener {
            val intent = Intent(this, addItem::class.java)
            startActivity(intent)
        }
    }
}
