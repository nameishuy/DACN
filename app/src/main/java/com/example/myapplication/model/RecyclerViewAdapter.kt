package com.example.myapplication.model

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Global
import com.example.myapplication.R
import com.example.myapplication.api.API
import com.example.myapplication.api.Retro
import retrofit2.Call
import retrofit2.Response

class RecyclerViewAdapter:RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    val global=Global()
    //Cause didn't have image yet, so i'll use some image which added in project.
    private val img = intArrayOf(R.drawable.beef,R.drawable.chicken_leg
        ,R.drawable.ham,R.drawable.fish,R.drawable.desert,R.drawable.cupcake)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_foodtype,parent,false)
        return ViewHolder(v)
    }

        override fun onBindViewHolder(holder: RecyclerViewAdapter.ViewHolder, @SuppressLint("RecyclerView") position: Int) {
            //Call API List Food Types
            val retro = Retro().getRetroClientInstance().create(API::class.java)
            retro.getData().enqueue(object : retrofit2.Callback<FoodTypeResponse>{
                override fun onResponse(
                    call: Call<FoodTypeResponse>,
                    response: Response<FoodTypeResponse>
                ) {
                    val data = response.body()
                    global.listFoodTypes = data.listFoodTypes
                    for(item:FoodTypeResponse.FoodType in data.listFoodTypes!!){
                        global.size+1
                    }
                    holder.itemTitle.text = global.listFoodTypes!![position].name!!.toString()
                }

                override fun onFailure(call: Call<FoodTypeResponse>, t: Throwable) {
                    Log.e("Error", t.message!!)
                }
            })
            holder.itemImage.setImageResource(img[position])
        }

        override fun getItemCount(): Int {
            return 6
        }

    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var itemImage:ImageView
        var itemTitle:TextView
        init {
            itemImage=itemView.findViewById(R.id.iconFoodType)
            itemTitle=itemView.findViewById(R.id.nameFoodType)
        }
    }

}