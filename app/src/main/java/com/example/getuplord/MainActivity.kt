package com.example.getuplord

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val addItemButton: Button = findViewById(R.id.addItemButton)
        val preferencesButton: Button = findViewById(R.id.button3)

        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)

        addItemButton.setOnClickListener {
            val intent = Intent(this, addItem::class.java)
            startActivity(intent)
        }

        preferencesButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }
}
