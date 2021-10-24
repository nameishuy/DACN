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
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Response
import kotlin.math.log

class RecyclerViewAdapter(private val size:Int,private val list:List<FoodTypeResponse.FoodType>) :RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_foodtype,parent,false)
        return ViewHolder(v)
    }

        override fun onBindViewHolder(holder: RecyclerViewAdapter.ViewHolder, @SuppressLint("RecyclerView") position: Int) {
            Picasso.get().load(list[position].image).into(holder.itemImage)
            holder.itemTitle.text = list[position].name
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