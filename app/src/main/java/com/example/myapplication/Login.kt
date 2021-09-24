package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_login.*
import com.klinker.android.link_builder.LinkBuilder




class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        // Change Register Layout
        register.setOnClickListener {
            val i: Intent = Intent(this, Register::class.java)
            startActivity(i)
            finish()
        }
    }
}