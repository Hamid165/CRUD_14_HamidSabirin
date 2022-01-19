package com.hamid.example.crud_hamidsabirin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class Dashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val btn_tombol = findViewById<ImageView>(R.id.imageView6)
        btn_tombol.setOnClickListener {
            val intentges = Intent(this, MainActivity::class.java)
            startActivity(intentges)
        }
    }
}