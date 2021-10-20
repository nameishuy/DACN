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
        val bundle = arguments
        var token:String = bundle!!.get("token").toString()
        //var fullname:String = data!!.getString("fullname") Lỗi ở chỗ này, do Log nó ra tận 2 cái String 1 cái là null và 1 cái là dữ liệu đúng của nó
        // Vì là 2 String nên khó lưu theo kiểu String
        Log.e("token",token)

    }

    override fun onResume() {
        super.onResume()
        carouselView()
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