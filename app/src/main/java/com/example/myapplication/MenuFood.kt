package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_menu_food.*

class MenuFood : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_food)
        showDataUser() // show data from Login Activity
        carouselView() // carousel
    }
    fun showDataUser(){
        // This is Data of User when they login in and come to this activity
        val i = intent
        var token:String = i.getStringExtra("token").toString()
        var fullname:String = i.getStringExtra("fullname").toString()
        var id:String = i.getStringExtra("id").toString()
        var roleId:String = i.getStringExtra("roleId").toString()

        //set data
        userName.setText("Xin chào " + fullname + "!")
        if(roleId=="1") roleName.setText("Admin")
        else roleName.setText("User")

    }

    fun carouselView(){
        var slide:IntArray = intArrayOf(
            R.drawable.slide1,
            R.drawable.slide2,
            R.drawable.slide3
        )
        carouselView.pageCount = 3;
        carouselView.setImageListener { position, imageView ->
            imageView.setImageResource(slide[position])
        }
    }
}