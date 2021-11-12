package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.api.API
import com.example.myapplication.api.Retro
import com.example.myapplication.model.ListFood.FoodTypeResponse
import com.example.myapplication.RecyclerViewAdapter.RecyclerViewAdapterFoodTypes
import com.example.myapplication.model.User.UserInfoResponse
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.nav_header_menu.view.*
import retrofit2.Call
import retrofit2.Response

class HomeFragment : Fragment() {

    val glbl = Global()
    var id:String?=null
    private var layoutManager :RecyclerView.LayoutManager?=null
    private var adapter :RecyclerView.Adapter<RecyclerViewAdapterFoodTypes.ViewHolder>?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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
        getData()

        //Call API to get Info User
        val retro = Retro().getRetroClientInstance().create(API::class.java)
        var infoAccount:Call<UserInfoResponse> = retro.getUserInfo(glbl.id!!.toInt())
        infoAccount.enqueue(object :retrofit2.Callback<UserInfoResponse>{
            override fun onResponse(
                call: Call<UserInfoResponse>,
                response: Response<UserInfoResponse>
            ) {
                //set Textview
                userName.text="Chào " + response.body().data!!.fullName
                if(glbl.roleId.toString() == "1") {
                    roleName.text="Admin"
                }else if(glbl.roleId.toString() == "2") {
                    roleName.text = "User"
                }
                if(response.body().data!!.imageUser == null){
                    avatarUser.setImageResource(R.drawable.logo)
                }else{
                    Picasso.get().load(response.body().data!!.imageUser).into(avatarUser)
                }
            }

            override fun onFailure(call: Call<UserInfoResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
        carouselView()

        //He is my Savior !!!
        //Link: https://www.youtube.com/watch?v=FiqiIJNALFs
        getAPI() //get data list food types to recycler view
    }


    fun getData(){
        val bundle = arguments

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

    fun getAPI(){
        //Call API List Food Types
        val retro = Retro().getRetroClientInstance().create(API::class.java)
        retro.getData().enqueue(object : retrofit2.Callback<FoodTypeResponse>{
            override fun onResponse(
                call: Call<FoodTypeResponse>,
                response: Response<FoodTypeResponse>
            ) {
                layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
                recyclerViewFoodTypes.layoutManager = layoutManager
                adapter = RecyclerViewAdapterFoodTypes(response.body().listFoodTypes!!,glbl)
                recyclerViewFoodTypes.adapter = adapter
            }

            override fun onFailure(call: Call<FoodTypeResponse>, t: Throwable) {
                Log.e("Error", t.message!!)
            }
        })
    }
}