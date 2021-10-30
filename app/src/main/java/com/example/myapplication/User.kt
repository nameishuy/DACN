package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment

class User : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        initActions()
    }
    fun initActions(){
        addFragment(UserUpdateFragment.newInstance())
    }

    private fun addFragment(fragment: Fragment){
        // This is Data of User when they login in and come to this activity
        val i = intent
        var id:String = i.getStringExtra("TransId").toString()
        Log.e("Trans ID",""+id)
        var roleId:String = i.getStringExtra("TransRole").toString()
        Log.e("Trans Role",""+roleId)
        var fullname:String = i.getStringExtra("TransFullName").toString()
        Log.e("Trans Full Name",""+fullname)
        var phoneNum:String = i.getStringExtra("TransPhoneNum").toString()
        Log.e("Trans Full Phone Num",""+phoneNum)
        var birthDay:String = i.getStringExtra("TransBirthDay").toString()
        Log.e("Trans Full Birth Day",""+birthDay)
        var region:String = i.getStringExtra("TransRegion").toString()
        Log.e("Trans Full Region",""+region)
        var image:String = i.getStringExtra("TransImage").toString()
        Log.e("Trans Full Image",""+image)
        // Send to Fragment
        val bundle = Bundle()
        bundle.putString("idUpUser",id)
        bundle.putString("roleUpIdUser",roleId)
        bundle.putString("fullNameUpUser",fullname)
        bundle.putString("phoneNumUpUser",phoneNum)
        bundle.putString("birthDayUpUser",birthDay)
        bundle.putString("regionUpUser",region)
        bundle.putString("imageUpUser",image)

        fragment.arguments = bundle
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.containerUser,fragment).addToBackStack(Fragment::class.java.simpleName).commit()
    }
}