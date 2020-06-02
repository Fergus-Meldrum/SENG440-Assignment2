package com.example.getuplord

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class CreateOutfit : AppCompatActivity() {

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
            startActivity(intent)
        }

        shirtButton.setOnClickListener{
            val intent = Intent(this, SelectItem::class.java)
            intent.putExtra("clothingItem", "shirt")
            startActivity(intent)
        }

        coatButton.setOnClickListener{
            val intent = Intent(this, SelectItem::class.java)
            intent.putExtra("clothingItem", "coat")
            startActivity(intent)
        }

        pantsButton.setOnClickListener{
            val intent = Intent(this, SelectItem::class.java)
            intent.putExtra("clothingItem", "pants")
            startActivity(intent)
        }

        socksButton.setOnClickListener{
            val intent = Intent(this, SelectItem::class.java)
            intent.putExtra("clothingItem", "socks")
            startActivity(intent)
        }

        shoesButton.setOnClickListener{
            val intent = Intent(this, SelectItem::class.java)
            intent.putExtra("clothingItem", "shoes")
            startActivity(intent)
        }
    }
}
