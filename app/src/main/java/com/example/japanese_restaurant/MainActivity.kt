package com.example.japanese_restaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val image_ramen = findViewById<ImageView>(R.id.ramen)
        image_ramen.setOnClickListener {
            Toast.makeText(this, "1번 클릭 완료", Toast.LENGTH_LONG)
        }

    }
}