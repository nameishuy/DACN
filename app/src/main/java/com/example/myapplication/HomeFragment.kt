package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.nav_header_menu.view.*

class HomeFragment : Fragment() {

    val glbl = Global()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
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
        carouselView()
        getData()

    }

    override fun onResume() {
        super.onResume()
        carouselView()
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

        //set Textview
        if(glbl.id == "1") roleName.text="Admin"
        else roleName.text="User"

        userName.text="Chào " + glbl.fullname
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