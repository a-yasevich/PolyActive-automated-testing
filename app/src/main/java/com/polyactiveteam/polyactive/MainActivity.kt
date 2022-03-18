package com.polyactiveteam.polyactive

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val toast = Toast(applicationContext)
        toast.setText("Application started successful!")
        toast.show()
    }
}