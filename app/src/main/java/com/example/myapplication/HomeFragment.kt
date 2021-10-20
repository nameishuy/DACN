package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.nav_header_menu.view.*
import kotlin.math.log

class HomeFragment : Fragment() {

    val glbl = Global()
    var token:String?=null
    var id:String?=null
    var roleId:String?=null
    var fullname:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getData()
        Log.e("state","onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        Log.e("state","onCreateView")
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("state","onViewCreated")
        if(savedInstanceState==null){
            getData()
            //set Textview
            userName.text="Chào " + glbl.fullname
            if(glbl.roleId.toString() == "1") {
                roleName.text="Admin"
            }else if(glbl.roleId.toString() == "2") {
                roleName.text = "User"
            }
        }else{
            Log.e("data",savedInstanceState.getString("fullname").toString())
            userName.text="Chào " + savedInstanceState.getString("fullname")
            if(savedInstanceState.getString("roleId") == "1") {
                roleName.text="Admin"
            }else if(savedInstanceState.getString("roleId") == "2") {
                roleName.text = "User"
            }
        }
        carouselView()

    }



    override fun onResume() {
        super.onResume()
        getData()
        //set Textview
        userName.text="Chào " + glbl.fullname
        if(glbl.roleId.toString() == "1") {
            roleName.text="Admin"
        }else if(glbl.roleId.toString() == "2") {
            roleName.text = "User"
        }
        Log.e("state","onResume")
    }



    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("token",glbl.token)
        outState.putString("id",glbl.id)
        outState.putString("fullname",glbl.fullname)
        outState.putString("roleId",glbl.roleId)
        Log.e("state","onSaveInstanceState")
    }

    fun getData(){
        val bundle = arguments
        if(bundle!!.getString("token") != null){
            glbl.token = bundle!!.getString("token")
            Log.e("data",glbl.token.toString())
        }
        if(bundle!!.getString("fullname") != null){
            glbl.fullname = bundle!!.getString("fullname")
            Log.e("data",glbl.fullname.toString())
        }
        if(bundle!!.getString("id") != null){
            glbl.id = bundle!!.getString("id")
            Log.e("data",glbl.id.toString())
        }
        if(bundle!!.getString("roleId") != null){
            glbl.roleId = bundle!!.getString("roleId")
            Log.e("data",glbl.roleId.toString())
        }
    }

    fun carouselView(){
        var slide:IntArray = intArrayOf(
            R.drawable.slide1,
            R.drawable.slide2,
            R.drawable.slide3
        )

        //for this problem, follow this link:
        //https://github.com/sayyam/carouselview/issues/43
        carouselView.setImageListener { position, imageView ->
            imageView.setImageResource(slide[position])
        }
        carouselView.pageCount = slide.size
    }
}