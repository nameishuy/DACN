package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_register.*

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //Set event click back to Login Layout
        btnBack.setOnClickListener{
            val i:Intent= Intent(this,Login::class.java)
            startActivity(i)
            finish()
        }
    }
}