package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.api.API
import com.example.myapplication.api.Retro
import com.example.myapplication.model.ListFood.FoodResponse
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_food.*
import retrofit2.Call
import retrofit2.Response


class FoodFragment : Fragment() {

    var idUser:Int?=null
    var idFood:Int?=null
    var nameFoodType:String?=null
    var flag:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        val retro = Retro().getRetroClientInstance().create(API::class.java)
        var food:Call<FoodResponse> = retro.getDetail(idFood)
        food.enqueue(object :retrofit2.Callback<FoodResponse>{
            override fun onResponse(call: Call<FoodResponse>, response: Response<FoodResponse>) {
                val data = response.body()
                Picasso.get().load(data.data!!.infoFood!!.img.toString()).into(imgFood)
                nameFood.text = data.data!!.infoFood!!.name.toString()
                description.text = data.data!!.infoFood!!.description
                titleFavoriteButton.text = "Thích (" + data.data!!.infoFood!!.totallike!! + ")"

                // set event click of like button
                favoriteButton.setOnClickListener {
                    if(flag == false){
                        favoriteIcon.setColorFilter(resources.getColor(R.color.red))
                        titleFavoriteButton.text = "Đã Thích (" + (data.data!!.infoFood!!.totallike!!+1) + ")"
                        titleFavoriteButton.setTextColor(resources.getColor(R.color.red))
                        flag = true
                    }else{
                        favoriteIcon.setColorFilter(null)
                        titleFavoriteButton.text = "Thích (" + (data.data!!.infoFood!!.totallike!! - 1) + ")"
                                titleFavoriteButton.setTextColor(resources.getColor(R.color.black))
                        flag = false
                    }
                }
            }

            override fun onFailure(call: Call<FoodResponse>, t: Throwable) {

            }
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FoodFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FoodFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    fun getData(){
        val bundle = arguments
        idUser = bundle?.getString("idUser")!!.toIntOrNull()
        Log.e("data2",idUser.toString())
        idFood = bundle?.getString("idFood")!!.toIntOrNull()
        Log.e("data2",idFood.toString())
        nameFoodType = bundle?.getString("typeFood")
        typeFood.text = nameFoodType
    }
}