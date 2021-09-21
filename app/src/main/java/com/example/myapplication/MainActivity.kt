package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread
// @Huy_Commit
//This is the splash layout
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Set Animation for Layout Start.
        var myanim:Animation = AnimationUtils.loadAnimation(this,R.anim.mytransition);
        tvName.startAnimation(myanim)
        ivLogo.startAnimation(myanim)

        //Declare intent for change from Layout Start to Layout Login.
        val i:Intent = Intent(this,Login::class.java)
        //Set timer
        val splashScreenTimeOut = 4000
        Handler().postDelayed({
            startActivity(i)
            finish()
        },splashScreenTimeOut.toLong())
    }
}