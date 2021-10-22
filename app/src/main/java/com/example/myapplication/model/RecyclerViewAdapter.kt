package com.example.myapplication.model

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Global
import com.example.myapplication.R
import com.example.myapplication.api.API
import com.example.myapplication.api.Retro
import retrofit2.Call
import retrofit2.Response
import kotlin.math.log

class RecyclerViewAdapter(private val size:Int,private val list:List<FoodTypeResponse.FoodType>) :RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

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
            holder.itemTitle.text = list[position].name
            holder.itemImage.setImageResource(img[position])
        }

        override fun getItemCount() = size

    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var itemImage:ImageView
        var itemTitle:TextView
        init {
            itemImage=itemView.findViewById(R.id.iconFoodType)
            itemTitle=itemView.findViewById(R.id.nameFoodType)
        }

    }

}