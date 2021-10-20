package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import kotlinx.android.synthetic.main.activity_menu_food.*

class MenuFood : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_food)
        initBottomNavigation() // init bottom navigation
        getDataUser() // get Data User from Login Activity
    }

    fun getDataUser(){
        // This is Data of User when they login in and come to this activity
        val i = intent
        var token:String = i.getStringExtra("token").toString()
        var fullname:String = i.getStringExtra("fullname").toString()
        var id:String = i.getStringExtra("id").toString()
        var roleId:String = i.getStringExtra("roleId").toString()

        // Send to Home Fragment
        val bundle = Bundle()
        bundle.putString("token",token)
        bundle.putString("fullname",fullname)
        bundle.putString("id",id)
        bundle.putString("roleId",roleId)
        val fragment = HomeFragment()
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment).commit()
    }

    fun initBottomNavigation(){
        addFragment(HomeFragment.newInstance())
        bottomNavigation.show(1)
        bottomNavigation.add(MeowBottomNavigation.Model(0,R.drawable.ic_user))
        bottomNavigation.add(MeowBottomNavigation.Model(1,R.drawable.ic_home))
        bottomNavigation.add(MeowBottomNavigation.Model(2,R.drawable.ic_favorite))

        //set event click listener
        bottomNavigation.setOnClickMenuListener {
            when(it.id){
                0 -> {
                    addFragment(UserFragment.newInstance())
                }

                1 -> {
                    addFragment(HomeFragment.newInstance())
                }

                else -> {
                    addFragment(FavoriteFragment.newInstance())
                }
            }
        }
    }

    private fun addFragment(fragment:Fragment){
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.fragmentContainer,fragment).addToBackStack(Fragment::class.java.simpleName).commit()
    }
}