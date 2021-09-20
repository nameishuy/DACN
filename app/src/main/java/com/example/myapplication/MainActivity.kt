package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Set Animation for Layout Start
        var myanim:Animation = AnimationUtils.loadAnimation(this,R.anim.mytransition);
        tvName.startAnimation(myanim)
        ivLogo.startAnimation(myanim)
    }
}